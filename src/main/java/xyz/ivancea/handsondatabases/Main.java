package xyz.ivancea.handsondatabases;

import java.util.List;
import xyz.ivancea.handsondatabases.shared.TaskConfig;

public class Main {
    public static void main(String[] args) {
        List<TaskConfig> tasks = TasksRegistry.getAll();
        CliHandler cli = new CliHandler(tasks);
        cli.handle(args);
    }
}
