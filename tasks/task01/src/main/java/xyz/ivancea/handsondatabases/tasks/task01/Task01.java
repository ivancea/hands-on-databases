package xyz.ivancea.handsondatabases.tasks.task01;

import xyz.ivancea.handsondatabases.tasks.shared.Task;
import xyz.ivancea.handsondatabases.tasks.shared.helpers.FileHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Task01 extends Task {
    public Task01(FileHelper fileHelper) {
        super(fileHelper);
    }

    private static final String FILE_NAME = "number.txt";

    public void store(int number) {
        if (fileHelper.exists(FILE_NAME)) {
            fileHelper.delete(FILE_NAME);
        }
        fileHelper.create(FILE_NAME);

        try (var file = fileHelper.open(FILE_NAME)) {
            ByteBuffer buffer = ByteBuffer.wrap(String.valueOf(number).getBytes());
            file.write(buffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer read() {
        if (!fileHelper.exists(FILE_NAME)) {
            return null;
        }

        try (var inputStream = new BufferedInputStream(fileHelper.read(FILE_NAME))) {
            String string = new String(inputStream.readAllBytes());
            return Integer.parseInt(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
