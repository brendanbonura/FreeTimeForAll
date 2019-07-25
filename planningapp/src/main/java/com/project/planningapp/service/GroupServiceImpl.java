package com.project.planningapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.GroupDao;
import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.User;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;

	@Override
	@Transactional
	public void saveGroup(Group group) {
		groupDao.saveGroup(group);
	}

	@Override
	@Transactional
	public Group addUserToGroup(Group group, User user) {
		return groupDao.addUserToGroup(group, user);
	}

	@Override
	@Transactional
	public Group getGroupById(Long id) {
		return groupDao.getGroupById(id);
	}

}
