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

	// �Ǽ����Ǽ�����ס��Ϣ
	public JSONObject addMainRegister(Loader ld, User user) {
		String loadingInfoId = getOnlyLoadId();
		ld.setLodgingInfoId(loadingInfoId);
		System.out.println("{ReceptionService}" + ld);
		JSONObject result = receDao.addMainRegister(ld, user);
		return result;
	}

	// �Ǽ�ͬ�б�����ס��Ϣ
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
			json.put("message", "ͬ�б�����Ϣ���ʧ�ܣ�");
			return json;
		}
	}

	// ��ȡ�����б�
	public PageBean getRoomList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from room where status='����' or status is null";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = receDao.getRoomList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��ȡָ�������б�
	public PageBean getNewRoomList(PageBean page, String typeId) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from room where status='����' or status is null and typeId=" + typeId + "";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Room> list = receDao.getNewRoomList(_page, page.getLimit(), typeId);
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��ȡ�������͵�option������ӷ���ʱʹ��
	public JSONObject getRoomOp() {
		JSONObject result = receDao.getRoomOp();
		return result;
	}

	// ��ȡ����۸������Ϣ�����ڽ���
	public JSONObject getRoomDeatil(Order order) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date _dateNow = new Date();
		String dateNow = sdf.format(_dateNow);
		Order newOrder = receDao.getRoomDeatil(order);
		JSONObject json = new JSONObject();
		// [1]��ѯ���
		json.put("result", "ok");
		// [2]��������
		json.put("roomType", newOrder.getRoomType());
		// [3]����۸�
		String _roomPrice;
		// �����۸�
		String dayPrice = newOrder.getDayPrice();
		// �ӵ�۸�
		String hourPrice = newOrder.getHourPrice();
		// ��û���ӵ�۸�����ʾ��
		if (hourPrice == null) {
			hourPrice = "��";
		}
		// Ԥ���۸�
		String rePrice = newOrder.getRePrice();
		// ƴװ����ļ۸�����
		_roomPrice = dayPrice + " / " + hourPrice + " / " + rePrice + "(��)";
		json.put("roomPrice", _roomPrice);
		// [4]��ס���ͣ��������ӵ�
		String _inStyle;
		// [5]�ܼ۸�
		int totalPrice = 0;
		String copResult;
		int otLast;
		int otHour;
		int otDay;
		String details = null;
		if (order.getDays().contains("h")) {
			_inStyle = "�ӵ㷿";
			int last = order.getDays().lastIndexOf("h");
			int hour = Integer.parseInt(order.getDays().substring(0, last));
			copResult = compareLeaveTime(order.getLeaveDate(), dateNow);
			if (copResult == null) {
				details = "��������";
				totalPrice = hour * Integer.parseInt(hourPrice);
			} else if (copResult.contains("h")) {
				otLast = copResult.lastIndexOf("h");
				otHour = Integer.parseInt(copResult.substring(0, last));
				details = "��������+��ʱ����(" + otHour + "Сʱ)";
				totalPrice = (hour + otHour) * Integer.parseInt(hourPrice);
			} else if (copResult.contains("d")) {
				otLast = copResult.lastIndexOf("d");
				otDay = Integer.parseInt(copResult.substring(0, last));
				details = "��������+��ʱ����(" + otDay + "��)";
				totalPrice = hour * Integer.parseInt(hourPrice) + otDay * Integer.parseInt(dayPrice);
			}
		} else {
			_inStyle = "����";
			int last = order.getDays().lastIndexOf("d");
			int days = Integer.parseInt(order.getDays().substring(0, last)) - 1;
			copResult = compareLeaveTime(order.getLeaveDate(), dateNow);
			if (copResult == null) {
				details = "��������";
				totalPrice = days * Integer.parseInt(dayPrice);
			} else if (copResult.contains("h")) {
				otLast = copResult.lastIndexOf("h");
				otHour = Integer.parseInt(copResult.substring(0, last));
				details = "��������+��ʱ����(" + otHour + "Сʱ)";
				totalPrice = days * Integer.parseInt(dayPrice) + otHour * Integer.parseInt(hourPrice);
				;
			} else if (copResult.contains("d")) {
				otLast = copResult.lastIndexOf("d");
				otDay = Integer.parseInt(copResult.substring(0, last));
				details = "��������+��ʱ����(" + otDay + "��)";
				totalPrice = (days + otDay) * Integer.parseInt(dayPrice);
			}
		}
		json.put("inStyle", _inStyle);
		json.put("totalPrice", totalPrice);
		json.put("detail", details);
		json.put("relLeaveTime", dateNow);
		return json;
	}

	// ��ȡδ�����б�
	public PageBean getNoPayList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from view_nopay_list";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Loader> list = receDao.getNoPayList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��ȡ�ѽ����б�
	public PageBean getHadPayList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from view_nopay_list";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Order> list = receDao.getHadPayList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��������
	public JSONObject continueRoom(Loader load) {
		JSONObject result = receDao.continueRoom(load);
		return result;
	}

	// ����
	public JSONObject payForRoom(Order order, User user) {
		JSONObject result = receDao.payForRoom(order, user);
		return result;
	}

	// ����
	public JSONObject changeRoomStatus(Room room) {
		JSONObject result = receDao.changeRoomStatus(room);
		return result;
	}

	// ��ȡά����Ա�б�
	public JSONObject getRepairOp() {
		JSONObject result = receDao.getRepairOp();
		return result;
	}

	// ��ȡ�����Ա�б�
	public JSONObject getCleanerOp() {
		JSONObject result = receDao.getCleanOp();
		return result;
	}

	// ���ά����Ϣ
	public JSONObject addReapirInfo(Repair repair) {
		JSONObject result = receDao.addRepairInfo(repair);
		return result;
	}

	// ���ά����Ϣ
	public JSONObject addCleanInfo(Cleaner cleaner) {
		JSONObject result = receDao.addCleanInfo(cleaner);
		return result;
	}

	// ��ȡά����Ϣ�б�
	public PageBean getRepairList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from maintain";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Repair> list = receDao.getRepairList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	
	// ��ȡ�����Ϣ�б�
	public PageBean getCleanList(PageBean page) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from clean";
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Cleaner> list = receDao.getCleanList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	// ��ȡ�����Ϣ�б�(��ѯ)
	public PageBean getCleanListBySearch(PageBean page,int roomid) {
		int _page = page.getPage();
		_page = (_page > 0) ? _page : 1; // {1}����ҳ��Ƿ�����
		int count = 0;
		// {2}�õ��ܼ�¼��
		String sql = "select count(*) from clean where roomid="+roomid;
		count = receDao.getCount(sql);
		// {3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize > 0) ? pageSize : 1;
		List<Cleaner> list = receDao.getCleanListBySearch(roomid,_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}

	// ��ȡʵʱ��̬
	public JSONObject getRealRoomStatus(Room room) {
		JSONObject json = receDao.getRealRoomStatus(room);
		return json;
	}

	// ��ȡ¥���б�
	public JSONObject getFloorList() {
		List<Integer> list = receDao.getFloorList();
		JSONObject json = new JSONObject();
		json.put("floor_list", list);
		return json;
	}

	// ��������Ψһ����ס��Ϣ��� ������+ʱ����
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

	// ��ȡ��ǰʱ��
	public String getDateNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateNow = sdf.format(date);
		return dateNow;
	}

	/**
	 * ��������compareLeavTime
	 * 
	 * @param preLTime
	 *            Ԥ���뿪ʱ��
	 * @param realLtime
	 *            ʵ���뿪ʱ��
	 * @return ʵ���뿪ʱ����Ԥ���뿪ʱ��ıȽϽ�� (xd��xh)
	 */
	public String compareLeaveTime(String preLTime, String realTime) {
		// [1]�ȱȽ�����
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
		if ((day = relDay.compareTo(preDay)) > 0) { // ����ʵ�����ʱ�����Ԥ�����ʱ��Ϊ1���1�����ϣ�ֱ�Ӱ�����㳬ʱ�۸�
			return day + "d";
		} else if (relDay.compareTo(preDay) == 0) { // ������ڵ����ڳ�ʱ
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
			if (hours > 0 && hours <= 4) { // ��Сʱ���ڣ���Сʱ�Ʒ�
				return hours + "h";
			} else if (hours == 0) { // Сʱ��ȣ�������ڻ򳬹�30���ӣ���1Сʱ����
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
