plugins {
    application
    kotlin("jvm") version "1.9.22"
    id("com.github.ben-manes.versions") version "0.50.0"
    id("se.ascp.gradle.gradle-versions-filter") version "0.1.16"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
}

application {
    mainClass.set("de.stefanbissell.easyclock.AppKt")
}

kotlin {
    jvmToolchain(11)
}
