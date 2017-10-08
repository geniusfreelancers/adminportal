package com.adminportal.service;

import java.util.List;

import com.adminportal.domain.Order;

public interface OrderService {
	Order save(Order order);
	
	List<Order> findAll();
	
	Order findOne(Long id);
	
	void removeOne(Long id);
}
