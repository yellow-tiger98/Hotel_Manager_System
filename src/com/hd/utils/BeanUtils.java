package com.hd.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
	/**
	 * ��������parseBean
	 * ��������1��Map<String,String[]>
	 * ��������2��Class
	 * ����Ӧ��ֵ��[1]req.getParameterMap() [2]JavaBean�ֽ������
	 * �������ͣ�����T
	 * ����ֵӦΪ��JavaBean����
	 * ���ã���ǰ�˴������û���Ϣͨ������ķ�ʽ��װ����������Ӧ��JavaBean����
	 */
	public static <T> T parseBean(Map<String,String[]> map, Class<T> clazz)
			throws IllegalAccessException {
		//{1} ���� clazz �ı�ʾ����Ķ���, ���� clazz �ṩ�޲ι�������
		T t = null;
		try {
			//{ps} ���� JavaBean ����
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalAccessException("��������ʧ��(ȱʧ�޲ι�����)");
		}
		//{2} �������һ�� Map
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			//{ps} KEY ��Ϊ JavaBean ������(�ֶ���)
			//     key ���ܵ����: username, password, weight..
			//{ps} value ��Ϊ JavaBean ����ֵ(�ֶ�ֵ)
			String value = map.get( key )[0]; //��ȡ����0��λ��
			//��ʽ: setProperty( ��������, �ֶ���, �ֶ�ֵ  );
			setProperty( t, key, value );
		}
		return t;
	}
	
	/**
	 * ��������setValue
	 * ��������1��Filed
	 * ��������2��Object
	 * ��������3��Object
	 * ����Ӧ��ֵ��[1]JavaBean����������ֶζ��� [2]��������JavaBean���� [3]�ֶ�ֵ
	 * ����ֵ����
	 * ���ã�Ϊjavabean������ֶ�����ֵ��������ֵ���͵��жϣ�����ǰ�˷����������ݽ�����Ӧ������ת��
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
			throw new IllegalAccessException("���� "+ field.getName() +" ����ʧ��");
		}
	}
	
	/**
	 * ��������setProperty
	 * ��������1��Object
	 * ��������2��String
	 * ��������3��Object
	 * ����Ӧ��ֵ��[1]��������JavaBean���� [2]ǰ�˴������ֶ���[3]ǰ�˴������ֶ�ֵ
	 * ����ֵ����
	 * ���ã�ͨ�������õ�����������������ĺ��ֶ�����Ӧ�����ԣ���û�д��ֶ��������ʾ�����о͵�������ֵ����Ϊ�ֶ����ö�Ӧֵ
	 */
	public static void setProperty(Object bean, String fldName, Object value){
		Class<?> clazz = bean.getClass();
		Field field = null;
		try {
			field = clazz.getDeclaredField( fldName );    
			field.setAccessible( true );//��ȡȫ��Ȩ�޵�����
			setValue( field, bean, value );
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("{BeanUtils} [%s] ���ֶβ�����\n", fldName);
		} catch( IllegalArgumentException | IllegalAccessException e){
			e.printStackTrace();
		}
	}

}

