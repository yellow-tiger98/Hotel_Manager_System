package com.hd.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.catalina.startup.HomesUserDatabase;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hd.dao.ReceptionDaoImp;
import com.hd.model.Cleaner;
import com.hd.model.Loader;
import com.hd.model.Order;
import com.hd.model.PageBean;
import com.hd.model.Repair;
import com.hd.model.Room;
import com.hd.model.User;

public class ReceptionServiceImp {
	private ReceptionDaoImp receDao = new ReceptionDaoImp();

	// 登记主登记人入住信息
	public JSONObject addMainRegister(Loader ld, User user) {
		String loadingInfoId = getOnlyLoadId();
		ld.setLodgingInfoId(loadingInfoId);
		System.out.println("{ReceptionService}" + ld);
		JSONObject result = receDao.addMainRegister(ld, user);
		return result;
	}

	// 登记同行宾客入住信息
	public JSONObject addCpRegister(List<Loader> list, String infoId) {
		JSONArray jarr = new JSONArray();
		int count = 0;
		int i = 0;
		for (Loader loader : list) {
			loader.setLodgingInfoId(infoId);
			jarr.add(receDao.addCpRegister(loader));
			i++;
		}
		for (Object object : jarr) {
			JSONObject json = (JSONObject) object;
			if (json.get("result").equals("ok")) {
				count++;
			}
		}
		JSONObject json = new JSONObject();
		System.out.println("{ReceptionService}count=" + count);
		if (count == i) {
			json.put("result", "ok");
			return json;
		} else {
			json.put("result", "no");
			json.put("message", "同行宾客信息添加失败！");
			return json;
		}
	}

	// 获取房间列表
	public PageBean getRoomList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from room where status='空闲' or status is null";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = receDao.getRoomList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 获取指定房间列表
	public PageBean getNewRoomList(PageBean page, String typeId) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from room where status='空闲' or status is null and typeId=" + typeId + "";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = receDao.getNewRoomList(_page, page.getLimit(), typeId);
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 获取房间类型的option用于添加房间时使用
	public JSONObject getRoomOp() {
		JSONObject result = receDao.getRoomOp();
		return result;
	}

