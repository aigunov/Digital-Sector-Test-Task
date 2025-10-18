package org.example.service;

import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    /**
     *
     * Находит сотрудника по id в переданном списке.
     *
     * @throws IllegalArgumentException  если список null
     * @throws EmployeeNotFoundException если сотрудник не найден
     */
    public Employee getEmployeeById(Integer id, List<Employee> employees) {
        if (employees == null) {
            throw new IllegalArgumentException("employees list must not be null");
        }

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        throw new EmployeeNotFoundException(id);
    }


    /**
     * Возвращает сотрудников с зарплатой >= targetSalary.
     * Если список null — возвращает пустой список.
     */
    public List<Employee> getEmployeesBySalaryGreaterThan(Integer targetSalary, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        if (employees == null) {
            return result;
        }

        for (Employee employee : employees) {
            if (employee.getSalary() >= targetSalary) {
                result.add(employee);
            }
        }

        return result;
    }

    /**
     * Преобразует список сотрудников в Map с ключом "id{number}", например "id5".
     * Если список null — возвращает пустую Map
     */
    public Map<String, Employee> getEmployeeMap(List<Employee> employees) {
        var map = new HashMap<String, Employee>();
        if (employees == null) {
            return map;
        }

        for (Employee employee : employees) {
            map.put("id" + employee.getId(), employee);
        }

        return map;
    }
}
