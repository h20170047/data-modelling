package com.svj.service;

import com.svj.entity.*;
import com.svj.repository.CourseRepository;
import com.svj.repository.InstructorDetailsRepository;
import com.svj.repository.InstructorRepository;
import com.svj.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

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

    public void deleteInstructor(Integer id) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);
        if(instructorOptional.isPresent()){
            Instructor instructor= instructorOptional.get();
            instructor.deleteCourses();
            instructorRepository.delete(instructor);
        }
    }

    public Instructor getInstructorDetailsFromID(Integer instrDetailsID) {
        Optional<InstructorDetail> instructorDetailOptional = instructorDetailsRepository.findById(instrDetailsID);
        if(instructorDetailOptional.isPresent())
            return instructorDetailOptional.get().getInstructor();
        return null;
    }

    public void deleteOnlyInstructorDetails(Integer instrDetailsID) {
        Optional<InstructorDetail> instructorDetail= instructorDetailsRepository.findById(instrDetailsID);
        if(instructorDetail.isPresent()) {
            InstructorDetail instructorDetail1= instructorDetail.get();
            Instructor instructor = instructorDetail1.getInstructor();
            // all dependencies of that instructorDetails need to be removed before deleting instuctorDetails
            instructor.setInstructorDetail(null);
            instructorDetailsRepository.delete(instructorDetail1);
        }
    }

    public Instructor getInstructorFromID(Integer instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if(instructorOptional.isPresent()){
            Instructor instructor= instructorOptional.get();
            return instructor;
        }
        return null;
    }

    public Course saveCourse(Course course, Integer instructorId) {
        Optional<Instructor> optionalInstructor= instructorRepository.findById(instructorId);
        if(optionalInstructor.isPresent()){
            Instructor instructor= optionalInstructor.get();
            instructor.addCourse(course);
        }
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }

    public List<Course> getCoursesOfInstructor(Integer instructorId) {
        Optional<Instructor> optionalInstructor= instructorRepository.findById(instructorId);
        if(optionalInstructor.isPresent()){
            Instructor instructor= optionalInstructor.get();
            return instructor.getCourseList();
        }
        return null;
    }

    public void deleteCourse(Integer courseId) {
        // deletion of a course needs to have a prereq of removing course from all other entities associated with it
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isPresent()){
            Course course= courseOptional.get();
            course.removeStudentFromCourse();
            courseRepository.delete(course);
        }

    }

    public Course saveReview(Integer courseId, Review review) {
        Optional<Course> courseOptional= courseRepository.findById(courseId);
        if(courseOptional.isPresent()){
            Course course= courseOptional.get();
            course.addReview(review);
            courseRepository.save(course);
            return course;
        }
        return null;
    }

    public Student enrollToCourse(Integer courseId, Integer studentId) {
        Optional<Course> courseOptional= courseRepository.findById(courseId);
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(courseOptional.isPresent() && studentOptional.isPresent()){
            Course course= courseOptional.get();
            Student student= studentOptional.get();
            student.addCourse(course);
            studentRepository.save(student);
            return student;
        }
        return null;
    }

    public Course getCourseDetails(Integer courseId) {
        Optional<Course> course= courseRepository.findById(courseId);
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
        List<Course> courses= new LinkedList<>(Arrays.asList(
            Course.builder().title("Java").build(),
            Course.builder().title("DBMS").build(),
            Course.builder().title("DSA").build()
        ));
        courseRepository.saveAll(courses);
        List<Student> students= new LinkedList<>(Arrays.asList(
            Student.builder().firstName("Jose").lastName("Prakash").email("jose.prakash@gmail.com").build(),
            Student.builder().firstName("Shambu").lastName("Sasi").email("shams@gmail.com").build(),
            Student.builder().firstName("Raghu").lastName("Dixit").email("jose.prakash@gmail.com").build()
        ));
        studentRepository.saveAll(students);
        enrollToCourse(1, 1);
        enrollToCourse(2, 1);
        enrollToCourse(3, 2);
        enrollToCourse(2, 2);
        // Manually delete a course- say course 2
    }

    public Student getStudentDetailsWithId(Integer studentId) {
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }
        return null;
    }

    public void removeStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }
}
