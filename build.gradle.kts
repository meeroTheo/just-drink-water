import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    id("com.autonomousapps.dependency-analysis") version "2.10.1"
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.versions)
    id("maven-publish")
    base
}

allprojects {
    group = PUBLISHING_GROUP
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}


