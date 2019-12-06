package org.test.hrdept.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.dao.ProfessionDao;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelView = new ModelAndView("main.html");
        return modelView;
    }

}
