plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "ru.alexsuvorov.paistewiki.feature.support.api"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
