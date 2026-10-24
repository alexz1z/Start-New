plugins {
    kotlin("jvm")
    id("com.lagradost.cloudstream3.gradle")
}

cloudstream {
    setRepoUrl("https://github.com/alexz1z/Start-New")
    name = "RogMovies"
    description = "RogMovies Bollywood & Dual Audio Stream & Download"
    authors = listOf("alexz1z")
    version = 1
}

dependencies {
    compileOnly("com.lagradost:cloudstream3:pre-release")
}
