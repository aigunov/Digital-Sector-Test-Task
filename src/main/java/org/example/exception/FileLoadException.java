package org.example.exception;

public class FileLoadException extends Exception {
    public FileLoadException(String filename) {
        super("Failed to load employees from file: " + filename);
    }

    public FileLoadException(String filename, Throwable cause) {
        super("Failed to load employees from file: " + filename,  cause);
    }
}
