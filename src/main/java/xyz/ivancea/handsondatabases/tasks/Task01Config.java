package xyz.ivancea.handsondatabases.tasks;

import java.util.Arrays;
import java.util.List;
import xyz.ivancea.handsondatabases.shared.CliAction;
import xyz.ivancea.handsondatabases.shared.TaskConfig;
import xyz.ivancea.handsondatabases.tasks.task01.Task01;

public class Task01Config implements TaskConfig {

    @Override
    public int id() {
        return 1;
    }

    @Override
    public String displayName() {
        return "Store and read a single number";
    }

    @Override
    public List<CliAction> actions() {
        return Arrays.asList(new CliAction("store", "Store a number, passed as --data \"123\"", (data, fileHelper) -> {
            if (data == null) {
                throw new IllegalArgumentException("store requires data argument");
            }

            new Task01(fileHelper).store(Integer.parseInt(data));
        }), new CliAction("read", "Reads the stored number", (_, fileHelper) -> {
            Integer response = new Task01(fileHelper).read();
            if (response == null) {
                System.out.println("No number stored");
            } else {
                System.out.println("Number: " + response);
            }
        }));
    }
}
