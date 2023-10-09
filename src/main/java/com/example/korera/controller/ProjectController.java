package com.example.korera.controller;

import com.example.korera.entity.Project;
import com.example.korera.entity.Resource;
import com.example.korera.exceptions.FormulaIsNullException;
import com.example.korera.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;


    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project,BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Project p = projectService.createProject(project);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/add/{id}")
//    public ResponseEntity<?> addProjectToUserById(@RequestBody Project project, @PathVariable Integer id){
//
//    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        if(project.getFormulas()==null){
            throw new FormulaIsNullException("formula cannot be null when updating");
        }
        Project p = projectService.updateProject(project);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

//    @PutMapping("/updateresource")

    @GetMapping("/get/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        Project p = projectService.getProjectById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Project>> getAllProject() {
        List<Project> projects = projectService.getAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

}
