plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.plugin.compose) apply false
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