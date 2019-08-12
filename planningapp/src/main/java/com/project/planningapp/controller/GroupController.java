package com.project.planningapp.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.project.planningapp.entity.AvailableTime;
import com.project.planningapp.entity.Group;
import com.project.planningapp.entity.SharedTime;
import com.project.planningapp.entity.User;
import com.project.planningapp.service.AvailableTimeService;
import com.project.planningapp.service.EmailService;
import com.project.planningapp.service.GroupService;
import com.project.planningapp.service.SharedTimeService;
import com.project.planningapp.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private UserService userService;
	 
	@Autowired
	private GroupService groupService;
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private AvailableTimeService availableTimeService;
	
	@Autowired
	private SharedTimeService sharedTimeService;
	
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
		
		User loggedInUser = userService.getUserByEmail(principal.getName());
		
		// Maps all unique dates of AvailableTimes in group 
		// to a set containing their corresponding AvailableTimes
		// Uses treemap to sort key set by date
		Map<LocalDate, List<AvailableTime>> availableTimesByDate = new TreeMap<>();
		for(AvailableTime availableTime : group.getAvailableTimes()) {
			if(!availableTimesByDate.containsKey(availableTime.getDate())) {
				availableTimesByDate.put(availableTime.getDate(), new ArrayList<AvailableTime>());
			}
			availableTimesByDate.get(availableTime.getDate()).add(availableTime);
		}
		
		// sorts available times in ascending order by start time for each date
		Comparator<AvailableTime> byStartTimeComparator = 
				(AvailableTime at1, AvailableTime at2) -> at1.getStartTime().compareTo(at2.getStartTime());
		for(LocalDate date : availableTimesByDate.keySet()) {
			Collections.sort(availableTimesByDate.get(date), byStartTimeComparator);
		}
		model.addAttribute("availableTimesByDate", availableTimesByDate);
		
		List<SharedTime> sharedTimes  = new ArrayList<>();
		
		// iterate over next two weeks
		if(!availableTimesByDate.isEmpty()) {
			sharedTimes = sharedTimeService.findSharedTimes(loggedInUser, availableTimesByDate);
		}
		
		model.addAttribute("sharedTimes", sharedTimes);
		
		return "groups/group";
	}
	
	@RequestMapping(value = "/processInviteUserEmail", method = RequestMethod.GET)
	public String processInviteUserEmail(
			@RequestParam("groupId") int groupId,
			@RequestParam("userId") int userId) {
		User user = userService.getUserById((long) userId);
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
		User loggedInUser = userService.getUserByEmail(principal.getName());
		User existingUser = userService.getUserByEmail(email);
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
				group, existingUser, loggedInUser);
		return "redirect:/home";
	}
	
	@RequestMapping(value = "inviteUser", method = RequestMethod.GET)
	public String inviteUserPage(
			@RequestParam("groupId") int groupId,
			Model model,
			Principal principal) {
		User loggedInUser = userService.getUserByEmail(principal.getName());
		
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("group", groupService.getGroupById((long) groupId));
		model.addAttribute("email", new String());
		
		return "groups/InviteUser";
	}
	
	@RequestMapping(value = "/newGroup", method = RequestMethod.GET)
	public String newGroupPage(Model model, Principal principal) {
		User loggedInUser = userService.getUserByEmail(principal.getName());
		
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
		User loggedInUser = userService.getUserByEmail(principal.getName());
		
		// form validation via entity annotations
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResultErrors", bindingResult.getAllErrors());
			model.addAttribute("group", group);
			return "groups/newGroup";
		}
		
		// add logged in user to group as first user and save group to db
		groupService.addUserToGroup(group, loggedInUser);
		groupService.saveGroup(group);
		
		return "home";	
	}
	
	@RequestMapping(value = "/newAvailableTime", method = RequestMethod.GET)
	public String newAvailableTimePage(
			@RequestParam("groupId") int groupId,
			Model model, 
			Principal principal) {
		User loggedInUser = userService.getUserByEmail(principal.getName());
		model.addAttribute("group", groupService.getGroupById((long) groupId));
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("availableTime", new AvailableTime());
		
		return "groups/newAvailableTime";
	}

	@RequestMapping(value = "/processNewAvailableTimeForm", method = RequestMethod.POST)
	public String processNewAvailableTimeForm(
			@RequestParam("groupId") int groupId,
			@Valid @ModelAttribute("availableTime") AvailableTime availableTime,
			BindingResult bindingResult,
			Model model,
			Principal principal) {
		User loggedInUser = userService.getUserByEmail(principal.getName());
		Group group = groupService.getGroupById((long) groupId);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResultErrors", bindingResult.getAllErrors());
			model.addAttribute("availaleTime", availableTime);
			model.addAttribute("group", groupService.getGroupById((long) groupId));
			return "groups/newAvailableTime";
		}
		
		availableTime.setUser(loggedInUser);
		availableTime.setGroup(group);
		
		availableTimeService.saveAvailableTime(availableTime);
		
		return "redirect:/groups/?groupId=" + groupId;
	}
	
}
