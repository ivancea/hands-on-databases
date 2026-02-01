package xyz.ivancea.handsondatabases.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.tasks.task02.Task02;

public class Task02Config implements TaskConfig {

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
    public List<CliAction> actions() {
        return Arrays.asList(new CliAction("store", "Store integers, passed as --data \"1,2,3\"", (data, fileHelper) -> {
            if (data == null) {
                throw new IllegalArgumentException("store requires data argument");
            }

            List<Integer> numbers = parseIntegers(data);
            new Task02(fileHelper).store(numbers);
        }), new CliAction("append", "Append integers to existing array, passed as --data \"4,5,6\"", (data, fileHelper) -> {
            if (data == null) {
                throw new IllegalArgumentException("append requires data argument");
            }

            List<Integer> numbers = parseIntegers(data);
            new Task02(fileHelper).append(numbers);
        }), new CliAction("read", "Reads all stored integers", (_, fileHelper) -> {
            List<Integer> response = new Task02(fileHelper).read();
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
