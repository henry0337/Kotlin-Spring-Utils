plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    api("org.springframework:spring-context")
    api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.5.0")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.security:spring-security-config")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.41")
    implementation("org.slf4j:slf4j-api")
}

kotlin {
    explicitApi()
    jvmToolchain(25)

    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property",
            "-Xreturn-value-checker=full"
        )
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
