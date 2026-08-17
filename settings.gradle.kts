plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "hands-on-databases"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include("shared")
include("tasks:task01")
include("tasks:task02")
include("solutions:task01")
include("tests:shared")
include("tests:task01")
