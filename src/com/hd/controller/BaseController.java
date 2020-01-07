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


//BaseController的方法执行流程：
//	[1]通过getURIPattern()
public class BaseController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		System.out.println("{BaseController}doGet()");
		//{1}获取req中的URI参数
		String uri = req.getRequestURI();
		//{2}得到login
		uri = getURIPattern(uri);
		//{3}得到对象的方法
		Method mtd = getAnnotationMethod(uri);
		if(mtd!=null) {
			System.out.println("{BaseController}找到符合映射的方法："+uri);
			try {
				//{ps}处理客户的请求动作
				String ret = processAction(mtd,req,resp);
				//{ps}得到方法的返回值
				if(ret!=null) { //{ps}假如有返回值
					//{ps}则去处理这个返回值
					//{ps}返回值的格式
					//	   [1]return "redirect:/AServlet"[重定向]
					//	   [2]return "forward:/Servlet"[内部转发]
					//	   [3]return "data:{"name":"andy","pass":"123"}"[字符数据]
					//	   [4]return "file:d:\123.txt"[文件]
					parseResult(ret, req, resp);
				}
			} catch (IllegalAccessException | FormatException e) {
				e.printStackTrace();
			}
		}else {
			//{ps}显示404找不到对应的资源
			resp.sendError(404,"[BaseController]找不到资源");
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	/**
	 * 方法名:getURIPattern
	 * 参数类型:String
	 * 参数应传入值:URI(用户访问的映射地址)
	 * 返回值类型:String
	 * 返回值应为:最后一节映射地址
	 * 作用:获取URI的最后一节映射，后面可以根据这个映射执行子类Controller中的业务
	 */
	private String getURIPattern(String uri) {
		int index = uri.lastIndexOf("/");
		return uri.substring(index+1, uri.length());
	}
	
	/**
	 * 方法名:getAnnotationMethod
	 * 参数类型:String
	 * 参数应传入值:通过getURIPattern获得的返回值
	 * 返回值类型:Mehod
	 * 返回值应为:子类和映射的对应方法
	 * 作用:用于获取对应的业务，并且是通过注解进行判断，子类控制器中的对应方法需注解才能够正常返回，无注解方法不进行匹配
	 */
	private Method getAnnotationMethod(String urlPattern) {
		//{1}先获取"当前对象"的字节码
		// 注：当前对象不为BaseController
		Class clazz = this.getClass();
		//{2}先获取子类控制器的所有方法
		Method[] mtds = clazz.getDeclaredMethods();
		//{3}"login"转为"/login"
		urlPattern = "/"+urlPattern;
		//{3}迭代每一个方法
		for (Method method : mtds) {
			//{ps}因为知道注解名称为：@ReqeustMapping
			//    所以直接去获取指定的注解对象
			ReqeustMapping anno = method.getAnnotation(ReqeustMapping.class);
			if(anno!=null) { //{ps}获取到这个注解
				//{ps}通过它可以取得注解中的value的值
				String mapping = anno.value();
				//{ps}找到了对应方法的入口
				if(mapping.equals(urlPattern)) {
				return method; //{ps}返回当前方法
				}
			}
		}
		return null;
	}
	
	/**
	 * 方法名：processAction
	 * 参数类型1:Method
	 * 参数类型2:HttpServletReqeust
	 * 参数类型3:HttpServletResponse 
	 * 参数应传入值:[1]通过getAnnotationMethod获得的方法 [2]http请求对象 [3]http响应对象
	 * 返回值类型:String
	 * 返回值应为:校验结果
	 * 作用:检验子类控制器中的方法是否符合方法格式，检验通过则通过callMethod方法调
	 * 		用子类控制器汇总对应方法
	 * @throws IllegalAccessException 
	 */
	private String processAction(Method mtd, HttpServletRequest req, HttpServletResponse resp) throws IOException, IllegalAccessException {
		//{1}拿到方法的参数个数
		int parCount = mtd.getParameterCount(); //JDK 1.8支持
		//{2}参数不符合规格要求
		if(parCount!=2&&parCount!=3) {
			System.out.println("{BaseController}方法不符合规则");
			resp.sendError(404, "[BaseController]找不到对应的资源");
			return null;
		}
		//{3}获取形式参数列表
		Class<?>[] paraTypes = mtd.getParameterTypes();
		//{4}创建一个数组用以保存“实参值”,长度 == 参数个数
		Object[] values = new Object[parCount];
		//{5}调用getMethodInfo来获取方法中的信息
		//	 用来作方法"格式校验"
		MethodInfo mi = getMethodInfo(paraTypes, values, req, resp);
		
		//{6}检测方法格式是否正确
		if(mi.reqCount!=1||mi.respCount!=1||
		(mi.otherCount!=0&&mi.otherCount!=1)) {
			System.out.println("{BaseController}方法不符合规则");
			resp.sendError(404, "[BaseController]找不到对应的资源");
			return null;
		}
		//{7}调用callMethod方法
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
 		int reqCount = 0;   //请求对象参数个数
		int respCount = 0;  //响应对象参数个数
		int beanPos = -1;  //-1:找不到有Bean
		int otherCount = 0; //其他类型参数个数
	}
	
	/**
	 * 方法名：getMethodInfo
	 * 参数类型1:Class<?>[]
	 * 参数类型2:Object[]
	 * 参数类型3:HttpServletRequest
	 * 参数类型4：HttpServletResponse 
	 * 参数应传入值:[1]getAnnotationMethod方法中获得的方法参数类型字节码数组
	 * 			 [2]用于保存方法实参的object数组
	 * 			 [3]http请求对象
	 * 			 [4]http响应对象
	 * 返回值类型:MethodInfo内部类
	 * 返回值应为:一个存有方法信息的MethodInfo对象
	 * 作用:详细校验所获的方法是否符合已定的几种方法格式，如果符合则给实参数组传值，可被后续执行，不符合则传错误信息
	 */
	private MethodInfo getMethodInfo(Class<?>[] types,Object[] values,HttpServletRequest
			req,HttpServletResponse resp) {
		MethodInfo med = new MethodInfo();
		for (int i = 0; i < types.length; i++) {
			if(types[i]==HttpServletRequest.class) {
				med.reqCount += 1; //检测到有请求的参数，数量+1
				//设置req实参到实参数组当中
				values[i] = req;
			}else if(types[i]==HttpServletResponse.class) {
				med.respCount += 1;
				values[i] = resp;
			}else {  //否则，这个参数则为其他类型
				med.otherCount += 1;   //记录其他参数个数
				med.beanClass = types[i]; //{ps}保存bean对象的字节码
				//{ps}保存bean参数在子类控制器的这个方法的位置
				med.beanPos = i ;
			}
		}
		return med;
	}
	
	/**
	 * 方法名：callMethod
	 * 参数类型1:Method对象
	 * 参数类型2:MethodInfo对象
	 * 参数类型3:Map<String,String[]>对象
	 * 参数类型4：Object[]对象
	 * 参数应传入值:[1]通过getAnnotationMethod方法中获得的方法对象
	 * 			 [2]通过getMethodInfo获得的方法对象信息
	 * 			 [3]客户端传来的请求参数信息
	 * 			 [4]方法执行所需实参数组对象
	 * 返回值类型:String
	 * 返回值应为:方法的执行信息
	 * 作用:用于在方法通过层层校验后，通过反射的方式进行执行
	 */
	private String callMethod(Method med,MethodInfo info,Map<String,String[]> map,Object[] values) 
			throws IllegalAccessException {
		//{1}检验JavaBean是否存在？
		if(info.beanClass!=null) { //{2}证明bean存在
			//{3}创建这个Bean，并且将"请求参数"填入Bean对象
			//	 调用格式：BeanUtils.parseBean(请求参数，Bean字节码)
			Object bean = BeanUtils.parseBean(map, info.beanClass);
			//{4}将Bean设置到实参数组中
			values[info.beanPos] = bean;
		}
		//{5}设置方法可访问
		med.setAccessible(true);
		//{6}直接调用方法
		// 		this ===>子类控制器对象
		// 		通过反射来调用子类控制器的方法
		//		med.invoke(主调对象，实参数组)
		try {
			String ret = (String)med.invoke(this, values);
			return ret;
		} catch (IllegalArgumentException | InvocationTargetException e) {
			Throwable ex = e.getCause();
			ex.printStackTrace();
		}
		return null;
	}
	
	//负责解析返回值，然后调用doResponse方法
	private void parseResult(String result,HttpServletRequest req,HttpServletResponse resp) throws FormatException {
		String regex = "([^:]+):(.+)";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(result);
		if(!mat.matches()) {
			throw new FormatException("返回格式错误！");
		}
		//方法名
		String methodName = mat.group(1);
		//方法内容
		String content = mat.group(2);
		//响应客户端
		doResponse(methodName,content,req,resp);
	}

	//{ps}通过反射来调用1：redirect,forward,...方法
	//    必须获取BaseController的方法对象
	//    首先，必须得到BaseController的字节码
	//    但是因为我现在this.getClass() --->UserController.class
	//    考虑一下如何获取BaseController.class
	private void doResponse(String methodName, String content, HttpServletRequest req, HttpServletResponse resp) {
		//{ps}得到父类的字节码：BaseController.class
		Class<?> supClass = this.getClass().getSuperclass();
		try {
			Method mtd = supClass.getDeclaredMethod(methodName, String.class,HttpServletRequest.class
					,HttpServletResponse.class);
			mtd.setAccessible(true);
			//this:传回子类控制器的引用
			mtd.invoke(this, content,req,resp);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException |IllegalArgumentException | 
				InvocationTargetException e) {
			Throwable ex = e.getCause();  //获取异常的源头
			ex.printStackTrace();		//打印异常信息
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
