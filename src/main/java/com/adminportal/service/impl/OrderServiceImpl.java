package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.Order;
import com.adminportal.repository.OrderRepository;
import com.adminportal.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	
	public Order save(Order order){
		return orderRepository.save(order);
	}

	public List<Order> findAll(){
		return (List<Order>) orderRepository.findAll();
	}
	
	public Order findOne(Long id){
		return orderRepository.findOne(id);
	}
	
	public void removeOne(Long id){
		orderRepository.delete(id);
	}
}
