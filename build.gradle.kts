import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("com.github.ben-manes.versions")
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

application {
    @Suppress("DEPRECATION")
    mainClassName = "de.stefanbissell.easyclock.AppKt"
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    withType<Jar> {
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to application.mainClass.get()
                )
            )
        }
    }

}
