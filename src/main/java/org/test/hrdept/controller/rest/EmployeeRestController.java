package org.test.hrdept.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.hrdept.dao.EmployeeDao;
import org.test.hrdept.domain.Employee;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/emp/*")
public class EmployeeRestController {

    private EmployeeDao employeeDao;

    @Resource(name = "employeeDao")
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @RequestMapping(value = "/emps")
    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeDao.selectAllEmployees(), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam("id") int id) {
        return new ResponseEntity<>(employeeDao.selectEmployeeById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        employeeDao.updateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee) {
        employeeDao.insertEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Employee> deleteEmployee(@RequestParam("id") int id) {
        employeeDao.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
