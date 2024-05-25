package com.sohail.main.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sohail.main.model.CourseModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(
		name = "users",
		uniqueConstraints = {    
				@UniqueConstraint(
						name="user_email_unique_constraint",   //giving name of constraint
						columnNames = "userEmail"     //specifying to which column the constraint should be applied
						),
				@UniqueConstraint(
						name="user_password_unique_constraint",  
						columnNames = "userPassword"     
						),
		}
		)
public class User {
	
	@Id
	@SequenceGenerator(
			name = "user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence"
			)
	private Long userId;
	private String userName;
	private String userPhone;
	private String userRole;
	private String userStatus;
	@Email(message = "mail not in correct format")
	private String userEmail;
	private String userPassword;
	
	private Date userCreatedDate;
	
	private String userLine1;
	private String userLine2;
	private String userCountry;
	private String userState;
	private Long userPin;
	
	
	@ManyToMany(
			cascade = CascadeType.ALL
			)
	@JoinTable(
			name = "user_enrolled_courses",
			joinColumns = @JoinColumn(
					name = "user_id",
					referencedColumnName = "userId"
					),   
			inverseJoinColumns = @JoinColumn(
					name = "course_id",
					referencedColumnName = "courseId"
					)
			)
	@JsonIgnore
	private Set<Course> userEnrolledCourses;
	
	
	@ManyToMany(
			cascade = CascadeType.ALL
			)
	@JoinTable(
			name = "cart",
			joinColumns = @JoinColumn(
					name = "user_id",
					referencedColumnName = "userId"
					),
			inverseJoinColumns = @JoinColumn(
					name = "course_id"
					)
			)
	@JsonIgnore
	private Set<CourseModel> userCartCourses;
	
	
	
	@OneToMany(
			cascade = CascadeType.ALL
			)
	@JoinColumn(
			name = "tutor_id",
			referencedColumnName = "userId"
			)
	@JsonIgnore
	private Set<Course> userRequestedCourses;


	public void addEnrolledCourse(Course course) {
		if(userEnrolledCourses==null)
			userEnrolledCourses=new HashSet<>();
		userEnrolledCourses.add(course);
	}
	
	public void addRequestCourse(Course course) {
		if(userRequestedCourses==null)
			userRequestedCourses=new HashSet<>();
		userRequestedCourses.add(course);
	}
	
	public void addCartCourse(CourseModel courseModel) {
		if(userCartCourses==null) {
			userCartCourses = new HashSet<>();
		}
		userCartCourses.add(courseModel);
	}
	
	public Set<Course> getEnrolledCourses(){
		return userEnrolledCourses;
	}
	
	public Set<Course> getRequestedCourses(){
		return userRequestedCourses;
	}
	
	public Set<CourseModel> getCartCourses(){
		return userCartCourses;
	}

	public User(String userName, String userPhone, String userRole,
			@Email(message = "mail not in correct format") String userEmail, String userPassword, String userLine1,
			String userLine2, String userCountry, String userState, Long userPin) {
		super();
		this.userName = userName;
		this.userPhone = userPhone;
		this.userRole = userRole;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userLine1 = userLine1;
		this.userLine2 = userLine2;
		this.userCountry = userCountry;
		this.userState = userState;
		this.userPin = userPin;
	}
	
}
