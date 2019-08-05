package com.project.planningapp.service;

import javax.mail.MessagingException;

import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.User;

public interface EmailService {

	void sendGroupInviteEmail(Group group, User recievingUser, User loggedInUser) throws MessagingException;
	
}
