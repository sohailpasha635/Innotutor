package com.sohail.main.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sohail.main.entity.Course;
import com.sohail.main.model.CourseModel;
import com.sohail.main.repo.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	//admin privileges
	
	@Override
	public List<Course> getCourseRequests() {
		return courseRepository.getCourseRequests("PENDING");
	}

	@Override
	public Course approveCourse(Long courseId,Float rating) {
		
		courseRepository.approveCourse("APPROVED","ACTIVE",rating,courseId);
		return courseRepository.findById(courseId).get();
	}

	@Override
	public List<Course> getAllActiveCourses() {
		
		return courseRepository.findAllActiveCourses("ACTIVE","APPROVED");
	}

	@Override
	public Course deleteCourse(Long courseId) {
		
		courseRepository.softDeleteById("INACTIVE",courseId);
		return courseRepository.findById(courseId).get();
	}

	@Override
	public Course getCourseById(Long courseId) {
		
		return courseRepository.findById(courseId).get();
	}

	@Override
	public List<Course> getCourseByName(String courseName) {
		return courseRepository.findBycourseName(courseName);
	}

	@Override
	public Course giveDiscount(Float discount, Long courseId) {
	    courseRepository.giveDiscount(discount,courseId);
		return courseRepository.findById(courseId).get();
	}

	@Override
	public List<Course> getAllCourses() {
		
		return courseRepository.findAll();
	}

	@Override
	public List<Course> getTutorRequests(Long tutorId) {
		
		return courseRepository.getTutorRequests(tutorId);
	}

	@Override
	public List<Course> getTutorPendingRequests(Long tutorId) {
		
		return courseRepository.getTutorPendingRequests(tutorId);
	}

	@Override
	public List<Course> getTutorApprovedRequest(Long tutorId) {
		
		return courseRepository.getTutorApprovedCourses(tutorId);
	}

	@Override
	public List<Course> getTutorRejectedRequest(Long tutorId) {
		
		return courseRepository.getTutorRejectedCourses(tutorId);
	}

	@Override
	public void giveRating(Long courseId, Float rating) {
		int count;
		count = courseRepository.getEnrolledCount(courseId);
		count-=1;
		Course course = courseRepository.findById(courseId).get();
		Float result_rating = ((course.getCourseRating()*count)+rating)/(count+1);
		course.setCourseRating(result_rating);
		courseRepository.save(course);
		
	}

	@Override
	public List<CourseModel> getPopularCourseModels() {
		
		List<CourseModel> popularCourseModels=new ArrayList<CourseModel>();
	
		List<Course> c=courseRepository.getPopularCourses();
		
		for(Course cr:c)
		{
			CourseModel cm= new CourseModel();
			cm.setCourseModelName(cr.getCourseName());
			cm.setCourseModelId(cr.getCourseId());
			cm.setCourseModelCategory(cr.getCourseCategory());
			cm.setCourseModelDescription(cr.getCourseDescription());
			cm.setCourseModelDiscount(cr.getCourseDiscount());
			cm.setCourseModelPrice(cr.getCoursePrice());
			cm.setCourseModelRating(cr.getCourseRating());
			cm.setCourseModelType(cr.getCourseType());
			cm.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			popularCourseModels.add(cm);
			
		}
		
		return(popularCourseModels);
	}

	@Override
	public List<CourseModel> getCourseModelsByCategory(String category) {
		
		List<CourseModel> courseByCategory=new ArrayList<CourseModel>();
		List<Course> c=courseRepository.findByCoursecategory(category);
		
		for(Course cr:c)
		{
			CourseModel cm2= new CourseModel();
			cm2.setCourseModelName(cr.getCourseName());
			cm2.setCourseModelId(cr.getCourseId());
			cm2.setCourseModelCategory(cr.getCourseCategory());
			cm2.setCourseModelDescription(cr.getCourseDescription());
			cm2.setCourseModelDiscount(cr.getCourseDiscount());
			cm2.setCourseModelPrice(cr.getCoursePrice());
			cm2.setCourseModelRating(cr.getCourseRating());
			cm2.setCourseModelType(cr.getCourseType());
			cm2.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByCategory.add(cm2);
			
		}
		
		return(courseByCategory);
	}

	@Override
	public List<CourseModel> getCourseModelsByName(String courseName) {
		
		List<CourseModel> courseByName=new ArrayList<CourseModel>();	
		
		List<Course> c = courseRepository.findBycourseName(courseName);
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDescription(cr.getCourseDescription());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByName.add(cm3);
			
		}
		
		return(courseByName);
	}

	@Override
	public List<CourseModel> getCourseModelsByCategoryAscPrice(String category) {
		
		List<CourseModel> courseByPrice=new ArrayList<CourseModel>();
		
		List<Course> c = courseRepository.findByCourseCategoryPriceAsc(category);
		
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByPrice.add(cm3);
			
		}
		
		return(courseByPrice);
	}
	
	@Override
	public List<CourseModel> getCourseModelsByCategoryDescPrice(String category) {
		
		List<CourseModel> courseByPriceDesc=new ArrayList<CourseModel>();
		
		List<Course> c = courseRepository.findByCourseCategoryPriceDesc(category);
		
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByPriceDesc.add(cm3);
			
		}
		
		return(courseByPriceDesc);
	}
	
	@Override
	public List<CourseModel> getCourseModelsByCategoryAscRating(String category) {
		
		List<CourseModel> courseByPrice=new ArrayList<CourseModel>();
		
		List<Course> c = courseRepository.findByCourseCategoryRatingAsc(category);
		
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByPrice.add(cm3);
			
		}
		
		return(courseByPrice);
	}
	
	@Override
	public List<CourseModel> getCourseModelsByCategoryDescRating(String category) {
		
		List<CourseModel> courseByPrice=new ArrayList<CourseModel>();
		
		List<Course> c = courseRepository.findByCourseCategoryRatingDesc(category);
		
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			
			courseByPrice.add(cm3);
			
		}
		
		return(courseByPrice);
	}

	@Override
	public List<CourseModel> getCourseModelsByTutorName(String tutorName) {
		
		
		List<Course> c= courseRepository.findByTutorName(tutorName);
		
		List<CourseModel> courseModelByTutorName = new ArrayList<>();
		
		for(Course cr:c)
		{
			CourseModel cm3= new CourseModel();
			cm3.setCourseModelName(cr.getCourseName());
			cm3.setCourseModelId(cr.getCourseId());
			cm3.setCourseModelCategory(cr.getCourseCategory());
			cm3.setCourseModelDescription(cr.getCourseDescription());
			cm3.setCourseModelDiscount(cr.getCourseDiscount());
			cm3.setCourseModelPrice(cr.getCoursePrice());
			cm3.setCourseModelRating(cr.getCourseRating());
			cm3.setCourseModelType(cr.getCourseType());
			cm3.setCourseModelTutorName(courseRepository.getTutorByCourseId(cr.getCourseId()));
			System.out.println("hiii");
			
			courseModelByTutorName.add(cm3);
			
		}
		
		return(courseModelByTutorName);
	}

	@Override
	public List<Course> getPopularCourses() {
		return courseRepository.getPopularCourses();
	}

	@Override
	public List<Course> getCoursesByCategory(String category) {
		return courseRepository.findByCoursecategory(category);
	}

	@Override
	public List<Course> getCoursesByName(String courseName) {
		return courseRepository.findBycourseName(courseName);
	}

	@Override
	public List<Course> getCoursesByTutorName(String tutorName) {
		return courseRepository.findByTutorName(tutorName);
	}
	
	
	
	
}
