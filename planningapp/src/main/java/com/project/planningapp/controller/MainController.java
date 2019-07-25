package com.project.planningapp.controller;

import java.security.Principal;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.planningapp.entity.Group;
import com.project.planningapp.service.GroupService;
import com.project.planningapp.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage(Model model) {
		model.addAttribute("title", "Planning App Index");
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("logoutSuccessful", "You are now logged out!");
		return "login";
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		User loggedInUser = (User) ((Authentication)principal).getPrincipal();
		com.project.planningapp.entity.User user = 
				userService.getUserByEmail(loggedInUser.getUsername());
		model.addAttribute("groups", user.getGroups());
		return "home";
	}
	
	@RequestMapping(value="/403", method=RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		return "403";
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {
		return "admin";
	}

}
