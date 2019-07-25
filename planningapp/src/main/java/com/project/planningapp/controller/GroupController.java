package com.project.planningapp.controller;

import java.security.Principal;

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
import com.project.planningapp.service.GroupService;
import com.project.planningapp.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
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
