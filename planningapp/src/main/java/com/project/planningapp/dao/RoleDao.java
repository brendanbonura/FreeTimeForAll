package com.project.planningapp.dao;

import java.util.List;

import com.project.planningapp.entity.Role;

public interface RoleDao {
	
	public List<String> getRoleNamesByUserId(Long userId);
	public Role getRoleById(Long id);

}
