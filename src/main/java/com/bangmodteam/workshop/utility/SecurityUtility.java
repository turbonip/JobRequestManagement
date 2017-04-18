package com.bangmodteam.workshop.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtility {

	public static String getCurrentUserName() {

		User currentUser = null;

		try {

			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				String principal = SecurityContextHolder.getContext().getAuthentication().getName();
				if (principal != null && !"anonymousUser".equalsIgnoreCase(principal)) {
					currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				}
			}

		} catch (Exception e) {
			System.out.println("getCurrentUser --> " + e.getMessage());
		}

		return currentUser.getUsername();

	}
	
}
