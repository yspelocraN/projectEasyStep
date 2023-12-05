package com.work.easystep2.REPO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.Traveler;

public interface TravelerRepository extends JpaRepository<Traveler, Integer> {
	
	@Query(value="from Traveler where acct = ?1")//使用者帳戶查詢
	Traveler findTravelerByAcct(String acct);
	
	@Query(value="from Traveler where sub = ?1")//使用者帳戶查詢
	Traveler findTravelerBySub(String sub);
	
	@Query("SELECT t FROM Traveler t WHERE t.username LIKE %:keyword% OR t.acct LIKE %:keyword%")
	List<Traveler> searchByKeyword(@Param("keyword") String keyword);
	
	/* 以Traveler ID刪除 */
	void		deleteTravelerByTravelerid(Integer tId);
	Traveler	findTravelerByTravelerid(Integer tId);
	
}
