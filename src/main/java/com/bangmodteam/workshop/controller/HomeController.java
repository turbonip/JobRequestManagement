package com.bangmodteam.workshop.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Secured("ROLE_ADMIN")
public class HomeController {

	@Secured({ "ROLE_USER" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("thymeleaf", "Test thymeleaf");

		// Customer cus = customerRepository.findByFirstName("ME");

		return "home";
	}

}
