package com.svj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
    private String title;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({"courseList", "instructorDetail"})
    private Instructor instructor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Review> reviews;

    public void addReview(Review review){
        if(reviews== null){
            reviews= new LinkedList<>();
        }
        reviews.add(review);
    }

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("courses")
    private Set<Student> students;

    // in a many-many relationship, the non-owner needs a helper method to remove its instance from other objects in many-many relationship
    public void removeStudentFromCourse(){
        for(Student student: students){
            student.removeCourse(this);
        }
    }
}