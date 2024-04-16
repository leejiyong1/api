plugins {
    kotlin("jvm")
}
allOpen{
    annotation("org.springframework.transaction.annotation.Transactional")
}
dependencies{
    implementation(project(":domain"))
    implementation(project(":db-main"))
    implementation(project(":configuration"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.json:json:20231013")
}