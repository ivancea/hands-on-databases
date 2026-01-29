package xyz.ivancea.handsondatabases;

import java.util.List;
import java.util.Optional;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;

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
            action.executor().execute(dataArg);
        } catch (Exception e) {
            System.out.println("Action failed: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private Optional<TaskConfig> findTask(String taskArg) {
        // try numeric value: match task id first, then index (1-based)
        try {
            int n = Integer.parseInt(taskArg);
            // first match by task.id()
            for (TaskConfig t : tasks) {
                if (t.id() == n) return Optional.of(t);
            }
            // fallback: treat as 1-based index
            if (n >= 1 && n <= tasks.size()) {
                return Optional.of(tasks.get(n - 1));
            }
        } catch (NumberFormatException ignored) {}
        return Optional.empty();
    }

    private void printGeneralHelp() {
        System.out.println("Usage: --task <id> --action <name> [--data <data>]\n");
        System.out.println("Options:");
        System.out.println("  --task, -t <id>   Select a task by id");
        System.out.println("  --action, -a <name>   Choose an action exposed by the task");
        System.out.println("  --data, -d <data>     Optional string data passed to the action");
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

    private void printAvailable() {
        System.out.println("Available tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            TaskConfig t = tasks.get(i);
            System.out.println("  " + (i + 1) + ") id=" + t.id() + " - " + t.displayName());
        }
    }
}
