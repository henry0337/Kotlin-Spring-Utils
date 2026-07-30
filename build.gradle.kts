plugins {
	alias(libs.plugins.kotlin.jvm) apply false
	alias(libs.plugins.kotlin.kapt) apply false
	alias(libs.plugins.kotlin.plugin.spring) apply false
	alias(libs.plugins.kotlin.plugin.jpa) apply false
	alias(libs.plugins.kotlin.plugin.serialization) apply false
	alias(libs.plugins.spring.boot) apply false
	alias(libs.plugins.dependency.management) apply false
	alias(libs.plugins.dokka) apply false
	alias(libs.plugins.binary.compatibility.validator) apply false
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
			languageVersion.set(JavaLanguageVersion.of(21))
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
