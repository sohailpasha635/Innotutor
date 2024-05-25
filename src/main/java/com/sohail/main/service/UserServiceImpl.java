package com.sohail.main.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sohail.main.entity.Course;
import com.sohail.main.entity.User;
import com.sohail.main.model.CourseModel;
import com.sohail.main.model.UserDto;
import com.sohail.main.repo.CourseModelRepository;
import com.sohail.main.repo.CourseRepository;
import com.sohail.main.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseModelRepository courseModelRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
//	@Override
//	public User addUser(UserDto userDto) {
//		User user =new User(
//				userDto.getUserName(),
//				userDto.getUserPhone(),
//				userDto.getUserRole(),
//				userDto.getUserEmail(),
//				userDto.getUserPassword(),
//				userDto.getUserLine1(),				
//				userDto.getUserLine2(),
//				userDto.getUserCountry(),
//				userDto.getUserState(),
//				userDto.getUserPin()		
//				);
//		
//		user.setUserStatus("INACTIVE");
//		Date date = new Date();
//		user.setUserCreatedDate(date);
////		String str = user.getUserPassword();
////		user.setUserPassword(new BCryptPasswordEncoder().encode(str));
//		return userRepository.save(user);
//	}

	@Override
	public List<User> getAllUsers() {
	
		return userRepository.findAll();
	}

	@Override
	public void deleteById(Long userId) {
		
		userRepository.deleteById(userId);
	}

	@Override
	public User changeStatus(Long userId) {
		User user = userRepository.findById(userId).get();
		if(user.getUserStatus()=="ACTIVE")
			user.setUserStatus("INACTIVE");
		else
			user.setUserStatus("ACTIVE");
		userRepository.save(user);
		return user;
		
	}

	@Override
	public String addRequestCourse(Long userId, Course course) {
		User user = userRepository.findById(userId).get();
		if(user.getUserRole().equals("TUTOR") && user.getUserStatus().equals("ACTIVE")) {
			course.setCourseStatus("INACTIVE");
			course.setCourseApproval("PENDING");
			course.setCourseCreationDate(new Date());
			course.setCourseRating(0.0f);
			course.setCourseDiscount(0.0f);
			user.addRequestCourse(course);
			userRepository.save(user);
			return "course request is added";
		}
		else
			return "Unauthorized service access";
	}

	@Override
	public List<User> getUserRequests() {
		return userRepository.getUserRequests("INACTIVE");
	}

	@Override
	public User changeUserRole(String role,Long userId) {
		userRepository.changeRole("TUTOR",userId);
		return userRepository.findById(userId).get();
	}

	@Override
	public void addCourseToCart(CourseModel courseModel, Long userId) {
		
		User user = userRepository.findById(userId).get();
		user.addCartCourse(courseModel);
		userRepository.save(user);
	}

	@Override
	public List<CourseModel> getCartDetails(Long userId) {
		return courseModelRepository.getCartDetails(userId);
	}

	@Override
	public String buyCart(Long userId) {
		
		List<CourseModel> cmList = courseModelRepository.getCartDetails(userId);

		
		User user = userRepository.findById(userId).get();
		
		cmList.forEach(cm -> {
			Long courseId = cm.getCourseModelId();
			Course course = courseRepository.findById(courseId).get();
			if(course.getCourseStatus()=="ACTIVE")
				user.addEnrolledCourse(course);
		});
		Set<Course> course_set = user.getEnrolledCourses();
		if(course_set.size()!=cmList.size())
			return "changed";
		courseModelRepository.deleteCartCourses(userId);
		userRepository.save(user);
		return "ok";
	}

	@Override
	public Set<Course> getEnrolledCourses(Long userId) {
		
		User user = userRepository.findById(userId).get();
		return user.getEnrolledCourses();
	}

	@Override
	public Course updateCourse(Long userId, Course course) {
		
		User user = userRepository.findById(userId).get();
		courseRepository.deleteCourseById(course.getCourseId());
		course.setCourseId(null);
		course.setCourseApproval("PENDING");
		course.setCourseUpdationDate(new Date());
		course.setCourseStatus("INACTIVE");
		user.addRequestCourse(course);
		userRepository.save(user);
		return courseRepository.findByCourseName(course.getCourseName()).get();
	}

	@Override
	public User addUser(UserDto userDto) {
		User user =new User(
				userDto.getUserName(),
				userDto.getUserPhone(),
				userDto.getUserRole(),
				userDto.getUserEmail(),
				new BCryptPasswordEncoder().encode( userDto.getUserPassword()),
				userDto.getUserLine1(),				
				userDto.getUserLine2(),
				userDto.getUserCountry(),
				userDto.getUserState(),
				userDto.getUserPin()		
				);
		
		user.setUserStatus("INACTIVE");
		Date date = new Date();
		user.setUserCreatedDate(date);
//		String str = user.getUserPassword();
//		user.setUserPassword(new BCryptPasswordEncoder().encode(str));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.findByUserEmail(username);
		if(user==null)
		{
			System.out.println("usern not found");
			throw new UsernameNotFoundException("user not found"+username);
		}
		System.out.println(getAuthority(user));
		return new org.springframework.security.core.userdetails.User(
				user.getUserEmail(), 
				user.getUserPassword(), getAuthority(user));
	};
		
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
	     Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	    /*user.getUsertype().forEach(role -> {
	         authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
	     });*/
	     String usertype=user.getUserRole();
	     authorities.add(new SimpleGrantedAuthority(usertype));

	     return authorities;
	 }

	@Override
	public User findByUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.findByUserEmail(userEmail);
	}

	@Override
	public void updatePassword(String encode, String email) {
		User user=userRepository.findByUserEmail(email);
		user.setUserPassword(encode);
		userRepository.save(user);
		
	}


	
	
	
	
}
