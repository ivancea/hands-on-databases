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
        return "Task 01 - Basic row storage";
    }

    @Override
    public List<CliAction> actions() {
        return Arrays.asList(new CliAction("storeNumber", "Store a number, passed as --data \"123\"", data -> {
            if (data == null) {
                throw new IllegalArgumentException("storeRow requires data argument");
            }

            Task01.storeNumber(Integer.parseInt(data));
        }), new CliAction("readNumber", "Reads the stored number", data -> {
            if (data == null) {
                throw new IllegalArgumentException("readRow requires data argument (id)");
            }

            Integer response = Task01.readNumber();
            if (response == null) {
                System.out.println("No number stored");
            } else {
                System.out.println("Number: " + response);
            }
        }));
    }
}
