package com.hd.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.PageBean;
import com.hd.model.Room;
import com.hd.model.RoomType;
import com.hd.service.RoomServiceImp;

@WebServlet("/Room/*")
public class RoomController extends BaseController {
	private RoomServiceImp service = new RoomServiceImp();

	// 用于进入显示房间列表页面
	@ReqeustMapping("/viewRoomList")
	protected String viewRoomList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/room_list.jsp";
	}

	// 获取房间列表
	@ReqeustMapping("/jsonRoomList")
	private String josnRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		PageBean<Room> pBean = service.getRoomList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 用于进入增加房间页面
	@ReqeustMapping("/viewAddRoom")
	protected String viewAddRoom(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/add_room.jsp";
	}

	// 用于实现添加酒店房间
	@ReqeustMapping("/addRoom")
	protected String addRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.addRoom(room);
		return "data:" + result.toString();
	}

	// 保存编辑的房间信息
	@ReqeustMapping("/viewEditRoom")
	private String viewEditRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.saveEditRoom(room);
		return "data:" + json;
	}

	// 删除房间
	@ReqeustMapping("/delRoom")
	private String delRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.delRoom(room);
		return "data:" + json;
	}
	
	//用于实时获取房间类型提供给前端页面下拉菜单
	@ReqeustMapping("/getSelectOp")
	protected String getSelectOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getSelectOp();
		return "data:" + result.toString();
	}

	// 用于封装符合layui表格分页的json数据格式
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("count", pBean.getCount());
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}
}
