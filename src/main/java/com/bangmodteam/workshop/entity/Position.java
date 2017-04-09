package com.bangmodteam.workshop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Position extends BaseEntity {

	@Column(name = "position_name")
	private String name;

	@OneToMany(mappedBy = "positionResponse", fetch = FetchType.LAZY)
	private List<ProblemCategory> problemCategories;

	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
	private List<User> users;

}
