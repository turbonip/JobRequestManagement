package com.bangmodteam.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.JobApp;

@Repository
public interface JobAppRepository extends JpaRepository<JobApp, Long> {

}
