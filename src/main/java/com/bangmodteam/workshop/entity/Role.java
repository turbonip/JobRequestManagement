package com.bangmodteam.workshop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Setter
@Getter
public class Role extends BaseEntity {

	private String name;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;

}