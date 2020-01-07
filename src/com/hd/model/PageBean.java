package com.hd.model;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class PageBean<T>{
	private int count = 0; //{ps}总记录数
	private int page = 0;  //{ps}当前页码
	private int limit  = 1;
	private List<T> list = null;
	private JSONArray jsArr = null;
	
	public void setList(List<T> list) {
		JSONArray arr = new JSONArray();
		for(T t:list) {
			arr.add(t);
		}
		this.list = list;
		this.jsArr = arr;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public JSONArray getJsArr() {
		return jsArr;
	}

	public List<T> getList() {
		return list;
	}
	
}
