package com.project.planningapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.UserDao;
import com.project.planningapp.entity.Role;
import com.project.planningapp.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	// inject customerDao
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User getUserById(Long id) {
		return userDao.getUserById(id);
	}
	
	@Override
	@Transactional
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	@Override
	@Transactional
	public User addRoleToUser(User user, Role role) {
		return userDao.addRoleToUser(user, role);
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) {
		userDao.deleteUserById(id);
	}

}
