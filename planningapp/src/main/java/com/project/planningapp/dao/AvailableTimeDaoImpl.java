package com.project.planningapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.User;

@Repository
public class AvailableTimeDaoImpl implements AvailableTimeDao {
	
	@Autowired
	SessionFactory sessionFactory;


	@Override
	public void saveAvailableTime(AvailableTime availableTime) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(availableTime);
	}


	@Override
	public List<AvailableTime> getAvailableTimesByUserAndDate(User user, LocalDate date) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<AvailableTime> theQuery = currentSession.createQuery(
				"from AvailableTime where user=:User and date=:Date" , AvailableTime.class);
		theQuery.setParameter("User", user);
		theQuery.setParameter("Date", date);
		List<AvailableTime> availableTimesForUserAndDate = theQuery.getResultList();
		return availableTimesForUserAndDate;
	}

}
