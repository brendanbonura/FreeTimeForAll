package com.project.planningapp.comparator;

import java.util.Comparator;

import com.project.planningapp.entity.AvailableTime;

public class AvailableTimeComparator implements Comparator<AvailableTime> {

	@Override
	public int compare(AvailableTime at1, AvailableTime at2) {
		return (at1.getStartTime().compareTo(at2.getStartTime()));
	}

}
