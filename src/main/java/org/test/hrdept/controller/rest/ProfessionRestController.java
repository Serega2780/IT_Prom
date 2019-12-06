package org.test.hrdept.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.hrdept.dao.ProfessionDao;
import org.test.hrdept.domain.Profession;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/prof/*")
public class ProfessionRestController {

    private ProfessionDao professionDao;

    @Resource(name = "professionDao")
    public void setProfessionDao(ProfessionDao professionDao) {
        this.professionDao = professionDao;
    }

    @RequestMapping(value = "/profs")
    public ResponseEntity<List<Profession>> getProfessions() {
        return new ResponseEntity<>(professionDao.selectAllProfessions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit")
    public ResponseEntity<Profession> getProfessionById(@RequestParam("id") int id) {
        return new ResponseEntity<>(professionDao.selectProfessionById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Profession> updateProfession(@RequestBody Profession profession) {
        professionDao.updateProfession(profession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<Profession> insertProfession(@RequestBody Profession profession) {
        professionDao.insertProfession(profession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Profession> deleteProfession(@RequestParam("id") int id) {
        professionDao.deleteProfession(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
