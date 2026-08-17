package xyz.ivancea.handsondatabases.shared.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Helper for file operations.
 * <p>
 *     This class provides basic file operations,
 *     but manual handling through the {@link #directory} field may be required.
 * </p>
 * <p>
 *     It's recommended to read the methods and understand their behavior
 *     before using them, and potentially using the appropriate APIs directly.
 * </p>
 * @param directory the base directory where data files are located. Fully managed by the actual task.
 */
public record FileHelper(Path directory) {

    public Path resolve(String fileName) {
        return directory.resolve(fileName);
    }

    public boolean exists(String fileName) {
        return Files.exists(resolve(fileName));
    }

    public boolean create(String fileName) {
        try {
            Files.createFile(resolve(fileName));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean delete(String fileName) {
        try {
            return Files.deleteIfExists(resolve(fileName));
        } catch (IOException e) {
            return false;
        }
    }

    public FileChannel open(String fileName) throws IOException {
        return FileChannel.open(resolve(fileName), StandardOpenOption.READ, StandardOpenOption.WRITE);
    }

    public InputStream read(String fileName) throws IOException {
        return Files.newInputStream(resolve(fileName));
    }

    public OutputStream write(String fileName, OpenOption... options) throws IOException {
        return Files.newOutputStream(resolve(fileName), options);
    }
}
