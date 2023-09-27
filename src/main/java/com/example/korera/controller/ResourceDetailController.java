package com.example.korera.controller;
import com.example.korera.entity.ResourceDetail;
import com.example.korera.service.ResourceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourcedetail")
public class ResourceDetailController {
    private final ResourceDetailService resourceServiceImp;

    @Autowired
    public ResourceDetailController(ResourceDetailService resourceServiceImp){
        this.resourceServiceImp = resourceServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<ResourceDetail> createResource(@RequestBody ResourceDetail resourceDetail){
        ResourceDetail resourceDetail1 = resourceServiceImp.createResourceDetail(resourceDetail);
        return new ResponseEntity<>(resourceDetail1, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResourceDetail> deleteResource(@PathVariable Integer id){
        ResourceDetail ResourceDetail = resourceServiceImp.deleteResourceDetailById(id);
        return new ResponseEntity<>(ResourceDetail,HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update")
    public ResponseEntity<ResourceDetail> updateResource(ResourceDetail ResourceDetail){
        ResourceDetail r  = resourceServiceImp.updateResourceDetail(ResourceDetail);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResourceDetail> getResourceById(@PathVariable Integer id){
        ResourceDetail r  = resourceServiceImp.getResourceDetailById(id);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ResourceDetail>> getAll(){
        List<ResourceDetail> resources = resourceServiceImp.getAll();
        return new ResponseEntity<>(resources,HttpStatus.OK);
    }
}