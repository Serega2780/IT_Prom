package org.test.hrdept.dao;

import org.test.hrdept.domain.Department;

import java.util.List;

public interface DepartmentDao {

    void insertDepartment(Department department);

    Department selectDepartmentById(int id);

    List<Department> selectAllDepartments();

    Department selectDepartmentByName(String name);

    boolean deleteDepartment(int id);

    void updateDepartment(Department department);
}
