package com.bangmodteam.workshop.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bangmodteam.workshop.entity.User;
import com.bangmodteam.workshop.repository.UserRepository;

@Controller
public class AuthenticationController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		model.addAttribute("thymeleaf", "Test thymeleaf");
		return "login";
	}

	@RequestMapping(value = "/logout", method = {})
	public String logOut(Locale locale, Model model) {

		User user = userRepository.findByUsername("ADMIN");

		return "index";
	}

}
