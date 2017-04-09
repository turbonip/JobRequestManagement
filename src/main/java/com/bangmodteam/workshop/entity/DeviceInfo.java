package com.bangmodteam.workshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Device_Info")
@Getter
@Setter
public class DeviceInfo extends BaseEntity {

	@Column(name = "device_name")
	private String name;
	
}
