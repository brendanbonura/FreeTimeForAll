package com.project.planningapp.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.User;

@Repository
public class GroupDaoImpl implements GroupDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveGroup(Group group) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(group);
	}

	@Override
	public Group addUserToGroup(Group group, User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		Set<User> users = group.getUsers();
		if (users == null) {
			users = new HashSet<User>();
		}
		users.add(user);
		group.setUsers(users);
		currentSession.saveOrUpdate(group);
		return group;
	}

	@Override
	public Group getGroupById(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Group group = (Group) currentSession.get(Group.class, id);
		return group;
	}

}
