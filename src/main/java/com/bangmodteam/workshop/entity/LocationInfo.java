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
@Table(name = "Location_Info")
@Getter
@Setter
public class LocationInfo extends BaseEntity {

	@Column(name = "location_name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "group_location", joinColumns = @JoinColumn(name = "location_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private GroupInfo groupInfo;

	@OneToMany(mappedBy = "locationInfo", fetch = FetchType.LAZY)
	private List<ProductLineInfo> productLineInfos;

}
