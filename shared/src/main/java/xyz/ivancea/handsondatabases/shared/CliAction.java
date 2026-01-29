package xyz.ivancea.handsondatabases.shared;

public record CliAction(String name, String description, CliAction.Executor executor) {

    @FunctionalInterface
    public interface Executor {
        void execute(String data) throws Exception;
    }
}
