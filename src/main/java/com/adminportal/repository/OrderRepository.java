package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
