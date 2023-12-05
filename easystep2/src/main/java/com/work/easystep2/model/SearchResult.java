package com.work.easystep2.model;

public class SearchResult<T> {
    @SuppressWarnings("unused")
	private T data;

    public SearchResult(T data) {
        this.data = data;
    }
    public SearchResult() {
        // 无参构造函数的内容
    }

}