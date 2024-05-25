	package com.sohail.main.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(
		name = "course",
		uniqueConstraints = @UniqueConstraint(
				name="course_name_unique_constraint",
				columnNames = "courseName"
				)
		)
public class Course {
	
	@Id
	@SequenceGenerator(
			name = "course_sequence",
			sequenceName = "course_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "course_sequence"
			)
	private Long courseId;
	private String courseName;
	@Column(length = 1337)
	private String courseDescription;
	private Float coursePrice;
	private String courseCategory;
	private Float courseDiscount;
	private Float courseRating;
	
	private String courseStatus;
	
	private String courseApproval;
	
	private Date courseCreationDate;
	
	private Date courseUpdationDate;
	
	private String courseType;
	
	
}