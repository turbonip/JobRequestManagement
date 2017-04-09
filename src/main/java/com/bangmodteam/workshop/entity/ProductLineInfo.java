package com.bangmodteam.workshop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Product_Line_Info")
@Getter
@Setter
public class ProductLineInfo extends BaseEntity {

	private String lineName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "productline_location", joinColumns = @JoinColumn(name = "productline_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
	private LocationInfo locationInfo;

}
