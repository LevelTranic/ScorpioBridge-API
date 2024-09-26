import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    `maven-publish`
}

group = "one.tranic"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven-central-asia.storage-download.googleapis.com/maven2/") {
        name = "central"
    }
    maven("https://repo.repsy.io/mvn/rdb/default") {
        name = "tranic-repo"
    }
}

dependencies {
    // Utils
    api("org.slf4j:slf4j-api:2.0.12")
    api("com.squareup.okhttp3:okhttp:4.12.0")

    // Tranic Utils
    api("one.tranic:kupdate:1.0.0")

    // Kotlin
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(javadocJar.get())
        }
    }
    repositories {
        maven {
            name = "repsy"
            url = uri("https://repo.repsy.io/mvn/rdb/default")
            credentials {
                username = project.findProperty("repsyUsername") as String? ?: System.getenv("REPSY_USERNAME")
                password = project.findProperty("repsyPassword") as String? ?: System.getenv("REPSY_PASSWORD")
            }
        }
    }
}
