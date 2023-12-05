package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	public Article findFirstByOrderByCreateTimeDesc();

	List<Article> findByTravelerId(Integer travelerId);
	
	/* 以Traveler ID進行刪除 */
	void deleteByTravelerId(Integer tId);
	
}
