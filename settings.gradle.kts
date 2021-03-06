@file:Suppress("LocalVariableName")

rootProject.name = "easy-clock"

pluginManagement {
    val kotlin_version: String by settings
    plugins {
        kotlin("jvm") version kotlin_version
        id("com.github.ben-manes.versions") version "0.36.0"
        id("com.github.johnrengelman.shadow") version "6.1.0"
    }
}
