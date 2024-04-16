tasks.getByName("bootJar"){
	enabled = true
}

tasks.getByName("jar"){
	enabled = false
}

dependencies{
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation(project(":domain"))
	implementation(project(":service"))
}

