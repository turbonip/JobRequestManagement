package com.bangmodteam.workshop.dto;

import com.bangmodteam.workshop.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {

	private Long userId;
	private String username;
	private String positionName;
	
	private String firstname;
	private String lastname;

	public UserDetailDTO(User user) {
		
		if (user != null) {
			this.userId = user.getId();
			this.username = user.getUsername();
			this.positionName = user.getPosition().getName();
			
			this.firstname = user.getFirstName();
			this.lastname = user.getLastName();
		}

	}
	
	public String getName() {
		return this.firstname + " " + this.lastname;
	}

}
