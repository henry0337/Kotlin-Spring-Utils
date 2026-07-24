import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binary.compatibility.validator)
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
