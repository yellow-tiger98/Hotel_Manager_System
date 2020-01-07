package com.hd.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
	/**
	 * 方法名：parseBean
	 * 参数类型1：Map<String,String[]>
	 * 参数类型2：Class
	 * 参数应传值：[1]req.getParameterMap() [2]JavaBean字节码对象
	 * 返回类型：泛型T
	 * 返回值应为：JavaBean对象
	 * 作用：将前端传来的用户信息通过反射的方式封装起来创建对应的JavaBean对象
	 */
	public static <T> T parseBean(Map<String,String[]> map, Class<T> clazz)
			throws IllegalAccessException {
		//{1} 创建 clazz 的表示的类的对象, 必须 clazz 提供无参构造器。
		T t = null;
		try {
			//{ps} 创建 JavaBean 对象
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalAccessException("创建对象失败(缺失无参构造器)");
		}
		//{2} 迭代这个一个 Map
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			//{ps} KEY 即为 JavaBean 属性名(字段名)
			//     key 可能的情况: username, password, weight..
			//{ps} value 即为 JavaBean 属性值(字段值)
			String value = map.get( key )[0]; //获取它的0号位。
			//格式: setProperty( 主调对象, 字段名, 字段值  );
			setProperty( t, key, value );
		}
		return t;
	}
	
	/**
	 * 方法名：setValue
	 * 参数类型1：Filed
	 * 参数类型2：Object
	 * 参数类型3：Object
	 * 参数应传值：[1]JavaBean对象的属性字段对象 [2]主调对象，JavaBean对象 [3]字段值
	 * 返回值：无
	 * 作用：为javabean对象的字段设置值，并进行值类型的判断，将从前端发过来的数据进行相应的类型转换
	 */
	public static void setValue(Field field, Object bean, Object value) 
			throws IllegalAccessException {
		Class<?> valType = field.getType();
		String cVal = value.toString();
		try{
			if( value!=null ){ 
				if( valType==String.class ){
					field.set(bean, value);
				}else if( valType==int.class || valType==Integer.class ){
					field.set(bean, Integer.valueOf( cVal ));
				}else if( valType==long.class || valType==Long.class ){
					field.set(bean, Long.valueOf( cVal ));
				}else if( valType==double.class || valType==Double.class ){
					field.set(bean, Double.valueOf( cVal ));
				}else if( valType==float.class || valType==Float.class ){
					field.set(bean, Float.valueOf( cVal ));
				}
			}
		}catch(IllegalArgumentException | IllegalAccessException e){
			throw new IllegalAccessException("设置 "+ field.getName() +" 属性失败");
		}
	}
	
	/**
	 * 方法名：setProperty
	 * 参数类型1：Object
	 * 参数类型2：String
	 * 参数类型3：Object
	 * 参数应传值：[1]主调对象，JavaBean对象 [2]前端传来的字段名[3]前端传来的字段值
	 * 返回值：无
	 * 作用：通过反射拿到此主调对象所在类的和字段名对应的属性，若没有此字段则进行提示，若有就调用设置值方法为字段设置对应值
	 */
	public static void setProperty(Object bean, String fldName, Object value){
		Class<?> clazz = bean.getClass();
		Field field = null;
		try {
			field = clazz.getDeclaredField( fldName );    
			field.setAccessible( true );//获取全部权限的属性
			setValue( field, bean, value );
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("{BeanUtils} [%s] 此字段不存在\n", fldName);
		} catch( IllegalArgumentException | IllegalAccessException e){
			e.printStackTrace();
		}
	}

}

