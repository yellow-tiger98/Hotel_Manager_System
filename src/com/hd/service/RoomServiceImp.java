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

	// ��ȡ�����б�
	public PageBean getRoomList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from room";
		count = roomDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = roomDao.getRoomList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��ȡ���������б�
	public PageBean getRoomTypeList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from roomtype";
		count = roomDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<RoomType> list = roomDao.getRoomTypeList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ���ӷ���
	public JSONObject addRoom(Room room) {
		JSONObject result = roomDao.addRoom(room);
		return result;
	}

	// ����༭�ķ�����Ϣ
	public JSONObject saveEditRoom(Room room) {
		JSONObject result = roomDao.saveEditRoom(room);
		return result;
	}

	// ɾ������
	public JSONObject delRoom(Room room) {
		JSONObject result = roomDao.delRoom(room);
		return result;
	}

	// ����༭�ķ���������Ϣ
	public JSONObject saveEditRtype(RoomType rt) {
		JSONObject result = roomDao.saveEditRoomType(rt);
		return result;
	}

	// ���ӷ�������
	public JSONObject addRoomType(RoomType rt) {
		JSONObject result = roomDao.addRoomType(rt);
		return result;
	}

	// ɾ����������
	public JSONObject delType(RoomType rt) {
		JSONObject result = roomDao.delType(rt);
		return result;
	}

	// ��ȡ�������͵�option������ӷ���ʱʹ��
	public JSONObject getSelectOp() {
		JSONObject result = roomDao.getSelectOp();
		return result;
	}

}
