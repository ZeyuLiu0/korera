package com.example.korera.controller;

import com.example.korera.entity.Formula;
import com.example.korera.entity.Project;
import com.example.korera.repository.ProjectRep;
import com.example.korera.service.FormulaService;
import com.example.korera.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formula")
public class FormulaController {

    private final FormulaService formulaService;

    private final ProjectService projectService;

    @Autowired
    public FormulaController(FormulaService formulaService, ProjectService projectService) {
        this.formulaService = formulaService;
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<Formula> createFormula(@RequestBody Formula formula) {
        Integer id = formula.getProject().getProjectId();
        Project project = projectService.getProjectById(id);
        formula.setProject(project);
        Formula f = formulaService.createFormula(formula);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFormula(@PathVariable int id) {
        formulaService.deleteFormulaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Formula> updateFormula(@RequestBody Formula formula) {
        Integer id = formula.getProject().getProjectId();
        Project project = projectService.getProjectById(id);
        formula.setProject(project);
        Formula f = formulaService.updateFormula(formula);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Formula> getFormulaById(@PathVariable int id) {
        Formula f = formulaService.getFormulaById(id);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Formula>> getAllFormula() {
        List<Formula> formulas = formulaService.getAll();
        return new ResponseEntity<>(formulas, HttpStatus.OK);
    }

}
