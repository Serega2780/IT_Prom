package org.test.hrdept.dao;

import org.test.hrdept.domain.Employee;

import java.util.List;

public interface EmployeeDao {

    void insertEmployee(Employee Employee);

    Employee selectEmployeeById(int id);

    List<Employee> selectAllEmployees();

    Employee selectEmployeeByName(String name);

    boolean deleteEmployee(int id);

    void updateEmployee(Employee Employee);
}
