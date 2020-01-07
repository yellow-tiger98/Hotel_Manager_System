package com.hd.controller;

import java.lang.annotation.*;

	//自定义注解
	@Target(ElementType.METHOD)  //元注解 @Target 规定注解的使用位置
	@Retention(RetentionPolicy.RUNTIME) //元注解 @Retention 规定注解的存活时间
	public @interface ReqeustMapping {
		public String value() default "";
	}