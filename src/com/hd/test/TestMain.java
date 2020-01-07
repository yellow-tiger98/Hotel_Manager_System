package com.hd.test;

import java.util.HashMap;
import java.util.Map;

import com.hd.model.User;
import com.hd.utils.BeanUtils;

public class TestMain {

	public static void main(String[] args) throws IllegalAccessException {
		Map<String,String[]> data = new HashMap<String,String[]>();
		data.put("username", new String[]{"andy"} );
		data.put("password", new String[]{"123"} );
		data.put("weight", new String[]{"63.33"} );
		data.put("length", new String[]{"172.55"} );
		data.put("age", new String[]{"53"} );
		data.put("id", new String[]{"007"} );

		User user = BeanUtils.parseBean( data, User.class );
		System.out.println( user );
	}
	
	
}
