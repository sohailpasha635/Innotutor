package com.sohail.main.service;

import java.util.List;
import java.util.Set;

import com.sohail.main.entity.Course;
import com.sohail.main.entity.User;
import com.sohail.main.model.CourseModel;
import com.sohail.main.model.UserDto;

public interface UserService {

	User addUser(UserDto userDto);

	List<User> getAllUsers();

	void deleteById(Long userId);

	User changeStatus(Long userId);

	String addRequestCourse(Long userId, Course course);

	List<User> getUserRequests();

	User changeUserRole(String role,Long userId);

	void addCourseToCart(CourseModel courseModel, Long userId);

	List<CourseModel> getCartDetails(Long userId);

	String buyCart(Long userId);

	Set<Course> getEnrolledCourses(Long userId);

	Course updateCourse(Long userId, Course course);

	User findByUserEmail(String userEmail);

	void updatePassword(String encode, String email);

	

}
