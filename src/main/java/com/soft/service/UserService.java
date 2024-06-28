package com.soft.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.soft.models.User;

public interface UserService {
	User findByUserName(String userName);
	List<User> findAll();

	void save(User user);

	List<User> searchUser(String keyword);

	Page<User> getAll(Integer pageNo);

	Page<User> searchUser(String keyword, Integer pageNo);
	User findById(Long id);
	Boolean deleteUserById(Long id);
}
