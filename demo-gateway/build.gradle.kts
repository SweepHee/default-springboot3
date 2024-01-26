extra["springCloudVersion"] = "2023.0.0"

plugins {
	java
	kotlin("jvm") version "1.9.21"
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("nu.studer.jooq") version "5.2.1"
	id("org.flywaydb.flyway") version "9.20.0"
	id("org.openapi.generator") version "6.6.0"
	kotlin("plugin.allopen") version("1.8.10")
	kotlin("plugin.noarg") version("1.8.10")
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
}


dependencies {
	implementation(project(":demo-common"))
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
}


dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
