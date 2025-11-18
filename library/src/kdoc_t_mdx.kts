#!/usr/bin/env kotlin

import java.io.File
import java.nio.charset.Charset

// =========================================
// Data Models
// =========================================
data class DocItem(
    val name: String,
    val type: ItemType,
    val description: String,
    val usage: String? = null,
    val params: List<Param> = emptyList(),
    val enumValues: List<EnumValue> = emptyList(),
    var parent: String? = null,
    val returnVal: Param? = null
)

enum class ItemType { COMPONENT, ENUM, OBJECT, DATA_CLASS, SEALED_CLASS, FUNCTION, UNKNOWN }

data class Param(val name: String, val type: String, val description: String)
data class EnumValue(val name: String, val description: String)

// =========================================
// Parser Logic
// =========================================
class KDocParser {
    private val paramRegex = Regex("""@param\s+(\w+)\s+([^:]+):\s*(.*)""")
    private val propertyRegex = Regex("""@property\s+(\w+)\s+([^:]+):\s*(.*)""")
    private val enumRowRegex = Regex("""-\s+([^:]+):\s*(.*)""")
    private val returnRegex = Regex("""@return\s+([^:]+):\s*(.*)""")

    fun parse(file: File): List<DocItem> {
        val lines = file.readLines(Charset.forName("UTF-8"))
        val blocks = mutableListOf<List<String>>()
        var currentBlock = mutableListOf<String>()
        var insideKDoc = false

        for (line in lines) {
            val trimmed = line.trim()
            if (!insideKDoc) {
                if (trimmed.startsWith("/**")) {
                    insideKDoc = true
                    currentBlock = mutableListOf()
                    currentBlock.add(line)
                    if (trimmed.endsWith("*/") && !trimmed.contains("/* ", ignoreCase = true)) {
                        if (line.indexOf("*/") > line.indexOf("/**") + 3) {
                            insideKDoc = false
                            blocks.add(currentBlock)
                        }
                    }
                }
            } else {
                currentBlock.add(line)
                if (trimmed == "*/" || trimmed == "**/" || (trimmed.startsWith("*") && trimmed.endsWith("*/") && !trimmed.contains("/*"))) {
                    insideKDoc = false
                    blocks.add(currentBlock)
                }
            }
        }

        val defaultComponentName = "Wanted" + file.nameWithoutExtension.capitalize()
        val docItems = blocks.mapNotNull { parseBlock(it, defaultComponentName) }

        var lastSealedClass: String? = null
        docItems.forEach { item ->
            if (item.type == ItemType.SEALED_CLASS) {
                lastSealedClass = item.name
            } else if ((item.type == ItemType.DATA_CLASS || item.type == ItemType.FUNCTION) && lastSealedClass != null) {
                item.parent = lastSealedClass
            } else if (item.type == ItemType.COMPONENT || item.type == ItemType.ENUM) {
                lastSealedClass = null
            }
        }

        return docItems
    }

