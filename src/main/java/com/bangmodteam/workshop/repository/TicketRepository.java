package com.bangmodteam.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bangmodteam.workshop.entity.Ticket;

@Transactional
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
