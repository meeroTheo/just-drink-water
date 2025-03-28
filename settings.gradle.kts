pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } //Add this line in your settings.gradle
    }
}

rootProject.name = ("just-drink-water")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "app"
)
