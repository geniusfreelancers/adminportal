package com.adminportal.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.adminportal.domain.Order;
import com.adminportal.service.OrderService;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.adminportal.AdminportalApplication;

@Controller
@RequestMapping("/order")
public class OrderController {

	private BraintreeGateway gateway = AdminportalApplication.gateway;

    private Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] {
       Transaction.Status.AUTHORIZED,
       Transaction.Status.AUTHORIZING,
       Transaction.Status.SETTLED,
       Transaction.Status.SETTLEMENT_CONFIRMED,
       Transaction.Status.SETTLEMENT_PENDING,
       Transaction.Status.SETTLING,
       Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };
    
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/orderList")
	public String orderList(Model model){
		List<Order> ordersList = orderService.findAll();
		model.addAttribute("ordersList", ordersList);
		if(ordersList == null) {
			model.addAttribute("emptyOrder", true);
			return "orderList";
		}else {
			model.addAttribute("emptyOrder", false);
		}
		return "orderList";
		
	}
	
	@RequestMapping(value = "/orderdetails/{orderId}")
	   public String orderDetailsPage(@PathVariable Long orderId, Model model) {
	       Transaction transaction;
	       Order order;
	       try {
	    	   order = orderService.findOne(orderId);
	    	   String transactionId = order.getPaymentConfirm();
	           transaction = gateway.transaction().find(transactionId);
	          
	       } catch (Exception e) {
	           System.out.println("Exception: " + e);
	           return "badRequestPage";
	       }

	       model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
	       model.addAttribute("transaction", transaction);
	   		LocalDate today = LocalDate.now();
	   		LocalDate estimatedDeliveryDate;
	   		
	   		if(order.getShippingMethod().equals("groundShipping")){
	   			estimatedDeliveryDate = today.plusDays(5);
	   		}else{
	   			estimatedDeliveryDate = today.plusDays(3);
	   		}
	   		if(transaction.getPaymentInstrumentType().equals("paypal_account")){
	        	   model.addAttribute("paypalMethod",true);
		   			 
		   		}else{
		   			model.addAttribute("creditMethod",true);
		   		}
	   		int currentStatus = 1;
	   		if(order.getOrderStatus().equalsIgnoreCase("created")) {
	   			currentStatus = 2;
	   		}else if (order.getOrderStatus().equalsIgnoreCase("processing")) {
	   			currentStatus = 3;
	   		}else if (order.getOrderStatus().equalsIgnoreCase("shipped")) {
	   			currentStatus = 4;
	   		}else if (order.getOrderStatus().equalsIgnoreCase("intransit")) {
	   			currentStatus = 5;
	   		}else if (order.getOrderStatus().equalsIgnoreCase("delivered")) {
	   			currentStatus = 6;
	   		}else {
	   			currentStatus = 2;
	   		}
	   		model.addAttribute("estimatedDeliveryDate",estimatedDeliveryDate);
	   		model.addAttribute("order",order);
	   		model.addAttribute("currentStatus",currentStatus);
	   		model.addAttribute("cartItemList", order.getCartItemList());

	       return "orderdetails";
	   }
	
	
	
}
