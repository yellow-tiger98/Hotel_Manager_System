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

	// ���ڽ�����ʾ�����б�ҳ��
	@ReqeustMapping("/viewRoomList")
	protected String viewRoomList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/room_list.jsp";
	}

	// ��ȡ�����б�
	@ReqeustMapping("/jsonRoomList")
	private String josnRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		PageBean<Room> pBean = service.getRoomList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ���ڽ������ӷ���ҳ��
	@ReqeustMapping("/viewAddRoom")
	protected String viewAddRoom(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/add_room.jsp";
	}

	// ����ʵ����ӾƵ귿��
	@ReqeustMapping("/addRoom")
	protected String addRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.addRoom(room);
		return "data:" + result.toString();
	}

	// ����༭�ķ�����Ϣ
	@ReqeustMapping("/viewEditRoom")
	private String viewEditRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.saveEditRoom(room);
		return "data:" + json;
	}

	// ɾ������
	@ReqeustMapping("/delRoom")
	private String delRoom(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.delRoom(room);
		return "data:" + json;
	}
	
	//����ʵʱ��ȡ���������ṩ��ǰ��ҳ�������˵�
	@ReqeustMapping("/getSelectOp")
	protected String getSelectOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getSelectOp();
		return "data:" + result.toString();
	}

	// ���ڷ�װ����layui����ҳ��json���ݸ�ʽ
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("count", pBean.getCount());
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}
}
