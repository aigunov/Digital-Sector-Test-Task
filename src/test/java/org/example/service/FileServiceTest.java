package org.example.service;

import org.example.exception.FileLoadException;
import org.example.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private final FileService fileService = new FileService();

    @TempDir
    Path tmp;

    @Test
    void saveAndLoad_roundTrip_ok() throws Exception {
        // arrange
        List<Employee> src = List.of(
                new Employee(1, "Ivan", "Petrov", 50_000),
                new Employee(2, "Maria", "Ivanova", 65_000)
        );
        Path file = tmp.resolve("employees.csv");
        String filename = file.toString();

        // act
        fileService.saveEmployeesToFile(src, filename);
        List<Employee> dst = fileService.loadEmployeesFromFile(filename);

        // assert
        assertEquals(src.size(), dst.size());
        assertEquals(1, dst.get(0).getId());
        assertEquals("Ivan", dst.get(0).getFirstName());
        assertEquals(65_000, dst.get(1).getSalary());
    }

    @Test
    void load_missingFile_throwsFileLoadException() {
        // arrange
        String filename = tmp.resolve("absent.csv").toString();
        // act + assert
        assertThrows(FileLoadException.class,
                () -> fileService.loadEmployeesFromFile(filename));
    }

    @Test
    void load_skipsBadLines_andContinues() throws Exception {
        // arrange
        Path file = tmp.resolve("bad_lines.csv");
        Files.writeString(file, String.join("\n",
                "1,Ivan,Petrov,50000",
                "bad_line_here",
                "2,Maria,Ivanova,not_a_number",
                "3,Dmitry,Sokolov,80000"
        ), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // act
        List<Employee> res = fileService.loadEmployeesFromFile(file.toString());

        // assert
        assertEquals(2, res.size());
        assertEquals(1, res.get(0).getId());
        assertEquals(3, res.get(1).getId());
    }
}