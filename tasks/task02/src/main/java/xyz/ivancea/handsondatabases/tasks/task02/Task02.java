package xyz.ivancea.handsondatabases.tasks.task02;

import java.util.List;
import xyz.ivancea.handsondatabases.shared.Task;
import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task02.Task02Operations;

public class Task02 extends Task implements Task02Operations {
    public Task02(FileHelper fileHelper) {
        super(fileHelper);
    }

    @Override
    public void store(List<Integer> numbers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void append(List<Integer> numbers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Integer> read() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
