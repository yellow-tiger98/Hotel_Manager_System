package com.hd.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.Cleaner;
import com.hd.model.Loader;
import com.hd.model.Order;
import com.hd.model.PageBean;
import com.hd.model.Repair;
import com.hd.model.Room;
import com.hd.model.User;
import com.hd.service.ReceptionServiceImp;

@WebServlet({ "/LodgingInfo/*", "/Reception/*" })
public class ReceptionController extends BaseController {
	private ReceptionServiceImp service = new ReceptionServiceImp();

	// ���ڷ���ѡ�����л�����������ʱ�洢��������
	static String typeId = null;

	// ����Ǽ���סҳ��
	@ReqeustMapping("/registerEntry")
	protected String registerEntry(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/registerEntry.jsp";
	}

	// ����δ����ҳ��
	@ReqeustMapping("/viewNoPayList")
	protected String viewNoPayList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/nopay_list.jsp";
	}

	// �����ѽ���ҳ��
	@ReqeustMapping("/viewHadPayList")
	protected String viewHadPayList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/hadpay_list.jsp";
	}

	// ���뷿̬����ҳ��
	@ReqeustMapping("/setRoomStatus")
	protected String setRoomStatus(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/room_status.jsp";
	}

	// ����ά����Ϣ�鿴ҳ��
	@ReqeustMapping("/viewRepairList")
	protected String viewRepairList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/repair_list.jsp";
	}

	// ����ά����Ϣ�鿴ҳ��
	@ReqeustMapping("/viewCleanList")
	protected String viewCleanList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/clean_list.jsp";
	}

	// ����ʵʱ��̬�鿴ҳ��
	@ReqeustMapping("/viewRoomRS")
	protected String viewRoomRS(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/room_rtstatus.jsp";
	}

	// ��ȡ�����б�
	@ReqeustMapping("/jsonRoomList")
	private String jsonRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Room> pBean = service.getRoomList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ��ȡδ���˱����б�
	@ReqeustMapping("/jsonNoPayList")
	private String jsonNoPayList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getNoPayList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ��ȡ�ѽ��˱����б�
	@ReqeustMapping("/jsonHadPayList")
	private String jsonHadPayList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getHadPayList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ���ڷ���ѡ�񵯴�ѡ��ָ����������ʱ���淿������id
	@ReqeustMapping("/refreshRoomType")
	protected String refreshRoomType(Room room, HttpServletRequest req, HttpServletResponse resp) {
		typeId = room.getTypeId();
		System.out.println("{ReceptionController}" + typeId);
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// ��ȡָ�����͵ķ����б�
	@ReqeustMapping("/jsonNewRoomList")
	private String jsonNewRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		PageBean<Room> pBean = service.getNewRoomList(page, typeId);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ��ȡ�������������˵�
	@ReqeustMapping("/getRoomOp")
	protected String getRoomOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRoomOp();
		return "data:" + json.toString();
	}

	// �Ǽ�����ס��Ϣ
	@ReqeustMapping("/addMainRegister")
	protected String addMainRegister(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		System.out.println("{ReceptionCrotroller}����Ա��" + user.getId());
		JSONObject json = service.addMainRegister(ld, user);
		return "data:" + json.toString();
	}

	// �Ǽ�ͬ�б�����Ϣ
	@ReqeustMapping("/addCpRegister")
	protected String addCpRegister(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		// [1]�õ����Ǽ��˵���ס��Ϣid
		String loadInfoId = ld.getLodgingInfoId();
		// [2]��Ӧ�ó�������ȡ�����б�����Ϣ��list����
		ServletContext context = req.getServletContext();
		List<Loader> list = (List) context.getAttribute("company");
		if (list == null) {
			JSONObject json1 = new JSONObject();
			json1.put("result", "null");
			return "data:" + json1.toString();
		} else {
			// [3]����service�㴦�������ݿ����ͨ�ţ��õ�ͨ�Ž��
			JSONObject json2 = service.addCpRegister(list, loadInfoId);
			return "data:" + json2.toString();
		}

	}

	// ��ȡ��ϸ��Ϣ
	@ReqeustMapping("/getRoomDetail")
	private String getRoomDetail(Order order, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRoomDeatil(order);
		return "data:" + json.toString();
	}

	// ��ס
	@ReqeustMapping("/continueRoom")
	private String continueRoom(Loader load, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.continueRoom(load);
		return "data:" + json.toString();
	}

	// ����
	@ReqeustMapping("/payForRoom")
	private String payForRoom(Order order, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		JSONObject json = service.payForRoom(order, user);
		return "data:" + json.toString();
	}

	// �ı䷿��״̬
	@ReqeustMapping("/changeRoomStatus")
	private String changeRoomStatus(Room room, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.changeRoomStatus(room);
		return "data:" + json.toString();
	}

	// ��ȡά����Ա�б�
	@ReqeustMapping("/getRepairOp")
	protected String getRepairOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getRepairOp();
		return "data:" + result.toString();
	}

	// ��ȡ�����Ա�б�
	@ReqeustMapping("/getCleanerOp")
	protected String getCleanerOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getCleanerOp();
		return "data:" + result.toString();
	}

	// ���ά����Ϣ
	@ReqeustMapping("/addRepairInfo")
	private String addRepairInfo(Repair repair, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.addReapirInfo(repair);
		return "data:" + json.toString();
	}

	// ��������Ϣ
	@ReqeustMapping("/addCleanInfo")
	private String addCleanInfo(Cleaner cleaner, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.addCleanInfo(cleaner);
		return "data:" + json.toString();
	}

	// ��ȡά����Ϣ�б�
	@ReqeustMapping("/jsonRepairList")
	private String jsonRepairList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Repair> pBean = service.getRepairList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ��ȡ�����Ϣ�б�
	@ReqeustMapping("/jsonCleanList")
	private String jsonCleanList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Repair> pBean = service.getCleanList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}
	
	// ��ȡ�����Ϣ�б�(��ѯ)
	@ReqeustMapping("/jsonCleanListBySearch")
	private String jsonCleanListBySearch(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		int roomid = Integer.parseInt(req.getParameter("roomid"));
		PageBean<Repair> pBean = service.getCleanListBySearch(page,roomid);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ��ȡʵʱ��̬��Ϣ
	@ReqeustMapping("/jsonRealRsList")
	private String jsonRealRsList(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRealRoomStatus(room);
		return "data:" + json.toString();
	}

	// ��ȡʵʱ��̬��¥���ҳ��Ϣ
	@ReqeustMapping("/jsonFloorList")
	private String jsonFloorList(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getFloorList();
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
