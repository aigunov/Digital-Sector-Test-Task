package org.example.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(int id) {
        super("Employee with id " + id + " not found");
    }

    public EmployeeNotFoundException(int id, Throwable cause) {
        super("Employee with id " + id + " not found", cause);
    }
}
