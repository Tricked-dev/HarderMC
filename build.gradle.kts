/*
 * Copyright (c) Tricked-dev 2023.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("xyz.jpenilla.run-paper") version "2.1.0"
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("wrapper")
}

object Versions {
    const val kotlin = "1.9.0"
    const val coroutines = "1.6.2"
    const val serialization = "1.3.3"
    const val atomicfu = "0.17.3"
    const val datetime = "0.3.2"
}

group = "dev.tricked"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("papermc-repo") {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("sonatype") {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

tasks.create<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.create<Jar>("javadocJar") {
    dependsOn.add(tasks.getByName("javadoc"))
    archiveClassifier.set("javadoc")
    from(tasks.getByName("javadoc"))
}

tasks.shadowJar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}

tasks.wrapper {
    gradleVersion = "8.2.1"
}

artifacts {
    archives(tasks.getByName("sourcesJar"))
    archives(tasks.getByName("javadocJar"))
    archives(tasks.shadowJar)
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    api("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    api("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.coroutines}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.coroutines}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:${Versions.serialization}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:${Versions.serialization}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-cbor-jvm:${Versions.serialization}")
    api("org.jetbrains.kotlinx:atomicfu-jvm:${Versions.atomicfu}")
    api("org.jetbrains.kotlinx:kotlinx-datetime-jvm:${Versions.datetime}")
}


val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.withType<JavaCompile>().configureEach {
    // Existing configurations...
    doFirst {
        options.compilerArgs = listOf("-Xlint:-processing")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

sourceSets {
    getByName("main") {
        kotlin.srcDirs("src/main/kotlin")
    }
}

tasks.withType<Copy>().named("processResources") {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}


tasks {
    runServer {
        minecraftVersion("1.20.1")
    }
}