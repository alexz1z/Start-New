pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Start-New"

include(":VegaMovies")
include(":RogMovies")
include(":PrimeHub")
include(":HDHub4u")
