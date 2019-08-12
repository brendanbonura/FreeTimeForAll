package com.project.planningapp.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SharedTime {

	private List<User> users;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public SharedTime() {
		
	}
	
	public SharedTime(
			List<User> users, 
			LocalDate date, 
			LocalTime startTime, 
			LocalTime endTime) {
		this.users = users;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public void addUser(User user) {
		users.add(user);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "SharedTime [users=" + users + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}
	
}
