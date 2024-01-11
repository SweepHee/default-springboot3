import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

plugins {
	java
	kotlin("jvm") version "1.9.21"
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("nu.studer.jooq") version "5.2.1"
	id("org.flywaydb.flyway") version "9.20.0"
	kotlin("plugin.allopen") version("1.8.10")
	kotlin("plugin.noarg") version("1.8.10")
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
}




bootJar.enabled = false
jar.enabled = true


allprojects {
	group = "demo.example"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "kotlin")

	kotlin {
		sourceSets.all {
			languageSettings {
				languageVersion = "1.9"
			}
		}
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-opt-in=kotlin.io.path.ExperimentalPathApi", "-Xallow-result-return-type")
			languageVersion = "1.9"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	repositories {
		mavenCentral()
	}
}

subprojects {

	apply {
		plugin("kotlin")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("org.jetbrains.kotlin.plugin.spring")
		plugin("org.flywaydb.flyway")
	}

	tasks.withType<org.flywaydb.gradle.task.FlywayCleanTask>().configureEach {
		isEnabled = false
	}


	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}
}