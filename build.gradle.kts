import extension.default
import extension.defaultKotlinOptions

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.kotlin.plugin.compose)
    id(libs.plugins.android.compose.get().pluginId)
}

android {
    default()

    kotlinOptions {
        defaultKotlinOptions()
    }

    buildFeatures.run {
        viewBinding = true
    }

    buildTypes.run {
        getByName(AppContract.BuildType.Release.type) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    namespace = "com.wanted.android.designsystem"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.legacy.supportV4)

    implementation(libs.androidx.vectordrawable)

    implementation(libs.androidx.constraintlayout)

    implementation(libs.lottie.compose)

    implementation(libs.google.material)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.glide.compose)
}

val extractDesignSystemJavadocToMd by tasks.registering {
    group = "documentation"
    description = "Extract Javadoc/KDoc from com.wanted.android.wanted.design to Markdown"

    // 1) 소스 디렉터리 목록 (Java/Kotlin)
    val srcDirs = listOf(
        file("src/main/java"),
        file("src/main/kotlin")
    ).filter { it.exists() }

    // 2) 출력 디렉토리
    val destDirProvider = layout.buildDirectory.dir("docs/designsystem-javadoc-md")

    // 3) 입력·출력 선언 (스프레드 연산자로 vararg 처리)
    srcDirs.forEach { inputs.dir(it) }
    outputs.dir(destDirProvider)

    doLast {
        srcDirs.forEach { srcDir ->
            println("▶ Scanning: ${srcDir.absolutePath}")
            srcDir.walkTopDown()
                .filter { it.isFile && (it.extension == "java" || it.extension == "kt") }
                .filter { f ->
                    f.relativeTo(srcDir)
                        .invariantSeparatorsPath
                        .startsWith("com/wanted/android/")
                }
                .forEach { srcFile ->
                    val lines = srcFile.readLines()
                    var inDocBlock = false
                    var inFence = false       // ```kotlin``` 펜스 안인가?
                    val sb = StringBuilder()
                    val blocks = mutableListOf<String>()

                    lines.forEach { raw ->
                        val line = raw

                        // ``` 로 시작하면 코드 펜스 상태 토글
                        if (inDocBlock && line.trim().contains("```")) {
                            inFence = !inFence
                        }

                        if (!inDocBlock) {
                            // Javadoc 또는 일반 block comment 시작
                            if (line.contains("/**") || line.contains("/*")) {
                                inDocBlock = true
                                sb.clear()
                                sb.appendLine(line.trim())
                                // 펜스 바로 진입 체크
                                if (line.trim().startsWith("```")) inFence = true
                                // 같은 라인에 닫힘도 있는지?
                                if (!inFence && line.contains("*/")) {
                                    blocks += sb.toString().trim()
                                    inDocBlock = false
                                }
                            }
                        } else {
                            // 이미 Javadoc 블록 안: 무조건 추가
                            sb.appendLine(line.trim())
                            // 펜스 밖에서만 종료로 인식
                            if (!inFence && line.contains("*/")) {
                                blocks += sb.toString().trim()
                                inDocBlock = false
                                inFence = false
                            }
                        }
                    }

                    if (blocks.isNotEmpty()) {
                        val rel = srcFile.relativeTo(srcDir).invariantSeparatorsPath
                        val mdFile = destDirProvider
                            .get()
                            .file(rel.replaceAfterLast(".", "md"))
                            .asFile
                        mdFile.parentFile.mkdirs()
                        mdFile.writeText(blocks.joinToString("\n\n"))
                        println("✅ Generated: ${mdFile.path}")
                    } else {
                        println("⚠️ Skip (no doc-comments): ${srcFile.relativeTo(srcDir)}")
                    }
                }
        }
    }
}
