package com.bangmodteam.workshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
