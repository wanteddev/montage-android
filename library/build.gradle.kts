plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)
}

android {
    namespace = "com.wanted.android.designsystem"
    compileSdk = 35
    buildToolsVersion = "35.0.1"

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.ExperimentalUnsignedTypes",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes += listOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/LICENSE.md",
                "/META-INF/LICENSE-notice.md"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.google.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.lottie.compose)
    implementation(libs.glide.compose)
    implementation(libs.accompanist.systemuicontroller)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

val extractDesignSystemJavadocToMd by tasks.registering {
    group = "documentation"
    description = "Extract Javadoc/KDoc from com.wanted.android.wanted.design to Markdown"

    // 1) 소스 디렉터리 목록 (Java/Kotlin)
    val srcDirs = listOf(
        file("src/main/java"),
        file("src/main/kotlin")
    ).filter { it.exists() }

    // 2) 출력 디렉터리
    val destDirProvider = layout.buildDirectory.dir("docs/designsystem-javadoc-md")

    // 3) 입력·출력 선언 (스프레드 연산자로 vararg 처리)
    srcDirs.forEach { inputs.dir(it) }
    outputs.dir(destDirProvider)

    doLast {
        // 이전에 생성된 문서 폴더들을 삭제
        val sourceDocDir = file("document/source")
        val mdDocDir = file("document/md")

        if (sourceDocDir.exists()) {
            sourceDocDir.deleteRecursively()
            println("🗑️ Deleted: ${sourceDocDir.absolutePath}")
        }

        if (mdDocDir.exists()) {
            mdDocDir.deleteRecursively()
            println("🗑️ Deleted: ${mdDocDir.absolutePath}")
        }

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

// 2) 신규: 패키지별로 합치기
val mergeDesignSystemJavadocByPackage by tasks.registering {
    group = "documentation"
    description = "Merge extracted .md files into one per package"

    // 이 태스크는 반드시 extract 후에 실행되어야 함
    dependsOn(extractDesignSystemJavadocToMd)

    // 합친 결과는 같은 destDir에 저장
    val destDir = layout.buildDirectory.dir("docs/designsystem-javadoc-md").get().asFile
//    val mergeDir = layout.buildDirectory.dir("docs/merge")

    inputs.dir(destDir)

    val outputDir =  layout.projectDirectory.dir("document/source")
    outputs.dir(outputDir)

    doLast {
        // root package folder
        val rootPkgDir = File(destDir, "com/wanted/android/")
        val outputBase = outputDir.asFile
        if (!rootPkgDir.exists()) {
            println("⚠️ No extracted docs found in $rootPkgDir")
            return@doLast
        }

        // config 폴더의 내용을 저장할 맵 (parentPackageName -> List<File>)
        val configContentMap = mutableMapOf<String, MutableList<File>>()

        // Contract 파일을 저장할 맵 (parentPackageName -> List<File>)
        val contractContentMap = mutableMapOf<String, MutableList<File>>()

        // 서브 패키지 파일을 저장할 맵 (parentPackageName -> List<File>)
        val subPackageContentMap = mutableMapOf<String, MutableList<File>>()

        // WantedModalContract 파일을 저장할 변수
        var wantedModalContractFile: File? = null

        // 처리할 패키지 정보를 저장할 리스트
        data class PackageInfo(
            val pkgDir: File,
            val pkgName: String,
            val segments: List<String>,
            val mdFiles: List<File>
        )

        val packagesToProcess = mutableListOf<PackageInfo>()

        // 상위 패키지로 합쳐져야 할 서브 패키지 이름들
        val subPackageNames = setOf("control", "config", "view")

        // 1단계: config 폴더들을 먼저 수집
        rootPkgDir.walkTopDown()
            .filter { it.isDirectory && it.name == "config" }
            .forEach { configDir ->
                val mdFiles = configDir.listFiles { f -> f.isFile && f.extension == "md" }
                    ?.sortedBy { it.name }
                if (!mdFiles.isNullOrEmpty()) {
                    val rel = configDir.relativeTo(rootPkgDir).invariantSeparatorsPath
                    val segments = rel.replace('/', '.').split('.')
                    if (segments.size > 1) {
                        val parentPackage = segments[segments.size - 2]
                        configContentMap.getOrPut(parentPackage) { mutableListOf() }.addAll(mdFiles)
                        println("📦 Found config files for parent package '$parentPackage': ${mdFiles.size} files")
                    }
                }
            }

        // 2단계: 모든 패키지를 스캔하고 Contract 파일 및 서브 패키지 분류
        rootPkgDir.walkTopDown()
            .filter { it.isDirectory && it.name != "config" }
            .forEach { pkgDir ->
                // find all .md files directly under this dir
                val allMdFiles =
                    pkgDir.listFiles { f -> f.isFile && f.extension == "md" }?.sortedBy { it.name }
                if (allMdFiles.isNullOrEmpty()) return@forEach

                // WantedModalContract 파일 찾기
                val modalContractFile =
                    allMdFiles.find { it.nameWithoutExtension == "WantedModalContract" }
                if (modalContractFile != null) {
                    wantedModalContractFile = modalContractFile
                    println("📄 Found WantedModalContract file: ${modalContractFile.name}")
                }

                // Contract 파일과 일반 파일 분리
                val contractFiles =
                    allMdFiles.filter { it.nameWithoutExtension.endsWith("Contract") }
                val regularFiles =
                    allMdFiles.filter { !it.nameWithoutExtension.endsWith("Contract") }

                // build package name: convert relative path to dots
                val rel = pkgDir.relativeTo(rootPkgDir).invariantSeparatorsPath
                val segments = rel.replace('/', '.').split('.')
                val pkgName = segments.last()

                // 서브 패키지 이름인 경우 상위 패키지로 합치기
                if (subPackageNames.contains(pkgName) && segments.size > 1) {
                    val parentPackage = segments[segments.size - 2]
                    // WantedModalContract는 제외하고 추가
                    val filesToAdd =
                        allMdFiles.filter { it.nameWithoutExtension != "WantedModalContract" }
                    if (filesToAdd.isNotEmpty()) {
                        subPackageContentMap.getOrPut(parentPackage) { mutableListOf() }
                            .addAll(filesToAdd)
                        println("📦 Moving ${filesToAdd.size} files from sub-package '$pkgName' to parent package '$parentPackage'")
                    }
                    return@forEach
                }

                // Contract 파일만 있고 일반 파일이 없으면 상위 패키지로 이동
                if (regularFiles.isEmpty() && contractFiles.isNotEmpty() && segments.size > 1) {
                    val parentPackage = segments[segments.size - 2]
                    // WantedModalContract는 제외하고 추가
                    val contractsToMove =
                        contractFiles.filter { it.nameWithoutExtension != "WantedModalContract" }
                    if (contractsToMove.isNotEmpty()) {
                        contractContentMap.getOrPut(parentPackage) { mutableListOf() }
                            .addAll(contractsToMove)
                        println("📄 Moving ${contractsToMove.size} Contract files from '$pkgName' to parent package '$parentPackage'")
                    }
                    return@forEach
                }

                // 일반 파일이 있으면 해당 패키지로 처리할 리스트에 추가 (Contract 포함, 단 WantedModalContract는 제외)
                val filesToProcess =
                    allMdFiles.filter { it.nameWithoutExtension != "WantedModalContract" }
                if (filesToProcess.isNotEmpty()) {
                    packagesToProcess.add(PackageInfo(pkgDir, pkgName, segments, filesToProcess))
                }
            }

        // 3단계: 수집된 패키지들을 실제로 병합
        packagesToProcess.forEach { packageInfo ->
            val (pkgDir, pkgName, segments, mdFiles) = packageInfo

            // base 패키지는 개별 파일로 유지 (합치지 않음)
            if (pkgName == "base") {
                mdFiles.forEach { mdFile ->
                    val fileName = mdFile.nameWithoutExtension
                        .removePrefix("Wanted")
                        .lowercase() + ".md"
                    val individualFile = File(outputBase, fileName)
                    individualFile.parentFile.mkdirs()
                    individualFile.writeText(mdFile.readText())
                    println("📄 Copied (not merged): ${individualFile.path}")
                }
                return@forEach
            }

            // merged file path: <destDir>/<pkgName>.md
            val merged = File(outputBase, "$pkgName.md")
            merged.parentFile.mkdirs()

            // concat all main package files
            val mainContent = mdFiles.joinToString("\n\n") { it.readText() }

            // config 폴더의 내용이 있으면 추가
            val configFiles = configContentMap[pkgName]
            val configContent = if (configFiles != null && configFiles.isNotEmpty()) {
                configFiles.joinToString("\n\n") { it.readText() }
            } else {
                ""
            }

            // Contract 파일의 내용이 있으면 추가
            val contractFiles = contractContentMap[pkgName]
            val contractContent = if (contractFiles != null && contractFiles.isNotEmpty()) {
                contractFiles.joinToString("\n\n") { it.readText() }
            } else {
                ""
            }

            // 서브 패키지의 내용이 있으면 추가
            val subPackageFiles = subPackageContentMap[pkgName]
            val subPackageContent = if (subPackageFiles != null && subPackageFiles.isNotEmpty()) {
                subPackageFiles.joinToString("\n\n") { it.readText() }
            } else {
                ""
            }

            // WantedModalContract를 popup과 bottomsheet 패키지에 추가
            val modalContractContent =
                if (pkgName == "popup" || pkgName == "bottomsheet") {
                    wantedModalContractFile?.let { contractFile ->
                        "\n\n" + contractFile.readText()
                    } ?: ""
                } else {
                    ""
                }

            // 모든 내용 합치기
            val finalContent =
                listOf(
                    mainContent,
                    configContent,
                    contractContent,
                    subPackageContent,
                    modalContractContent
                )
                    .filter { it.isNotEmpty() }
                    .joinToString("\n\n")

            merged.writeText(finalContent)

            val totalFiles = mdFiles.size + (configFiles?.size ?: 0) + (contractFiles?.size
                ?: 0) + (subPackageFiles?.size ?: 0)
            val parts = mutableListOf<String>()
            parts.add("${mdFiles.size} files")
            if (configFiles != null && configFiles.isNotEmpty()) {
                parts.add("${configFiles.size} config files")
            }
            if (contractFiles != null && contractFiles.isNotEmpty()) {
                parts.add("${contractFiles.size} contract files")
            }
            if (subPackageFiles != null && subPackageFiles.isNotEmpty()) {
                parts.add("${subPackageFiles.size} sub-package files")
            }
            if (modalContractContent.isNotEmpty()) {
                parts.add("WantedModalContract")
            }
            println("🔗 Merged ${parts.joinToString(" + ")} → ${merged.path}")
        }
    }
}

tasks.register<Exec>("runKdocScript") {
    val scriptPath = projectDir.resolve("src/kdoc_t_mdx.kts").absolutePath
    val inputPath = project.file("document/source/topbar.md").absolutePath
    val outputPath = project.file("document/md/topbar.mdx").absolutePath

    commandLine(scriptPath, inputPath, outputPath)

    // 표준 출력과 에러를 Gradle 로그로 연결
    standardOutput = System.out
    errorOutput = System.err

    // 에러 무시하고 진행할지 여부 (디버깅용으로 true 설정 가능)
    isIgnoreExitValue = false
}

tasks.register("runKdocScriptAll") {
    group = "documentation"
    description = "Convert all .md files in document/source/ to .mdx in document/md/"

    val scriptPath = projectDir.resolve("src/kdoc_t_mdx.kts").absolutePath
    val sourceDir = project.file("document/source")
    val outputDir = project.file("document/md")

    // Remove inputs/outputs to always run the task
    // inputs.dir(sourceDir)
    // outputs.dir(outputDir)

    doLast {
        if (!sourceDir.exists()) {
            println("⚠️ Source directory not found: ${sourceDir.absolutePath}")
            return@doLast
        }

        // Ensure output directory exists
        outputDir.mkdirs()

        // Get all .md files in source directory
        val mdFiles: Array<File> = sourceDir.listFiles { file: File ->
            file.isFile && file.extension == "md"
        }?.sortedBy { it.name }?.toTypedArray() ?: emptyArray()

        if (mdFiles.isEmpty()) {
            println("⚠️ No .md files found in ${sourceDir.absolutePath}")
            return@doLast
        }

        println("📝 Found ${mdFiles.size} .md files to convert")

        mdFiles.forEach { mdFile: File ->
            val inputPath = mdFile.absolutePath
            val outputFileName = mdFile.nameWithoutExtension + ".mdx"
            val outputPath = File(outputDir, outputFileName).absolutePath

            println("  Converting: ${mdFile.name} → $outputFileName")

            try {
                val process = ProcessBuilder("kotlin", scriptPath, inputPath, outputPath)
                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                    .redirectError(ProcessBuilder.Redirect.PIPE)
                    .start()

                val exitCode = process.waitFor()

                if (exitCode != 0) {
                    val errorOutput = process.errorStream.bufferedReader().readText()
                    println("    ❌ Error converting ${mdFile.name}: $errorOutput")
                } else {
                    println("    ✅ Success: $outputFileName")
                }
            } catch (e: Exception) {
                println("    ❌ Exception converting ${mdFile.name}: ${e.message}")
            }
        }

        println("🎉 Conversion complete! Processed ${mdFiles.size} files")
    }
}

/**
 * 디자인 시스템 문서화 자동화 스크립트
 * master에 merge 될 때 자동으로 실행되도록 설정되어있음.
 *
 * 실행 커멘드
 * ./gradlew :library:extractDesignSystemJavadocToMd
 * ./gradlew :library:mergeDesignSystemJavadocByPackage
 */
