import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dokka)
}

dependencies {
    api(libs.spring.context)
    api(libs.kotlinx.collections.immutable)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.data.commons)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.security.config)
    implementation(libs.spring.tx)
    implementation(libs.swagger.annotations)
    implementation(libs.slf4j.api)
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.jspecify)
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class) abiValidation()
    jvmToolchain(21)

    compilerOptions {
        val vmargs = listOf(
            "-Xjsr305=strict",
            "-Xreturn-value-checker=full"
        )
        
        freeCompilerArgs.addAll(vmargs)
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
