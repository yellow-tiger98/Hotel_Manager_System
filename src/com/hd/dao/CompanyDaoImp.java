package com.hd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hd.model.Loader;
import com.hd.model.Room;
import com.hd.utils.JdbcUtils;

public class CompanyDaoImp {
	// 获取同行宾客列表
	public List<Loader> getCpList(String lodgInfoId,int page, int pageSize) {
		System.out.println("{ReceptionDaoImp}执行getRoomList");
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from lodger where lodgingInfoId=? and isRegister=0 limit ?,?";
		PreparedStatement pstmt = null;
		List<Loader> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// {ps}计算偏移量：offset
			int offset = (page - 1) * pageSize;
			pstmt.setString(1, lodgInfoId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Loader ld = new Loader();
				ld.setLodgerName(rs.getString("lodgerName"));
				ld.setSex(rs.getString("sex"));
				ld.setCertificate(rs.getString("certificate"));
				ld.setCertificateNo(rs.getString("certificateNo"));
				System.out.println(ld);
				list.add(ld);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
	}
}
