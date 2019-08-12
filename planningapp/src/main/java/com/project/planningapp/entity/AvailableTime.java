package com.project.planningapp.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "availabletimes")
public class AvailableTime {
	
	// Table fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "availableTimes_id")
	private Long id;
	
	@Column(name = "availableTimes_date")
	@NotNull(message = "* Please provide a valid date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@Column(name = "availableTimes_startTime")
	@NotNull(message = "* Please provide a valid start time")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@Column(name = "availableTimes_endTime")
	@NotNull(message = "*Please provide a valid end time")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
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
			@NotEmpty(message = "* Please provide a valid date") LocalDate date,
			@NotEmpty(message = "* Please provide a valid start time") LocalTime startTime,
			@NotEmpty(message = "*Please provide a valid end time") LocalTime endTime,
			User user, 
			Group group) {
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

	@Override
	public String toString() {
		return "AvailableTime [id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", user=" + user + ", group=" + group + "]";
	}


}
