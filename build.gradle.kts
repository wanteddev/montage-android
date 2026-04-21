plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.plugin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    id("maven-publish")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}


afterEvaluate {
    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")
    }
}