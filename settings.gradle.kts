rootProject.name = "hands-on-databases"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include("shared")
include("tasks:task01")
