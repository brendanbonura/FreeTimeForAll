package com.project.planningapp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.planningapp.entity.Role;
import com.project.planningapp.entity.User;
import com.project.planningapp.service.RoleService;
import com.project.planningapp.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	// used to trim empty strings to null
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("User", new User());
		model.addAttribute("confirmPassword", "");
		return "register";
	}
	
	@RequestMapping(value = "/processRegistrationForm", method = RequestMethod.POST)
	public String processRegistrationForm(
			@Valid @ModelAttribute("User") User user,
			BindingResult bindingResult,
			Model model,
			@ModelAttribute("confirmPassword") String confirmPassword) {
		
		// form validation via entity annotations
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResultErrors", bindingResult.getAllErrors());
			model.addAttribute("User", user);
			return "register";
		}
		
		// check if that username is taken
		User existingUser = userService.getUserByEmail(user.getEmail());
		if (existingUser != null) {
			user.setEmail(null);
			model.addAttribute("User", user);
			model.addAttribute("registrationError", "Email is already taken");
			return "register";
		}
		if(!user.getPassword().equals(confirmPassword)) {
			user.setPassword(null);
			model.addAttribute("confirmPassword", null);
			model.addAttribute("User", user);
			model.addAttribute("registrationError", "The Password and Confirm Password fields do not match");
			return "register";
		}
		
		// add basic role to user upon user creation
		Role role = roleService.getRoleById((long) 1);
		userService.addRoleToUser(user, role);
		
		userService.saveUser(user);
		model.addAttribute("registrationConfirmation",
				"Thank you for registering " + user.getFirstName() + "! You can now sign in.");
		
		return "login";
	}
	
}