    private fun parseBlock(lines: List<String>, defaultComponentName: String): DocItem? {
        val cleanedLines = lines.mapNotNull { line ->
            val trimmed = line.trim()
            when {
                trimmed.startsWith("/**") -> null
                trimmed.startsWith("*/") || trimmed == "**/" -> null
                trimmed.startsWith("*") -> line.substring(line.indexOf('*') + 1).let { if (it.startsWith(" ")) it.substring(1) else it }
                else -> line
            }
        }

        if (cleanedLines.all { it.isBlank() }) return null

        val headerLineIndex = cleanedLines.indexOfFirst { it.isNotBlank() && !it.trim().startsWith("@") }
        val (headerLine, headerIndex) = if (headerLineIndex != -1) {
            cleanedLines[headerLineIndex].trimEnd() to headerLineIndex
        } else {
            val firstLineIndex = cleanedLines.indexOfFirst { it.isNotBlank() }
            if (firstLineIndex != -1) {
                cleanedLines[firstLineIndex].trimEnd() to firstLineIndex
            } else {
                "" to 0
            }
        }

        var name = headerLine
        var type = ItemType.COMPONENT

        when {
            headerLine.startsWith("enum class") -> { name = headerLine.removePrefix("enum class").trim(); type = ItemType.ENUM }
            headerLine.startsWith("sealed class") -> { name = headerLine.removePrefix("sealed class").trim(); type = ItemType.SEALED_CLASS }
            headerLine.startsWith("object") -> { name = headerLine.removePrefix("object").trim(); type = ItemType.OBJECT }
            headerLine.startsWith("data object") -> { name = headerLine.removePrefix("data object").trim(); type = ItemType.DATA_CLASS }
            headerLine.startsWith("data class") -> { name = headerLine.removePrefix("data class").trim(); type = ItemType.DATA_CLASS }
            headerLine.startsWith("class ") -> { name = headerLine.removePrefix("class ").trim(); type = ItemType.DATA_CLASS }
            headerLine.startsWith("interface") -> { name = headerLine.removePrefix("interface").trim(); type = ItemType.OBJECT }
            headerLine.startsWith("fun ") -> { name = headerLine.removePrefix("fun ").substringBefore("(").trim(); type = ItemType.FUNCTION }
            headerLine.startsWith("abstract fun ") -> { name = headerLine.removePrefix("abstract fun ").substringBefore("(").trim(); type = ItemType.FUNCTION }
            headerLine.startsWith("companion object") -> { name = "Companion Object"; type = ItemType.OBJECT }
            headerLine.startsWith("@") -> {
                name = headerLine
                type = ItemType.UNKNOWN
            }
            headerLine.contains(" ") && !headerLine.contains("(") -> {
                name = defaultComponentName
                type = ItemType.COMPONENT
            }
            else -> {
                name = headerLine.substringBefore("(").trim()
                if (name.contains(" ") || name.isEmpty()) {
                    name = defaultComponentName
                }
                type = ItemType.COMPONENT
            }
        }

        val descriptionStartIndex = if (type == ItemType.UNKNOWN || (name == defaultComponentName && headerLine.contains(" "))) headerIndex else headerIndex + 1

        val descriptionStr = cleanedLines.drop(descriptionStartIndex)
            .takeWhile { !it.trim().startsWith("@") && !it.trim().startsWith("```") && !it.trim().startsWith("사용 예시") }
            .filter { if (type == ItemType.ENUM) !it.trim().startsWith("-") else true }
            .filter { it.isNotBlank() }
            .joinToString("\n") { it.trimEnd() }

        val usageStartIndex = cleanedLines.indexOfFirst { it.trim().startsWith("```") }
        val usageEndIndex = cleanedLines.indexOfLast { it.trim().startsWith("```") }
        val usage = if (usageStartIndex != -1 && usageEndIndex != -1 && usageStartIndex < usageEndIndex) {
            cleanedLines.subList(usageStartIndex + 1, usageEndIndex)
                .joinToString("\n")
                .trimEnd()
        } else {
            null
        }

        val params = mutableListOf<Param>()
        val enumValues = mutableListOf<EnumValue>()
        var returnVal: Param? = null

        cleanedLines.forEach { line ->
            val trimmed = line.trim()
            if (type != ItemType.ENUM) {
                (paramRegex.find(trimmed) ?: propertyRegex.find(trimmed))?.destructured?.let { (n, t, d) -> params.add(Param(n, t, d)) }
                returnRegex.find(trimmed)?.destructured?.let { (t, d) -> returnVal = Param("Return", t, d) }
            }
            if (type == ItemType.ENUM && trimmed.startsWith("- ")) {
                enumRowRegex.find(trimmed)?.destructured?.let { (n, d) -> enumValues.add(EnumValue(n, d)) }
            }
        }

        if (type == ItemType.UNKNOWN && params.isEmpty() && returnVal == null) return null

        return DocItem(name, type, descriptionStr, usage, params, enumValues, parent = null, returnVal = returnVal)
    }
}

