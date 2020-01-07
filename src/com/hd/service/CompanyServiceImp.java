package com.hd.service;

import java.util.List;

import com.hd.dao.CompanyDaoImp;
import com.hd.model.Loader;
import com.hd.model.PageBean;
import com.hd.model.Room;

public class CompanyServiceImp {
	private CompanyDaoImp cpDao = new CompanyDaoImp();

	// 获取同行列表
	public PageBean getCpList(PageBean page, String lodgingInfo) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Loader> list = cpDao.getCpList(lodgingInfo, _page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
}
