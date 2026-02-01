plugins {
    id("java")
    id("application")
    id("com.diffplug.spotless") version "8.2.1"
}

application {
    mainClass.set("xyz.ivancea.handsondatabases.Main")
}

group = "xyz.ivancea"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        java {
            target("**/src/main/java/**/*.java", "**/src/test/java/**/*.java")

            eclipse("4.38")
                // From https://github.com/elastic/elasticsearch/blob/004f84a3b7cffa9519ffe18fba90afd6cf868593/build-conventions/formatterConfig.xml
                .configFile("${rootDir}/eclipseFormatterConfig.xml")
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    // Apply test dependencies to all projects (root and subprojects)
    plugins.withType<JavaPlugin> {
        dependencies {
            testImplementation(platform("org.junit:junit-bom:5.10.0"))
            testImplementation("org.junit.jupiter:junit-jupiter")
            testImplementation("org.assertj:assertj-core:3.27.7")
            testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        }

        tasks.named<Test>("test") {
            useJUnitPlatform()
        }
    }
}

dependencies {
    // Automatically depend on all task modules
    // This ensures all task implementations are available to the CLI
    rootProject.subprojects
        .filter { it.path.startsWith(":tasks:") }
        .forEach { taskProject ->
            implementation(taskProject)
        }
}