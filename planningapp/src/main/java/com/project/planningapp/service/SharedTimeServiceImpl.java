package com.project.planningapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.SharedTime;
import com.project.planningapp.entity.User;

@Service
public class SharedTimeServiceImpl implements SharedTimeService {
	
	@Autowired
	AvailableTimeService availableTimeService;
	
	public List<SharedTime> findSharedTimes(User loggedInUser, Map<LocalDate, List<AvailableTime>> availableTimesByDate) {
		
		List<SharedTime> sharedTimes = new ArrayList<>();
		
		for(LocalDate date = LocalDate.now(); date.isBefore(LocalDate.now().plusWeeks(2)); date = date.plusDays(1)) {
			List<AvailableTime> timesForLoggedInUser = 
					availableTimeService.getAvailableTimesByUserAndDate(loggedInUser, date);
			// iterate over available times for logged in specific date
			for(AvailableTime timeLoggedIn : timesForLoggedInUser) {
				SharedTime lastSharedTime = new SharedTime();
				// iterate over available times for all users on specific date
				for(AvailableTime timeOther : availableTimesByDate.get(date)) {
					// if ((StartA <= EndB) and (EndA >= StartB))
					if(timeOther.getUser() != loggedInUser && 
							(timeLoggedIn.getStartTime().compareTo(timeOther.getEndTime()) < 0 &&
							 timeLoggedIn.getEndTime().compareTo(timeOther.getStartTime()) > 0)) {
						
						LocalTime latestStartTime = timeLoggedIn.getStartTime().compareTo(timeOther.getStartTime()) > 0 
								? timeLoggedIn.getStartTime() : timeOther.getStartTime();
						LocalTime earliestEndTime = timeLoggedIn.getEndTime().compareTo(timeOther.getEndTime()) < 0
								? timeLoggedIn.getEndTime() : timeOther.getEndTime();

						if(lastSharedTime.getUsers() != null
								&& lastSharedTime.getEndTime().compareTo(timeOther.getStartTime()) < 0) {
							if(!lastSharedTime.getUsers().contains(timeOther.getUser())) {
								List<User> lastSharedTimeUsers = lastSharedTime.getUsers();
								lastSharedTimeUsers.add(timeOther.getUser());
								lastSharedTime.setUsers(lastSharedTimeUsers);
							} 
							if(lastSharedTime.getStartTime().compareTo(latestStartTime) < 0) {
								lastSharedTime.setStartTime(latestStartTime);
							}
							if(lastSharedTime.getEndTime().compareTo(earliestEndTime) > 0) {
								lastSharedTime.setEndTime(earliestEndTime);
							}
						} else {
							lastSharedTime = new SharedTime(new ArrayList<User>(),
									date, latestStartTime, earliestEndTime);
							lastSharedTime.addUser(timeOther.getUser());
						}
						
						if(!sharedTimes.contains(lastSharedTime)) sharedTimes.add(lastSharedTime);
					}		
				}
			}
		}
		return sharedTimes;
	}
	
}

