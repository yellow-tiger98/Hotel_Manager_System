package com.hd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {
	// ���Ӹ�ʽ��[jdbc��mysql��//ip��ַ���˿ں�/���ݿ����ƣ�ѡ��1&ѡ��2...] useSSL=false �ر�SSL���Ӿ���
	private static String url = "jdbc:mysql://localhost:3306/hotelsystem?"
			+ "useUnicode=true&characterEncoding=utf-8&useSSL=false";
	// mysql���ݿ��û���
	private static String user = "root";
	// mysql���ݿ�����
	private static String password = "220352";

	private static boolean loadStatus = false;

	// ��̬����飬���౻����ʱ���Զ�ִ��
	static {
		// ��1������MySQL�ṩ��������(ͨ���������ʽ)
		// ��2��������������������ȡ�����������Ϣ�Լ���������������������JVM�е�����
		try {
			Class.forName("com.mysql.jdbc.Driver");
			loadStatus = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ���ڽ�������
	public static Connection getConnection() {
		Connection conn = null;
		if (loadStatus == true) {
			try {
				conn = DriverManager.getConnection(url, user, password);
				System.out.println("[FM������]���ݿ����ӽ����ɹ�");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	// ���ڹر�����
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ���ڹرս��������
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}