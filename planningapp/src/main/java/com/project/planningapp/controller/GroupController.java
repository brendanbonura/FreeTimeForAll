package com.project.planningapp.controller;

import java.security.Principal;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.planningapp.entity.Group;
import com.project.planningapp.service.EmailService;
import com.project.planningapp.service.GroupService;
import com.project.planningapp.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private UserService userService;
	 
	@Autowired
	private GroupService groupService;
	
	@Autowired EmailService emailService;
	
	// used to trim empty strings to null
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String groupPage(
			@RequestParam("groupId") int groupId,
			Model model, 
			Principal principal) {
		Group group = groupService.getGroupById((long) groupId);
		model.addAttribute("group", group);

		return "groups/group";
	}
	
	@RequestMapping(value = "/processInviteUserEmail", method = RequestMethod.GET)
	public String processInviteUserEmail(
			@RequestParam("groupId") int groupId,
			@RequestParam("userId") int userId) {
		com.project.planningapp.entity.User user = userService.getUserById((long) userId);
		Group group = groupService.getGroupById((long) groupId);
		groupService.addUserToGroup(group, user);
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/processInviteUserForm", method = RequestMethod.POST)
	public String processInviteUserForm(
			@ModelAttribute("email") String email,
			@RequestParam("groupId") int groupId,
			Model model,
			Principal principal) throws MessagingException {
		User loggedInUser = (User) ((Authentication)principal).getPrincipal();
		com.project.planningapp.entity.User existingUser = userService.getUserByEmail(email);
		Group group = groupService.getGroupById((long) groupId);
		
		model.addAttribute("group", group);
		
		if(existingUser == null) {
			model.addAttribute("email", new String());
			model.addAttribute("inviteError", "The email you entered is not associated with a registered user");
			return "groups/inviteUser";
		}
		else if (existingUser != null && group.getUsers().contains(existingUser)) {
			model.addAttribute("email", new String());
			model.addAttribute("inviteError", "That user is alread in this group!");
			return "groups/inviteUser";
		}
		emailService.sendGroupInviteEmail(
				group, existingUser, userService.getUserByEmail(loggedInUser.getUsername()));
		return "redirect:/home";
	}
	
	@RequestMapping(value = "inviteUser", method = RequestMethod.GET)
	public String inviteUserPage(
			@RequestParam("groupId") int groupId,
			Model model,
			Principal principal) {
		User loggedInUser = (User) ((Authentication)principal).getPrincipal();
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("group", groupService.getGroupById((long) groupId));
		model.addAttribute("email", new String());
		return "groups/InviteUser";
	}
	
	@RequestMapping(value = "/newGroup", method = RequestMethod.GET)
	public String newGroupPage(Model model, Principal principal) {
		User loggedInUser = (User) ((Authentication)principal).getPrincipal();
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("group", new Group());
		return "groups/newGroup";
	}
	
	@RequestMapping(value = "/processNewGroupForm", method = RequestMethod.POST)
	public String processNewGroupForm(
			@Valid @ModelAttribute("group") Group group,
			BindingResult bindingResult,
			Model model,
			Principal principal) {
		User loggedInUser = (User) ((Authentication)principal).getPrincipal();
		
		// form validation via entity annotations
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResultErrors", bindingResult.getAllErrors());
			model.addAttribute("group", group);
			return "groups/newGroup";
		}
		
		// add logged in user to group as first user and save group to db
		groupService.addUserToGroup(group, userService.getUserByEmail(loggedInUser.getUsername()));
		groupService.saveGroup(group);
		
		return "home";
		
	}

}
