package xyz.ivancea.handsondatabases.solutions.task01;

import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;
import xyz.ivancea.handsondatabases.shared.tasks.task01.Task01Operations;

/** CLI adapter; follow {@link Task01Solution} only if you want to see the solution. */
public class Task01SolutionAdapter implements Task01Operations {
    private final Task01Solution solution;

    public Task01SolutionAdapter(FileHelper fileHelper) {
        this(new Task01Solution(fileHelper));
    }

    Task01SolutionAdapter(Task01Solution solution) {
        this.solution = solution;
    }

    @Override
    public void store(int number) {
        solution.store(number);
    }

    @Override
    public Integer read() {
        return solution.read();
    }
}
