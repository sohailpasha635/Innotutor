package com.sohail.main.model;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(
		name = "course_model"
		)
public class CourseModel {
	
	@Id
	private Long courseModelId;
	private String courseModelName;
	@Column(length = 1337)
	private String courseModelDescription;
	private Float courseModelPrice;
	private String courseModelCategory;
	private Float courseModelDiscount;
	private Float courseModelRating;
    private String courseModelType;
	
    private Date courseModelUpdationDate;
    
    private String courseModelTutorName;
}
