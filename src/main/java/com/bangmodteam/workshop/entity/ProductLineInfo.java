package com.bangmodteam.workshop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Product_Line_Info")
@Getter
@Setter
public class ProductLineInfo extends BaseEntity {

	private String lineName;

	@JsonIgnore()
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "location_id")
	private LocationInfo locationInfo;

}
