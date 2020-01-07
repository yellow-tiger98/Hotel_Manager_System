package com.hd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.Role;
import com.hd.model.User;
import com.hd.utils.JdbcUtils;

public class UserDaoImp {
	// 验证用户登录
	public User validateUser(User user) {
		System.out.println("{UserDaoImp}执行validateUser");
		String sql = "select * from user where account=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getAccount());
			rs = psmt.executeQuery();
			if (rs.next()) {
				User dbUser = new User();
				dbUser.setId(rs.getString("id"));
				dbUser.setAccount(rs.getString("account"));
				dbUser.setUsername(rs.getString("username"));
				dbUser.setPassword(rs.getString("password"));
				dbUser.setSex(rs.getString("sex"));
				dbUser.setRoleid(rs.getString("roleId"));
				dbUser.setCreateDate(rs.getString("createDate"));
				return dbUser; // 返回数据库查询结果
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeConnection(conn);
		}
		return null;
	}

	// ----------------------以上为各身份用户通用功能-----------------------------

	// 管理员获取用户列表
	public List<User> getUserList(int page, int pageSize) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select u.*,r.roleName from user u left join " + "role r on u.roleId=r.id limit ?,?";
		PreparedStatement pstmt = null;
		List<User> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User dbUser = packgeUser(rs);
				list.add(dbUser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 管理员获取角色列表
	public List<Role> getRoleList(int page, int pageSize) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from role limit ?,?";
		PreparedStatement pstmt = null;
		List<Role> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setRoleid(rs.getString("id"));
				role.setRoleName(rs.getString("roleName"));
				role.setDesc(rs.getString("descript"));
				list.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}

	// 管理员添加用户
	public JSONObject addUser(User user) {
		System.out.println("{UserDaoImp}执行addUser");
		// [1]创建sql语句用于查询添加的用户id或账号是否已经存在
		String sql1 = "select * from user where id=? or account=?";
		String sql2 = "insert into user (id,account,username,password,sex,roleid) values (?,?,?,?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getAccount());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "添加失败！id或者账号已存在");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, user.getId());
				psmt.setString(2, user.getAccount());
				psmt.setString(3, user.getUsername());
				psmt.setString(4, user.getPassword());
				psmt.setString(5, user.getSex());
				psmt.setString(6, user.getRoleid());
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

	// 管理员编辑用户
	public JSONObject saveEditUser(User user) {
		System.out.println("{UserDaoImp}执行saveEditUser");
		// [1]创建sql语句用于查询更新后账号是否和已有账号重复
		String sql1 = "select * from user where id not in (?) and account=?";
		String sql2 = "update user set account=?,password=?,sex=?,roleId=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getAccount());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "此账号名已存在，不能重复！");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, user.getAccount());
				psmt.setString(2, user.getPassword());
				psmt.setString(3, user.getSex());
				psmt.setString(4, user.getRoleid());
				psmt.setString(5, user.getId());
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

	// 管理员删除用户
	public JSONObject doDeleteUser(User user) {
		System.out.println("{UserDaoImp}执行doDeleteUser");
		String sql = "delete from user where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
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
	
	// 管理员添加角色
	public JSONObject addRole(Role role) {
		System.out.println("{UserDaoImp}执行addRole");
		// [1]创建sql语句用于查询该角色的id和名字是否已经存在
		String sql1 = "select * from role where id=? or roleName=?";
		String sql2 = "insert into role values (?,?,?)";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, role.getRoleid());
			psmt.setString(2, role.getRoleName());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "添加失败！id或者角色名称已存在");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, role.getRoleid());
				psmt.setString(2, role.getRoleName());
				psmt.setString(3, role.getDesc());
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
	
	@Test
	public void test() {
		Role role = new Role();
		role.setRoleid("6");
		role.setRoleName("系统管理员");
		role.setDesc("测试测试");
		JSONObject json = addRole(role);
		System.out.println(json);
	}

	// 管理员删除角色
	public JSONObject doDeleteRole(Role role) {
		System.out.println("{UserDaoImp}执行doDeleteRole");
		String sql = "delete from role where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, role.getRoleid());
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

	// 管理员编辑角色
	public JSONObject saveEditRole(Role role) {
		System.out.println("{UserDaoImp}执行saveEditRole");
		// [1]创建sql语句用于查询该角色名称是否已经存在于列表中(将自身排除)
		String sql1 = "select * from role where id not in (?) and roleName=?";
		// [2]创建sql语句用于更新此角色信息
		String sql2 = "update role set roleName=?,descript=? where id=?";
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement psmt = null;
		JSONObject json = new JSONObject();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, role.getRoleid());
			psmt.setString(2, role.getRoleName());
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				json.put("result", "no");
				json.put("message", "此角色名称已存在，不能重复！");
				return json;
			} else {
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, role.getRoleName());
				psmt.setString(2, role.getDesc());
				psmt.setString(3, role.getRoleid());
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

	// ----------------------以上为管理员功能-------------------------

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

	public User packgeUser(ResultSet rs) throws SQLException {
		User u = new User();
		u.setId(rs.getString("id"));
		u.setAccount(rs.getString("account"));
		u.setUsername(rs.getString("username"));
		u.setPassword(rs.getString("password"));
		u.setSex(rs.getString("sex"));
		u.setRoleid(rs.getString("roleId"));
		u.setRoleName(rs.getString("roleName"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sf.parse(rs.getString("createDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		u.setCreateDate(sf.format(date));
		return u;
	}

	// public void saveUser(User user) {
	// Connection conn = JdbcUtils.getConnection();
	// String uuid = UUID.randomUUID().toString();
	// uuid = uuid.replaceAll("-", "");
	// String sql = "insert into user(id,username,password,nickname,createDate)"+
	// "values(?,?,?,?,curTime())";
	// PreparedStatement pstmt = null;
	// int updateCnt;
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1,uuid);
	// pstmt.setString(2, user.getUsername());
	// pstmt.setString(3, user.getPassword());
	// pstmt.setString(4, user.getNickName());
	//
	// updateCnt = pstmt.executeUpdate();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
}
