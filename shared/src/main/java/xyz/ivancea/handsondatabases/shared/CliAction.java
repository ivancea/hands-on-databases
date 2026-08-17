package xyz.ivancea.handsondatabases.shared;

public record CliAction<T>(String name, String description, CliAction.Executor<T> executor) {

    @FunctionalInterface
    public interface Executor<T> {
        void execute(String data, T task);
    }
}
