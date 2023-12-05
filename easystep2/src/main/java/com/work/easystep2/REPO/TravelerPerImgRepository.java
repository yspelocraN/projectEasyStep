package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerPerImg;

public interface TravelerPerImgRepository extends JpaRepository<TravelerPerImg, Integer>{

	TravelerPerImg findTravelerPerImgByphotoToken(String photoToken);
	
	TravelerPerImg findTravelerPerImgByphotoid(Integer photoid);
	
	TravelerPerImg findByTraveler(Traveler traveler);
	
	 @Query("SELECT tp FROM TravelerPerImg tp WHERE tp.traveler.travelerid = :travelerid")
	 TravelerPerImg findByTravelerId(@Param("travelerid") Integer travelerId);
	 
	 /* 以Traveler ID進行刪除 */
	 void deleteByTravelerTravelerid(Integer travelerid);
	 
}
