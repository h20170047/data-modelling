package com.svj.controller;

import com.svj.entity.Course;
import com.svj.entity.Instructor;
import com.svj.entity.Review;
import com.svj.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService){
        this.schoolService= schoolService;
    }

    @PostMapping("/instructors")
    public ResponseEntity<Instructor> saveInstructor(@RequestBody Instructor instructor){
        Instructor saveInstructor = schoolService.saveInstructor(instructor);
        return new ResponseEntity<>(saveInstructor, HttpStatus.OK);
    }

    @DeleteMapping("/instructors/{id}")
    public ResponseEntity deleteInstructor(@PathVariable String id){
        schoolService.deleteInstructor(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/instructorDetails/{id}")
    public ResponseEntity getInstructorByDetailsID(@PathVariable(value = "id") String instrDetailsID){
        Instructor instructor= schoolService.getInstructorDetailsFromID(instrDetailsID);
        return new ResponseEntity(instructor, HttpStatus.OK);
    }

    @GetMapping("/instructors/{id}")
    public ResponseEntity getInstructorById(@PathVariable(value = "id") String instructorId){
        Instructor instructor= schoolService.getInstructorFromID(instructorId);
        return new ResponseEntity(instructor, HttpStatus.OK);
    }

    @DeleteMapping("/instructorDetails/{id}")
    public ResponseEntity deleteInstructorDetailsOnly(@PathVariable(value = "id") String instrDetailsID){
        schoolService.deleteOnlyInstructorDetails(instrDetailsID);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable String courseId){
        schoolService.deleteCourse(courseId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/courses/{instructorId}")
    public ResponseEntity createCourse(@RequestBody Course course, @PathVariable String instructorId){
        Instructor savedInstructor= schoolService.saveCourse(course, instructorId);
        return new ResponseEntity(savedInstructor, HttpStatus.CREATED);
    }

    @PostMapping("/courses/{courseId}/reviews")
    public ResponseEntity createReview(@RequestBody Review review, @PathVariable String courseId){
        Course savedCourse= schoolService.saveReview(courseId, review);
        return new ResponseEntity(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping("/instructors/courses/{instructorId}")
    public ResponseEntity getCoursesOfInstructor(@PathVariable String instructorId){
        List<Course> courses= schoolService.getCoursesOfInstructor(instructorId);
        return new ResponseEntity(courses, HttpStatus.OK);
    }


}
