package org.example.service;

import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private final EmployeeService service = new EmployeeService();
    private final List<Employee> data = List.of(
            new Employee(1, "A", "A", 10_000),
            new Employee(2, "B", "B", 20_000),
            new Employee(3, "C", "C", 30_000)
    );

    @Test
    void getEmployeeById_found() {
        // arrange
        int id = 2;
        // act
        Employee e = service.getEmployeeById(id, data);
        // assert
        assertEquals(id, e.getId());
        assertEquals("B", e.getFirstName());
    }

    @Test
    void getEmployeeById_notFound_throws() {
        // arrange
        int id = 99;
        // act + assert
        assertThrows(EmployeeNotFoundException.class,
                () -> service.getEmployeeById(id, data));
    }

    @Test
    void getEmployeesBySalaryGreaterThan_inclusiveBoundary() {
        // arrange
        int target = 20_000;
        // act
        var res = service.getEmployeesBySalaryGreaterThan(target, data);
        // assert
        assertEquals(2, res.size());
        assertTrue(res.stream().allMatch(e -> e.getSalary() >= target));
    }

    @Test
    void getEmployeesBySalaryGreaterThan_emptyWhenNoMatch() {
        // arrange
        int target = 100_000;
        // act
        var res = service.getEmployeesBySalaryGreaterThan(target, data);
        // assert
        assertTrue(res.isEmpty());
    }

    @Test
    void getEmployeeMap_buildsKeysAndValues() {
        // arrange
        // act
        Map<String, Employee> map = service.getEmployeeMap(data);
        // assert
        assertEquals(3, map.size());
        assertTrue(map.containsKey("id1"));
        assertEquals(3, map.get("id3").getId());
    }
}
