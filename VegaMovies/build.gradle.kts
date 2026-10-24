plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

cloudstream {
    description = "VegaMovies Bollywood & Hollywood Movies & Series"
    authors = listOf("alexz1z")
    version = 1
}

dependencies {
    compileOnly("com.lagradost:cloudstream3:pre-release")
}
