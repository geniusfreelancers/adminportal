package com.adminportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adminportal.domain.User;
import com.adminportal.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(){
		return "home";
	}
	


	@RequestMapping("/login")
	public String login(){
	
		return "login";
	}
}
