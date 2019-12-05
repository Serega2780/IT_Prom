package org.test.hrdept.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.domain.Department;
import org.test.hrdept.domain.Employee;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HrDeptController {

    private DepartmentDao departmentDao;

    @Resource(name = "departmentDao")
    public void setUserDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @RequestMapping(value = {"/deps"}, method = RequestMethod.GET)
    public ModelAndView listDept() {
        ModelAndView modelView = new ModelAndView("main.html");
        List<Department> deptList = departmentDao.selectAllDepartments();
        modelView.addObject("deptList", deptList);
        return modelView;
    }

//    @RequestMapping(value = {"/deps"}, method = RequestMethod.GET)
//    public ModelAndView listEmp() {
//        ModelAndView modelView = new ModelAndView("/main.html");
//        List<Employee> deptList = departmentDao.selectAllEmployees();
//        modelView.addObject("empList", empList);
//        return modelView;
//    }
}
