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

	// ��ӱ���
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

	// �༭����
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

	// ɾ������
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

	// ��ȡ���е�ͬ�б����б�
	@ReqeustMapping("/jsonCompanyList")
	private String josnTypeList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		ServletContext context = req.getServletContext();
		context.getAttribute("company");
		List<Loader> list = (List) context.getAttribute("company");
		System.out.println(list);
		PageBean<Loader> pBean = new PageBean<>();
		pBean.setList(list);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ���µǼǱ�����Ϣid
	@ReqeustMapping("/updateInfoId")
	protected String updateInfoId(Loader ld, HttpServletRequest req, HttpServletResponse resp) {
		lodgingInfoId = ld.getLodgingInfoId();
		JSONObject json = new JSONObject();
		json.put("result", "ok");
		return "data:" + json.toString();
	}

	// ��ȡͬ�б����б�
	@ReqeustMapping("/jsonCpList")
	private String josnCpList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		PageBean<Loader> pBean = service.getCpList(page, lodgingInfoId);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;

	}

	// ���ڷ�װ����layui����ҳ��json���ݸ�ʽ
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}

}
