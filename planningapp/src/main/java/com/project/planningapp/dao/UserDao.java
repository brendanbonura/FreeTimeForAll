package com.project.planningapp.dao;

import java.util.List;

import com.project.planningapp.entity.Role;
import com.project.planningapp.entity.User;

public interface UserDao {

	public List<User> getUsers();
	public void saveUser(User user);
	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public User addRoleToUser(User user, Role role);
	public void deleteUserById(Long id);
	
}