	// 获取房间价格相关信息，用于结账
	public JSONObject getRoomDeatil(Order order) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date _dateNow = new Date();
		String dateNow = sdf.format(_dateNow);
		Order newOrder = receDao.getRoomDeatil(order);
		JSONObject json = new JSONObject();
		// [1]查询结果
		json.put("result", "ok");
		// [2]房间类型
		json.put("roomType", newOrder.getRoomType());
		// [3]房间价格
		String _roomPrice;
		// 正常价格
		String dayPrice = newOrder.getDayPrice();
		// 钟点价格
		String hourPrice = newOrder.getHourPrice();
		// 若没有钟点价格，则显示无
		if (hourPrice == null) {
			hourPrice = "无";
		}
		// 预定价格
		String rePrice = newOrder.getRePrice();
		// 拼装房间的价格类型
		_roomPrice = dayPrice + " / " + hourPrice + " / " + rePrice + "(￥)";
		json.put("roomPrice", _roomPrice);
		// [4]入住类型：正常，钟点
		String _inStyle;
		// [5]总价格
		int totalPrice = 0;
		String copResult;
		int otLast;
		int otHour;
		int otDay;
		String details = null;
		if (order.getDays().contains("h")) {
			_inStyle = "钟点房";
			int last = order.getDays().lastIndexOf("h");
			int hour = Integer.parseInt(order.getDays().substring(0, last));
			copResult = compareLeaveTime(order.getLeaveDate(), dateNow);
			if (copResult == null) {
				details = "正常房费";
				totalPrice = hour * Integer.parseInt(hourPrice);
			} else if (copResult.contains("h")) {
				otLast = copResult.lastIndexOf("h");
				otHour = Integer.parseInt(copResult.substring(0, last));
				details = "正常房费+超时费用(" + otHour + "小时)";
				totalPrice = (hour + otHour) * Integer.parseInt(hourPrice);
			} else if (copResult.contains("d")) {
				otLast = copResult.lastIndexOf("d");
				otDay = Integer.parseInt(copResult.substring(0, last));
				details = "正常房费+超时费用(" + otDay + "天)";
				totalPrice = hour * Integer.parseInt(hourPrice) + otDay * Integer.parseInt(dayPrice);
			}
		} else {
			_inStyle = "正常";
			int last = order.getDays().lastIndexOf("d");
			int days = Integer.parseInt(order.getDays().substring(0, last)) - 1;
			copResult = compareLeaveTime(order.getLeaveDate(), dateNow);
			if (copResult == null) {
				details = "正常房费";
				totalPrice = days * Integer.parseInt(dayPrice);
			} else if (copResult.contains("h")) {
				otLast = copResult.lastIndexOf("h");
				otHour = Integer.parseInt(copResult.substring(0, last));
				details = "正常房费+超时费用(" + otHour + "小时)";
				totalPrice = days * Integer.parseInt(dayPrice) + otHour * Integer.parseInt(hourPrice);
				;
			} else if (copResult.contains("d")) {
				otLast = copResult.lastIndexOf("d");
				otDay = Integer.parseInt(copResult.substring(0, last));
				details = "正常房费+超时费用(" + otDay + "天)";
				totalPrice = (days + otDay) * Integer.parseInt(dayPrice);
			}
		}
		json.put("inStyle", _inStyle);
		json.put("totalPrice", totalPrice);
		json.put("detail", details);
		json.put("relLeaveTime", dateNow);
		return json;
	}

	// 获取未结账列表
	public PageBean getNoPayList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from view_nopay_list";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Loader> list = receDao.getNoPayList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 获取已结账列表
	public PageBean getHadPayList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from view_nopay_list";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Order> list = receDao.getHadPayList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 房间续费
	public JSONObject continueRoom(Loader load) {
		JSONObject result = receDao.continueRoom(load);
		return result;
	}

	// 结账
	public JSONObject payForRoom(Order order, User user) {
		JSONObject result = receDao.payForRoom(order, user);
		return result;
	}

	// 结账
	public JSONObject changeRoomStatus(Room room) {
		JSONObject result = receDao.changeRoomStatus(room);
		return result;
	}

	// 获取维修人员列表
	public JSONObject getRepairOp() {
		JSONObject result = receDao.getRepairOp();
		return result;
	}

	// 获取清洁人员列表
	public JSONObject getCleanerOp() {
		JSONObject result = receDao.getCleanOp();
		return result;
	}

	// 添加维修信息
	public JSONObject addReapirInfo(Repair repair) {
		JSONObject result = receDao.addRepairInfo(repair);
		return result;
	}

	// 添加维修信息
	public JSONObject addCleanInfo(Cleaner cleaner) {
		JSONObject result = receDao.addCleanInfo(cleaner);
		return result;
	}

	// 获取维修信息列表
	public PageBean getRepairList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from maintain";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Repair> list = receDao.getRepairList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	
	// 获取清洁信息列表
	public PageBean getCleanList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from clean";
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Cleaner> list = receDao.getCleanList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	// 获取清洁信息列表(查询)
	public PageBean getCleanListBySearch(PageBean page,int roomid) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}处理页码非法问题
		int count = 0;
		// {2}拿到总记录数
		String sql = "select count(*) from clean where roomid="+roomid;
		count = receDao.getCount(sql);
		// {3}拿到页大小
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Cleaner> list = receDao.getCleanListBySearch(roomid,_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// 获取实时房态
	public JSONObject getRealRoomStatus(Room room) {
		JSONObject json = receDao.getRealRoomStatus(room);
		return json;
	}

	// 获取楼层列表
	public JSONObject getFloorList() {
		List<Integer> list = receDao.getFloorList();
		JSONObject json = new JSONObject();
		json.put("floor_list", list);
		return json;
	}

	// 用于生成唯一的入住信息编号 年月日+时分秒
	public String getOnlyLoadId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _uuid = sdf.format(date);
		String[] arr = _uuid.split(" ");
		String first = arr[0].replaceAll("-", "");
		String second = arr[1].replaceAll(":", "");
		String uuid = first + second;
		return uuid;
	}

	// 获取当前时间
	public String getDateNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateNow = sdf.format(date);
		return dateNow;
	}

	/**
	 * 方法名：compareLeavTime
	 * 
	 * @param preLTime
	 *            预计离开时间
	 * @param realLtime
	 *            实际离开时间
	 * @return 实际离开时间与预计离开时间的比较结果 (xd或xh)
	 */
	public String compareLeaveTime(String preLTime, String realTime) {
		// [1]先比较天数
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date _preDay = null;
		Date _relDay = null;
		try {
			_preDay = sdf1.parse(preLTime);
			_relDay = sdf1.parse(realTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String preDay = sdf1.format(_preDay);
		String relDay = sdf1.format(_relDay);
		int day;
		int hours;
		if ((day = relDay.compareTo(preDay)) > 0) { // 若是实际离店时间大于预计离店时间为1天或1天以上，直接按天计算超时价格
			return day + "d";
		} else if (relDay.compareTo(preDay) == 0) { // 如果是在当天内超时
			sdf1 = new SimpleDateFormat("HH");
			Date _preHour = null;
			Date _relHour = null;
			try {
				_preHour = sdf1.parse(preLTime.split(" ")[1]);
				_relHour = sdf1.parse(realTime.split(" ")[1]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String relHour = sdf1.format(_relHour);
			String preHour = sdf1.format(_preHour);
			hours = Integer.parseInt(relHour) - Integer.parseInt(preHour);
			if (hours > 0 && hours <= 4) { // 四小时以内，按小时计费
				return hours + "h";
			} else if (hours == 0) { // 小时相等，如果等于或超过30分钟，则按1小时计算
				sdf1 = new SimpleDateFormat("HH:mm");
				Date _preMn = null;
				Date _relMn = null;
				try {
					_preMn = sdf1.parse(preLTime.split(" ")[1]);
					_relMn = sdf1.parse(realTime.split(" ")[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String preMn = sdf1.format(_preMn).split(":")[1];
				String relMn = sdf1.format(_relMn).split(":")[1];
				int minutes = Integer.parseInt(relMn) - Integer.parseInt(preMn);
				if (minutes >= 30) {
					return 1 + "h";
				}
			} else {
				return 1 + "d";
			}
		} else {
			return null;
		}
		return null;
	}

	@Test
	public void test() {
		String preTime = "2019-09-06 10:20:00";
		String leaveTime = "2019-09-06 10:49:00";
		String result = compareLeaveTime(preTime, leaveTime);
		System.out.println(result);
	}
}
