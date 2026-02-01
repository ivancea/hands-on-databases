package xyz.ivancea.handsondatabases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import xyz.ivancea.handsondatabases.shared.TaskConfig;

public class TasksRegistryTest {

    @Test
    public void testGetAll_hasTasks() {
        List<TaskConfig> tasks = TasksRegistry.getAll();
        assertThat(tasks).isNotEmpty();
    }

    @Test
    public void testGetAll_hasNoRepeatedTaskIds() {
        List<TaskConfig> tasks = TasksRegistry.getAll();
        Set<Integer> seenIds = new HashSet<>();
        for (TaskConfig task : tasks) {
            assertThat(seenIds.add(task.id())).withFailMessage("Duplicate task ID found: " + task.id()).isTrue();
        }
    }
}
