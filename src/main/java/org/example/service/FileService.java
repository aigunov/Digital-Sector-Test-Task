package org.example.service;

import org.example.exception.FileLoadException;
import org.example.model.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileService {


    /**
     * Сохраняет список сотрудников в текстовый csv-файл.
     * Если файл отсутствуют — будет создан.
     */
    public void saveEmployeesToFile(List<Employee> employees, String filename) {
        Path path = Paths.get(filename);

        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                System.err.println("Не получается создать директорию для: '" + filename + "': " + e.getMessage());
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path, StandardCharsets.UTF_8, CREATE, TRUNCATE_EXISTING)) {

            if (employees != null) {
                for (Employee e : employees) {
                    writer.write(e.getId() + "," +
                            e.getFirstName() + "," +
                            e.getLastName() + "," +
                            e.getSalary());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл '" + filename + "': " + e.getMessage());
        }
    }

    /**
     * Загружает сотрудников из csv.
     * @throws FileLoadException если файл отсутствует .
     * Некорректные строки пропускаются а ошибки печатаются в System.err.
     */
    public List<Employee> loadEmployeesFromFile(String filename) throws FileLoadException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            throw new FileLoadException(filename);
        }

        List<Employee> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNo = 0;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.err.println("Неккоректный формат данных на строке #" + lineNo + ": " + line);
                    continue;
                }

                String idStr = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName  = parts[2].trim();
                String salaryStr = parts[3].trim();

                try {
                    int id = Integer.parseInt(idStr);
                    int salary = Integer.parseInt(salaryStr);
                    result.add(new Employee(id, firstName, lastName, salary));
                } catch (NumberFormatException nfe) {
                    System.err.println("Ошибка парсинга числа на #" + lineNo + ": " + line);
                }
            }
        } catch (IOException io) {
            throw new FileLoadException(filename, io);
        }
        return result;
    }
}
