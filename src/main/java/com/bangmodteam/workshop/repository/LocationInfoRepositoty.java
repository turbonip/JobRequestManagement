package com.bangmodteam.workshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bangmodteam.workshop.entity.LocationInfo;

@Repository
public interface LocationInfoRepositoty extends CrudRepository<LocationInfo, Long> {

	@Transactional(readOnly = true)
	public List<LocationInfo> findByGroupInfoId(Long groupId);

}
