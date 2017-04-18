package com.bangmodteam.workshop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroupInfo extends BaseEntity {

	@Column(name = "group_name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_id")
	private User leader;

	@OneToMany(mappedBy = "groupInfo", fetch = FetchType.LAZY)
	private List<User> userMembers;

	@OneToMany(mappedBy = "groupInfo", fetch = FetchType.LAZY)
	private List<LocationInfo> locations;

}
