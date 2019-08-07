package com.project.planningapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.AvailableTimeDao;
import com.project.planningapp.entity.AvailableTime;

@Service
public class AvailableTimeServiceImpl implements AvailableTimeService {
	
	@Autowired
	private AvailableTimeDao availableTimeDao;

	@Override
	@Transactional
	public void saveAvailableTime(AvailableTime availableTime) {
		availableTimeDao.saveAvailableTime(availableTime);
	}

}
