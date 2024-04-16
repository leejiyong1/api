plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.23"
}
allOpen{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
dependencies {
    implementation(project(":domain"))
    runtimeOnly("mysql:mysql-connector-java:8.0.22")
}