package com.project.planningapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "availabletimes")
public class AvailableTime {
	
	// Table fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "availableTimes_id")
	private Long id;
	
	@Column(name = "availableTimes_date")
	@NotEmpty(message = "* Please provide a valid date")
	@Future(message = "* Please provide a date that has not passed")
	private String date;
	
	@Column(name = "availableTimes_startTime")
	@NotEmpty(message = "* Please provide a valid start time")
	@Future(message = "* Please provide a start time that has not passed")
	private String startTime;
	
	@Column(name = "availableTimes_endTime")
	@NotEmpty(message = "*Please provide a valid end time")
	@Future(message = "* Please provide an end time that has not passed")
	private String endTime;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="group_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Group group;
	
	// Constructors
	
	public AvailableTime() {
		
	}
	
	public AvailableTime(
			@NotEmpty(message = "* Please provide a valid date") 
			@Future(message = "* Please provide a date that has not passed") 
				String date,
			@NotEmpty(message = "* Please provide a valid start time") 
			@Future(message = "* Please provide a start time that has not passed") 
				String startTime,
			@NotEmpty(message = "*Please provide a valid end time") 
			@Future(message = "* Please provide an end time that has not passed") 
				String endTime,
			User user, Group group) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.user = user;
		this.group = group;
	}
	
	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	// tostring
	@Override
	public String toString() {
		return "AvailableTime [id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", user=" + user + ", group=" + group + "]";
	}

}
