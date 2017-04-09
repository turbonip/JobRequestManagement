package com.bangmodteam.workshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.DeviceInfo;

@Repository
public interface DeviceInfoRepository extends CrudRepository<DeviceInfo, Long> {
	
	@Query("select case when count(d.id) > 0 then true else false end from DeviceInfo d where d.name = ?1")
	public boolean existsByName(String name);
	

}
