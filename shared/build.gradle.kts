plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

group = "dev.myrlennia237"
version = "0.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    api("org.springframework.boot:spring-boot-starter-aop:4.0.0-M2")
}

kotlin {
    jvmToolchain(25)

    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}