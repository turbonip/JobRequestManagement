package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.ProblemSubCategory;

@Repository
public interface ProblemSubCategoryRepository extends CrudRepository<ProblemSubCategory, Long> {

}
