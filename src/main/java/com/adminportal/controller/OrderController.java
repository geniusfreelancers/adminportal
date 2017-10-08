package com.adminportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adminportal.domain.Order;
import com.adminportal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/orderList")
	public String orderList(Model model){
		List<Order> ordersList = orderService.findAll();
		model.addAttribute("ordersList", ordersList);
		
		return "orderList";
		
	}
	
}
