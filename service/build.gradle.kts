plugins {
    kotlin("jvm")
}
allOpen{
    annotation("org.springframework.transaction.annotation.Transactional")
}
dependencies{
    implementation(project(":domain"))
    implementation(project(":db-main"))
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    implementation("org.json:json:20231013")
}