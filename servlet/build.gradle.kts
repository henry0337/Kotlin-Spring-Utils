import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binary.compatibility.validator)
}

dependencies {
    api(projects.shared)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.resilience4j.spring.boot3)
    kapt(libs.spring.boot.configuration.processor)
    implementation(libs.querydsl.jpa)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.archunit)
    kapt(variantOf(libs.querydsl.apt) { classifier("jpa") })
    kapt(libs.jakarta.persistence.api)
    compileOnly(libs.jspecify)
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
    jvmToolchain(17)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xjvm-default=all",
            "-Xjsr305=strict"
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

kapt {
    correctErrorTypes = true
}
