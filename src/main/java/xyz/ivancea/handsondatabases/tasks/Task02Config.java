package xyz.ivancea.handsondatabases.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task02.Task02Operations;
import xyz.ivancea.handsondatabases.tasks.task02.Task02;

public class Task02Config implements TaskConfig<Task02Operations> {

    @Override
    public int id() {
        return 2;
    }

    @Override
    public String displayName() {
        return "Store and read an array of integers";
    }

    @Override
    public List<String> tips() {
        return List.of("Use \"fileHelper.write(fileName, StandardOpenOption.APPEND)\" to append data to the end of the file");
    }

    @Override
    public Task02Operations getTask(FileHelper fileHelper) {
        return new Task02(fileHelper);
    }

    @Override
    public Task02Operations getSolution(FileHelper fileHelper) {
        throw new UnsupportedOperationException("Task 2 solution is not implemented yet");
    }

    @Override
    public List<CliAction<Task02Operations>> actions() {
        return Arrays.asList(new CliAction<>("store", "Store integers, passed as --data \"1,2,3\"", (data, task) -> {
            if (data == null) {
                throw new IllegalArgumentException("store requires data argument");
            }

            List<Integer> numbers = parseIntegers(data);
            task.store(numbers);
        }), new CliAction<>("append", "Append integers to existing array, passed as --data \"4,5,6\"", (data, task) -> {
            if (data == null) {
                throw new IllegalArgumentException("append requires data argument");
            }

            List<Integer> numbers = parseIntegers(data);
            task.append(numbers);
        }), new CliAction<>("read", "Reads all stored integers", (_, task) -> {
            List<Integer> response = task.read();
            if (response == null || response.isEmpty()) {
                System.out.println("No integers stored");
            } else {
                System.out.println("Integers: " + response);
            }
        }));
    }

    private List<Integer> parseIntegers(String data) {
        try {
            return Arrays.stream(data.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer format. Expected comma-separated integers like \"1,2,3\"");
        }
    }
}
