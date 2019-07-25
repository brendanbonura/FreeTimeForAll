package com.project.planningapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.planningapp.entity.Role;

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

	@Override
	public Role getRoleById(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Role> theQuery = currentSession.createQuery(
				"from Role where id=:roleId", Role.class);
		theQuery.setParameter("roleId", id);
		Role role = theQuery.getSingleResult();
		return role;
	}

}
