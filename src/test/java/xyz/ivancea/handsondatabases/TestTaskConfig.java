package xyz.ivancea.handsondatabases;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;

/**
 * @param actionConsumer A consumer that accepts action name and data.
 */
public record TestTaskConfig(BiConsumer<String, String> actionConsumer) implements TaskConfig {
    public static final int TASK_ID = 111222333;
    public static final String TASK_DISPLAY_NAME = "Test Task";

    public static final String ACTION_1_NAME = "action1";
    public static final String ACTION_1_DESCRIPTION = "Action one";
    public static final String ACTION_2_NAME = "action2";
    public static final String ACTION_2_DESCRIPTION = "Action two";

    public TestTaskConfig() {
        this((name, data) -> System.out.println(name + ":" + data));
    }

    @Override
    public int id() {
        return TASK_ID;
    }

    @Override
    public String displayName() {
        return TASK_DISPLAY_NAME;
    }

    @Override
    public List<CliAction> actions() {
        return Arrays.asList(
            new CliAction(ACTION_1_NAME, ACTION_1_DESCRIPTION, (data, _) -> actionConsumer.accept(ACTION_1_NAME, data)),
            new CliAction(ACTION_2_NAME, ACTION_2_DESCRIPTION, (data, _) -> actionConsumer.accept(ACTION_2_NAME, data))
        );
    }
}
