package com.bangmodteam.workshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bangmodteam.workshop.repository.UserRepository;

@Controller
public class AuthenticationController {

	@Autowired
	UserRepository userRepository;


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index()
	{
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {

		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

		HttpSession session = request.getSession();

		if (session != null) {
			session.invalidate();
		}

		return "login";
	}

}
