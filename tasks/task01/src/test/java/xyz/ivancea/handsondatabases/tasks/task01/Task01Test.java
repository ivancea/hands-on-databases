package xyz.ivancea.handsondatabases.tasks.task01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import xyz.ivancea.handsondatabases.tasks.shared.helpers.FileHelper;

import java.nio.file.Path;

class Task01Test {
    @TempDir
    Path tempDir;

    private FileHelper fileHelper;
    private Task01 task;

    @BeforeEach
    void setUp() {
        fileHelper = new FileHelper(tempDir);
        task = new Task01(fileHelper);
    }

    @AfterEach
    void tearDown() throws Exception {
        java.nio.file.Files.list(tempDir).forEach(path -> {
            try {
                java.nio.file.Files.deleteIfExists(path);
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        });
    }

    @ParameterizedTest
    @ValueSource(
        ints = {
            42, // Positive number
            0, // Zero
            -42, // Negative number
            Integer.MAX_VALUE, // Maximum integer
            Integer.MIN_VALUE, // Minimum integer
            1, // One
            -1 // Minus one
        }
    )
    void testStoreAndReadNumber(int number) {
        task.store(number);
        Integer result = task.read();
        assertThat(result).isEqualTo(number);
    }

    @Test
    void testReadWhenFileDoesNotExist() {
        Integer result = task.read();
        assertThat(result).isNull();
    }

    @Test
    void testStoreOverwritesPreviousValue() {
        task.store(100);
        task.store(200);
        Integer result = task.read();
        assertThat(result).isEqualTo(200);
    }

    @Test
    void testReadMultipleTimesReturnsSameValue() {
        task.store(123);
        assertThat(task.read()).isEqualTo(123);
        assertThat(task.read()).isEqualTo(123);
        assertThat(task.read()).isEqualTo(123);
    }

    @Test
    void testStoreSameValueMultipleTimes() {
        task.store(42);
        assertThat(task.read()).isEqualTo(42);

        task.store(42);
        assertThat(task.read()).isEqualTo(42);

        task.store(42);
        assertThat(task.read()).isEqualTo(42);
    }
}
