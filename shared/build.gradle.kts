import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("org.jetbrains.dokka")
}

dependencies {
    api("org.springframework:spring-context")
    api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.5.0")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework:spring-tx")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.41")
    implementation("org.slf4j:slf4j-api")
    compileOnly("org.jetbrains:annotations:26.1.0")
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi() // Kích hoạt chế độ Explicit API
    jvmToolchain(25)

    // ABI validation tích hợp của Kotlin Gradle plugin (dùng cho thư viện Java 25).
    // Task: `updateLegacyAbi` sinh/ghi baseline; `checkLegacyAbi` (chạy trong `check`) so sánh.
    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

    compilerOptions {
        jvmDefault = JvmDefaultMode.NO_COMPATIBILITY
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property",
            // Experimental: Return value checker, available only in Kotlin 2.3.x or later.
            // After upgraded to Kotlin 2.3.x, uncomment to use if needed.
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
