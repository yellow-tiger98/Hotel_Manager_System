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
	 * 方法名：doLogin
	 * 参数类型：User对象
	 * 参数应传值：根据用户在前端页面输入账号信息创建的临时User对象
	 * 返回类型：User对象
	 * 返回应传值：根据临时对象所提供的信息返回的一个真实存在的User对象
	 * 作用：将用户的登录信息传给dao层与数据库中所存信息进行验证，返回User对象则登录成功，抛异常表示登录有误
	 * @throws NoAccountException(不存在此账号异常)
	 * @throws PasswordErrorException(密码错误异常)
	 */
	public User doLogin(User user) throws NoAccountException, PasswordErrorException {
		System.out.println("{UserService}doLogin...");
		User daoUser = userDao.validateUser(user);
		if(daoUser!=null) { //{ps}证明有这个人存在，密码尚未匹配
			String p = user.getPassword();
			if(daoUser.getPassword().equals(p)) {
				//{ps}密码正确
				return daoUser;
			}
			else {
				throw new PasswordErrorException("密码错误");
			}
		}else {
			throw new NoAccountException("没有这一个用户存在!");
		}
	}

	/**
	 * 方法名：getRoleMenus
	 * 参数类型：String对象
	 * 参数应传值：登录角色id
	 * 返回类型：Map<String,Menu>集合对象
	 * 返回应传值：存有显示菜单栏的全部信息的Map集合，键为主菜单id，键为主菜单对象(其中存有主菜单id，主菜单名以及子菜单集合)
	 * 作用：负责与数据库进行交互，获取对应角色身份的菜单栏显示在前端页面
	 */
	public Map<String,Menu> getRoleMenus(String roleId){
		return menuDao.getRoleMenuMap(roleId);
	}
	
	public PageBean getUserList(PageBean page) {
		int _page = page.getPage();
		_page = (_page>0)?_page : 1;  //{1}处理页码非法问题
		int count = 0;
		//{2}拿到总记录数
		String sql = "select count(*) from user";
		count = userDao.getCount(sql);
		//{3}拿到页大小
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
		_page = (_page>0)?_page : 1;  //{1}处理页码非法问题
		int count = 0;
		//{2}拿到总记录数
		String sql = "select count(*) from role";
		count = userDao.getCount(sql);
		//{3}拿到页大小
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
//	//{1}先去数据库找一下有没有这么个用户
//	User daoUser = userDao.validateUser(user);
//	if(daoUser==null) { //证明用户不存在
//		userDao.saveUser(user);
//		return true;
//	}
//	return false;
//}
}