// =========================================
// Generator Logic
// =========================================
class MdxGenerator {
    fun generate(items: List<DocItem>, fileName: String): String {
        val sb = StringBuilder()
        val seenComponents = mutableSetOf<String>()

        // ★★★ (수정) 메인 컴포넌트 찾는 로직 ★★★
        var mainComponent = items.firstOrNull { it.type == ItemType.COMPONENT && it.usage != null }
        if (mainComponent == null) {
            mainComponent = items.firstOrNull { it.type == ItemType.COMPONENT }
        }
        // (Fallback) COMPONENT가 없으면, 첫 번째 FUNCTION 이나 OBJECT를 메인으로 승격
        if (mainComponent == null) {
            mainComponent = items.firstOrNull {
                (it.type == ItemType.FUNCTION || it.type == ItemType.OBJECT || it.type == ItemType.DATA_CLASS) && it.parent == null
            }
        }
        // ★★★ (수정) 끝 ★★★

        val guessedNameFromFile = "Wanted" + fileName.substringBeforeLast(".").capitalize()

        // Frontmatter 생성 (mainComponent가 없어도 첫 항목 기준)
        val frontmatterItem = mainComponent ?: items.firstOrNull()
        val guessedTitleFromFile = fileName.substringBeforeLast(".").capitalize()

        if (frontmatterItem != null) {
            sb.appendLine("---")

            val title = if (mainComponent != null && !mainComponent.name.contains(" ")) {
                mainComponent.name.removePrefix("Wanted")
            } else {
                guessedTitleFromFile
            }

            val shortDesc = frontmatterItem.description.lines().firstOrNull { it.isNotBlank() }
                ?: (if (frontmatterItem.name.contains(" ") && frontmatterItem.name != guessedNameFromFile) frontmatterItem.name else "Documentation")

            sb.appendLine("title: $title")
            sb.appendLine("description: $shortDesc")
            sb.appendLine("---\n")
        }


        // 1. Main Component (존재할 경우)
        if (mainComponent != null) {
            if (mainComponent.name.length > 30 || mainComponent.name.contains(" ")) {
                sb.appendLine("## $guessedNameFromFile")
                if (mainComponent.description.isBlank()) sb.appendLine("${mainComponent.name}\n")
            } else {
                sb.appendLine("## ${mainComponent.name}")
            }

            if (mainComponent.description.isNotBlank()) sb.appendLine("${mainComponent.description}\n")
            mainComponent.usage?.let {
                sb.appendLine("```kotlin")
                sb.appendLine(it.trimIndent())
                sb.appendLine("```\n")
            }
            if (mainComponent.params.isNotEmpty()) {
                // 메인 섹션은 <details> 없이 바로 출력
                if (mainComponent.type == ItemType.COMPONENT) {
                    sb.appendLine("### Parameters")
                    appendParamTable(sb, mainComponent.params)
                } else {
                    // FUNCTION 등이 메인이 된 경우
                    val tableTitle = if (mainComponent.type == ItemType.FUNCTION) "Parameters" else "Properties"
                    sb.appendLine("### $tableTitle")
                    appendParamTable(sb, mainComponent.params)
                }
            }
            mainComponent.returnVal?.let {
                sb.appendLine("### Return")
                appendReturnTable(sb, it)
            }
            seenComponents.add(mainComponent.name)
            if (mainComponent.name != guessedNameFromFile) seenComponents.add(guessedNameFromFile)
        }

        // 2. Overloads (이름이 같은 항목만)
        var overloadHeaderPrinted = false
        items.filter { (it.type == ItemType.COMPONENT || it.type == ItemType.UNKNOWN) && it != mainComponent }.forEach { item ->
            val isOverload = item.type == ItemType.UNKNOWN || seenComponents.contains(item.name) || item.name == guessedNameFromFile

            if (isOverload) {
                if (!overloadHeaderPrinted) { sb.appendLine("## Overload"); overloadHeaderPrinted = true } else { sb.appendLine("<br />\n") }
                if (item.description.isNotBlank()) sb.appendLine("${item.description}\n")
                item.usage?.let { sb.appendLine("```kotlin"); sb.appendLine(it.trimIndent()); sb.appendLine("```") }

                if (item.params.isNotEmpty()) {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Parameters</summary>\n")
                    sb.appendLine()
                    appendParamTable(sb, item.params, indent = "  ")
                    sb.appendLine("</details>")
                }
                item.returnVal?.let {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Return</summary>")
                    sb.appendLine()
                    appendReturnTable(sb, it, indent = "  ")
                    sb.appendLine("</details>")
                }
            }
        }

        // 3. Advanced Section
        val otherComponents = items.filter {
            it.type == ItemType.COMPONENT && it != mainComponent && !seenComponents.contains(it.name)
        }
        val trueAdvancedItems = items.filter {
            // ★★★ 메인으로 승격된 항목은 여기서 제외 ★★★
            (it.type == ItemType.OBJECT || it.type == ItemType.DATA_CLASS || it.type == ItemType.FUNCTION) && it.parent == null && it != mainComponent
        }

        if (otherComponents.isNotEmpty() || trueAdvancedItems.isNotEmpty()) {
            sb.appendLine("\n## Advanced")

            otherComponents.forEach { item ->
                sb.appendLine("### ${item.name}")
                if (item.description.isNotBlank()) sb.appendLine("${item.description}\n")
                item.usage?.let { sb.appendLine("```kotlin"); sb.appendLine(it.trimIndent()); sb.appendLine("```\n") }

                if (item.params.isNotEmpty()) {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Parameters</summary>")
                    sb.appendLine()
                    appendParamTable(sb, item.params, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }
                item.returnVal?.let {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Return</summary>")
                    sb.appendLine()
                    appendReturnTable(sb, it, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }
                seenComponents.add(item.name)
            }

            trueAdvancedItems.forEach { item ->
                sb.appendLine("### ${item.name}")
                if (item.description.isNotBlank()) sb.appendLine("${item.description}\n")
                item.usage?.let { sb.appendLine("```kotlin"); sb.appendLine(it.trimIndent()); sb.appendLine("```\n") }

                if (item.params.isNotEmpty()) {
                    val tableTitle = if (item.type == ItemType.FUNCTION) "Parameters" else "Properties"
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>$tableTitle</summary>")
                    sb.appendLine()
                    appendParamTable(sb, item.params, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }
                item.returnVal?.let {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Return</summary>")
                    sb.appendLine()
                    appendReturnTable(sb, it, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }
            }
        }

        // 4. Enum Section
        val enums = items.filter { it.type == ItemType.ENUM }
        if (enums.isNotEmpty()) {
            sb.appendLine("\n## Enum")
            enums.forEachIndexed { index, enumItem ->
                if (index > 0) sb.appendLine()
                sb.appendLine("### ${enumItem.name}")
                if (enumItem.enumValues.isNotEmpty()) {
                    sb.appendLine("| 값 | 설명 |"); sb.appendLine("|:---|:---|")
                    enumItem.enumValues.forEach { sb.appendLine("| ${it.name} | ${it.description} |") }
                }
            }
        }

        // 5. Sealed Class Section
        val sealedClasses = items.filter { it.type == ItemType.SEALED_CLASS }
        if (sealedClasses.isNotEmpty()) {
            sb.appendLine("\n## Sealed Class")
            sealedClasses.forEachIndexed { index, sealedItem ->
                if (index > 0) sb.appendLine()
                sb.appendLine("### ${sealedItem.name}")

                if (sealedItem.description.isNotBlank()) sb.appendLine("${sealedItem.description}\n")

                val children = items.filter { it.parent == sealedItem.name && (it.type == ItemType.DATA_CLASS) }
                if (children.isNotEmpty()) {
                    sb.appendLine("| 값 | 설명 |")
                    sb.appendLine("|:---|:---|")
                    children.forEach { child ->
                        sb.appendLine("| ${child.name} | ${child.description.replace("\n", " ")} |")
                    }
                    sb.appendLine()
                }

                if (sealedItem.params.isNotEmpty()) {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Properties</summary>")
                    sb.appendLine()
                    appendParamTable(sb, sealedItem.params, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }

                val functions = items.filter { it.parent == sealedItem.name && it.type == ItemType.FUNCTION }
                if (functions.isNotEmpty()) {
                    functions.forEach { func ->
                        sb.appendLine("#### ${func.name}")
                        if(func.description.isNotBlank()) sb.appendLine("${func.description}\n")
                        func.returnVal?.let {
                            sb.appendLine("<details>")
                            sb.appendLine("  <summary>Return</summary>")
                            sb.appendLine()
                            appendReturnTable(sb, it, indent = "  ")
                            sb.appendLine("</details><br />\n")
                        }
                    }
                }

                sealedItem.returnVal?.let {
                    sb.appendLine("<details>")
                    sb.appendLine("  <summary>Return</summary>")
                    sb.appendLine()
                    appendReturnTable(sb, it, indent = "  ")
                    sb.appendLine("</details>")
                    sb.appendLine("<br />")
                }
            }
        }

        return sb.toString().trim()
    }

    private fun appendParamTable(sb: StringBuilder, params: List<Param>, indent: String = "") {
        sb.appendLine("$indent| 이름 | 타입 | 설명 |")
        sb.appendLine("$indent|:---|:---|:---|")
        params.forEach {
            val type = it.type.replace("|", "\\|")
            val desc = it.description.replace("|", "\\|")
            sb.appendLine("$indent| ${it.name} | $type | $desc |")
        }
        sb.appendLine()
    }

    private fun appendReturnTable(sb: StringBuilder, returnVal: Param, indent: String = "") {
        sb.appendLine("$indent| 타입 | 설명 |")
        sb.appendLine("$indent|:---|:---|")
        val type = returnVal.type.replace("|", "\\|")
        val desc = returnVal.description.replace("|", "\\|")
        sb.appendLine("$indent| $type | $desc |")
        sb.appendLine()
    }
}

// =========================================
// Main Execution
// =========================================
if (args.size < 2) { println("Usage: kotlin kdoc_to_mdx.main.kts <input.md> <output.mdx>"); System.exit(1) }
val inputFile = File(args[0]); val outputFile = File(args[1])
if (!inputFile.exists()) { println("Error: Input file not found: ${inputFile.absolutePath}"); System.exit(1) }
try {
    val docItems = KDocParser().parse(inputFile)
    val mdxContent = MdxGenerator().generate(docItems, inputFile.name)
    outputFile.writeText(mdxContent, Charset.forName("UTF-8"))
} catch (e: Exception) { println("Error: ${e.message}"); e.printStackTrace(); System.exit(1) }
fun String.capitalize(): String = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }