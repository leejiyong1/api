import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23" apply false
	id("org.springframework.boot") version "3.2.4" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
}



java {
	sourceCompatibility = JavaVersion.VERSION_17
}
allprojects{
	group = "com.example"
	version = "0.0.1-SNAPSHOT"
	repositories {
		mavenCentral()
	}
}

subprojects{
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(module = "junit-vintage-engine")
		}
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

