import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//	id("org.springframework.boot") version "2.4.3"
//	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "com.masarnovsky"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.11.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
