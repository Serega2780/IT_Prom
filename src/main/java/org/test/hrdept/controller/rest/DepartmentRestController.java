package org.test.hrdept.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.domain.Department;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/dept/*")
public class DepartmentRestController {

    private DepartmentDao departmentDao;

    @Resource(name = "departmentDao")
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @RequestMapping(value = "/depts")
    public ResponseEntity<List<Department>> getDepartments() {
        return new ResponseEntity<>(departmentDao.selectAllDepartments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/dept")
    public ResponseEntity<Department> getDepartmentByName(@RequestParam("name") String name) {
        Department department = departmentDao.selectDepartmentByName(name);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit")
    public ResponseEntity<Department> getDepartmentById(@RequestParam("id") int id) {
        return new ResponseEntity<>(departmentDao.selectDepartmentById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        departmentDao.updateDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Department> insertDepartment(@RequestBody Department department) {
        departmentDao.insertDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Department> deleteDepartment(@RequestParam("id") int id) {
        departmentDao.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
