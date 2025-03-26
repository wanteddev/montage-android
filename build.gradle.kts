import extension.default
import extension.defaultKotlinOptions

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.android.compose.get().pluginId)
    alias(libs.plugins.kotlin.plugin.compose)
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

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.legacy.supportV4)

    implementation(libs.androidx.vectordrawable)

    implementation(libs.androidx.constraintlayout)

    implementation(libs.lottie.compose)

    implementation(libs.google.material)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.glide.compose)
}
