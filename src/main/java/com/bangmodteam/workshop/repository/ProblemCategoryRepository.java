package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.ProblemCategory;

@Repository
public interface ProblemCategoryRepository extends CrudRepository<ProblemCategory, Long> {

}
