package com.project.planningapp.dao;

import java.util.List;

public interface RoleDao {
	
	public List<String> getRoleNamesByUserId(Long userId);

}
