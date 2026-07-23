plugins {
	kotlin("jvm") version "2.2.21" apply false
	kotlin("kapt") version "2.2.21" apply false
	kotlin("plugin.spring") version "2.2.21" apply false
	kotlin("plugin.jpa") version "2.2.21" apply false
	kotlin("plugin.serialization") version "2.2.21" apply false
	id("org.springframework.boot") version "3.5.16" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.jetbrains.dokka") version "2.2.0" apply false
	id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.1" apply false
}

allprojects {
	group = "dev.myrlennia237"
	version = "0.1.0-SNAPSHOT"
}

subprojects {
	pluginManager.apply("java-library")
	pluginManager.apply("maven-publish")
	pluginManager.apply("org.springframework.boot")
	pluginManager.apply("io.spring.dependency-management")

	configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(17))
		}
		withSourcesJar()
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	afterEvaluate {
		configure<PublishingExtension> {
			publications {
				create<MavenPublication>("maven") {
					from(components["java"])
				}
			}
		}
	}
}
