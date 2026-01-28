package me.mattia.maze.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static void copyWorld(File source, File target) throws IOException {
        if (source.isDirectory()) {
            if (!target.exists()) target.mkdirs();

            for (String file : source.list()) {
                copyWorld(
                        new File(source, file),
                        new File(target, file)
                );
            }
        } else {
            Files.copy(
                    source.toPath(),
                    target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
