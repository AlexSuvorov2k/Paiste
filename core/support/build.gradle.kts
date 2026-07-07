plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "ru.alexsuvorov.paistewiki.core.support"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "consumer-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
}