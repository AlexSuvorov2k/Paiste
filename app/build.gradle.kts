plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}

android {
    namespace = Config.namespace
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    bundle {
        language {
            enableSplit = Config.enableLanguageSplit
        }
    }

    sourceSets {
        getByName("main") {
            assets.directories.add("src/main/assets")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(project(":core:database"))
    implementation(project(":core:support"))
    implementation(project(":feature:about:impl"))
    implementation(project(":feature:artist_list:impl"))
    implementation(project(":feature:product_list:impl"))
    implementation(project(":feature:support:impl"))
    implementation(project(":feature:news:impl"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.google.material)
}