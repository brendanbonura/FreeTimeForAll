package com.project.planningapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.AvailableTimeDao;
import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.User;

@Service
public class AvailableTimeServiceImpl implements AvailableTimeService {
	
	@Autowired
	private AvailableTimeDao availableTimeDao;

	@Override
	@Transactional
	public void saveAvailableTime(AvailableTime availableTime) {
		availableTimeDao.saveAvailableTime(availableTime);
	}

	@Override
	@Transactional
	public List<AvailableTime> getAvailableTimesByUserAndDate(User user, LocalDate date) {
		return availableTimeDao.getAvailableTimesByUserAndDate(user, date);
	}

}
