package com.adminportal.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adminportal.domain.PromoCodes;
import com.adminportal.domain.SiteSetting;
import com.adminportal.domain.StaticPage;
import com.adminportal.domain.User;
import com.adminportal.repository.PromoCodesRepository;
import com.adminportal.service.PromoCodesService;
import com.adminportal.service.SiteSettingService;
import com.adminportal.service.UserService;
@Controller
@RequestMapping("/coupons")
public class CouponController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SiteSettingService siteSettingService;
	
	@Autowired
	private PromoCodesService promoCodesService;
	
	@Autowired
	private PromoCodesRepository promoCodesRepository;
	
	@RequestMapping("/allcoupons")
	public String coupons(Model model,@AuthenticationPrincipal User activeUser) {
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
		List<PromoCodes> promoCodesList = promoCodesService.findAll();
		model.addAttribute("promoCodesList", promoCodesList);
		if(promoCodesList == null) {
			model.addAttribute("emptyPage", true);
		}else {
			model.addAttribute("emptyPage", false);
		}
		return "allcoupons";
	}
	
	@RequestMapping("/addcoupon")
	public String addCoupon(Model model,@AuthenticationPrincipal User activeUser)
	{
		PromoCodes promoCodes = new PromoCodes();
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("promoCodes", promoCodes);
		return "addcoupon";
	}
	
	@RequestMapping(value="/addcoupon", method=RequestMethod.POST)
	public String addCouponPOST(@ModelAttribute("promoCodes") PromoCodes promoCodes,BindingResult bindingResult, Model model,@AuthenticationPrincipal User activeUser)
	{
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
		//Change coupon code to lowercase to compare
		if(promoCodesService.findByPromoCode(promoCodes.getCouponCode()) != null) {
			model.addAttribute("duplicatepromo", true);
			model.addAttribute("promoCodes", promoCodes);
			PromoCodes existingpromo =promoCodesService.findByPromoCode(promoCodes.getCouponCode());
			model.addAttribute("existingpromo", existingpromo);
			return "addcoupon";
		}
		promoCodes.setCouponCode(promoCodes.getCouponCode().toLowerCase());
		promoCodes.setPromoType(promoCodes.getPromoType().toLowerCase());
		/*SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date startDate = formatter.parse(promoCodes.getStartDate().toString()); 
		Date endDate = formatter.parse(promoCodes.getExpiryDate().toString()); */
		promoCodes.setStartDate(promoCodes.getStartDate());
		promoCodes.setExpiryDate(promoCodes.getExpiryDate());
        promoCodes.setAddedBy(user.getFirstName()+" "+user.getLastName());
        promoCodes.setAddedOn(Calendar.getInstance().getTime());
        promoCodesRepository.save(promoCodes);
        model.addAttribute("promoCodes", promoCodes);
        model.addAttribute("duplicatepromo", false);
		return "redirect:/coupons/allcoupons";
	}
	
	@RequestMapping("/details/{id}")
	public String couponDetails(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser)
	{
		PromoCodes promoCodes = promoCodesService.findOne(id);
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
		if(promoCodes != null) {
			model.addAttribute("promoCodes", promoCodes);
			return "promodetails";
		}
		
		return "badRequestPage";
	}
	
	@RequestMapping("/active/{id}")
	public String updateCouponStatus(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser) {
		PromoCodes promoCodes = promoCodesService.findOne(id);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        if(promoCodes.isPromoStatus()) {
        	promoCodes.setPromoStatus(false);
        }else {
        	promoCodes.setPromoStatus(true);
        }
        promoCodes.setUpdatedBy(user.getFirstName()+" "+user.getLastName());
        promoCodes.setUpdatedOn(Calendar.getInstance().getTime());
        promoCodesRepository.save(promoCodes); 
        return "redirect:/coupons/allcoupons";
	}
	

	@RequestMapping("/edit/{id}")
	public String editCoupon(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser) {
		PromoCodes promoCodes = promoCodesService.findOne(id);
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("promoCodes",promoCodes);
        return "editpromo";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editCouponPost(
			@Valid @ModelAttribute("promoCodes") PromoCodes promoCodes, BindingResult result,Model model,
			HttpServletRequest request, @AuthenticationPrincipal User activeUser) {
		 User user = userService.findByUsername(activeUser.getUsername());
	     model.addAttribute("user", user);
	     PromoCodes newPromoCodes = promoCodesService.findOne(promoCodes.getId());
/*	     newPromoCodes.setAddedBy(promoCodes.getAddedBy());
	     newPromoCodes.setAddedOn(promoCodes.getAddedOn());*/
	     newPromoCodes.setUpdatedBy(user.getFirstName()+" "+user.getLastName());
	     newPromoCodes.setUpdatedOn(Calendar.getInstance().getTime());
	     promoCodesRepository.save(newPromoCodes); 
	     return "redirect:/coupons/allcoupons";
	}
}
