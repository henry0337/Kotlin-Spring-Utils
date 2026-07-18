import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.dokka")
}

dependencies {
    api(projects.shared)
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("io.github.resilience4j:resilience4j-spring-boot4:2.4.0")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("io.github.openfeign.querydsl:querydsl-jpa:7.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.tngtech.archunit:archunit:1.4.2")
    kapt("io.github.openfeign.querydsl:querydsl-apt:7.4.0:jpa")
    kapt("jakarta.persistence:jakarta.persistence-api")
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi() // Kích hoạt chế độ Explicit API
    jvmToolchain(25)

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

kapt {
    correctErrorTypes = true
}
