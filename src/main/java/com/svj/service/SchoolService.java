package com.svj.service;

import com.svj.entity.*;
import com.svj.repository.CourseRepository;
import com.svj.repository.InstructorDetailsRepository;
import com.svj.repository.InstructorRepository;
import com.svj.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private InstructorRepository instructorRepository;
    private InstructorDetailsRepository instructorDetailsRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public SchoolService(InstructorRepository instructorRepository,
                         InstructorDetailsRepository instructorDetailsRepository,
                         CourseRepository courseRepository, StudentRepository studentRepository){
        this.instructorRepository= instructorRepository;
        this.instructorDetailsRepository= instructorDetailsRepository;
        this.courseRepository= courseRepository;
        this.studentRepository= studentRepository;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(String id) {
        instructorRepository.deleteById(Integer.parseInt(id));
    }

    public Instructor getInstructorDetailsFromID(String instrDetailsID) {
        Optional<InstructorDetail> instructorDetailOptional = instructorDetailsRepository.findById(Integer.parseInt(instrDetailsID));
        if(instructorDetailOptional.isPresent())
            return instructorDetailOptional.get().getInstructor();
        return null;
    }

    public void deleteOnlyInstructorDetails(String instrDetailsID) {
        Optional<InstructorDetail> instructorDetail= instructorDetailsRepository.findById(Integer.parseInt(instrDetailsID));
        if(instructorDetail.isPresent()) {
            InstructorDetail instructorDetail1= instructorDetail.get();
            Instructor instructor = instructorDetail1.getInstructor();
            // all dependencies of that instructorDetails need to be removed before deleting instuctorDetails
            instructor.setInstructorDetail(null);
            instructorDetailsRepository.delete(instructorDetail1);
        }
    }

    public Instructor getInstructorFromID(String instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(Integer.parseInt(instructorId));
        if(instructorOptional.isPresent()){
            Instructor instructor= instructorOptional.get();
            return instructor;
        }
        return null;
    }

    public Course saveCourse(Course course, String instructorId) {
        Optional<Instructor> optionalInstructor= instructorRepository.findById(Integer.parseInt(instructorId));
        if(optionalInstructor.isPresent()){
            Instructor instructor= optionalInstructor.get();
            instructor.addCourse(course);
//            return instructorRepository.save(instructor);
        }
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }

    public List<Course> getCoursesOfInstructor(String instructorId) {
        Optional<Instructor> optionalInstructor= instructorRepository.findById(Integer.parseInt(instructorId));
        if(optionalInstructor.isPresent()){
            Instructor instructor= optionalInstructor.get();
            return instructor.getCourseList();
        }
        return null;
    }

    public void deleteCourse(String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Integer.parseInt(courseId));
        if(courseOptional.isPresent()){
            courseRepository.delete(courseOptional.get());
        }
    }

    public Course saveReview(String courseId, Review review) {
        Optional<Course> courseOptional= courseRepository.findById(Integer.parseInt(courseId));
        if(courseOptional.isPresent()){
            Course course= courseOptional.get();
            course.addReview(review);
            courseRepository.save(course);
            return course;
        }
        return null;
    }

    public Student enrollToCourse(String courseId, String studentId) {
        Optional<Course> courseOptional= courseRepository.findById(Integer.parseInt(courseId));
        Optional<Student> studentOptional= studentRepository.findById(Integer.parseInt(studentId));
        if(courseOptional.isPresent() && studentOptional.isPresent()){
            Course course= courseOptional.get();
            Student student= studentOptional.get();
            course.addStudent(student);
            student.addCourse(course);
            courseRepository.save(course);
            return student;
        }
        return null;
    }

    public Course getCourseDetails(String courseId) {
        Optional<Course> course= courseRepository.findById(Integer.parseInt(courseId));
        if(course.isPresent()){
            return course.get();
        }
        return null;
    }

    public Student saveStudent(Student student) {
        Student savedStudent= studentRepository.save(student);
        return savedStudent;
    }


    @PostConstruct
    public void setup(){
        // add student, courses, enroll student to courses.
        // Manually delete a course
    }
}
