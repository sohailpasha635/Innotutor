package com.sohail.main.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sohail.main.entity.Course;
import com.sohail.main.model.CourseModel;
import com.sohail.main.repo.CourseRepository;
import com.sohail.main.service.CourseService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
//	@Autowired
//	private CourseRepository courseRepository;
	
	
	//Admin privileges
	
	@GetMapping("admin/getCourseRequests")
	public ResponseEntity<?> getCourseRequests(){
		List<Course> list = courseService.getCourseRequests();
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any course requests",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("admin/{courseId}/approveCourse")
	public ResponseEntity<?> approveCourse(@PathVariable("courseId") Long courseId, Float rating) {
		try {
			courseService.approveCourse(courseId,rating);
			return new ResponseEntity<String>("course approval sucessfull",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not approve course",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping("admin/getAllCourses")
	public ResponseEntity<?> getAllCourses(){
		
		List<Course> list= courseService.getAllCourses();
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not fetch courses",HttpStatus.NOT_FOUND);
		}
	}
	
//	@PutMapping("admin/changeCategory")
//	public List<Course> changeCategory(){
//		courseRepository.changeCategory("Public_Service","Business_Administration");
//		return courseService.getAllCourses();
//	}
	
	@GetMapping("admin/getAllActiveCourses")
	public ResponseEntity<?> getAllActiveCourses(){
		
		List<Course> list= courseService.getAllActiveCourses();
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not fetch active courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("admin/{courseId}/deleteCourse")
	public ResponseEntity<?> deleteCourse(@PathVariable("courseId") Long courseId) {
		try {
			courseService.deleteCourse(courseId);
			return new ResponseEntity<String>("course deleted",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not delete course",HttpStatus.BAD_REQUEST);
		}
	}
	
//	@DeleteMapping("course/{courseId}/delete")
//	public void delete(@PathVariable("courseId") Long courseId) {
//		courseRepository.deleteById(courseId);
//	}
	
//	@GetMapping("admin/{courseId}/getCourseById")
//	public ResponseEntity<?> getCourseById(@PathVariable("courseId") Long courseId) {
//		try {
//			Course c = courseService.getCourseById(courseId);
//			return new ResponseEntity<Course>(c,HttpStatus.OK);
//		}
//		catch (Exception e) {
//			return new ResponseEntity<>("course not found",HttpStatus.NOT_FOUND);
//		}
//	}
	
	@GetMapping("admin/getCourseByName")
	public ResponseEntity<?> getCourseByName(String courseName) {
		try {
			List<Course> list = courseService.getCourseByName(courseName);
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not fetch course",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("admin/{courseId}/giveDiscount")
	public ResponseEntity<?> giveDiscount(Float discount,@PathVariable("courseId") Long courseId) {
		try {
			courseService.giveDiscount(discount,courseId);
			return new ResponseEntity<String>("operation sucessfull",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not give discount",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("admin/getPopularCourses")
	public ResponseEntity<?> getPopularCourses (){
		List<Course> list = courseService.getPopularCourses();
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("admin/getCoursesByCategory")
	public ResponseEntity<?> getCoursesByCategory(@RequestParam String category){
		List<Course> list = courseService.getCoursesByCategory(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("admin/getCoursesByName")
	public ResponseEntity<?> getCoursesByName(@RequestParam String courseName){
		List<Course> list = courseService.getCoursesByName(courseName);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("admin/{tutorName}/getCoursesByTutorName")
	public ResponseEntity<?> getCoursesByTutor(@PathVariable("tutorName") String tutorName){
		List<Course> list = courseService.getCoursesByTutorName(tutorName);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no course found",HttpStatus.NOT_FOUND);
		}
	}
	
	//Tutor privileges
	
	@GetMapping("tutor/{userId}/getTutorRequests")
	public ResponseEntity<?> getTutorRequests(@PathVariable("userId") Long tutorId){
		List<Course> list = courseService.getTutorRequests(tutorId);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no requests found",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("tutor/{tutorId}/getTutorPendingRequests")
	public ResponseEntity<?> getTutorPendingRequests(@PathVariable("tutorId") Long tutorId){
		List<Course> list = courseService.getTutorPendingRequests(tutorId);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no pending requests found",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("tutor/{tutorId}/getTutorApprovedRequests")
	public ResponseEntity<?> getTutorApprovedRequests(@PathVariable("tutorId") Long tutorId){
		List<Course> list = courseService.getTutorApprovedRequest(tutorId);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no approved courses found",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("tutor/{tutorId}/getTutorRejectedRequests")
	public ResponseEntity<?> getTutorRejectedRequests(@PathVariable("tutorId") Long tutorId){
		List<Course> list = courseService.getTutorRejectedRequest(tutorId);
		if(list.size()!=0) {
			return new ResponseEntity<List<Course>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no rejected course found",HttpStatus.NOT_FOUND);
		}
	}
	
	//tutor and learner privileges
	
	@PostMapping("learner/{courseId}/giveRating")
	public ResponseEntity<?> giveRating(@PathVariable("courseId") Long courseId,@RequestParam(name="rating") Float rating){
		try {
			courseService.giveRating(courseId,rating);
			return new ResponseEntity<String>("course rating given",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("no course found",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/getPopularCourseModels")
	public ResponseEntity<?> getPopularCourseModels (){
		List<CourseModel> list = courseService.getPopularCourseModels();
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/getCourseModelsByCategory")
	public ResponseEntity<?> getCourseModelsByCategory(@RequestParam String category){
		List<CourseModel> list = courseService.getCourseModelsByCategory(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/getCourseModelByName")
	public ResponseEntity<?> getCourseModelByName(@RequestParam String courseName){
		List<CourseModel> list = courseService.getCourseModelsByName(courseName);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/{category}/getCourseModelByCategoryAscPrice")
	public ResponseEntity<?> getCourseModelsByCateoryAscPrice(@PathVariable("category") String category){
		List<CourseModel> list = courseService.getCourseModelsByCategoryAscPrice(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/{category}/getCourseModelByCategoryDescPrice")
	public ResponseEntity<?> getCourseModelsByCategoryDescPrice(@PathVariable("category") String category){
		List<CourseModel> list = courseService.getCourseModelsByCategoryDescPrice(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/{category}/getCourseModelByCategoryAscRating")
	public ResponseEntity<?> getCourseModelsByCateoryAscRating(@PathVariable("category") String category){
		List<CourseModel> list = courseService.getCourseModelsByCategoryAscRating(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/{category}/getCourseModelByCategoryDescRating")
	public ResponseEntity<?> getCourseModelsByCategoryDescRating(@PathVariable("category") String category){
		List<CourseModel> list = courseService.getCourseModelsByCategoryDescRating(category);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find any courses",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("learner/{tutorName}/getCourseModelsByTutorName")
	public ResponseEntity<?> getCourseModelsByTutor(@PathVariable("tutorName") String tutorName){
		List<CourseModel> list = courseService.getCourseModelsByTutorName(tutorName);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("no course found",HttpStatus.NOT_FOUND);
		}
	}
	
	
}
