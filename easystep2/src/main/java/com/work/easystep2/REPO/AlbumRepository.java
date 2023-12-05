package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.PhotoAlbum;

public interface AlbumRepository extends JpaRepository<PhotoAlbum, Integer> {

	List<PhotoAlbum> findByTravelerId(Integer userId);
	
	/* 以Traveler ID進行搜尋 */
	List<PhotoAlbum>	findAlbumIdByTravelerId(Integer tId);
	/* 從Traveler ID進行刪除 */
	void	deleteByTravelerId(Integer tId);

}
