package com.adminportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adminportal.domain.SiteSetting;
import com.adminportal.domain.User;
import com.adminportal.service.SiteSettingService;
import com.adminportal.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SiteSettingService siteSettingService;

	@RequestMapping("/")
	public String index(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(Model model,@AuthenticationPrincipal User activeUser){
		User user = userService.findByUsername(activeUser.getUsername());

        model.addAttribute("user", user);
		return "home";
	}
	
	@RequestMapping("/login")
	public String login(){
	
		return "login";
	}
	
	@RequestMapping("/settings")
	public String settings(Model model,@AuthenticationPrincipal User activeUser){
		User user = userService.findByUsername(activeUser.getUsername());
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
		model.addAttribute("siteSettings",siteSettings);
        model.addAttribute("user", user);
		return "settings";
	}
}
