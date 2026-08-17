dependencies {
    testImplementation(project(":tests:shared"))
    testImplementation(project(":shared"))
    testImplementation(project(":tasks:task01"))
    testImplementation(project(":solutions:task01"))
}

val testSourceSet = sourceSets.test.get()

fun Test.configureContractTest(implementation: String) {
    group = "verification"
    description = "Runs the task contract against the $implementation implementation."
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath
    useJUnitPlatform()
    systemProperty("implementation", implementation)
    inputs.property("implementation", implementation)
    outputs.upToDateWhen { false }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val exerciseTest = tasks.register<Test>("exerciseTest") {
    configureContractTest("exercise")
}

val solutionTest = tasks.register<Test>("solutionTest") {
    configureContractTest("solution")
}

tasks.test {
    dependsOn(exerciseTest, solutionTest)
    enabled = false
}
