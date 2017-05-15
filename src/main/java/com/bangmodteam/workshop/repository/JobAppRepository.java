package com.bangmodteam.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.entity.JobApp;

@Repository
public interface JobAppRepository extends JpaRepository<JobApp, Long> {

	@Query(value = "select j from JobApp j " 
				 + " where j.id = case when :jobId > 0L then :jobId else j.id end "
				 + " and j.problemCategory.id = case when :problemCatId > 0L then :problemCatId else j.problemCategory.id end "
				 + " and j.locationInfo.id = case when :locationId > 0L then :locationId else j.locationInfo.id end "
				 + " and 1 = case when :jobDateBegin is null or :jobDateBegin = '' or :jobDateEnd is null or :jobDateEnd = '' then 1 else "
	 							+ "case when DATE_FORMAT(j.openAt, '%Y%m%d') between :jobDateBegin and :jobDateEnd then 1 else 0 end "
	 						+ "end "
	 			 + " order by j.openAt desc, j.id "
			, nativeQuery = false)
	public List<JobApp> findBysCriteria(@Param("jobId") Long jobId, @Param("problemCatId") Long problemCatId, @Param("locationId") Long locationId
									  , @Param("jobDateBegin") String jobDateBegin, @Param("jobDateEnd") String jobDateEnd
									  );
	
	@Query(value = "select j from JobApp j " 
			 + " where j.id = case when :jobId > 0L then :jobId else j.id end "
			 + " and j.problemCategory.id = case when :problemCatId > 0L then :problemCatId else j.problemCategory.id end "
			 + " and j.locationInfo.id = case when :locationId > 0L then :locationId else j.locationInfo.id end "
			 + " and j.jobStatus = :jobStatus "
//			 + " and 1 = case when :jobDateBegin is null or :jobDateBegin <> '' or :jobDateEnd is null or :jobDateEnd <> '' then 1 else "
//							+ "case when DATE_FORMAT(j.openAt, '%Y%m%d') between :jobDateBegin and :jobDateEnd then 1 else 0 end "
//						+ "end "
			 + " order by j.openAt desc, j.id "
		, nativeQuery = false)
	public List<JobApp> findBysCriteriaWithStatus(@Param("jobId") Long jobId, @Param("problemCatId") Long problemCatId, @Param("locationId") Long locationId
												//, @Param("jobDateBegin") String jobDateBegin, @Param("jobDateEnd") String jobDateEnd
												, @Param("jobStatus") JobStatus jobStatus);
	
}
