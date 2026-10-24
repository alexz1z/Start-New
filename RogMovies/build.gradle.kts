plugins {
    kotlin("jvm")
    id("com.lagradost.cloudstream3.gradle")
}

cloudstream {
    description = "RogMovies Bollywood & Dual Audio Stream & Download"
    authors = listOf("alexz1z")
    version = 1
}

dependencies {
    compileOnly("com.lagradost:cloudstream3:pre-release")
}
