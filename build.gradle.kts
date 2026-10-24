import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

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

    extensions.configure<BaseExtension>("android") {
        namespace = "com.alexz1z.${project.name.lowercase()}"
        compileSdkVersion(34)

        defaultConfig {
            minSdk = 21
            targetSdk = 34
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xno-call-assertions",
                "-Xno-param-assertions",
                "-Xno-receiver-assertions"
            )
        }
    }

    dependencies {
        val implementation by configurations
        implementation("com.github.recloudstream.cloudstream:library:-SNAPSHOT")
        implementation(kotlin("stdlib"))
        implementation("com.github.Blatzar:NiceHttp:0.4.11")
        implementation("org.jsoup:jsoup:1.17.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    }
}
