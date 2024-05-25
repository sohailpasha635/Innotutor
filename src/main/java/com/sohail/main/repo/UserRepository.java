package com.sohail.main.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sohail.main.entity.User;
import com.sohail.main.model.CourseModel;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(
			value="select * from users u where u.user_status=?1",
			nativeQuery = true
			)
	List<User> getUserRequests(String status);

	
	@Modifying
	@Transactional
	@Query(
			value="update users set user_role=?1 where user_id=?2",
			nativeQuery = true
			)
	void changeRole(String role, Long userId);


	User findByUserEmail(String username);
	

}
