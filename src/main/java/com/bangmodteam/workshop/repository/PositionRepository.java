package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.Position;

@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {

}
