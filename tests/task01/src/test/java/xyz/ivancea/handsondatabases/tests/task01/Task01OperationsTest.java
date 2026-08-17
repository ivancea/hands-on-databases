package xyz.ivancea.handsondatabases.tests.task01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task01.Task01Operations;
import xyz.ivancea.handsondatabases.solutions.task01.Task01SolutionAdapter;
import xyz.ivancea.handsondatabases.tasks.task01.Task01;
import xyz.ivancea.handsondatabases.tests.shared.TaskOperationsTestCase;

class Task01OperationsTest extends TaskOperationsTestCase<Task01Operations> {

    @ParameterizedTest
    @ValueSource(ints = { 42, 0, -42, Integer.MAX_VALUE, Integer.MIN_VALUE, 1, -1 })
    void storesAndReadsNumber(int number) {
        task.store(number);

        assertThat(task.read()).isEqualTo(number);
    }

    @Test
    void returnsNullWhenFileDoesNotExist() {
        assertThat(task.read()).isNull();
    }

    @Test
    void overwritesPreviousValue() {
        task.store(100);
        task.store(200);

        assertThat(task.read()).isEqualTo(200);
    }

    @Test
    void repeatedReadsReturnSameValue() {
        task.store(123);

        assertThat(task.read()).isEqualTo(123);
        assertThat(task.read()).isEqualTo(123);
        assertThat(task.read()).isEqualTo(123);
    }

    @Test
    void storingSameValueRepeatedlyPreservesIt() {
        task.store(42);
        assertThat(task.read()).isEqualTo(42);

        task.store(42);
        assertThat(task.read()).isEqualTo(42);

        task.store(42);
        assertThat(task.read()).isEqualTo(42);
    }

    @Override
    protected Task01Operations createTask(FileHelper fileHelper) {
        return switch (implementation()) {
            case "exercise" -> new Task01(fileHelper);
            case "solution" -> new Task01SolutionAdapter(fileHelper);
            default -> throw new IllegalStateException("Unknown test implementation: " + implementation());
        };
    }

    @Override
    protected void doCheckImplementation(Task01Operations task) {
        task.store(42);
        task.read();
    }
}
