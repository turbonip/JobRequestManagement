package com.bangmodteam.workshop.repository;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.Customer;

@Transactional
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	public Customer findByFirstName(String firstName);
}
