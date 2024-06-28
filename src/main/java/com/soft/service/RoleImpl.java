package com.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.models.Role;
import com.soft.repository.RoleRepository;

@Service
public class RoleImpl implements RoleService{
	@Autowired
    private RoleRepository roleRepository;
	 public List<Role> getAllRoles() {
	        return roleRepository.findAll();
	 }
	 public Role getRoleById(Long id) {
	        return roleRepository.findById(id).get();
	 }
}
