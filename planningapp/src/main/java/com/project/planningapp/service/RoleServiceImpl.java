package com.project.planningapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.RoleDao;

public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public List<String> getRoleNamesByUserId(Long userId) {
		return roleDao.getRoleNamesByUserId(userId);
	}

}
