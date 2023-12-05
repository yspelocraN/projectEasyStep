package com.work.easystep2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.DTO.ArticleDTO;
import com.work.easystep2.model.Article;
import com.work.easystep2.service.ArticleService;

@RestController
@CrossOrigin
public class ArticleController {
	
	@Autowired
	private ArticleService aService;
	

	//新建文章
	@PostMapping("/article/post")
	public ResponseEntity<?> CreateArt(	@RequestParam String Title,
										@RequestParam String text,
										@RequestParam Integer user) {
		Article art =new Article();

		art.setTravelerId(user);
		art.setTitle(Title);
		art.setText(text);
		aService.insert(art);
		
		return new ResponseEntity<Article>(art,HttpStatus.OK);
	}
	
	//根據ID取得該旅者的所有文章
	@GetMapping("/article/allArticle/{travelerId}")
	public List<ArticleDTO> showAllArt(@PathVariable("travelerId") Integer travelerId) {
		List<Article> result = aService.showAllArticle(travelerId);
		if(result!=null) {
			List<ArticleDTO> art = new ArrayList<>();
			
			for(Article aa : result) {
				ArticleDTO aaa = new ArticleDTO(aa);
				art.add(aaa);
			}
			return art;	
		}
		return null;
	}
	
	//刪除文章
	@DeleteMapping("/article/deleteArt/{ArtId}")
	public ResponseEntity<?> deleteArt(@PathVariable Integer ArtId){
		Article result = aService.findById(ArtId);
		if(result != null) {
			aService.deleteById(ArtId);
	        return ResponseEntity.ok().build();
		}else {		
        	return ResponseEntity.notFound().build();
		}
	}
	
	//文章內容
	@GetMapping("/article/showArt")
	public ResponseEntity<?> showArtById(@RequestParam Integer ArtId){
		Article result = aService.findById(ArtId);
		if(result != null) {
			ArticleDTO artDTO = new ArticleDTO(result);
			return new ResponseEntity<ArticleDTO>(artDTO,HttpStatus.OK);
	    }
		return new ResponseEntity<String>("沒有此資料",HttpStatus.NOT_FOUND);
	}	
	
	//更新文章
	@PutMapping("/article/updateArt/{ArtId}")
	public ResponseEntity<?> updateArticle(@PathVariable Integer ArtId,
	                                       @RequestParam String title,
	                                       @RequestParam String text) {
	    Article result = aService.findById(ArtId);
	    if (result != null) {
	        // 更新文章信息
	        result.setTitle(title);
	        result.setText(text);
	        aService.insert(result);
	    
	        return ResponseEntity.ok("Article updated successfully");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	//最近的文章
	@GetMapping("/article/TopArticle/{travelerId}")
	public List<ArticleDTO> showTopArt(@PathVariable("travelerId") Integer travelerId) {
	    List<Article> result = aService.showAllArticle(travelerId);
	    if (result != null) {
	        List<ArticleDTO> art = new ArrayList<>();

	        int count = Math.min(result.size(), 6); // 

	        for (int i = 0; i < count; i++) {
	            Article aa = result.get(i);
	            ArticleDTO aaa = new ArticleDTO(aa);
	            art.add(aaa);
	        }
	        return art;
	    }
	    return null;
	}
	
}
