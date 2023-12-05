package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.ChatWithTraveler;

public interface ChatWithTravelerRepository extends JpaRepository<ChatWithTraveler, Integer> {
	
	// 找尋兩個人之間的對話
	@Query(value = "select * from chat_traveler where (traveler_id1 = :one and traveler_id2 = :other) or (traveler_id1 = :other and traveler_id2 = :one) order by msgdatetime asc",nativeQuery = true)
	List<ChatWithTraveler> findbetweenTalks(@Param(value = "one")Integer t1, @Param(value = "other")Integer t2);
	
	// 找曾說話的對象 當自己比較小時
	@Query(value = "select traveler_id1 from chat_traveler where traveler_id2=:one group by traveler_id1 order by MAX(msgdatetime) desc",nativeQuery = true)
	List<Integer> findothersmall(@Param(value = "one")Integer t1);
	
	// 找曾說話的對象 當自己比較大時
	@Query(value = "select traveler_id2 from chat_traveler where traveler_id1=:one group by traveler_id2 order by MAX(msgdatetime) desc",nativeQuery = true)
	List<Integer> findotherlarge(@Param(value = "one")Integer t1);
	
	// 設計 新增對話時 ID小的排1 ID大的排2

}
