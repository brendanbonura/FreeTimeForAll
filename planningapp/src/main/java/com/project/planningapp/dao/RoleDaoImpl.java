package com.project.planningapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<String> getRoleNamesByUserId(Long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<String> theQuery = currentSession.createQuery(
				"SELECT role_name FROM roles r LEFT JOIN users_roles ur ON r.role_id = ur.role_id WHERE ur.user_id =:userId"
				, String.class);
		theQuery.setParameter("userId", userId);
		List<String> roles = theQuery.getResultList();
		return roles;
	}

}
