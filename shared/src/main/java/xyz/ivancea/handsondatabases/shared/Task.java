package xyz.ivancea.handsondatabases.shared;

import xyz.ivancea.handsondatabases.shared.helpers.FileHelper;

public abstract class Task {
    protected final FileHelper fileHelper;

    protected Task(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }
}
