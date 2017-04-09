package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.GroupInfo;

@Repository
public interface GroupInfoRepository extends CrudRepository<GroupInfo, Long> {

}
