package xyz.ivancea.handsondatabases.shared;

import java.util.List;

public interface TaskConfig {
    int id();

    String displayName();

    List<CliAction> actions();
}
