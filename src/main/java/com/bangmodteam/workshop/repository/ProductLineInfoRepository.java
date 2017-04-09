package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.ProductLineInfo;

@Repository
public interface ProductLineInfoRepository extends CrudRepository<ProductLineInfo, Long> {

}
