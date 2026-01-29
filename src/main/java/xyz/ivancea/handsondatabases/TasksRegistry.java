package xyz.ivancea.handsondatabases;

import java.util.List;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.tasks.Task01Config;

public class TasksRegistry {
    private TasksRegistry() {}

    public static List<TaskConfig> getAll() {
        return List.of(new Task01Config());
    }
}
