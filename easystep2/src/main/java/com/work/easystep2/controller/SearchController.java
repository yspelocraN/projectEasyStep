package com.work.easystep2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.service.SearchService;
import com.work.easystep2.DTO.SearchResultDTO;
import com.work.easystep2.DTO.TravelerSearchResultDTO;
import com.work.easystep2.DTO.VenderSearchResultDTO;

import java.util.List;

@RestController
@CrossOrigin
public class SearchController {
    
    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDTO<?>>> searchByKeyword(@RequestParam("q") String keyword) {
        List<SearchResultDTO<?>> searchResults = searchService.searchByKeyword(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
    
    @GetMapping("/search/traveler")
    public ResponseEntity<List<TravelerSearchResultDTO>> searchTravelerByKeyword(@RequestParam("q") String keyword) {
        List<TravelerSearchResultDTO> trs = searchService.searchTravelersByKeyword(keyword);
        return new ResponseEntity<>(trs, HttpStatus.OK);
    }

    @GetMapping("/search/vender")
    public ResponseEntity<List<VenderSearchResultDTO>> searchVenderByKeyword(@RequestParam("q") String keyword) {
        List<VenderSearchResultDTO> vrs = searchService.searchVendersByKeyword(keyword);
        return new ResponseEntity<>(vrs, HttpStatus.OK);
    }

}
