package com.bangmodteam.workshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.context.SecurityContextHolder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "created_by")
	private Long createBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;

	@Column(name = "updated_by")
	private Long updateBy;

	@PrePersist
	protected void onCreate() {
		updateAt = createAt = new Date();
		updateBy = createBy = checkCurrentUpdateBy();

	}

	@PreUpdate
	protected void onUpdate() {
		updateAt = new Date();
		updateBy = checkCurrentUpdateBy();
	}

	protected Long checkCurrentUpdateBy() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			String principal = SecurityContextHolder.getContext().getAuthentication().getName();
			if (principal != null && !"anonymousUser".equalsIgnoreCase(principal)) {
				User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				return currentUser.getId();
			}
		}
		return null;
	}

}
