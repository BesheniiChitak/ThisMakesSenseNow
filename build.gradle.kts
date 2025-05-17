plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta12"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
}

group = "me.artofluxis"
version = "1.2.2"
description = "PaperMC Plugin in Kotlin"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.6.0")

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    archiveBaseName.set("ThisMakesSenseNow")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())

    relocate("kotlin", "me.artofluxis.shaded.kotlin")

    mergeServiceFiles()

    configurations = listOf(project.configurations.runtimeClasspath.get())
}


tasks {
    runServer { minecraftVersion("1.21.4") }

    build { dependsOn(shadowJar) }
    reobfJar { dependsOn(shadowJar) }
}