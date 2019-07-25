package com.project.planningapp.dao;

import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.User;

public interface GroupDao {
	
	public void saveGroup(Group group);
	public Group addUserToGroup(Group group, User user);
	public Group getGroupById(Long id);

}
