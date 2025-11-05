plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
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
    implementation(libs.androidx.material3)
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

// 2) 신규: 패키지별로 합치기
val mergeDesignSystemJavadocByPackage by tasks.registering {
    group = "documentation"
    description = "Merge extracted .md files into one per package"

    // 이 태스크는 반드시 extract 후에 실행되어야 함
    dependsOn(extractDesignSystemJavadocToMd)

    // 합친 결과는 같은 destDir에 저장
    val destDir = layout.buildDirectory.dir("docs/designsystem-javadoc-md").get().asFile
    inputs.dir(destDir)

    val outputDir = layout.projectDirectory.dir("document/source")
    outputs.dir(outputDir)

    doLast {
        val rootPkgDir = File(destDir, "com/wanted/android/")
        val outputBase = outputDir.asFile
        if (!rootPkgDir.exists()) {
            println("⚠️ No extracted docs found in $rootPkgDir")
            return@doLast
        }

        // for each package directory (including rootPkgDir itself)
        rootPkgDir.walkTopDown()
            .filter { it.isDirectory }
            .forEach { pkgDir ->
                // find all .md files directly under this dir
                val mdFiles = pkgDir.listFiles { f -> f.isFile && f.extension == "md" }?.sortedBy { it.name }
                if (mdFiles.isNullOrEmpty()) return@forEach

                // build package name: convert relative path to dots
                val rel = pkgDir.relativeTo(rootPkgDir).invariantSeparatorsPath
                val pkgName = if (rel.isEmpty())
                    "com.wanted.android.wanted.design"
                else
                    rel.replace('/', '.').split('.').last()

                // merged file path: <destDir>/<pkgName>.md
                val merged = File(outputBase, "$pkgName.md")
                merged.parentFile.mkdirs()

                // concat all
                merged.writeText(mdFiles.joinToString("\n\n") { it.readText() })
                println("🔗 Merged ${mdFiles.size} → ${merged.path}")
            }
    }
}


/**
 * 디자인 시스템 문서화 자동화 스크립트
 * master에 merge 될 때 자동으로 실행되도록 설정되어있음.
 *
 * 실행 커멘드
 * ./gradlew :core:wanted-library-design-system:extractDesignSystemJavadocToMd
 * ./gradlew :core:wanted-library-design-system:mergeDesignSystemJavadocByPackage
 */
