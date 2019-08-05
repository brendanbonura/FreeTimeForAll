package com.project.planningapp.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.User;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Value("${spring.mail.username}")
	private String springEmail;
	
	@Value("${homeUrl}")
	private String homeUrl;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendGroupInviteEmail(Group group, User recievingUser, User loggedInUser) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		
		String inviteLink = "http://" + homeUrl + "/groups/processInviteUserEmail/?groupId=" 
				+ group.getId() + "&userId=" + recievingUser.getId();
		String htmlMsg = "<p>"
				+ "You have been invited to join the '" + group.getName()
				+ "' group by " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "."
				+ "<br>"
				+ "To join this group, please click the link bellow:"
				+ "<br>"
				+ inviteLink
				+ "</p>";
		System.out.println(inviteLink);
		System.out.println(htmlMsg);
		
		helper.setText(htmlMsg, true);
		helper.setTo(recievingUser.getEmail());
		helper.setSubject("Group Invite");
		helper.setFrom(springEmail);
		javaMailSender.send(mimeMessage);
	}
	
}
