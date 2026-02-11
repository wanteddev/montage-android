import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)
    id("maven-publish")
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
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

    lint {
        abortOnError = false
        checkReleaseBuilds = false
        ignoreWarnings = true
        // Disable problematic checks that fail with Kotlin 2.0
        disable += setOf(
            "ObsoleteLintCustomCheck",
            "LintError"
        )
        // Workaround for Kotlin 2.0 FIR compiler compatibility
        warningsAsErrors = false
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

    debugImplementation(libs.androidx.compose.ui.tooling)
}


val publishProperties = Properties().apply {
    file("publish.properties").inputStream().use { fis -> load(fis) }
}

val libGroupId = publishProperties.getProperty("groupId")
val libArtifactId = publishProperties.getProperty("artifactId")
val libVersion = publishProperties.getProperty("version")

// Build AAR task
val buildAar by tasks.registering {
    group = "build"
    description = "Builds the release AAR file"
    dependsOn("assembleRelease")

    doLast {
        val aarFile = file("build/outputs/aar/library-release.aar")
        if (aarFile.exists()) {
            println("✅ AAR built successfully:")
            println("   Location: ${aarFile.absolutePath}")
            println("   Size: ${aarFile.length() / 1024} KB")
        } else {
            println("❌ AAR file not found")
        }
    }
}

afterEvaluate {
    // Disable lint analysis tasks to avoid Kotlin 2.0 FIR compatibility issues
    tasks.matching {
        it.name.contains("lint", ignoreCase = true)
    }.configureEach {
        enabled = false
    }

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = libGroupId
                artifactId = libArtifactId
                version = libVersion

                pom {
                    name.set("Montage Android Design System")
                    description.set("Montage Android Design System Library")
                    url.set("https://github.com/wanteddev/montage-android")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("wanteddev")
                            name.set("Wanted")
//                            email.set("dev@wantedlab.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/wanteddev/montage-android.git")
                        developerConnection.set("scm:git:ssh://github.com/wanteddev/montage-android.git")
                        url.set("https://github.com/wanteddev/montage-android")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GithubPackages"
                url = uri("https://maven.pkg.github.com/wanteddev/montage-android")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

apply(from = "gradle/documentation.gradle.kts")
