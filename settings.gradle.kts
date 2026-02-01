rootProject.name = "hands-on-databases"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include("tasks:shared")
include("tasks:task01")
include("tasks:task02")
