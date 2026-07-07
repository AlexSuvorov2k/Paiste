plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}

allprojects {
    ext {
        set("jvmTarget", "17")
        set("javaVersion", JavaVersion.VERSION_17)
    }
}