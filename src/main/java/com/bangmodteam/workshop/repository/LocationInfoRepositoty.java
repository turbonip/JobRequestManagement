package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.LocationInfo;

@Repository
public interface LocationInfoRepositoty extends CrudRepository<LocationInfo, Long> {

}
