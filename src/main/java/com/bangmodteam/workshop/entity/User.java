package com.bangmodteam.workshop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User extends BaseEntity {

	private String username;
	private String password;

	private String firstName;
	private String lastName;
	
	public String getName() {
		
		return this.firstName + " " + this.lastName;
	}
	
	@Transient
	private String passwordConfirm;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private GroupInfo groupInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Position position;

	@OneToMany(mappedBy = "assignTo", fetch = FetchType.LAZY)
	private List<Ticket> assignedTickets;

}