package com.work.easystep2.service;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.work.easystep2.DTO.SearchResultDTO;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.DTO.TravelerSearchResultDTO;
import com.work.easystep2.model.Vender;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.REPO.VenderRepository;
import com.work.easystep2.DTO.VenderSearchResultDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService implements Serializable{
	 private static final long serialVersionUID = 1L;
    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private VenderRepository venderRepository;

    public List<SearchResultDTO<?>> searchByKeyword(String keyword) {
        List<Traveler> travelers = travelerRepository.searchByKeyword("%" + keyword + "%");
        List<Vender> venders = venderRepository.searchByKeyword("%" + keyword + "%");

        List<SearchResultDTO<?>> results = new ArrayList<>();
        results.addAll(mapToResults(venders, VenderSearchResultDTO::new));
        results.addAll(mapToResults(travelers, TravelerSearchResultDTO::new));

        printStatistics(travelers.size(), keyword);

        return results;
    }

    private <T, R extends SearchResultDTO<T>> List<R> mapToResults(List<T> items, Function<T, R> mapper) {
        return items.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public List<TravelerSearchResultDTO> searchTravelersByKeyword(String keyword) {
        List<Traveler> travelers = travelerRepository.searchByKeyword("%" + keyword + "%");
        return mapToTravelerSearchResults(travelers);
    }

    public List<VenderSearchResultDTO> searchVendersByKeyword(String keyword) {
        List<Vender> venders = venderRepository.searchByKeyword("%" + keyword + "%");
        return mapToVenderSearchResults(venders);
    }

    private List<VenderSearchResultDTO> mapToVenderSearchResults(List<Vender> venders) {
        return venders.stream()
                .map(vender -> new VenderSearchResultDTO(vender))
                .collect(Collectors.toList());
    }


    private List<TravelerSearchResultDTO> mapToTravelerSearchResults(List<Traveler> travelers) {
        return travelers.stream()
                .map(traveler -> new TravelerSearchResultDTO(traveler))
                .collect(Collectors.toList());
    }

    private void printStatistics(int travelerCount, String keyword) {
        System.out.println("Number of travelers found: " + travelerCount);
        System.out.println("Entering searchByKeyword with keyword: " + keyword);
    }
}