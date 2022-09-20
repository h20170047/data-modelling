package com.svj.service;

import com.svj.entity.Instructor;
import com.svj.entity.InstructorDetail;
import com.svj.repository.InstructorDetailsRepository;
import com.svj.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {
    private InstructorRepository instructorRepository;
    private InstructorDetailsRepository instructorDetailsRepository;
    public SchoolService(InstructorRepository instructorRepository, InstructorDetailsRepository instructorDetailsRepository){
        this.instructorRepository= instructorRepository;
        this.instructorDetailsRepository= instructorDetailsRepository;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(String id) {
        instructorRepository.deleteById(Integer.parseInt(id));
    }

    public Instructor getInstructorFromDetailsID(String instrDetailsID) {
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
}
