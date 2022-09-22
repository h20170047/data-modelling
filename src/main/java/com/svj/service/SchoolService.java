package com.svj.service;

import com.svj.entity.Course;
import com.svj.entity.Instructor;
import com.svj.entity.InstructorDetail;
import com.svj.entity.Review;
import com.svj.repository.CourseRepository;
import com.svj.repository.InstructorDetailsRepository;
import com.svj.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private InstructorRepository instructorRepository;
    private InstructorDetailsRepository instructorDetailsRepository;
    private CourseRepository courseRepository;

    public SchoolService(InstructorRepository instructorRepository, InstructorDetailsRepository instructorDetailsRepository, CourseRepository courseRepository){
        this.instructorRepository= instructorRepository;
        this.instructorDetailsRepository= instructorDetailsRepository;
        this.courseRepository= courseRepository;
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

    public Instructor saveCourse(Course course, String instructorId) {
        Optional<Instructor> optionalInstructor= instructorRepository.findById(Integer.parseInt(instructorId));
        if(optionalInstructor.isPresent()){
            Instructor instructor= optionalInstructor.get();
            instructor.addCourse(course);
//            return instructorRepository.save(instructor);
            courseRepository.save(course);
            return instructor;
        }
        return null;
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
        courseRepository.deleteById(Integer.parseInt(courseId));
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
}
