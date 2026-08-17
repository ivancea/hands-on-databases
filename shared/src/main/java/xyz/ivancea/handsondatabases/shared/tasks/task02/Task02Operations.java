package xyz.ivancea.handsondatabases.shared.tasks.task02;

import java.util.List;

public interface Task02Operations {
    void store(List<Integer> numbers);

    void append(List<Integer> numbers);

    List<Integer> read();
}
