package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soft.models.User;


public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
	@Query("SELECT u FROM User u where u.userName LIKE %?1%")
	List<User> searchUser(String keyword);
}
