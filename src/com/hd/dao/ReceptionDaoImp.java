package com.hd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.Cleaner;
import com.hd.model.Loader;
import com.hd.model.Order;
import com.hd.model.Repair;
import com.hd.model.Room;
import com.hd.model.User;
import com.hd.utils.JdbcUtils;

public class ReceptionDaoImp {

	// 录入主入住人信息
	public JSONObject addMainRegister(Loader ld, User user) {
		// [1]向入住者信息表插入数据语句
		String sql1 = "insert into lodger (lodgingInfoId,certificate,certificateNo,lodgerName,sex,isRegister,phone,note) "
				+ "values (?,?,?,?,?,?,?,?)";
		String sql2 = "insert into lodginginfo (id,entryDate,leaveDate,days,roomId,deposit,status,operatorId) values "
				+ "(?,?,?,?,?,?,?,?)";
		String sql3 = "update room set status=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			if (getDays(ld.getInTime(), ld.getOutTime()).contains("h")) {
				String sql = "select hourprice from view_room_list where id=?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, ld.getRoomNo());
				ResultSet rs = psmt.executeQuery();
				if (rs.next()) {
					if (rs.getInt("hourprice") == 0) {
						System.out.println(44);
						json.put("result", "no");
						json.put("message", "此房间无钟点定价，请更改！");
						return json;
					}
				}
			}
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, ld.getLodgingInfoId());
			psmt.setString(2, ld.getCertificate());
			psmt.setString(3, ld.getCertificateNo());
			psmt.setString(4, ld.getLodgerName());
			psmt.setString(5, ld.getSex());
			psmt.setInt(6, 1);
			psmt.setString(7, ld.getPhone());
			psmt.setString(8, ld.getNote());
			int count = psmt.executeUpdate();
			if (count > 0) {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, ld.getLodgingInfoId());
				psmt.setString(2, ld.getInTime());
				psmt.setString(3, ld.getOutTime());
				String days = getDays(ld.getInTime(), ld.getOutTime());
				psmt.setString(4, days);
				psmt.setString(5, ld.getRoomNo());
				psmt.setString(6, ld.getDesposit());
				psmt.setString(7, "入住中");
				psmt.setString(8, user.getId());
				count = psmt.executeUpdate();
				if (count > 0) {
					psmt = conn.prepareStatement(sql3);
					psmt.setString(1, "入住中");
					psmt.setString(2, ld.getRoomNo());
					count = psmt.executeUpdate();
					if (count > 0) {
						json.put("result", "ok");
						json.put("infoId", ld.getLodgingInfoId());
						return json;
					} else {
						json.put("result", "no");
						json.put("message", "房态信息更新失败！");
						return json;
					}
				} else {
					json.put("result", "no");
					json.put("message", "入住信息添加失败");
					return json;
				}
			} else {
				json.put("result", "no");
				json.put("message", "入住登记失败");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "入住登记失败");
		return json;
	}

	// 录入同行宾客信息
	public JSONObject addCpRegister(Loader ld) {
		// [1]向入住者信息表插入数据语句
		String sql = "insert into lodger (lodgingInfoId,certificate,certificateNo,lodgerName,sex,isRegister) "
				+ "values (?,?,?,?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, ld.getLodgingInfoId());
			psmt.setString(2, ld.getCertificate());
			psmt.setString(3, ld.getCertificateNo());
			psmt.setString(4, ld.getLodgerName());
			psmt.setString(5, ld.getSex());
			psmt.setInt(6, 0);
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			} else {
				json.put("result", "no");
				json.put("message", "添加入住信息失败");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "添加入住信息失败");
		return json;
	}

	// 获取房间列表
	public List<Room> getRoomList(int page, int pageSize) {
		System.out.println("{ReceptionDaoImp}执行getRoomList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select r.*,rt.typename from room r left join roomtype rt on r.typeId=rt.id where"
				+ " r.status in ('空闲') or r.status is null limit ?,?;";
		PreparedStatement pstmt = null;
		List<Room> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room();
				room.setId(rs.getString("r.id"));
				room.setTypeName(rs.getString("rt.typename"));
				list.add(room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取指定房间列表
	public List<Room> getNewRoomList(int page, int pageSize, String typeId) {
		System.out.println("{ReceptionDaoImp}执行getRoomList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select r.*,rt.typename from room r left join roomtype rt on r.typeId=rt.id where"
				+ " (r.status in ('空闲') or r.status is null) and r.typeId=? limit ?,?;";
		PreparedStatement pstmt = null;
		List<Room> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setString(1, typeId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room();
				room.setId(rs.getString("r.id"));
				room.setTypeName(rs.getString("rt.typename"));
				list.add(room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取增加房间时房间类型的列表
	public JSONObject getRoomOp() {
		String sql = "select typename,id from roomtype";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			List list = new ArrayList();
			while (rs.next()) {
				JSONObject js = new JSONObject();
				js.put("id", rs.getString(2));
				js.put("name", rs.getString(1));
				list.add(js);
			}
			json.put("list", list);
			return json;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 获取未结账列表
	public List<Loader> getNoPayList(int page, int pageSize) {
		System.out.println("{ReceptionDaoImp}执行getNoPayList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from view_nopay_list order by entryDate desc limit ?,?";
		PreparedStatement pstmt = null;
		List<Loader> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Loader load = new Loader();
				load.setLodgerName(rs.getString("lodgerName"));
				load.setRoomNo(rs.getString("roomId"));
				load.setInTime(rs.getString("entryDate").substring(0, rs.getString("entryDate").length() - 2));
				load.setOutTime(rs.getString("leaveDate").substring(0, rs.getString("leaveDate").length() - 2));
				load.setDays(rs.getString("days"));
				load.setDesposit(rs.getString("deposit"));
				load.setLodgingInfoId(rs.getString("lodgingInfoId"));
				load.setCertificate(rs.getString("certificate"));
				load.setCertificateNo(rs.getString("certificateNo"));
				load.setSex(rs.getString("sex"));
				load.setPhone(rs.getString("phone"));
				load.setNote(rs.getString("note"));
				list.add(load);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取已结账列表
	public List<Order> getHadPayList(int page, int pageSize) {
		System.out.println("{ReceptionDaoImp}执行getNoPayList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from view_pay_list order by entryDate desc limit ?,?";
		PreparedStatement pstmt = null;
		List<Order> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setLodgingInfoId(rs.getString("lodgingInfoId"));
				order.setLodgerName(rs.getString("lodgerName"));
				order.setRoomId(rs.getString("roomId"));
				order.setEntryDate(rs.getString("entryDate").substring(0, rs.getString("entryDate").length() - 2));
				order.setLeaveDate(rs.getString("leaveDate").substring(0, rs.getString("leaveDate").length() - 2));
				order.setRelLeaveDate(rs.getString("createDate").substring(0, rs.getString("createDate").length() - 2));
				order.setRePrice(rs.getString("amount"));
				order.setOperaId(rs.getString("operatorId"));
				order.setDays(rs.getString("days"));
				order.setCretificate(rs.getString("certificate"));
				order.setCretificateNo(rs.getString("certificateNo"));
				order.setSex(rs.getString("sex"));
				order.setPhone(rs.getString("phone"));
				order.setNote(rs.getString("note"));
				order.setDeposit(rs.getString("deposit"));
				list.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 用于获取未结账详细信息
	public Order getRoomDeatil(Order order) {
		String sql = "select * from view_room_list where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, order.getRoomId());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				order.setDayPrice(rs.getString("dayprice"));
				order.setHourPrice(rs.getString("hourprice"));
				order.setRePrice(rs.getString("residence"));
				order.setRoomType(rs.getString("typename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return order;
	}

	// 房间续费
	public JSONObject continueRoom(Loader load) {
		String sql = "update lodginginfo set leaveDate=?,days=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, load.getOutTime());
			String days = getDays(load.getInTime(), load.getOutTime());
			psmt.setString(2, days);
			psmt.setString(3, load.getLodgingInfoId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "续住失败");
		return json;
	}

	// 房间结账
	public JSONObject payForRoom(Order order, User user) {
		String sql1 = "update lodginginfo set status=? where id=?";
		String sql2 = "update room set status=? where id=?";
		String sql3 = "insert into reckoning (lodgingInfoId,amount,operatorId) values (?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, "已离店");
			psmt.setString(2, order.getLodgingInfoId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, "清洁中");
				psmt.setString(2, order.getRoomId());
				count = psmt.executeUpdate();
				if (count > 0) {
					psmt = conn.prepareStatement(sql3);
					psmt.setString(1, order.getLodgingInfoId());
					psmt.setString(2, order.getRePrice());
					psmt.setString(3, user.getId());
					count = psmt.executeUpdate();
					if (count > 0) {
						json.put("result", "ok");
						return json;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "结算出现错误");
		return json;
	}

	// 更改房态
	public JSONObject changeRoomStatus(Room room) {
		String sql = "update room set status=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, room.getStatus());
			psmt.setString(2, room.getId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "房态设置失败");
		return json;
	}

	// 获取维修人员列表
	public JSONObject getRepairOp() {
		String sql = "select username from user where roleId=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "3");
			ResultSet rs = psmt.executeQuery();
			List list = new ArrayList();
			while (rs.next()) {
				JSONObject js = new JSONObject();
				js.put("name", rs.getString(1));
				list.add(js);
			}
			json.put("list", list);
			return json;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 获取清洁人员列表
	public JSONObject getCleanOp() {
		String sql = "select * from user where roleId=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "2");
			ResultSet rs = psmt.executeQuery();
			List list = new ArrayList();
			while (rs.next()) {
				JSONObject js = new JSONObject();
				js.put("id", rs.getString("id"));
				js.put("name", rs.getString("username"));
				list.add(js);
			}
			json.put("list", list);
			return json;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 增加维修信息
	public JSONObject addRepairInfo(Repair repair) {
		String sql = "insert into maintain (roomId,operator,floor,info) values (?,?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, repair.getRoomId());
			psmt.setString(2, repair.getOperator());
			psmt.setInt(3, repair.getFloor());
			psmt.setString(4, repair.getInfo());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "维修信息添加失败");
		return json;
	}

	// 增加清洁信息
	public JSONObject addCleanInfo(Cleaner cleaner) {
		String sql = "insert into clean (roomid,floor,note,opid,opname) values (?,?,?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cleaner.getRoomid());
			psmt.setInt(2, cleaner.getFloor());
			psmt.setString(3, cleaner.getNote());
			psmt.setString(4, cleaner.getId());
			psmt.setString(5, cleaner.getName());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		json.put("message", "清洁信息添加失败");
		return json;
	}

	// 获取实时房态
	public JSONObject getRealRoomStatus(Room room) {
		String sql = "select * from view_realrs_list where floor=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;
		JSONObject json = new JSONObject();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, room.getFloor());
			ResultSet rs = pstmt.executeQuery();
			List list = new ArrayList();
			while (rs.next()) {
				JSONObject json2 = new JSONObject();
				json2.put("roomid", rs.getString("id"));
				json2.put("roomtype", rs.getString("typename"));
				json2.put("status", rs.getString("status"));
				String ldstatus = rs.getString("ldstatus");
				if (!(ldstatus.equals("无"))) {
					json2.put("ldstatus", ldstatus);
					json2.put("ldname", rs.getString("ldname"));
				} else {
					json2.put("ldname", null);
				}
				list.add(json2);
			}
			json.put("list", list);
			return json;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 获取维修记录
	public List<Repair> getRepairList(int page, int pageSize) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from maintain";
		PreparedStatement pstmt = null;
		List<Repair> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Repair repair = new Repair();
				repair.setRoomId(rs.getString("roomId"));
				repair.setFloor(rs.getInt("floor"));
				repair.setOperator(rs.getString("operator"));
				repair.setInfo(rs.getString("info"));
				repair.setTime(rs.getString("time").substring(0, rs.getString("time").length() - 2));
				list.add(repair);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取清洁记录
	public List<Cleaner> getCleanList(int page, int pageSize) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from clean";
		PreparedStatement pstmt = null;
		List<Cleaner> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Cleaner cleaner = new Cleaner();
				cleaner.setRoomid(rs.getInt("roomid"));
				cleaner.setFloor(rs.getInt("floor"));
				cleaner.setId(rs.getString("opid"));
				cleaner.setName(rs.getString("opname"));
				cleaner.setNote(rs.getString("note"));
				cleaner.setTime(rs.getString("time").substring(0, rs.getString("time").length() - 2));
				list.add(cleaner);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取清洁记录
	public List<Cleaner> getCleanListBySearch(int roomid, int page, int pageSize) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from clean where roomid=?";
		PreparedStatement pstmt = null;
		List<Cleaner> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomid);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Cleaner cleaner = new Cleaner();
				cleaner.setRoomid(rs.getInt("roomid"));
				cleaner.setFloor(rs.getInt("floor"));
				cleaner.setId(rs.getString("opid"));
				cleaner.setName(rs.getString("opname"));
				cleaner.setNote(rs.getString("note"));
				cleaner.setTime(rs.getString("time").substring(0, rs.getString("time").length() - 2));
				list.add(cleaner);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 获取房间楼层
	public List<Integer> getFloorList() {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select distinct floor from room";
		List<Integer> list = new ArrayList();
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("floor"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void test10() {
		List<Integer> list = getFloorList();
		for (Integer integer : list) {
			System.out.println(integer);
		}
	}

	// 获取查询总数，用于前端分页
	public int getCount(String sql) {
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return count;
	}

	// 用于计算入住天数
	public String getDays(String inTime, String outTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int inDay = 0;
		int leaveDay = 0;
		try {
			Date _entryDate = sdf.parse(inTime);
			Date _leaveDate = sdf.parse(outTime);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(_entryDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(_leaveDate);
			inDay = cal1.get(Calendar.DAY_OF_YEAR);
			leaveDay = cal2.get(Calendar.DAY_OF_YEAR);
			int year1 = cal1.get(Calendar.YEAR);
			int year2 = cal2.get(Calendar.YEAR);
			if (year1 != year2) // 不同年
			{
				int timeDistance = 0;
				for (int i = year1; i < year2; i++) {
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
					{
						timeDistance += 366;
					} else // 不是闰年
					{
						timeDistance += 365;
					}
				}
				return (timeDistance + (leaveDay - inDay) + 1) + "d";
			} else // 同年
			{
				if ((leaveDay - inDay) == 0) {
					sdf = new SimpleDateFormat("HH");
					long from = sdf.parse(inTime.split(" ")[1]).getTime();
					long to = sdf.parse(outTime.split(" ")[1]).getTime();
					int hours = (int) ((to - from) / (1000 * 60 * 60));
					return hours + "h";
				}
				return (leaveDay - inDay + 1) + "d";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test() {
		Room room = new Room();
		room.setFloor(1);
		JSONObject json = getRealRoomStatus(room);
		List list = (List) json.get("list");
		for (Object object : list) {
			JSONObject json2 = (JSONObject) object;
			System.out.println(json2);
		}
	}
}
