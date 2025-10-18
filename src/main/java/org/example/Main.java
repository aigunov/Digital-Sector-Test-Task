package org.example;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.FileLoadException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.service.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        FileService fileService = new FileService();

        String filename = "data/employees.csv";

        List<Employee> seed = new ArrayList<>(Arrays.asList(
                new Employee(1, "Иван", "Петров", 50_000),
                new Employee(2, "Мария", "Иванова", 65_000),
                new Employee(3, "Дмитрий", "Соколов", 80_000),
                new Employee(4, "Анна", "Кузнецова", 45_000),
                new Employee(5, "Сергей", "Смирнов", 70_000),
                new Employee(6, "Ольга", "Федорова", 90_000)
        ));

        System.out.println("Сохраняем сотрудников в файл: " + filename);
        fileService.saveEmployeesToFile(seed, filename);

        System.out.println("Выгружаем сотрудников из файла");
        try {
            List<Employee> employees = fileService.loadEmployeesFromFile(filename);
            employees.forEach(System.out::println);

            System.out.println("\n Поиск по id=3");
            Employee e3 = employeeService.getEmployeeById(3, employees);
            System.out.println("Найден: " + e3);

            System.out.println("\n  Поиск по id=99 (ожидаем EmployeeNotFoundException)");
            try {
                employeeService.getEmployeeById(99, employees);
            } catch (EmployeeNotFoundException ex) {
                System.out.println("Обработано: " + ex.getMessage());
            }

            System.out.println("\n  Сотрудники с зарплатой >= 65000");
            List<Employee> rich = employeeService.getEmployeesBySalaryGreaterThan(65_000, employees);
            rich.forEach(System.out::println);

            System.out.println("\n  Map-представление (ключ вида 'id{number}')");
            Map<String, Employee> map = employeeService.getEmployeeMap(employees);
            map.forEach((k, v) -> System.out.println(k + " -> " + v));

        } catch (FileLoadException ioEx) {
            System.err.println("Ошибка чтения файла: " + ioEx.getMessage());
        }

    }
}
