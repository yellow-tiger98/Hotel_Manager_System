package com.hd.controller;

import java.lang.annotation.*;

	//�Զ���ע��
	@Target(ElementType.METHOD)  //Ԫע�� @Target �涨ע���ʹ��λ��
	@Retention(RetentionPolicy.RUNTIME) //Ԫע�� @Retention �涨ע��Ĵ��ʱ��
	public @interface ReqeustMapping {
		public String value() default "";
	}