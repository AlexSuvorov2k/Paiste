pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Paiste"
include(":app")
include(":core:database")
include(":core:design")
include(":core:support")
include(":feature:about:api")
include(":feature:about:impl")
include(":feature:artist_list:api")
include(":feature:artist_list:impl")
include(":feature:news:api")
include(":feature:news:impl")
include(":feature:product_list:api")
include(":feature:product_list:impl")
include(":feature:support:api")
include(":feature:support:impl")