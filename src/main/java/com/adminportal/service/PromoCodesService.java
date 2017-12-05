package com.adminportal.service;

import com.adminportal.domain.PromoCodes;

public interface PromoCodesService {
	
	PromoCodes findOne(Long id);
	
	PromoCodes findByPromoCode(String couponCode);
}
