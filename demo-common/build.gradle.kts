val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
	implementation ("com.slack.api:slack-api-client:1.18.0")
	implementation ("com.squareup.okhttp3:okhttp:4.9.3")
	implementation ("org.apache.commons:commons-lang3")
	implementation ("commons-io:commons-io:2.8.0")
}