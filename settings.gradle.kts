plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "weather"
include("application")
include("service")
include("domain")
include("db-main")
include("configuration")
