buildscript {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

apply(plugin = "com.lagradost.cloudstream3.gradle")

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}
