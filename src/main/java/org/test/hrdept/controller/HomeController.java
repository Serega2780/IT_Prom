package org.test.hrdept.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.dao.ProfessionDao;
import org.test.hrdept.domain.Department;
import org.test.hrdept.domain.Employee;
import org.test.hrdept.domain.Profession;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    private DepartmentDao departmentDao;
    private ProfessionDao professionDao;

    @Resource(name = "departmentDao")
    public void setUserDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Resource(name = "professionDao")
    public void setProfessionDao(ProfessionDao professionDao) {
        this.professionDao = professionDao;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelView = new ModelAndView("main.html");

//        Department department = new Department("myDept", "myDeptDesc");
//        Department department2 = departmentDao.selectDepartmentByName("IT");
//        department.setDepartment(department2);
//        departmentDao.insertDepartment(department);
//
//
//        List<Department> deptList = departmentDao.selectAllDepartments();
//        modelView.addObject("deptList", deptList);

        return modelView;
    }

//    @RequestMapping(value = {"/deps"}, method = RequestMethod.GET)
//    public ModelAndView listEmp() {
//        ModelAndView modelView = new ModelAndView("/main.html");
//        List<Employee> deptList = departmentDao.selectAllEmployees();
//        modelView.addObject("empList", empList);
//        return modelView;
//    }

    //REST

}
