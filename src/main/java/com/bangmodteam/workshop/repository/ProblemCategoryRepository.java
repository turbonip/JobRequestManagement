package com.bangmodteam.workshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bangmodteam.workshop.entity.ProblemCategory;

@Repository
public interface ProblemCategoryRepository extends CrudRepository<ProblemCategory, Long> {

	@Transactional(readOnly = true)
	public List<ProblemCategory> findByPositionResponseId(Long positionId);

}
