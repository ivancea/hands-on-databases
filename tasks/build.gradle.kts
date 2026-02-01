// This build file configures all task subprojects (task01, task02, etc.)
// It automatically applies shared dependencies and common configuration

subprojects {
    // Apply common configuration to all task modules
    apply(plugin = "java")

    group = "xyz.ivancea"
    version = "1.0-SNAPSHOT"

    // Only task implementation modules (not shared itself) depend on :tasks:shared
    if (name != "shared") {
        plugins.withType<JavaPlugin> {
            dependencies {
                "implementation"(project(":tasks:shared"))
            }
        }
    }
}
