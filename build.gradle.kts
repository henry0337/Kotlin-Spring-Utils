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

val kotlinStdlibCoordinate = "org.jetbrains.kotlin:kotlin-stdlib:${libs.versions.kotlin.get()}"
val kotlinReflectCoordinate = "org.jetbrains.kotlin:kotlin-reflect:${libs.versions.kotlin.get()}"

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

	// Boot BOM ghim kotlin-stdlib/kotlin-reflect ở bản mới hơn (tested với Kotlin 1.9.x) so với
	// Kotlin Gradle plugin 1.8.10 đang dùng ở đây — ghi đè để tránh xung đột metadata.
	configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
		dependencies {
			dependency(kotlinStdlibCoordinate)
			dependency(kotlinReflectCoordinate)
		}
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
