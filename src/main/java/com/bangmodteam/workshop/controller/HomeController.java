package com.bangmodteam.workshop.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bangmodteam.workshop.constant.RoleType;
import com.bangmodteam.workshop.entity.Customer;
import com.bangmodteam.workshop.repository.CustomerRepository;

@Controller
public class HomeController {

	@Autowired
	private CustomerRepository customerRepository;

	// @Secured("ROLE_USER")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("thymeleaf", "Test thymeleaf");
		
		 Customer cus = customerRepository.findByFirstName("ME");
		
		return "dashboard/dashboard";
	}

}
