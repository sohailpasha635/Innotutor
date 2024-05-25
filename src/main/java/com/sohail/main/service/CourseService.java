package com.sohail.main.service;

import java.util.List;

import com.sohail.main.entity.Course;
import com.sohail.main.model.CourseModel;

public interface CourseService {

	List<Course> getCourseRequests();

	Course approveCourse(Long courseId,Float rating);

	List<Course> getAllCourses();

	Course deleteCourse(Long courseId);

	Course getCourseById(Long courseId);

	List<Course> getCourseByName(String courseName);

	Course giveDiscount(Float discount, Long courseId);

	List<Course> getAllActiveCourses();

	List<Course> getTutorRequests(Long tutorId);

	List<Course> getTutorPendingRequests(Long tutorId);

	List<Course> getTutorApprovedRequest(Long tutorId);

	List<Course> getTutorRejectedRequest(Long tutorId);

	void giveRating(Long courseId, Float rating);

	List<CourseModel> getPopularCourseModels();

	List<CourseModel> getCourseModelsByCategory(String category);

	List<CourseModel> getCourseModelsByName(String courseName);

	List<CourseModel> getCourseModelsByCategoryAscPrice(String category);

	List<CourseModel> getCourseModelsByCategoryDescPrice(String category);

	List<CourseModel> getCourseModelsByCategoryAscRating(String category);

	List<CourseModel> getCourseModelsByCategoryDescRating(String category);

	List<CourseModel> getCourseModelsByTutorName(String tutorName);

	List<Course> getPopularCourses();

	List<Course> getCoursesByCategory(String category);

	List<Course> getCoursesByName(String courseName);

	List<Course> getCoursesByTutorName(String tutorName);


}
