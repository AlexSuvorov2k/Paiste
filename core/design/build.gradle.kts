plugins {
    id("com.android.library")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.alexsuvorov.paistewiki.core.design"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    api(libs.androidx.foundation)
    api(libs.androidx.foundation.layout)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.coil.compose)

    debugApi(libs.androidx.ui.tooling)
}
