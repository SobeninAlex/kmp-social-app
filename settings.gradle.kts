enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kmp-social-app"
include(":androidApp")
include(":core:utils")
include(":core:resources")
include(":core:common")
include(":data")
include(":features:authorization")
include(":features:home")
include(":features:account")
include(":features:post-detail")
