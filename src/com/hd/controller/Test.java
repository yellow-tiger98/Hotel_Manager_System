package com.hd.controller;

import com.alibaba.fastjson.JSONObject;

public class Test {
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		json.put("gotoPage","1231231");
		System.out.println(json);
		String uri = "/uri/sss";
		int first = uri.indexOf("/");
		System.out.println(first);
		int last = uri.lastIndexOf("/");
		System.out.println(last);
		String role = uri.substring(first+1,last);
		System.out.println(role);
	}
}
