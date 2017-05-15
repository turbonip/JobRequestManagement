package com.bangmodteam.workshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bangmodteam.workshop.constant.TicketStatus;
import com.bangmodteam.workshop.entity.Ticket;

@Transactional
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	public Optional<Integer> getMaxTicketSequenceByJobAppId(Long jobId);
	
	@Query(value = "select t from Ticket t " 
			 + " where t.id = case when :ticketId > 0L then :ticketId else t.id end "
			 + " and t.jobApp.id = case when :jobId > 0L then :jobId else t.jobApp.id end "
			 + " and t.jobApp.locationInfo.id = case when :locationId > 0L then :locationId else t.jobApp.locationInfo.id end "
			 + " and t.jobApp.problemCategory.id = case when :problemCatId > 0L then :problemCatId else t.jobApp.problemCategory.id end "
			 + " and t.jobApp.deviceInfo.id = case when :deviceId > 0L then :deviceId else t.jobApp.deviceInfo.id end "
			 + " and 1 = case when :assignDateBegin is null or :assignDateBegin = '' or :assignDateEnd is null or :assignDateEnd = '' then 1 else "
							+ "case when DATE_FORMAT(t.assignAt, '%Y%m%d') between :assignDateBegin and :assignDateEnd then 1 else 0 end "
						+ "end "
			 + " order by t.assignAt desc, t.id "
		, nativeQuery = false)
	public List<Ticket> findByCriteria(@Param("ticketId") Long ticketId, @Param("jobId") Long jobId
									  , @Param("problemCatId") Long problemCatId, @Param("locationId") Long locationId, @Param("deviceId") Long deviceId
									  , @Param("assignDateBegin") String assignDateBegin, @Param("assignDateEnd") String assignDateEnd
									  );
	
	@Query(value = "select t from Ticket t " 
			 + " where t.id = case when :ticketId > 0L then :ticketId else t.id end "
			 + " and t.jobApp.id = case when :jobId > 0L then :jobId else t.jobApp.id end "
			 + " and t.jobApp.locationInfo.id = case when :locationId > 0L then :locationId else t.jobApp.locationInfo.id end "
			 + " and t.jobApp.problemCategory.id = case when :problemCatId > 0L then :problemCatId else t.jobApp.problemCategory.id end "
			 + " and t.jobApp.deviceInfo.id = case when :deviceId > 0L then :deviceId else t.jobApp.deviceInfo.id end "
			 + " and 1 = case when :assignDateBegin is null or :assignDateBegin = '' or :assignDateEnd is null or :assignDateEnd = '' then 1 else "
							+ "case when DATE_FORMAT(t.assignAt, '%Y%m%d') between :assignDateBegin and :assignDateEnd then 1 else 0 end "
						+ "end "
			 + " and t.ticketStatus = :ticketStatus "				
			 + " order by t.assignAt desc, t.id "
		, nativeQuery = false)
	public List<Ticket> findByCriteriaWithStatus(@Param("ticketId") Long ticketId, @Param("jobId") Long jobId
									  , @Param("problemCatId") Long problemCatId, @Param("locationId") Long locationId, @Param("deviceId") Long deviceId
									  , @Param("assignDateBegin") String assignDateBegin, @Param("assignDateEnd") String assignDateEnd
									  , @Param("ticketStatus") TicketStatus ticketStatus
									  );

}
