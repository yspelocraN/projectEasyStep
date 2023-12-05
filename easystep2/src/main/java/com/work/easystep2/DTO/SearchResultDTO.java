package com.work.easystep2.DTO;

public class SearchResultDTO<T> {

    @SuppressWarnings("unused")
	private T data;

    public SearchResultDTO(T data) {
        this.data = data;
    }
    public SearchResultDTO() {
        // 无参构造函数的内容
    }

}