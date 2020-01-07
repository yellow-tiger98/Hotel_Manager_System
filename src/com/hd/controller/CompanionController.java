package com.hd.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.Loader;
import com.hd.model.PageBean;
import com.hd.model.RoomType;
import com.hd.service.CompanyServiceImp;

@WebServlet("/Company/*")
public class CompanionController extends BaseController {
	public static List<Loader> list = new LinkedList();
	public static int cpId = 0;
	public static String lodgingInfoId;
	private CompanyServiceImp service = new CompanyServiceImp();

	// 添加宾客
	@ReqeustMapping("/addCompany")
	protected String addCompany(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		cpId++;
		ServletContext context = req.getServletContext();
		ld.setCpId(cpId);
		ld.setIsRegister(0);
		list.add(ld);
		context.setAttribute("company", list);
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// 编辑宾客
	@ReqeustMapping("/updateCompany")
	protected String updateCompany(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		ServletContext context = req.getServletContext();
		int i = 0;
		for (Loader loader : list) {
			if (loader.getCpId() == ld.getCpId()) {
				loader.setLodgerName(ld.getLodgerName());
				loader.setCertificate(ld.getCertificate());
				loader.setCertificateNo(ld.getCertificateNo());
				loader.setSex(ld.getSex());
				System.out.println(ld.getCpId());
				System.out.println(loader);
				list.set(i, loader);
				context.setAttribute("company", list);
			}
			i++;
		}
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// 删除宾客
	@ReqeustMapping("/deleteCompany")
	protected String deleteCompany(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		ServletContext context = req.getServletContext();
		int i = 0;
		for (Loader loader : list) {
			if (loader.getCpId() == ld.getCpId()) {
				list.remove(i);
				context.setAttribute("company", list);
			}
			i++;
		}
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// 获取域中的同行宾客列表
	@ReqeustMapping("/jsonCompanyList")
	private String josnTypeList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		ServletContext context = req.getServletContext();
		context.getAttribute("company");
		List<Loader> list = (List) context.getAttribute("company");
		System.out.println(list);
		PageBean<Loader> pBean = new PageBean<>();
		pBean.setList(list);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 更新登记宾客信息id
	@ReqeustMapping("/updateInfoId")
	protected String updateInfoId(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		lodgingInfoId = ld.getLodgingInfoId();
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// 获取同行宾客列表
	@ReqeustMapping("/jsonCpList")
	private String josnCpList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getCpList(page, lodgingInfoId);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;

	}

	// 用于封装符合layui表格分页的json数据格式
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}

}
