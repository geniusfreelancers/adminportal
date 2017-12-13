package com.adminportal.controller;

import java.util.Calendar;
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
	
	@RequestMapping("/allcoupons")
	public String pages(Model model,@AuthenticationPrincipal User activeUser) {
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
	
	/*@RequestMapping("/addcoupons")
	public String addPages(Model model,@AuthenticationPrincipal User activeUser)
	{
		StaticPage staticpage = new StaticPage();
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("staticpage", staticpage);
		return "addpage";
	}
	
	@RequestMapping(value="/addcoupons", method=RequestMethod.POST)
	public String addPagesPOST(@ModelAttribute("staticpage") StaticPage staticpage,Model model,@AuthenticationPrincipal User activeUser)
	{
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
		//Change pagename to lowercase to compare
		if(staticPageService.findByPagename(staticpage.getPagename()) != null) {
			model.addAttribute("duplicatepage", true);
			model.addAttribute("staticpage", staticpage);
			StaticPage existingpage =staticPageService.findByPagename(staticpage.getPagename());
			model.addAttribute("existingpage", existingpage);
			return "addpage";
		}
		
        staticpage.setAddedBy(user.getFirstName()+" "+user.getLastName());
        staticpage.setAddedDate(Calendar.getInstance().getTime());
        staticPageRepository.save(staticpage);
        model.addAttribute("staticpage", staticpage);
        model.addAttribute("duplicatepage", false);
		return "redirect:/pages";
	}
	
	@RequestMapping("/details/{id}")
	public String pages(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser)
	{
		StaticPage staticpage = staticPageService.findById(id);
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
		if(staticpage != null) {
			model.addAttribute("staticpage", staticpage);
			return "staticpage";
		}
		
		return "badRequestPage";
	}
	
	@RequestMapping("/active/{id}")
	public String updatePageStatus(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser) {
		StaticPage staticpage = staticPageService.findById(id);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        if(staticpage.isPublished()) {
        	staticpage.setPublished(false);
        }else {
        	staticpage.setPublished(true);
        }
        staticpage.setUpdateBy(user.getFirstName()+" "+user.getLastName());
        staticpage.setUpdatedDate(Calendar.getInstance().getTime());
        staticPageRepository.save(staticpage); 
        return "redirect:/pages";
	}
	
	@RequestMapping("/edit/{id}")
	public String editPage(@PathVariable Long id, Model model,@AuthenticationPrincipal User activeUser) {
		StaticPage staticpage = staticPageService.findById(id);
		SiteSetting siteSettings = siteSettingService.findOne(new Long(1));
        model.addAttribute("siteSettings",siteSettings);
        User user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("staticpage",staticpage);
        return "editpage";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editPagePost(
			@Valid @ModelAttribute("staticpage") StaticPage staticpage, BindingResult result,Model model,
			HttpServletRequest request, @AuthenticationPrincipal User activeUser) {
		 User user = userService.findByUsername(activeUser.getUsername());
	     model.addAttribute("user", user);
	    staticpage.setAddedBy(staticpage.getAddedBy());
	    staticpage.setAddedDate(staticpage.getAddedDate());
		staticpage.setUpdateBy(user.getFirstName()+" "+user.getLastName());
        staticpage.setUpdatedDate(Calendar.getInstance().getTime());
		staticPageRepository.save(staticpage); 
		return "redirect:/pages";
	}*/
}
