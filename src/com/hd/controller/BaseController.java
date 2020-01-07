package com.hd.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hd.exception.FormatException;
import com.hd.utils.BeanUtils;


//BaseController�ķ���ִ�����̣�
//	[1]ͨ��getURIPattern()
public class BaseController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		System.out.println("{BaseController}doGet()");
		//{1}��ȡreq�е�URI����
		String uri = req.getRequestURI();
		//{2}�õ�login
		uri = getURIPattern(uri);
		//{3}�õ�����ķ���
		Method mtd = getAnnotationMethod(uri);
		if(mtd!=null) {
			System.out.println("{BaseController}�ҵ�����ӳ��ķ�����"+uri);
			try {
				//{ps}����ͻ���������
				String ret = processAction(mtd,req,resp);
				//{ps}�õ������ķ���ֵ
				if(ret!=null) { //{ps}�����з���ֵ
					//{ps}��ȥ�����������ֵ
					//{ps}����ֵ�ĸ�ʽ
					//	   [1]return "redirect:/AServlet"[�ض���]
					//	   [2]return "forward:/Servlet"[�ڲ�ת��]
					//	   [3]return "data:{"name":"andy","pass":"123"}"[�ַ�����]
					//	   [4]return "file:d:\123.txt"[�ļ�]
					parseResult(ret, req, resp);
				}
			} catch (IllegalAccessException | FormatException e) {
				e.printStackTrace();
			}
		}else {
			//{ps}��ʾ404�Ҳ�����Ӧ����Դ
			resp.sendError(404,"[BaseController]�Ҳ�����Դ");
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	/**
	 * ������:getURIPattern
	 * ��������:String
	 * ����Ӧ����ֵ:URI(�û����ʵ�ӳ���ַ)
	 * ����ֵ����:String
	 * ����ֵӦΪ:���һ��ӳ���ַ
	 * ����:��ȡURI�����һ��ӳ�䣬������Ը������ӳ��ִ������Controller�е�ҵ��
	 */
	private String getURIPattern(String uri) {
		int index = uri.lastIndexOf("/");
		return uri.substring(index+1, uri.length());
	}
	
	/**
	 * ������:getAnnotationMethod
	 * ��������:String
	 * ����Ӧ����ֵ:ͨ��getURIPattern��õķ���ֵ
	 * ����ֵ����:Mehod
	 * ����ֵӦΪ:�����ӳ��Ķ�Ӧ����
	 * ����:���ڻ�ȡ��Ӧ��ҵ�񣬲�����ͨ��ע������жϣ�����������еĶ�Ӧ������ע����ܹ��������أ���ע�ⷽ��������ƥ��
	 */
	private Method getAnnotationMethod(String urlPattern) {
		//{1}�Ȼ�ȡ"��ǰ����"���ֽ���
		// ע����ǰ����ΪBaseController
		Class clazz = this.getClass();
		//{2}�Ȼ�ȡ��������������з���
		Method[] mtds = clazz.getDeclaredMethods();
		//{3}"login"תΪ"/login"
		urlPattern = "/"+urlPattern;
		//{3}����ÿһ������
		for (Method method : mtds) {
			//{ps}��Ϊ֪��ע������Ϊ��@ReqeustMapping
			//    ����ֱ��ȥ��ȡָ����ע�����
			ReqeustMapping anno = method.getAnnotation(ReqeustMapping.class);
			if(anno!=null) { //{ps}��ȡ�����ע��
				//{ps}ͨ��������ȡ��ע���е�value��ֵ
				String mapping = anno.value();
				//{ps}�ҵ��˶�Ӧ���������
				if(mapping.equals(urlPattern)) {
				return method; //{ps}���ص�ǰ����
				}
			}
		}
		return null;
	}
	
	/**
	 * ��������processAction
	 * ��������1:Method
	 * ��������2:HttpServletReqeust
	 * ��������3:HttpServletResponse 
	 * ����Ӧ����ֵ:[1]ͨ��getAnnotationMethod��õķ��� [2]http������� [3]http��Ӧ����
	 * ����ֵ����:String
	 * ����ֵӦΪ:У����
	 * ����:��������������еķ����Ƿ���Ϸ�����ʽ������ͨ����ͨ��callMethod������
	 * 		��������������ܶ�Ӧ����
	 * @throws IllegalAccessException 
	 */
	private String processAction(Method mtd, HttpServletRequest req, HttpServletResponse resp) throws IOException, IllegalAccessException {
		//{1}�õ������Ĳ�������
		int parCount = mtd.getParameterCount(); //JDK 1.8֧��
		//{2}���������Ϲ��Ҫ��
		if(parCount!=2&&parCount!=3) {
			System.out.println("{BaseController}���������Ϲ���");
			resp.sendError(404, "[BaseController]�Ҳ�����Ӧ����Դ");
			return null;
		}
		//{3}��ȡ��ʽ�����б�
		Class<?>[] paraTypes = mtd.getParameterTypes();
		//{4}����һ���������Ա��桰ʵ��ֵ��,���� == ��������
		Object[] values = new Object[parCount];
		//{5}����getMethodInfo����ȡ�����е���Ϣ
		//	 ����������"��ʽУ��"
		MethodInfo mi = getMethodInfo(paraTypes, values, req, resp);
		
		//{6}��ⷽ����ʽ�Ƿ���ȷ
		if(mi.reqCount!=1||mi.respCount!=1||
		(mi.otherCount!=0&&mi.otherCount!=1)) {
			System.out.println("{BaseController}���������Ϲ���");
			resp.sendError(404, "[BaseController]�Ҳ�����Ӧ����Դ");
			return null;
		}
		//{7}����callMethod����
		String ret = callMethod(mtd, mi, req.getParameterMap(), values);
		System.out.println(ret);
		return ret;
	}	
	/**
	 * 
	 * @author 94366
	 *
	 */
	class MethodInfo{
		Class<?> beanClass = null;
 		int reqCount = 0;   //��������������
		int respCount = 0;  //��Ӧ�����������
		int beanPos = -1;  //-1:�Ҳ�����Bean
		int otherCount = 0; //�������Ͳ�������
	}
	
	/**
	 * ��������getMethodInfo
	 * ��������1:Class<?>[]
	 * ��������2:Object[]
	 * ��������3:HttpServletRequest
	 * ��������4��HttpServletResponse 
	 * ����Ӧ����ֵ:[1]getAnnotationMethod�����л�õķ������������ֽ�������
	 * 			 [2]���ڱ��淽��ʵ�ε�object����
	 * 			 [3]http�������
	 * 			 [4]http��Ӧ����
	 * ����ֵ����:MethodInfo�ڲ���
	 * ����ֵӦΪ:һ�����з�����Ϣ��MethodInfo����
	 * ����:��ϸУ������ķ����Ƿ�����Ѷ��ļ��ַ�����ʽ������������ʵ�����鴫ֵ���ɱ�����ִ�У��������򴫴�����Ϣ
	 */
	private MethodInfo getMethodInfo(Class<?>[] types,Object[] values,HttpServletRequest
			req,HttpServletResponse resp) {
		MethodInfo med = new MethodInfo();
		for (int i = 0; i < types.length; i++) {
			if(types[i]==HttpServletRequest.class) {
				med.reqCount += 1; //��⵽������Ĳ���������+1
				//����reqʵ�ε�ʵ�����鵱��
				values[i] = req;
			}else if(types[i]==HttpServletResponse.class) {
				med.respCount += 1;
				values[i] = resp;
			}else {  //�������������Ϊ��������
				med.otherCount += 1;   //��¼������������
				med.beanClass = types[i]; //{ps}����bean������ֽ���
				//{ps}����bean��������������������������λ��
				med.beanPos = i ;
			}
		}
		return med;
	}
	
	/**
	 * ��������callMethod
	 * ��������1:Method����
	 * ��������2:MethodInfo����
	 * ��������3:Map<String,String[]>����
	 * ��������4��Object[]����
	 * ����Ӧ����ֵ:[1]ͨ��getAnnotationMethod�����л�õķ�������
	 * 			 [2]ͨ��getMethodInfo��õķ���������Ϣ
	 * 			 [3]�ͻ��˴��������������Ϣ
	 * 			 [4]����ִ������ʵ���������
	 * ����ֵ����:String
	 * ����ֵӦΪ:������ִ����Ϣ
	 * ����:�����ڷ���ͨ�����У���ͨ������ķ�ʽ����ִ��
	 */
	private String callMethod(Method med,MethodInfo info,Map<String,String[]> map,Object[] values) 
			throws IllegalAccessException {
		//{1}����JavaBean�Ƿ���ڣ�
		if(info.beanClass!=null) { //{2}֤��bean����
			//{3}�������Bean�����ҽ�"�������"����Bean����
			//	 ���ø�ʽ��BeanUtils.parseBean(���������Bean�ֽ���)
			Object bean = BeanUtils.parseBean(map, info.beanClass);
			//{4}��Bean���õ�ʵ��������
			values[info.beanPos] = bean;
		}
		//{5}���÷����ɷ���
		med.setAccessible(true);
		//{6}ֱ�ӵ��÷���
		// 		this ===>�������������
		// 		ͨ����������������������ķ���
		//		med.invoke(��������ʵ������)
		try {
			String ret = (String)med.invoke(this, values);
			return ret;
		} catch (IllegalArgumentException | InvocationTargetException e) {
			Throwable ex = e.getCause();
			ex.printStackTrace();
		}
		return null;
	}
	
	//�����������ֵ��Ȼ�����doResponse����
	private void parseResult(String result,HttpServletRequest req,HttpServletResponse resp) throws FormatException {
		String regex = "([^:]+):(.+)";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(result);
		if(!mat.matches()) {
			throw new FormatException("���ظ�ʽ����");
		}
		//������
		String methodName = mat.group(1);
		//��������
		String content = mat.group(2);
		//��Ӧ�ͻ���
		doResponse(methodName,content,req,resp);
	}

	//{ps}ͨ������������1��redirect,forward,...����
	//    �����ȡBaseController�ķ�������
	//    ���ȣ�����õ�BaseController���ֽ���
	//    ������Ϊ������this.getClass() --->UserController.class
	//    ����һ����λ�ȡBaseController.class
	private void doResponse(String methodName, String content, HttpServletRequest req, HttpServletResponse resp) {
		//{ps}�õ�������ֽ��룺BaseController.class
		Class<?> supClass = this.getClass().getSuperclass();
		try {
			Method mtd = supClass.getDeclaredMethod(methodName, String.class,HttpServletRequest.class
					,HttpServletResponse.class);
			mtd.setAccessible(true);
			//this:�������������������
			mtd.invoke(this, content,req,resp);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException |IllegalArgumentException | 
				InvocationTargetException e) {
			Throwable ex = e.getCause();  //��ȡ�쳣��Դͷ
			ex.printStackTrace();		//��ӡ�쳣��Ϣ
		} 
	}
	
	public void redirect(String content,HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String ctxPath = req.getContextPath();
		resp.sendRedirect(ctxPath+content);
	}
	
	public void forward(String content,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(content).forward(req, resp);
	}
	
	public void data(String content,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write(content);
	}
	
}
