package xyz.ivancea.handsondatabases;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

public class CliHandler {

    private final List<TaskConfig> tasks;

    public CliHandler(List<TaskConfig> tasks) {
        this.tasks = tasks;
    }

    public void handle(String[] args) {
        // Simple arg parsing
        String taskArg = null;
        String actionArg = null;
        String dataArg = null;
        boolean showTips = false;

        for (int i = 0; i < (args == null ? 0 : args.length); i++) {
            String a = args[i];
            switch (a) {
                case "--task":
                case "-t":
                    if (i + 1 < args.length) {
                        taskArg = args[++i];
                    }
                    break;
                case "--action":
                case "-a":
                    if (i + 1 < args.length) {
                        actionArg = args[++i];
                    }
                    break;
                case "--data":
                case "-d":
                    if (i + 1 < args.length) {
                        dataArg = args[++i];
                    }
                    break;
                case "--tips":
                    showTips = true;
                    break;
                default:
                    // ignore unknown tokens for now
            }
        }

        if (taskArg == null) {
            // No task selected: show general help
            printGeneralHelp();
            return;
        }

        // find task: support numeric index (1-based) or id match
        Optional<TaskConfig> taskOpt = findTask(taskArg);
        if (!taskOpt.isPresent()) {
            System.out.println("Unknown task: " + taskArg);
            printAvailable();
            return;
        }

        TaskConfig task = taskOpt.get();

        if (showTips) {
            // Show tips for the task
            printTaskTips(task);
            return;
        }

        if (actionArg == null) {
            // show task help
            printTaskHelp(task);
            return;
        }

        // find action
        CliAction found = null;
        for (CliAction act : task.actions()) {
            if (act.name().equals(actionArg)) {
                found = act;
                break;
            }
        }
        Optional<CliAction> actionOpt = Optional.ofNullable(found);
        if (!actionOpt.isPresent()) {
            System.out.println("Unknown action for task '" + task.id() + "': " + actionArg);
            printTaskHelp(task);
            return;
        }

        CliAction action = actionOpt.get();
        if (action.executor() == null) {
            System.out.println("Action '" + actionArg + "' not implemented.");
            return;
        }

        try {
            Path dataFolder = Path.of("./data");
            if (!Files.exists(dataFolder)) {
                Files.createDirectory(dataFolder);
            }
            FileHelper fileHelper = new FileHelper(dataFolder);

            action.executor().execute(dataArg, fileHelper);
        } catch (Exception e) {
            System.out.println("### Action failed: " + e.getMessage() + "\n");
            printTaskHelp(task);
        }
    }

    private Optional<TaskConfig> findTask(String taskArg) {
        try {
            int n = Integer.parseInt(taskArg);
            for (TaskConfig t : tasks) {
                if (t.id() == n) {
                    return Optional.of(t);
                }
            }
        } catch (NumberFormatException ignored) {}
        return Optional.empty();
    }

    private void printGeneralHelp() {
        System.out.println("Usage: --task <id> --action <name> [--data <data>] [--tips]\n");
        System.out.println("Options:");
        System.out.println("  --task, -t <id>   Select a task by id");
        System.out.println("  --action, -a <name>   Choose an action exposed by the task");
        System.out.println("  --data, -d <data>     Optional string data passed to the action");
        System.out.println("  --tips                Show tips for the selected task");
        System.out.println();
        printAvailable();
    }

    private void printTaskHelp(TaskConfig task) {
        System.out.println("Task: id=" + task.id() + " " + task.displayName());
        System.out.println("Available actions:");
        for (CliAction a : task.actions()) {
            System.out.println("  " + a.name() + " - " + a.description());
        }
    }

    private void printTaskTips(TaskConfig task) {
        System.out.println("Task: id=" + task.id() + " " + task.displayName());
        if (task.tips() == null || task.tips().isEmpty()) {
            System.out.println("No tips available for this task.");
        } else {
            System.out.println("Tips:");
            for (String tip : task.tips()) {
                System.out.println("  - " + tip);
            }
        }
    }

    private void printAvailable() {
        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            TaskConfig t = tasks.get(i);
            System.out.println("  " + (i + 1) + ") id=" + t.id() + " - " + t.displayName());
        }
    }
}
