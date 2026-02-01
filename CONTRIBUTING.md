# Contributing

## Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) to maintain consistent code formatting.

Before committing your changes, run:

```sh
./gradlew spotlessApply
```

This will automatically format all Java files according to the project's style rules.

## Adding a New Task

To add a new task to the project, follow these steps:

### 1. Create a New Module (Recommended)

Copy an existing task module (e.g., `tasks/task01/`) and rename it to `tasks/taskXX/`.

Don't forget to add the new module to [settings.gradle.kts](settings.gradle.kts), and rename all its files and packages.

### 2. Create the Task Class

Create the task implementation class with empty methods that the user will implement:

```java
package xyz.ivancea.handsondatabases.tasks.taskXX;

import xyz.ivancea.handsondatabases.shared.Task;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

public class TaskXX extends Task {
    public TaskXX(FileHelper fileHelper) {
        super(fileHelper);
    }

    public void someAction() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
```

### 3. Create the CLI Config Class

Create a configuration class in `src/main/java/xyz/ivancea/handsondatabases/tasks/` to define the task's CLI commands.
See [Task01Config.java](src/main/java/xyz/ivancea/handsondatabases/tasks/Task01Config.java) for an example.

### 4. Register the Task

Add your new task configuration to the `TasksRegistry` class in [TasksRegistry.java](src/main/java/xyz/ivancea/handsondatabases/TasksRegistry.java).

### 5. Test Your Task

Run your task using the provided scripts:

```sh
./run -t XX -a action-name
```
