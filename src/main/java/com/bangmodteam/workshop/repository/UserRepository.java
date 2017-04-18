package com.bangmodteam.workshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bangmodteam.workshop.entity.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.username = :name")
	User findByUsername(@Param("name") String username);

	@Query("SELECT DISTINCT u FROM User u INNER JOIN u.groupInfo g INNER JOIN g.locations l WHERE g.leader.id = :leaderId AND l.id = :locationId AND u.position.id = :positionId")
	List<User> findByUnderLeaderAndLocationsAndPosition(@Param("leaderId") Long leaderId, @Param("locationId") Long locationId, @Param("positionId") Long positionId);

	@Query("SELECT DISTINCT u FROM User u INNER JOIN u.groupInfo g INNER JOIN g.locations l WHERE u.id <> :leaderId AND g.leader.id = :leaderId AND l.id = :locationId AND u.position.id <> :positionId")
	List<User> findByUnderLeaderAndLocationsAndNotPosition(@Param("leaderId") Long leaderId, @Param("locationId") Long locationId, @Param("positionId") Long positionId);
	
//	@Query("SELECT DISTINCT u FROM User u INNER JOIN u.groupInfo g INNER JOIN g.locations l INNER JOIN g.leader ld INNER JOIN u.position p INNER JOIN p.problemCategories pm WHERE l.id = ?1 AND ld.id = ?2 AND pm.id <> ?3")
//	List<User> findByLocationIdAndUnderLeaderIdAndIsNotProblemCatId(Long locationId, Long leaderId, Long problemCateId);
//
//
//	List<User> findDistinctUsernameByGroupInfoLocationsIdAndGroupInfoLeaderIdAndPositionIdIsNot(Long locationId, Long leaderId, Long positionId);

}
