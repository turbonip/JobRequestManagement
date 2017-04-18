package com.bangmodteam.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

}
