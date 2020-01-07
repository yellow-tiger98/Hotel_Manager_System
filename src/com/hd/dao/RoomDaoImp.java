package com.hd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.Role;
import com.hd.model.Room;
import com.hd.model.RoomType;
import com.hd.model.User;
import com.hd.utils.JdbcUtils;

public class RoomDaoImp {

	// 获取房间列表
	public List<Room> getRoomList(int page, int pageSize) {
		System.out.println("{RoomDaoImp}执行getRoomList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select r.*,rt.typename  from room r left join roomtype rt on r.typeid = rt.id order by r.id asc limit ?,?";
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
				room.setTypeId(rs.getString("r.typeId"));
				room.setTypeName(rs.getString("rt.typename"));
				room.setFloor(rs.getInt("r.floor"));
				if (rs.getString("status") == null) {
					room.setStatus("空闲");
				} else {
					room.setStatus(rs.getString("status"));
				}
				list.add(room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 增加房间
	public JSONObject addRoom(Room room) {
		System.out.println("{RoomDaoImp}执行addRoom");
		// [1]查看创建的房间号是否已经存在
		String sql1 = "select * from room where id=?";
		String sql2 = "insert into room (id,typeId,floor,status) values (?,?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, room.getId());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "添加失败，该房间编号已经被使用！");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, room.getId());
				psmt.setString(2, room.getTypeId());
				psmt.setInt(3, room.getFloor());
				psmt.setString(4, "空闲");
				int count = psmt.executeUpdate();
				if (count > 0) {
					json.put("result", "ok");
					return json;
				} else {
					json.put("result", "no");
					return json;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 编辑房间
	public JSONObject saveEditRoom(Room room) {
		System.out.println("{RoomDaoImp}执行saveEditRoom");
		String sql = "update room set typeid=?,floor=?,status=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, room.getTypeId());
			psmt.setInt(2, room.getFloor());
			psmt.setString(3, room.getStatus());
			psmt.setString(4, room.getId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			} else {
				json.put("result", "no");
				return json;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 删除房间
	public JSONObject delRoom(Room room) {
		System.out.println("{RoomDaoImp}执行delRoom");
		String sql = "delete from room where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, room.getId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			} else {
				json.put("result", "no");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 获取房间类型列表
	public List<RoomType> getRoomTypeList(int page, int pageSize) {
		System.out.println("{RoomDaoImp}执行getRoomTypeList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from roomtype limit ?,?";
		PreparedStatement pstmt = null;
		List<RoomType> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomType rt = new RoomType();
				rt.setId(rs.getString("id"));
				rt.setTypeName(rs.getString("typename"));
				rt.setDayPrice("￥" + rs.getInt("dayPrice"));
				if (rs.getInt("hourPrice") == 0) {
					rt.setHourPrice("无此定价");
				} else {
					rt.setHourPrice("￥" + rs.getInt("hourPrice"));
				}
				rt.setResidence("￥" + rs.getInt("residence"));
				list.add(rt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 编辑房间类型
	public JSONObject saveEditRoomType(RoomType rt) {
		System.out.println("{RoomDaoImp}执行saveEditRoomType");
		// [1]创建sql语句用于查询该房间类型名称是否已经存在于列表中(将自身排除)
		String sql1 = "select * from roomtype where id not in (?) and typename=?";
		// [2]创建sql语句用于更新此角色信息
		String sql2 = "update roomtype set typename=?,dayPrice=?,hourPrice=?,residence=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, rt.getId());
			psmt.setString(2, rt.getTypeName());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "保存失败！此房间类型名称已存在！");
				return json;
			} else {
				String dayprice = rt.getDayPrice();
				if (dayprice.contains("￥")) {
					dayprice = dayprice.split("￥")[1];
					System.out.println(dayprice);
				}
				String hourprice = rt.getHourPrice();
				if (hourprice.contains("￥") && hourprice != null) {
					hourprice = hourprice.split("￥")[1];
				}
				String reprice = rt.getResidence();
				if (reprice.contains("￥")) {
					reprice = reprice.split("￥")[1];
				}
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, rt.getTypeName());
				psmt.setInt(2, Integer.parseInt(dayprice));
				if (hourprice.equals("")) {
					psmt.setInt(3, 0);
				} else {
					psmt.setInt(3, Integer.parseInt(hourprice));
				}
				psmt.setInt(4, Integer.parseInt(reprice));
				psmt.setString(5, rt.getId());
				int count = psmt.executeUpdate();
				if (count > 0) {
					json.put("result", "ok");
					return json;
				} else {
					json.put("result", "no");
					return json;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}
	// 增加房间
		public JSONObject addRoomType(RoomType roomType) {
			System.out.println("{RoomDaoImp}执行addRoomType");
			// [1]查看创建的房间类型id是否已经存在和房间类型名称是否已经被使用
			String sql1 = "select * from roomtype where id=? or typename=?";
			String sql2 = "insert into roomtype (id,typename,dayPrice,hourPrice,residence) values (?,?,?,?,?)";
			Connection conn = JdbcUtils.getConnection();
			PreparedStatement psmt = null;
			JSONObject json = new JSONObject();
			try {
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, roomType.getId());
				psmt.setString(2, roomType.getTypeName());
				ResultSet rs = psmt.executeQuery();
				if (rs.next()) {
					json.put("result", "no");
					json.put("message", "添加失败，该id或类型名称已经被使用！");
					return json;
				} else {
					psmt = conn.prepareStatement(sql2);
					psmt.setString(1, roomType.getId());
					psmt.setString(2, roomType.getTypeName());
					psmt.setInt(3, Integer.parseInt(roomType.getDayPrice()));
					int hourprice = 0;
					if(!(roomType.getHourPrice().equals(""))) {
						 hourprice = Integer.parseInt(roomType.getHourPrice());
					}
					psmt.setInt(4, hourprice);
					psmt.setInt(5, Integer.parseInt(roomType.getResidence()));
					int count = psmt.executeUpdate();
					if (count > 0) {
						json.put("result", "ok");
						return json;
					} else {
						json.put("result", "no");
						return json;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.closeConnection(conn);
			}
			json.put("result", "no");
			return json;
		}

	// 删除房间类型
	public JSONObject delType(RoomType rt) {
		System.out.println("{RoomDaoImp}执行ddelType");
		String sql = "delete from roomtype where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, rt.getId());
			int count = psmt.executeUpdate();
			if (count > 0) {
				json.put("result", "ok");
				return json;
			} else {
				json.put("result", "no");
				return json;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		json.put("result", "no");
		return json;
	}

	// 获取增加房间时房间类型的列表
	public JSONObject getSelectOp() {
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
	
	//获取查询总数，用于前端分页
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

}
