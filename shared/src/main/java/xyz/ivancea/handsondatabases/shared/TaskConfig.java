package xyz.ivancea.handsondatabases.shared;

import java.util.List;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

public interface TaskConfig<T> {
    int id();

    String displayName();

    T getTask(FileHelper fileHelper);

    T getSolution(FileHelper fileHelper);

    List<CliAction<T>> actions();

    List<String> tips();
}
