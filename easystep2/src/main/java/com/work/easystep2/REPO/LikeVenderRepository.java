package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.work.easystep2.model.LikeVender;

public interface LikeVenderRepository extends JpaRepository<LikeVender, Integer>{

	//查詢多筆
	List<LikeVender> findLikeVenderBytravelerId(Integer travelerId);
		
	//查詢單筆
	@Query(value = "from LikeVender lv where lv.travelerId = ?1 and lv.venderId = ?2")
	LikeVender findBytravelerIdAndvenderId(Integer travelerId, Integer venderId);
		
	//刪除一筆
	@Transactional
	void deleteBytravelerIdAndVenderId(Integer travelerId, Integer venderId);
		
	//刪除多筆
	@Modifying
	@Transactional
	@Query(value = "delete from likeVender where travelerId=?1",nativeQuery = true)
	void deleteBytravelerId(Integer travelerId);
		
	/* 從使用者ID刪除 */
	void	deleteByTravelerId(Integer travelerId);
	void	deleteByVenderId(Integer venderId);
	
}
