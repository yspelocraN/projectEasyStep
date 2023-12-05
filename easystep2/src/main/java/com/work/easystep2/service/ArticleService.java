package com.work.easystep2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.work.easystep2.REPO.ArticleRepository;
import com.work.easystep2.model.Article;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository ArtRepo;
	
	public void insert(Article Art) {
		ArtRepo.save(Art);
	}
	
	public Article findById(Integer id){
		Optional<Article> optional = ArtRepo.findById(id);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deleteById(Integer id) {
		ArtRepo.deleteById(id);
	}
	
	public Article findLastest() {
		return ArtRepo.findFirstByOrderByCreateTimeDesc();
	}
	
	public List<Article> showAllArticle(Integer travelerId){
		return ArtRepo.findByTravelerId(travelerId);
	}
	
}
