package com.project.planningapp.service;

import java.time.LocalDate;
import java.util.List;

import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.User;

public interface AvailableTimeService {

	public void saveAvailableTime(AvailableTime availableTime);
	public List<AvailableTime> getAvailableTimesByUserAndDate(User user, LocalDate date);
	
}
