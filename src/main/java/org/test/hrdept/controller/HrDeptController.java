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
@RestController
@RequestMapping(value = "/")
public class HrDeptController {

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

    @RequestMapping(value = {"/deps"}, method = RequestMethod.GET)
    public ModelAndView listDept() {
        ModelAndView modelView = new ModelAndView("main.html");

        Department department = new Department("myDept", "myDeptDesc");
        Department department2 = departmentDao.selectDepartmentByName("IT");
        department.setDepartment(department2);
        departmentDao.insertDepartment(department);


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

    //REST
    @RequestMapping(value = "/rest/profs")
    public ResponseEntity<List<Profession>> getProfessions() {
        return new ResponseEntity<>(professionDao.selectAllProfessions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/prof/edit")
    public ResponseEntity<Profession> getProfessionById(@RequestParam("id") int id) {
        return new ResponseEntity<>(professionDao.selectProfessionById(id), HttpStatus.OK);
    }

    @PutMapping("/rest/prof/update")
    @ResponseBody
    public ResponseEntity<Profession> updateProfession(@RequestBody Profession profession) {
        professionDao.updateProfession(profession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/prof/insert")
    @ResponseBody
    public ResponseEntity<Profession> insertProfession(@RequestBody Profession profession) {
        professionDao.insertProfession(profession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/rest/prof/delete")
    public ResponseEntity<Profession> deleteProfession(@RequestParam("id") int id) {
        professionDao.deleteProfession(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
