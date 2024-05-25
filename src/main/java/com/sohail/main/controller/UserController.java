package com.sohail.main.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.sohail.main.entity.Course;
import com.sohail.main.entity.SavePrincipal;
import com.sohail.main.entity.User;
import com.sohail.main.model.CourseModel;
import com.sohail.main.model.UserDto;
import com.sohail.main.repo.SavePrincipalRepo;
import com.sohail.main.repo.UserRepository;
import com.sohail.main.security.AuthenticationRequest;
import com.sohail.main.security.AuthenticationResponse;
import com.sohail.main.security.JwtUtil;
import com.sohail.main.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	SavePrincipalRepo repo;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtTokenUtil;
	
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private UserRepository userRepository;
	
	
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
	
	@PostMapping("user/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
		try {
			userService.addUser(userDto);
			return new ResponseEntity<String>("user registration sucessfull",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("could not add user",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@GetMapping("admin/getAllUsers")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
//	@PostMapping("user/{userId}/changeLine2")
//	public List<User> changePin(String Line2,@PathVariable("userId") Long userId){
//		User user = userRepository.findById(userId).get();
//		user.setUserLine2(Line2);
//		userRepository.save(user);
//		return userService.getAllUsers();
//	}
//	
//	@DeleteMapping("user/deleteById/{userId}")
//	public String deleteUserById(@PathVariable("userId") Long userId) {
//		userService.deleteById(userId);
//		return "user deleted";
//	} 
	
	//Admin privileges
	
	@PutMapping("admin/{userId}/changeStatus")
	public ResponseEntity<?> changeUserStatus(@PathVariable("userId") Long userId) {
		try{
			userService.changeStatus(userId);
			return new ResponseEntity<String>("user status changed sucessfully",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not change status",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("admin/getUserRequests")
	public ResponseEntity<?> getUserRequests(){

		List<User> list = userService.getUserRequests();
		if(list.size()!=0) {
			return new ResponseEntity<List<User>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("user request not found",HttpStatus.NOT_FOUND);
		}
	}
	
//	@PutMapping("user/{userId}/changeRole")
//	public User changeUserRole(String role,@PathVariable("userId") Long userId) {
//		return userService.changeUserRole(role,userId);
//	}
	
	
	//tutor privileges
	
	@PostMapping("tutor/{userId}/uploadCourse")
	public ResponseEntity<?> addRequestCourse(@RequestBody Course course,@PathVariable("userId") Long userId) {
		try{
			String str = userService.addRequestCourse(userId,course);
			return new ResponseEntity<String>(str,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not upload course",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("tutor/{userId}/updateCourse")
	public ResponseEntity<?> updateCourse(@RequestBody Course course,@PathVariable("userId") Long userId) {
		try{
			userService.updateCourse(userId,course);
			return new ResponseEntity<String>("course updated sucessfully",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not update course",HttpStatus.BAD_REQUEST);
		}
	}
	
	//tutor and learner privileges
	
	@PostMapping("learner/{userId}/addCourseToCart")
	public ResponseEntity<?> addCourseToCart(@RequestBody CourseModel courseModel,@PathVariable("userId") Long userId) {
		try{
			userService.addCourseToCart(courseModel,userId);
			return new ResponseEntity<String>("course added to cart",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not add course to cart",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("learner/{userId}/getCartDetails")
	public ResponseEntity<?> getCartDetails(@PathVariable("userId") Long userId){
		
		List<CourseModel> list = userService.getCartDetails(userId);
		if(list.size()!=0) {
			return new ResponseEntity<List<CourseModel>>(list,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("no course found in cart",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("learner/{userId}/buyCart")
	public ResponseEntity<?> buyCart(@PathVariable("userId") Long userId) {
		try{
			String str = userService.buyCart(userId);
			if(str.equals("changed"))
				return new ResponseEntity<String>("course status is changed",HttpStatus.OK);
			return new ResponseEntity<String>("cart payment sucessfull",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("could not buy cart",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("learner/{userId}/getEnrolledCourses")
	public ResponseEntity<?> getEnrolledCourses(@PathVariable("userId") Long userId){
		
		Set<Course> set = userService.getEnrolledCourses(userId);
		if(set.size()!=0) {
			return new ResponseEntity<Set<Course>>(set,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("could not find enrolled courses",HttpStatus.NOT_FOUND);
		}
		
	}
@RequestMapping(value = "/auth", method = RequestMethod.POST)
	
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		
		SavePrincipal prince=new SavePrincipal(authenticationRequest.getUsername(),new Date());
		 repo.save(prince);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
					
			);
		
		}
		catch (BadCredentialsException e) {
			//throw new Exception("Incorrect username or password", e);
			return new ResponseEntity<String>("wrong credentials",HttpStatus.BAD_REQUEST);
		}

		User user = userRepository.findByUserEmail(authenticationRequest.getUsername());
		System.out.println(user);
		if(user.getUserStatus().equals("ACTIVE")) {
			
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
		else
		{
			return new ResponseEntity<String>("contact admin for approval",HttpStatus.BAD_REQUEST);
		}
	}

//   @PostMapping("user/encrypt")
//   public List<User> enc(){
//	   List<User> list = userService.getAllUsers();
//	   list.forEach(user -> {
//		   String pass = new BCryptPasswordEncoder().encode(user.getUserPassword());
//		   user.setUserPassword(pass);
//		   userRepository.save(user);
//	   });
//	   return userService.getAllUsers();
//			   
//   }
@PostMapping("/updatePassword")
public String  updatePass(String password,String confirmPassword,String email) {
	if(password.equals(confirmPassword)) {
	userService.updatePassword( new BCryptPasswordEncoder().encode(password) ,email);
	return "password Updated";
}
	else {
		return "enter correct detailes";
	}
	}

@PostMapping("/forgotPassword")
public String reSetPass(String userName,String userEmail, String password) {

	User user=userService.findByUserEmail(userEmail);
	

	if(user.getUserName().equals(userName)) {
		user.setUserPassword(new BCryptPasswordEncoder().encode(password));
		

		userRepository.save(user);
	return "password changed successfully";
	}
	else {
	return " invalid firstname or username";
	}
}
}

