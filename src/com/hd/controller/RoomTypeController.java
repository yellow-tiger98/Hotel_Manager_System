package com.hd.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.PageBean;
import com.hd.model.Room;
import com.hd.model.RoomType;
import com.hd.model.User;
import com.hd.service.RoomServiceImp;

@WebServlet("/RoomType/*")
public class RoomTypeController extends BaseController {
	private RoomServiceImp service = new RoomServiceImp();

	@ReqeustMapping("/viewTypeList")
	protected String viewTypeList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/roomtype_list.jsp";
	}

	// 用于进入增加房间页面
	@ReqeustMapping("/viewAddType")
	protected String viewAddRoom(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/add_roomtype.jsp";
	}

	// 获取房间类型列表
	@ReqeustMapping("/jsonTypeList")
	private String josnTypeList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		PageBean<RoomType> pBean = service.getRoomTypeList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 保存编辑的房间类型
	@ReqeustMapping("/viewEditType")
	private String viewEditType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.saveEditRtype(rt);
		return "data:" + json;
	}

	// 实现添加房间类型
	@ReqeustMapping("/addRoomType")
	protected String addRoomType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.addRoomType(rt);
		return "data:" + result.toString();
	}

	// 删除房间类型
	@ReqeustMapping("/delType")
	private String delType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.delType(rt);
		return "data:" + json.toString();
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
