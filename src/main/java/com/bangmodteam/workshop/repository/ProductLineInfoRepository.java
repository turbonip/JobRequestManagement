package com.bangmodteam.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bangmodteam.workshop.entity.ProductLineInfo;

@Repository
public interface ProductLineInfoRepository extends JpaRepository<ProductLineInfo, Long> {

	@Transactional(readOnly = true)
	public List<ProductLineInfo> findByLocationInfoId(Long locationId);

}
