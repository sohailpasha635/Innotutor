package com.sohail.main.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sohail.main.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query(
			value="select * from course c where c.course_approval=?1",
			nativeQuery = true
			)
	List<Course> getCourseRequests(String approval);
	
	@Transactional
	@Modifying
	@Query(
			value="update course set course_approval=?1, course_status=?2, course_rating=?3 where course_id=?4",
			nativeQuery = true
			)
	void approveCourse(String approval,String status,Float rating,Long courseId);
	
	
	@Transactional
	@Modifying
	@Query(
			value="update course set course_status=?1 where course_id=?2",
			nativeQuery = true
			)
	void softDeleteById(String status,Long courseId);

	Optional<Course> findByCourseName(String courseName);
	
	@Transactional
	@Modifying
	@Query(
			value="update course set course_discount=?1 where course_id=?2",
			nativeQuery = true
			)
	void giveDiscount(Float discount, Long courseId);
	
	@Query(
			value="select * from course c where c.course_status=?1 and c.course_approval=?2",
			nativeQuery = true
			)
	List<Course> findAllActiveCourses(String status,String approval);
	
	@Query(
			value="select * from course c where c.tutor_id=?1",
			nativeQuery = true
			)
	List<Course> getTutorRequests(Long tutorId);

	
	@Query(
			value="select * from course c where c.course_approval='PENDING' and c.tutor_id=?1",
			nativeQuery = true
			)
	List<Course> getTutorPendingRequests(Long tutorId);

	@Query(
			value="select * from course c where c.course_approval='APPROVED' and c.tutor_id=?1",
			nativeQuery = true
			)
	List<Course> getTutorApprovedCourses(Long tutorId);

	@Query(
			value="select * from course c where c.course_approval='REJECTED' and c.tutor_id=?1",
			nativeQuery = true
			)
	List<Course> getTutorRejectedCourses(Long tutorId);

	@Transactional
	@Modifying
	@Query(
			value="delete from course c where c.course_name=?1",
			nativeQuery = true
			)
	void deleteByName(String courseName);

	@Transactional
	@Modifying
	@Query(
			value="delete from course c where c.course_id=?1",
			nativeQuery = true
			)
	void deleteCourseById(Long courseId);

	@Query(
			value="select count(*) from user_enrolled_courses where course_id=?1",
			nativeQuery = true
			)
	int getEnrolledCount(Long courseId);

	@Transactional
	@Modifying
	@Query(
			value="update course set course_category=?1 where course_category=?2",
			nativeQuery = true
			)
	void changeCategory(String change, String ini);
	
	
	@Query(
			value = "select * from course where course_status='ACTIVE' and course_approval='APPROVED' order by course_rating DESC limit 15 ",
			nativeQuery=true
			)
	List<Course> getPopularCourses();

	
	@Query(
			value="select * from course where course_status='ACTIVE' and course_approval='APPROVED'and course_category iLike %:x%",
			nativeQuery=true
			)
	List<Course> findByCoursecategory(@Param("x") String category);
	
	
	@Query(
			value="select * from course where course_status='ACTIVE' and course_approval='APPROVED'and course_name iLike %:x%",
			nativeQuery=true
			)
	List<Course> findBycourseName(@Param("x") String name);

	
	@Query(value="select * from course where course_status='ACTIVE' and course_category iLike %:x% and course_approval='APPROVED'order by course_price limit 10",
			nativeQuery=true
			)
	List<Course> findByCourseCategoryPriceAsc(@Param("x") String category);
	
	@Query(value="select * from course where course_status='ACTIVE' and course_category iLike %:x% and course_approval='APPROVED' order by course_price desc limit 10",
			nativeQuery=true
			)
	List<Course> findByCourseCategoryPriceDesc(@Param("x") String category);

	
	@Query(value="select * from course where course_status='ACTIVE' and course_category iLike %:x% and course_approval='APPROVED'order by course_rating limit 10",
			nativeQuery=true
			)
	List<Course> findByCourseCategoryRatingAsc(@Param("x") String category);
	
	@Query(value="select * from course where course_status='ACTIVE' and course_category iLike %:x% and course_approval='APPROVED'order by course_rating desc limit 10",
			nativeQuery=true
			)
	List<Course> findByCourseCategoryRatingDesc(@Param("x") String category);

	
	@Query(
			value="select * from course where course_status='ACTIVE' and course_approval='APPROVED' and tutor_id IN(select user_id from users where user_name iLike %:x%)",
			nativeQuery = true
			)
	List<Course> findByTutorName(@Param("x") String tutorName);
	
	
	@Query(
			value="select user_name from users where user_id IN(select tutor_id from course where course_id=?1)",
			nativeQuery = true
			)
	String getTutorByCourseId(Long courseId);
}
