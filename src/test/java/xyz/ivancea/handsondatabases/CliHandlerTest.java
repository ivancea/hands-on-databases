package xyz.ivancea.handsondatabases;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CliHandlerTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private CliHandler handlerWithTest() {
        return new CliHandler(List.of(new TestTaskConfig()));
    }

    // Provider of test cases: full command line string, expectedOutput (multiline
    // text block)
    private static Stream<Arguments> outputCases() {
        return Stream.of(
            Arguments.of("", """
                Usage: --task <id> --action <name> [--data <data>]

                Options:
                  --task, -t <id>   Select a task by id
                  --action, -a <name>   Choose an action exposed by the task
                  --data, -d <data>     Optional string data passed to the action

                Available tasks:
                  1) id=%d - %s
                """.formatted(TestTaskConfig.TASK_ID, TestTaskConfig.TASK_DISPLAY_NAME)),
            Arguments.of(
                "--task 1",
                """
                    Task: id=%d %s
                    Available actions:
                      %s - %s
                      %s - %s
                    """.formatted(
                    TestTaskConfig.TASK_ID,
                    TestTaskConfig.TASK_DISPLAY_NAME,
                    TestTaskConfig.ACTION_1_NAME,
                    TestTaskConfig.ACTION_1_DESCRIPTION,
                    TestTaskConfig.ACTION_2_NAME,
                    TestTaskConfig.ACTION_2_DESCRIPTION
                )
            ),
            Arguments.of(
                "--task " + TestTaskConfig.TASK_ID,
                """
                    Task: id=%d %s
                    Available actions:
                      %s - %s
                      %s - %s
                    """.formatted(
                    TestTaskConfig.TASK_ID,
                    TestTaskConfig.TASK_DISPLAY_NAME,
                    TestTaskConfig.ACTION_1_NAME,
                    TestTaskConfig.ACTION_1_DESCRIPTION,
                    TestTaskConfig.ACTION_2_NAME,
                    TestTaskConfig.ACTION_2_DESCRIPTION
                )
            ),
            Arguments.of(
                "--task 1 --action " + TestTaskConfig.ACTION_1_NAME + " --data hello",
                "%s:hello".formatted(TestTaskConfig.ACTION_1_NAME)
            ),
            Arguments.of(
                "--task 1 --action " + TestTaskConfig.ACTION_1_NAME + " --data \"hello world\"",
                "%s:hello world".formatted(TestTaskConfig.ACTION_1_NAME)
            ),
            Arguments.of("--task unknown", """
                Unknown task: unknown
                Available tasks:
                  1) id=%d - %s
                """.formatted(TestTaskConfig.TASK_ID, TestTaskConfig.TASK_DISPLAY_NAME)),
            Arguments.of(
                "--task 1 --action doesnotexist",
                """
                    Unknown action for task '%d': doesnotexist
                    Task: id=%d %s
                    Available actions:
                      %s - %s
                      %s - %s
                    """.formatted(
                    TestTaskConfig.TASK_ID,
                    TestTaskConfig.TASK_ID,
                    TestTaskConfig.TASK_DISPLAY_NAME,
                    TestTaskConfig.ACTION_1_NAME,
                    TestTaskConfig.ACTION_1_DESCRIPTION,
                    TestTaskConfig.ACTION_2_NAME,
                    TestTaskConfig.ACTION_2_DESCRIPTION
                )
            ),
            Arguments.of("--task 1 --action " + TestTaskConfig.ACTION_2_NAME, "%s:null".formatted(TestTaskConfig.ACTION_2_NAME))
        );
    }

    @ParameterizedTest
    @MethodSource("outputCases")
    public void testOutputCases(String cmdLine, String expected) {
        CliHandler cli = handlerWithTest();
        String[] args = cmdLine == null || cmdLine.isBlank() ? new String[] {} : splitCommand(cmdLine);
        cli.handle(args);
        String s = out.toString().replace("\r\n", "\n");
        // exact match for the full expected text
        assertThat(s.trim()).isEqualTo(expected.trim());
    }

    // Helper to split a command line into args respecting double quotes
    private static String[] splitCommand(String cmdLine) {
        java.util.List<String> parts = new java.util.ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < cmdLine.length(); i++) {
            char c = cmdLine.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ' ' && !inQuotes) {
                if (cur.length() > 0) {
                    parts.add(cur.toString());
                    cur.setLength(0);
                }
            } else {
                cur.append(c);
            }
        }
        if (cur.length() > 0) parts.add(cur.toString());
        return parts.toArray(new String[0]);
    }

    @Test
    public void testActionConsumerIsCalled() {
        AtomicReference<String> called = new AtomicReference<>();
        TestTaskConfig cfg = new TestTaskConfig((name, data) -> called.set(name + ":" + data));
        CliHandler cli = new CliHandler(List.of(cfg));
        cli.handle(
            new String[] { "--task", String.valueOf(TestTaskConfig.TASK_ID), "--action", TestTaskConfig.ACTION_1_NAME, "--data", "payload" }
        );
        assertThat(called.get()).isEqualTo(TestTaskConfig.ACTION_1_NAME + ":payload");
    }
}
