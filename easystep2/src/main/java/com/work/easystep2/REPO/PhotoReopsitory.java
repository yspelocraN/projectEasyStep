package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.Photo;

public interface PhotoReopsitory extends JpaRepository<Photo, Integer> {
	
	@Query("delete from Photo p where p.albumId.AlbumId = :AlbumId")
	void	deleteByAlbumId(@Param("AlbumId") Integer AlbumId);

}
