package com.svj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="instructor")
public class Instructor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;

	@Column(name="email")
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JsonIgnoreProperties("instructor")
	private List<Course> courseList;

	@OneToOne(cascade= CascadeType.ALL )
	@JoinColumn(name="instructor_detail_id")
	@JsonIgnoreProperties("instructor")
	private InstructorDetail instructorDetail;

	public void addCourse(Course course){
		if(courseList== null)
			courseList= new LinkedList<>();
		courseList.add(course);
		course.setInstructor(this);
	}
}






