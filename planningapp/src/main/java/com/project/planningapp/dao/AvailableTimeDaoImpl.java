package com.project.planningapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.planningapp.entity.AvailableTime;

@Repository
public class AvailableTimeDaoImpl implements AvailableTimeDao {
	
	@Autowired
	SessionFactory sessionFactory;


	@Override
	public void saveAvailableTime(AvailableTime availableTime) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(availableTime);
	}

}
