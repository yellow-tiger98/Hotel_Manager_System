package com.hd.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hd.dao.MenuDaoImp;
import com.hd.dao.UserDaoImp;
import com.hd.exception.NoAccountException;
import com.hd.exception.PasswordErrorException;
import com.hd.model.Menu;
import com.hd.model.PageBean;
import com.hd.model.Role;
import com.hd.model.User;

public class UserServiceIml {
	private UserDaoImp userDao = new UserDaoImp();
	private MenuDaoImp menuDao = new MenuDaoImp();
	/**
	 * ��������doLogin
	 * �������ͣ�User����
	 * ����Ӧ��ֵ�������û���ǰ��ҳ�������˺���Ϣ��������ʱUser����
	 * �������ͣ�User����
	 * ����Ӧ��ֵ��������ʱ�������ṩ����Ϣ���ص�һ����ʵ���ڵ�User����
	 * ���ã����û��ĵ�¼��Ϣ����dao�������ݿ���������Ϣ������֤������User�������¼�ɹ������쳣��ʾ��¼����
	 * @throws NoAccountException(�����ڴ��˺��쳣)
	 * @throws PasswordErrorException(��������쳣)
	 */
	public User doLogin(User user) throws NoAccountException, PasswordErrorException {
		System.out.println("{UserService}doLogin...");
		User daoUser = userDao.validateUser(user);
		if(daoUser!=null) { //{ps}֤��������˴��ڣ�������δƥ��
			String p = user.getPassword();
			if(daoUser.getPassword().equals(p)) {
				//{ps}������ȷ
				return daoUser;
			}
			else {
				throw new PasswordErrorException("�������");
			}
		}else {
			throw new NoAccountException("û����һ���û�����!");
		}
	}

	/**
	 * ��������getRoleMenus
	 * �������ͣ�String����
	 * ����Ӧ��ֵ����¼��ɫid
	 * �������ͣ�Map<String,Menu>���϶���
	 * ����Ӧ��ֵ��������ʾ�˵�����ȫ����Ϣ��Map���ϣ���Ϊ���˵�id����Ϊ���˵�����(���д������˵�id�����˵����Լ��Ӳ˵�����)
	 * ���ã����������ݿ���н�������ȡ��Ӧ��ɫ��ݵĲ˵�����ʾ��ǰ��ҳ��
	 */
	public Map<String,Menu> getRoleMenus(String roleId){
		return menuDao.getRoleMenuMap(roleId);
	}
	
	public PageBean getUserList(PageBean page) {
		int _page = page.getPage();
		_page = (_page>0)?_page : 1;  //{1}����ҳ��Ƿ�����
		int count = 0;
		//{2}�õ��ܼ�¼��
		String sql = "select count(*) from user";
		count = userDao.getCount(sql);
		//{3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize>0)? pageSize:1;
		List<User> list = userDao.getUserList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	
	public PageBean getRoleList(PageBean page) {
		int _page = page.getPage();
		_page = (_page>0)?_page : 1;  //{1}����ҳ��Ƿ�����
		int count = 0;
		//{2}�õ��ܼ�¼��
		String sql = "select count(*) from role";
		count = userDao.getCount(sql);
		//{3}�õ�ҳ��С
		int pageSize = page.getLimit();
		pageSize = (pageSize>0)? pageSize:1;
		List<Role> list = userDao.getRoleList(_page, page.getLimit());
		PageBean pBean = new PageBean();
		pBean.setList(list);
		pBean.setCount(count);
		return pBean;
	}
	
	public JSONObject addUser(User user){
		JSONObject result = userDao.addUser(user);
		return result;
	}
	
	public JSONObject saveEditUser(User user){
		JSONObject result = userDao.saveEditUser(user);
		return result;
	}
	
	public JSONObject deleteUser(User user){
		JSONObject result = userDao.doDeleteUser(user);
		return result;
	}
	
	public JSONObject saveEditRole(Role role){
		JSONObject result = userDao.saveEditRole(role);
		return result;
	}
	
	public JSONObject deleteRole(Role role){
		JSONObject result = userDao.doDeleteRole(role);
		return result;
	}
	
	public JSONObject addRole(Role role) {
		JSONObject result = userDao.addRole(role);
		return result;
	}
	
//	public boolean doRegister(User user) {
//	//{1}��ȥ���ݿ���һ����û����ô���û�
//	User daoUser = userDao.validateUser(user);
//	if(daoUser==null) { //֤���û�������
//		userDao.saveUser(user);
//		return true;
//	}
//	return false;
//}
}
