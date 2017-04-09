package com.bangmodteam.workshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Problem_Sub_Category")
@Getter
@Setter
public class ProblemSubCategory extends BaseEntity {

	@Column(name = "problem_sub_cat_code")
	private String code;

	@Column(name = "problem_sub_cat_name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "problem_cat_id")
	private ProblemCategory problemCategory;

}