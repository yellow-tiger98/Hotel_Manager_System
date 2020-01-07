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

	// 用于房间选择中切换房间类型临时存储房间类型
	static String typeId = null;

	// 进入登记入住页面
	@ReqeustMapping("/registerEntry")
	protected String registerEntry(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/registerEntry.jsp";
	}

	// 进入未结账页面
	@ReqeustMapping("/viewNoPayList")
	protected String viewNoPayList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/nopay_list.jsp";
	}

	// 进入已结账页面
	@ReqeustMapping("/viewHadPayList")
	protected String viewHadPayList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/hadpay_list.jsp";
	}

	// 进入房态设置页面
	@ReqeustMapping("/setRoomStatus")
	protected String setRoomStatus(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/room_status.jsp";
	}

	// 进入维修信息查看页面
	@ReqeustMapping("/viewRepairList")
	protected String viewRepairList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/repair_list.jsp";
	}

	// 进入维修信息查看页面
	@ReqeustMapping("/viewCleanList")
	protected String viewCleanList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/clean_list.jsp";
	}

	// 进入实时房态查看页面
	@ReqeustMapping("/viewRoomRS")
	protected String viewRoomRS(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/reception/room_rtstatus.jsp";
	}

	// 获取房间列表
	@ReqeustMapping("/jsonRoomList")
	private String jsonRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Room> pBean = service.getRoomList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 获取未结账宾客列表
	@ReqeustMapping("/jsonNoPayList")
	private String jsonNoPayList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getNoPayList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 获取已结账宾客列表
	@ReqeustMapping("/jsonHadPayList")
	private String jsonHadPayList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getHadPayList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 用于房间选择弹窗选择指定房间类型时保存房间类型id
	@ReqeustMapping("/refreshRoomType")
	protected String refreshRoomType(Room room, HttpServletRequest req, HttpServletResponse resp) {
		typeId = room.getTypeId();
		System.out.println("{ReceptionController}" + typeId);
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// 获取指定类型的房间列表
	@ReqeustMapping("/jsonNewRoomList")
	private String jsonNewRoomList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		PageBean<Room> pBean = service.getNewRoomList(page, typeId);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 获取房间类型下拉菜单
	@ReqeustMapping("/getRoomOp")
	protected String getRoomOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRoomOp();
		return "data:" + json.toString();
	}

	// 登记主入住信息
	@ReqeustMapping("/addMainRegister")
	protected String addMainRegister(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		System.out.println("{ReceptionCrotroller}操作员：" + user.getId());
		JSONObject json = service.addMainRegister(ld, user);
		return "data:" + json.toString();
	}

	// 登记同行宾客信息
	@ReqeustMapping("/addCpRegister")
	protected String addCpRegister(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		// [1]拿到主登记人的入住信息id
		String loadInfoId = ld.getLodgingInfoId();
		// [2]从应用程序域中取出存有宾客信息的list集合
		ServletContext context = req.getServletContext();
		List<Loader> list = (List) context.getAttribute("company");
		if (list == null) {
			JSONObject json1 = new JSONObject();
			json1.put("result", "null");
			return "data:" + json1.toString();
		} else {
			// [3]交给service层处理与数据库进行通信，得到通信结果
			JSONObject json2 = service.addCpRegister(list, loadInfoId);
			return "data:" + json2.toString();
		}

	}

	// 获取详细信息
	@ReqeustMapping("/getRoomDetail")
	private String getRoomDetail(Order order, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRoomDeatil(order);
		return "data:" + json.toString();
	}

	// 续住
	@ReqeustMapping("/continueRoom")
	private String continueRoom(Loader load, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.continueRoom(load);
		return "data:" + json.toString();
	}

	// 结账
	@ReqeustMapping("/payForRoom")
	private String payForRoom(Order order, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		JSONObject json = service.payForRoom(order, user);
		return "data:" + json.toString();
	}

	// 改变房间状态
	@ReqeustMapping("/changeRoomStatus")
	private String changeRoomStatus(Room room, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.changeRoomStatus(room);
		return "data:" + json.toString();
	}

	// 获取维修人员列表
	@ReqeustMapping("/getRepairOp")
	protected String getRepairOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getRepairOp();
		return "data:" + result.toString();
	}

	// 获取清洁人员列表
	@ReqeustMapping("/getCleanerOp")
	protected String getCleanerOp(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = service.getCleanerOp();
		return "data:" + result.toString();
	}

	// 添加维修信息
	@ReqeustMapping("/addRepairInfo")
	private String addRepairInfo(Repair repair, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.addReapirInfo(repair);
		return "data:" + json.toString();
	}

	// 添加清洁信息
	@ReqeustMapping("/addCleanInfo")
	private String addCleanInfo(Cleaner cleaner, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		JSONObject json = service.addCleanInfo(cleaner);
		return "data:" + json.toString();
	}

	// 获取维修信息列表
	@ReqeustMapping("/jsonRepairList")
	private String jsonRepairList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Repair> pBean = service.getRepairList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 获取清洁信息列表
	@ReqeustMapping("/jsonCleanList")
	private String jsonCleanList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Repair> pBean = service.getCleanList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}
	
	// 获取清洁信息列表(查询)
	@ReqeustMapping("/jsonCleanListBySearch")
	private String jsonCleanListBySearch(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		int roomid = Integer.parseInt(req.getParameter("roomid"));
		PageBean<Repair> pBean = service.getCleanListBySearch(page,roomid);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 获取实时房态信息
	@ReqeustMapping("/jsonRealRsList")
	private String jsonRealRsList(Room room, HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getRealRoomStatus(room);
		return "data:" + json.toString();
	}

	// 获取实时房态的楼层分页信息
	@ReqeustMapping("/jsonFloorList")
	private String jsonFloorList(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject json = service.getFloorList();
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
