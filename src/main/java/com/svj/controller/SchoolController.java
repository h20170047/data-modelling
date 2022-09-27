package com.svj.controller;

import com.svj.entity.Course;
import com.svj.entity.Instructor;
import com.svj.entity.Review;
import com.svj.entity.Student;
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
    public ResponseEntity deleteInstructor(@PathVariable Integer id){
        schoolService.deleteInstructor(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/instructorDetails/{id}")
    public ResponseEntity getInstructorByDetailsID(@PathVariable(value = "id") Integer instrDetailsID){
        Instructor instructor= schoolService.getInstructorDetailsFromID(instrDetailsID);
        return new ResponseEntity(instructor, HttpStatus.OK);
    }

    @GetMapping("/instructors/{id}")
    public ResponseEntity getInstructorById(@PathVariable(value = "id") Integer instructorId){
        Instructor instructor= schoolService.getInstructorFromID(instructorId);
        return new ResponseEntity(instructor, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity getStudentDetailsById(@PathVariable(value = "id") Integer studentId){
        Student student= schoolService.getStudentDetailsWithId(studentId);
        return new ResponseEntity(student, HttpStatus.OK);
    }

    @DeleteMapping("/instructorDetails/{id}")
    public ResponseEntity deleteInstructorDetailsOnly(@PathVariable(value = "id") Integer instrDetailsID){
        schoolService.deleteOnlyInstructorDetails(instrDetailsID);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable Integer courseId){
        schoolService.deleteCourse(courseId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Integer studentId){
        schoolService.removeStudent(studentId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/courses/{instructorId}")
    public ResponseEntity createCourse(@RequestBody Course course, @PathVariable Integer instructorId){
        Course savedCourse= schoolService.saveCourse(course, instructorId);
        return new ResponseEntity(savedCourse, HttpStatus.CREATED);
    }

    @PostMapping("/courses/{courseId}/reviews")
    public ResponseEntity createReview(@RequestBody Review review, @PathVariable Integer courseId){
        Course savedCourse= schoolService.saveReview(courseId, review);
        return new ResponseEntity(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping("/instructors/courses/{instructorId}")
    public ResponseEntity getCoursesOfInstructor(@PathVariable Integer instructorId){
        List<Course> courses= schoolService.getCoursesOfInstructor(instructorId);
        return new ResponseEntity(courses, HttpStatus.OK);
    }

    @PostMapping("/enroll/course/{courseId}/student/{studentId}")
    public ResponseEntity enrollStudent(@PathVariable Integer courseId, @PathVariable Integer studentId){
        // Return enrolled courses for the student
        Student student= schoolService.enrollToCourse(courseId, studentId);
        return new ResponseEntity(student, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity getCourseDetails(@PathVariable Integer courseId){
        Course course= schoolService.getCourseDetails(courseId);
        return new ResponseEntity(course, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity addStudent(@RequestBody Student student){
        Student savedStudent= schoolService.saveStudent(student);
        return new ResponseEntity(savedStudent, HttpStatus.OK);
    }

}
