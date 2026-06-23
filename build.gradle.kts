plugins {
	kotlin("jvm") version "2.3.21" apply false
	kotlin("kapt") version "2.3.21" apply false
	kotlin("plugin.spring") version "2.3.21" apply false
	kotlin("plugin.jpa") version "2.3.21" apply false
	kotlin("plugin.serialization") version "2.3.21" apply false
	id("org.springframework.boot") version "4.0.7" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
	group = "dev.myrlennia237"
	version = "0.1.0"
}

subprojects {
	pluginManager.apply("java-library")
	pluginManager.apply("maven-publish")
	pluginManager.apply("org.springframework.boot")
	pluginManager.apply("io.spring.dependency-management")

	configure<JavaPluginExtension> {
		toolchain {
			languageVersion = JavaLanguageVersion.of(25)
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
