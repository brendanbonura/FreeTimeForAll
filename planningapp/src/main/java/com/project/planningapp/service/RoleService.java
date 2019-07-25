package com.project.planningapp.service;

import java.util.List;

import com.project.planningapp.entity.Role;

public interface RoleService {

	public List<String> getRoleNamesByUserId(Long userId);
	public Role getRoleById(Long id);
	
}
