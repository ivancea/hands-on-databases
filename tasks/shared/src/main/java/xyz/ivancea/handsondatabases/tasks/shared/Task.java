package xyz.ivancea.handsondatabases.tasks.shared;

import xyz.ivancea.handsondatabases.tasks.shared.helpers.FileHelper;

public abstract class Task {
    protected final FileHelper fileHelper;

    protected Task(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }
}
