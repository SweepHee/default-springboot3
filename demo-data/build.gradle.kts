import org.jooq.meta.jaxb.Logging
val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

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


bootJar.enabled = false
jar.enabled = true

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

tasks.withType<org.flywaydb.gradle.task.FlywayCleanTask>().configureEach {
	isEnabled = true
}

val jdbcUrl = "jdbc:postgresql://localhost:5432/boot3test?useUnicode=true&characterEncoding=utf8"
val jdbcDriver = "org.postgresql.Driver"
val jdbcUser = "boot3test"
val jdbcPassword = "1234"

flyway {
	url = jdbcUrl
	user = jdbcUser
	password = jdbcPassword
	cleanDisabled = false
}

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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.github.vladimir-bukhtoyarov:bucket4j-core:7.1.0")
	implementation ("org.springframework.boot:spring-boot-starter-aop")
	implementation ("org.springframework.kafka:spring-kafka")
	implementation("org.jooq:jooq:3.17.7")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("com.github.maricn:logback-slack-appender:1.4.0")
	implementation("io.github.microutils:kotlin-logging:2.0.6")
	implementation("com.github.ua-parser:uap-java:1.5.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-allopen")
	implementation("org.jetbrains.kotlin:kotlin-noarg")
	implementation("org.apache.tika:tika-core:2.4.1")
	implementation("org.apache.poi:poi-ooxml:4.1.2")
	implementation("org.yaml:snakeyaml:2.1")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-devtools")

	implementation ("org.apache.commons:commons-text:1.8")

	jooqGenerator("org.postgresql:postgresql:42.5.1")
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:spring-mock-mvc:4.0.0")
	testImplementation("io.mockk:mockk:1.9.3")
}