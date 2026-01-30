package xyz.ivancea.handsondatabases.shared;

import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

public record CliAction(String name, String description, CliAction.Executor executor) {

    @FunctionalInterface
    public interface Executor {
        void execute(String data, FileHelper fileHelper);
    }
}
