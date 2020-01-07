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

	// ��ȡ�����б�
	public List<Room> getRoomList(int page, int pageSize) {
		System.out.println("{RoomDaoImp}ִ��getRoomList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select r.*,rt.typename  from room r left join roomtype rt on r.typeid = rt.id order by r.id asc limit ?,?";
		PreparedStatement pstmt = null;
		List<Room> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}����ƫ������offset
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
					room.setStatus("����");
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

	// ���ӷ���
	public JSONObject addRoom(Room room) {
		System.out.println("{RoomDaoImp}ִ��addRoom");
		// [1]�鿴�����ķ�����Ƿ��Ѿ�����
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
				json.put("message", "���ʧ�ܣ��÷������Ѿ���ʹ�ã�");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, room.getId());
				psmt.setString(2, room.getTypeId());
				psmt.setInt(3, room.getFloor());
				psmt.setString(4, "����");
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

	// �༭����
	public JSONObject saveEditRoom(Room room) {
		System.out.println("{RoomDaoImp}ִ��saveEditRoom");
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

	// ɾ������
	public JSONObject delRoom(Room room) {
		System.out.println("{RoomDaoImp}ִ��delRoom");
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

	// ��ȡ���������б�
	public List<RoomType> getRoomTypeList(int page, int pageSize) {
		System.out.println("{RoomDaoImp}ִ��getRoomTypeList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from roomtype limit ?,?";
		PreparedStatement pstmt = null;
		List<RoomType> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}����ƫ������offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomType rt = new RoomType();
				rt.setId(rs.getString("id"));
				rt.setTypeName(rs.getString("typename"));
				rt.setDayPrice("��" + rs.getInt("dayPrice"));
				if (rs.getInt("hourPrice") == 0) {
					rt.setHourPrice("�޴˶���");
				} else {
					rt.setHourPrice("��" + rs.getInt("hourPrice"));
				}
				rt.setResidence("��" + rs.getInt("residence"));
				list.add(rt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// �༭��������
	public JSONObject saveEditRoomType(RoomType rt) {
		System.out.println("{RoomDaoImp}ִ��saveEditRoomType");
		// [1]����sql������ڲ�ѯ�÷������������Ƿ��Ѿ��������б���(�������ų�)
		String sql1 = "select * from roomtype where id not in (?) and typename=?";
		// [2]����sql������ڸ��´˽�ɫ��Ϣ
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
				json.put("message", "����ʧ�ܣ��˷������������Ѵ��ڣ�");
				return json;
			} else {
				String dayprice = rt.getDayPrice();
				if (dayprice.contains("��")) {
					dayprice = dayprice.split("��")[1];
					System.out.println(dayprice);
				}
				String hourprice = rt.getHourPrice();
				if (hourprice.contains("��") && hourprice != null) {
					hourprice = hourprice.split("��")[1];
				}
				String reprice = rt.getResidence();
				if (reprice.contains("��")) {
					reprice = reprice.split("��")[1];
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
	// ���ӷ���
		public JSONObject addRoomType(RoomType roomType) {
			System.out.println("{RoomDaoImp}ִ��addRoomType");
			// [1]�鿴�����ķ�������id�Ƿ��Ѿ����ںͷ������������Ƿ��Ѿ���ʹ��
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
					json.put("message", "���ʧ�ܣ���id�����������Ѿ���ʹ�ã�");
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

	// ɾ����������
	public JSONObject delType(RoomType rt) {
		System.out.println("{RoomDaoImp}ִ��ddelType");
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

	// ��ȡ���ӷ���ʱ�������͵��б�
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
	
	//��ȡ��ѯ����������ǰ�˷�ҳ
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
