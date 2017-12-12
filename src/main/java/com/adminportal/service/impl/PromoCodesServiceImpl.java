package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.PromoCodes;
import com.adminportal.repository.PromoCodesRepository;
import com.adminportal.service.PromoCodesService;

@Service
public class PromoCodesServiceImpl implements PromoCodesService {
	
	@Autowired
	private PromoCodesRepository promoCodesRepository;
	
	public PromoCodes findOne(Long id){
		return promoCodesRepository.findOne(id);
	}
	
	
	public PromoCodes findByPromoCode(String couponCode){
		return promoCodesRepository.findByCouponCode(couponCode);
	}

}
