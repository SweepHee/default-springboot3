import org.jooq.meta.jaxb.Logging

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

tasks {
	bootJar {
		launchScript()
	}
}


apply {
	plugin("org.jetbrains.kotlin.plugin.spring")
	plugin("io.spring.dependency-management")
	plugin("org.springframework.boot")
	plugin("org.flywaydb.flyway")
	plugin("nu.studer.jooq")
	plugin("kotlin")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

noArg {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

val jdbcUrl = "jdbc:postgresql://localhost:5432/boot3test?useUnicode=true&characterEncoding=utf8"
val jdbcDriver = "org.postgresql.Driver"
val jdbcUser = "boot3test"
val jdbcPassword = "1234"

jooq {
	version.set("3.17.7")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(false)

			jooqConfiguration.apply {
				logging = Logging.INFO
				jdbc.apply {
					driver = jdbcDriver
					url = jdbcUrl
					user = jdbcUser
					password = jdbcPassword
				}

				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						excludes = """flyway_schema_history"""
						schemaVersionProvider = """select max(flyway_schema_history.version) from flyway_schema_history"""
					}

					generate.apply {
						isGlobalObjectReferences = true
						isDeprecated = true
						isRecords = true
						isImmutablePojos = true
						isRelations = true
						isFluentSetters = true
						isPojos = true
					}
					target.apply {
						directory = "build/generated-src/jooq"
						packageName = "demo.web.jooq"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"

				}
			}
		}
	}

}


dependencies {
	implementation(project(":demo-common"))
	implementation(project(":demo-data"))
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation ("org.springframework.kafka:spring-kafka")
	implementation("org.apache.kafka:kafka-streams")
	implementation("com.jayway.jsonpath:json-path:2.7.0")

    implementation("org.springframework.boot:spring-boot-starter-actuator")


}