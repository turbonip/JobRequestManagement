package com.bangmodteam.workshop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Problem_Category")
@Getter
@Setter
public class ProblemCategory extends BaseEntity {

	@Column(name = "problem_cat_code")
	private String code;

	@Column(name ="problem_cat_name")
	private String name;

	@OneToMany(mappedBy = "problemCategory", fetch = FetchType.LAZY)
	private List<ProblemSubCategory> problemSubCategories;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "position_problemcat", joinColumns = @JoinColumn(name = "problem_cat_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
	private Position positionResponse;

}
