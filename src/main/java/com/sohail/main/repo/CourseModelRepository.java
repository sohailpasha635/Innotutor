package com.sohail.main.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sohail.main.model.CourseModel;

@Repository
public interface CourseModelRepository extends JpaRepository<CourseModel, Long> {
	
	@Query(
			value="select * from course_model c where c.course_model_id IN(select course_id from cart where user_id=?1)",
			nativeQuery = true
			)
	List<CourseModel> getCartDetails(Long userId);

	@Modifying
	@Transactional
	@Query(
			value="delete from cart where user_id=?1",
			nativeQuery = true
			)
	void deleteCartCourses(Long userId);
}
