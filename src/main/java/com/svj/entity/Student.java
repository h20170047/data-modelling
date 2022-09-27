package com.svj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="student")
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="student_id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(
			name = "student_course",
			joinColumns =@JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name="course_id")
	)
	@JsonIgnoreProperties("students")
	private Set<Course> courses;

	public void addCourse(Course course){
		if(courses== null){
			courses= new HashSet<>();
		}
		courses.add(course);
		if(course.getStudents()== null){
			course.setStudents(new HashSet<>());
		}
		course.getStudents().add(this);
	}

	public void removeCourse(Course course){
		courses.remove(course);
		course.getStudents().remove(this);
	}

}




