plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.springframework.boot")
}

group = "dev.myrlennia237"
version = "0.1.0"

dependencies {
    api("org.springframework:spring-context")
    implementation("org.springframework.data:spring-data-commons")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    implementation("org.slf4j:slf4j-api")
}

kotlin {
    jvmToolchain(25)

    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}
