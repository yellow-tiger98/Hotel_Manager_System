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

	// ���ڽ������ӷ���ҳ��
	@ReqeustMapping("/viewAddType")
	protected String viewAddRoom(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/room/add_roomtype.jsp";
	}

	// ��ȡ���������б�
	@ReqeustMapping("/jsonTypeList")
	private String josnTypeList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		PageBean<RoomType> pBean = service.getRoomTypeList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ����༭�ķ�������
	@ReqeustMapping("/viewEditType")
	private String viewEditType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.saveEditRtype(rt);
		return "data:" + json;
	}

	// ʵ����ӷ�������
	@ReqeustMapping("/addRoomType")
	protected String addRoomType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.addRoomType(rt);
		return "data:" + result.toString();
	}

	// ɾ����������
	@ReqeustMapping("/delType")
	private String delType(RoomType rt, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.delType(rt);
		return "data:" + json.toString();
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
