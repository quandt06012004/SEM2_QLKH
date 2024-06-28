package com.soft.service;

import java.util.List;
import com.soft.models.Role;

public interface RoleService {
	    public List<Role> getAllRoles();
	    public Role getRoleById(Long id);
}
