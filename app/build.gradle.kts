plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp") version "2.1.10-1.0.29"
}

android {

    compileSdk = 34
    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()
        namespace = "com.jdw.justdrink"

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
        disable.add("GradleDependency")
        disable.add("BooleanPropertyPrefix")
    }

}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.runtime.android)

    // Compose dependencies
    implementation(platform(libs.compose.bom))
    //implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.material3.android)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.navigation.compose)
    implementation(libs.ui.tooling.preview.android)
    implementation(libs.androidx.core.i18n)

    //pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    //room
    implementation(libs.androidx.room.runtime) //
    implementation(libs.androidx.room.ktx) //
    ksp(libs.androidx.room.compiler) //

    implementation(libs.accompanist.navigation.animation)

    implementation(libs.androidx.work.runtime.ktx)


}
