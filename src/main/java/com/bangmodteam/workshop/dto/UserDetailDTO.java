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

	public UserDetailDTO(User user) {
		
		if (user != null) {
			this.userId = user.getId();
			this.username = user.getUsername();
			this.positionName = user.getPosition().getName();
		}

	}

}
