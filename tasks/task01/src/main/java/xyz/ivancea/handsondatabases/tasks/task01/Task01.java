package xyz.ivancea.handsondatabases.tasks.task01;

import xyz.ivancea.handsondatabases.shared.Task;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task01.Task01Operations;

public class Task01 extends Task implements Task01Operations {
    public Task01(FileHelper fileHelper) {
        super(fileHelper);
    }

    @Override
    public void store(int number) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Integer read() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
