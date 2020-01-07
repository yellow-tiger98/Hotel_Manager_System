package com.hd.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hd.dao.RoomDaoImp;
import com.hd.model.PageBean;
import com.hd.model.Role;
import com.hd.model.Room;
import com.hd.model.RoomType;

public class RoomServiceImp {
	private RoomDaoImp roomDao = new RoomDaoImp();

	// 获取房间列表
	public PageBean getRoomList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from room";
		count = roomDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = roomDao.getRoomList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 获取房间类型列表
	public PageBean getRoomTypeList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from roomtype";
		count = roomDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<RoomType> list = roomDao.getRoomTypeList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 增加房间
	public JSONObject addRoom(Room room) {
		JSONObject result = roomDao.addRoom(room);
		return result;
	}

	// 保存编辑的房间信息
	public JSONObject saveEditRoom(Room room) {
		JSONObject result = roomDao.saveEditRoom(room);
		return result;
	}

	// 删除房间
	public JSONObject delRoom(Room room) {
		JSONObject result = roomDao.delRoom(room);
		return result;
	}

	// 保存编辑的房间类型信息
	public JSONObject saveEditRtype(RoomType rt) {
		JSONObject result = roomDao.saveEditRoomType(rt);
		return result;
	}

	// 增加房间类型
	public JSONObject addRoomType(RoomType rt) {
		JSONObject result = roomDao.addRoomType(rt);
		return result;
	}

	// 删除房间类型
	public JSONObject delType(RoomType rt) {
		JSONObject result = roomDao.delType(rt);
		return result;
	}

	// 获取房间类型的option用于添加房间时使用
	public JSONObject getSelectOp() {
		JSONObject result = roomDao.getSelectOp();
		return result;
	}

}
