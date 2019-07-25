package com.project.planningapp.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.project.planningapp.entity.Role;
import com.project.planningapp.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public List<User> getUsers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> theQuery = currentSession.createQuery("from User", User.class);
		List<User> users = theQuery.getResultList();
		return users;
	}

	@Override
	public void saveUser(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		currentSession.saveOrUpdate(user);	
	}

	@Override
	public User getUserById(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = currentSession.get(User.class, id);
		return user;
	}
	
	@Override
	public User getUserByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> theQuery = currentSession.createQuery(
				"from User where email=:uEmail", User.class);
		theQuery.setParameter("uEmail", email);
		User user = null;
		try {
			user = theQuery.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	@Override
	public User addRoleToUser(User user, Role role) {
		Session currentSession = sessionFactory.getCurrentSession();
		Set<Role> roles = user.getRoles();
		if(roles == null) {
			roles = new HashSet<Role>();
		}
		roles.add(role);
		user.setRoles(roles);
		currentSession.saveOrUpdate(user);
		return user;
	}

	@Override
	public void deleteUserById(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> theQuery =
				currentSession.createQuery("delete from User where id=:userId", User.class);
		theQuery.setParameter("userId", id);
		theQuery.executeUpdate();
	}

}
