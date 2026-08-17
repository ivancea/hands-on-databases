package xyz.ivancea.handsondatabases.tests.shared;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TaskOperationsTestCase<T> {
    private FileHelper fileHelper;

    @BeforeAll
    protected void checkImplementation(@TempDir Path implementationCheckDirectory) throws Exception {
        if (!implementation().equals("exercise")) {
            return;
        }

        fileHelper = new FileHelper(implementationCheckDirectory);
        try {
            doCheckImplementation(task());
        } catch (UnsupportedOperationException e) {
            assumeTrue(false, "Task not implemented - skipping exercise tests");
        } catch (Exception ignored) {
            // Other exceptions indicate an attempted implementation that the tests should diagnose.
        }
    }

    @BeforeEach
    protected void setUpTask(@TempDir Path tempDir) {
        fileHelper = new FileHelper(tempDir);
    }

    /** Creates a new selected implementation under test. */
    protected final T task() {
        return switch (implementation()) {
            case "exercise" -> createTask(fileHelper);
            case "solution" -> createSolution(fileHelper);
            default -> throw new IllegalStateException("Unknown test implementation: " + implementation());
        };
    }

    protected abstract T createTask(FileHelper fileHelper);

    protected abstract T createSolution(FileHelper fileHelper);

    /** Calls every operation once to ensure it is implemented. */
    protected abstract void doCheckImplementation(T implementation) throws Exception;

    protected final String implementation() {
        return System.getProperty("implementation", "exercise");
    }
}
