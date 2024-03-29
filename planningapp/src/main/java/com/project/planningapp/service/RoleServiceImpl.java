package com.project.planningapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.RoleDao;
import com.project.planningapp.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public List<String> getRoleNamesByUserId(Long userId) {
		return roleDao.getRoleNamesByUserId(userId);
	}
	
	@Override
	@Transactional
	public Role getRoleById(Long id) {
		return roleDao.getRoleById(id);
	}

}
