import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
}

group = "one.tranic"
version = "1.0.0"

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
    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
    jvmToolchain(17)
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            jdkVersion.set(17)
            languageVersion.set("2.0")
            apiVersion.set("2.0")
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            pom {
                name.set(rootProject.name)
                organization {
                    name.set("TranicSoft Studio")
                    url.set("https://tranic.one")
                }
                description.set("ScorpioBridge API")
                inceptionYear.set("2024")
                url.set("https://github.com/LevelTranic/ScorpioBridge-API")
                licenses {
                    license {
                        name.set("Apache 2.0 LICENSE")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("tranic.one")
                        name.set("404")
                        email.set("no-reply@tranic.one")
                        url.set("https://tranic.one")
                    }
                }
            }
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
