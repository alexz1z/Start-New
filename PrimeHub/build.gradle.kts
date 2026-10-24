plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

android {
    namespace = "com.alexz1z.primehub"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

cloudstream {
    description = "PrimeHub Movies & Series Stream & Download"
    authors = listOf("alexz1z")
    version = 1
}

dependencies {
    implementation("com.github.recloudstream.cloudstream:library:-SNAPSHOT")
    implementation("com.github.Blatzar:NiceHttp:0.4.11")
    implementation("org.jsoup:jsoup:1.17.2")
}
