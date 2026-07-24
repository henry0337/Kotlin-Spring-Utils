import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binary.compatibility.validator)
}

dependencies {
    api(projects.shared)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(libs.spring.boot.starter.data.redis.reactive)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.reactor.kotlin.extensions)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlinx.datetime)
    implementation(libs.resilience4j.spring.boot3)
    implementation(libs.querydsl.r2dbc)
    kapt(libs.spring.boot.configuration.processor)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.archunit)
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
            "-Xjsr305=strict",
            "-opt-in=dev.myrlennia237.annotation.ExperimentalKotlinVariantApi"
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
