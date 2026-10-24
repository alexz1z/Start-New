buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    }
}

apply(plugin = "com.lagradost.cloudstream3.gradle")

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    afterEvaluate {
        extensions.findByName("android")?.let { android ->
            (android as com.android.build.gradle.BaseExtension).apply {
                compileSdkVersion(34)
                defaultConfig {
                    minSdk = 21
                    targetSdk = 34
                }
            }
        }
    }
}
