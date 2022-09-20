package com.svj.controller;

import com.svj.entity.Instructor;
import com.svj.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
public class SchoolController {
    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService){
        this.schoolService= schoolService;
    }

    @PostMapping("/instructor")
    public ResponseEntity<Instructor> saveInstructor(@RequestBody Instructor instructor){
        Instructor saveInstructor = schoolService.saveInstructor(instructor);
        return new ResponseEntity<>(saveInstructor, HttpStatus.OK);
    }

    @DeleteMapping("/instructor/{id}")
    public ResponseEntity deleteInstructor(@PathVariable String id){
        schoolService.deleteInstructor(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/instructorDetails/{id}")
    public ResponseEntity getInstructorByDetailsID(@PathVariable(value = "id") String instrDetailsID){
        Instructor instructor= schoolService.getInstructorFromDetailsID(instrDetailsID);
        return new ResponseEntity(instructor, HttpStatus.OK);
    }

    @DeleteMapping("/instructorDetails/alone/{id}")
    public ResponseEntity deleteInstructorDetailsOnly(@PathVariable(value = "id") String instrDetailsID){
        schoolService.deleteOnlyInstructorDetails(instrDetailsID);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }


}
