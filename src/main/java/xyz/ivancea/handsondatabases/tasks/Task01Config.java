package xyz.ivancea.handsondatabases.tasks;

import java.util.Arrays;
import java.util.List;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task01.Task01Operations;
import xyz.ivancea.handsondatabases.solutions.task01.Task01SolutionAdapter;
import xyz.ivancea.handsondatabases.tasks.task01.Task01;

public class Task01Config implements TaskConfig<Task01Operations> {

    @Override
    public int id() {
        return 1;
    }

    @Override
    public String displayName() {
        return "Store and read a single number";
    }

    @Override
    public List<String> tips() {
        return List.of(
            "Use fileHelper.create(fileName) to create a new file",
            "Use fileHelper.write(fileName) to get an OutputStream for writing to the file",
            "Use fileHelper.read(fileName) to get an InputStream for reading the file",
            "Use fileHelper.open(fileName) to get a FileChannel for reading and writing"
        );
    }

    @Override
    public Task01Operations getTask(FileHelper fileHelper) {
        return new Task01(fileHelper);
    }

    @Override
    public Task01Operations getSolution(FileHelper fileHelper) {
        return new Task01SolutionAdapter(fileHelper);
    }

    @Override
    public List<CliAction<Task01Operations>> actions() {
        return Arrays.asList(new CliAction<>("store", "Store a number, passed as --data \"123\"", (data, task) -> {
            if (data == null) {
                throw new IllegalArgumentException("store requires data argument");
            }

            task.store(Integer.parseInt(data));
        }), new CliAction<>("read", "Reads the stored number", (_, task) -> {
            Integer response = task.read();
            if (response == null) {
                System.out.println("No number stored");
            } else {
                System.out.println("Number: " + response);
            }
        }));
    }
}
