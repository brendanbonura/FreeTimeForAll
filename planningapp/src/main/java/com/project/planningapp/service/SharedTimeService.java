package com.project.planningapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.SharedTime;
import com.project.planningapp.entity.User;

public interface SharedTimeService {
	
	public List<SharedTime> findSharedTimes(User loggedInUser, Map<LocalDate, List<AvailableTime>> availableTimesByDate);

}
