package com.synesoft.fisp.app.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author zhongHubo
 * @date 2013年11月14日 19:21:34
 * @version 1.0
 * @Description 日志打印
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class PrintTransUtil {

	/** 操作日志对象 */
	public static final LogUtil log = new LogUtil(PrintTransUtil.class);

	/** 常用数据类型 **/
	public static final String type_base = String.class + "," + 
											boolean.class + "," + Boolean.class + "," + 
											double.class + "," + Double.class + "," + 
											float.class + "," + Float.class + "," + 
											byte.class + "," + Byte.class + "," + 
											short.class + "," + Short.class + "," + 
											int.class + "," + Integer.class + "," + 
											long.class + "," + Long.class + "," + 
											char.class + "," + Character.class + ",";

	/** List类型 **/
	public static final String type_list = List.class + "," + ArrayList.class + "," +
											LinkedList.class + "," + Vector.class + ",";

	/** byte数组类型 **/
	public static final String type_array_byte = byte[].class + "," + Byte[].class + ",";

	/** String数组类型 **/
	public static final String type_array_String = String[].class + ",";

	/** 实体类数组(被包含关系，所以不能加间隔符",") **/
	public static final String type_entitys = "Class<?> [L";

	/** 定义生产环境不需要打印的文件类型 **/
	private static final String vouTypeField = "vouFile、";

	
	/**
	 * 打印交易请求报文
	 * 
	 * @param transno
	 *            交易码
	 * @param toServer	服务端简称
	 * @param obj
	 *            暂时支持以下类型：String,boolean,double,float,byte,short,int,
	 *            long,char,List,String[],byte[],自定义实体类类型
	 */
	public static void printReqMsg(String transno, String toServer, Object obj) {
		// 打印空行
		log.info("");
		PrintTransUtil.printSeparateLine("send " + transno + " to " + toServer, "begin");
		PrintTransUtil.printLog(obj);
		PrintTransUtil.printSeparateLine("send " + transno + " to " + toServer, "end");
	}

	/**
	 * 打印交易返回报文
	 * 
	 * @param transno
	 *            交易码
	 * @param toServer	服务端简称
	 * @param obj
	 *            暂时支持以下类型：String,boolean,double,float,byte,short,int,
	 *            long,char,List,String[],byte[],自定义实体类类型
	 */
	public static void printRetMsg(String transno, String toServer, Object obj) {
		// 打印空行
		log.info("");
		PrintTransUtil.printSeparateLine(toServer + " return " + transno, "begin");
		PrintTransUtil.printLog(obj);
		PrintTransUtil.printSeparateLine(toServer + " return " + transno, "end");
	}

	/**
	 * 打印Object数组中的对象
	 * 
	 * @param obj
	 * @param obj2
	 */
	@SuppressWarnings("unused")
	private static void printArrays(Object[] objs, Object obj2) {
		/* 获得对象的类型 */
		Class<?> classType = obj2.getClass();

		if (objs.length > 0) {
			/* 循环打印 objs实体内容 */
			for (int i = 0; i < objs.length; i++) {
				try {
					/* 通过默认构造方法创建一个新的对象 */
					Object objectCopy = classType
							.getConstructor(new Class[] {}).newInstance(
									new Object[] {});
					objectCopy = objs[i];
					printLog(objectCopy);
				} catch (IllegalArgumentException e) {
					log.error(e, e);
				} catch (SecurityException e) {
					log.error(e, e);
				} catch (InstantiationException e) {
					log.error(e, e);
				} catch (IllegalAccessException e) {
					log.error(e, e);
				} catch (InvocationTargetException e) {
					log.error(e, e);
				} catch (NoSuchMethodException e) {
					log.error(e, e);
				}
			}
		} else {
			log.info("Object[]<" + classType.getName() + "> is null");
		}

	}

	/**
	 * 打印byte数组
	 * 
	 * @param objs
	 *            数组对象
	 * @param parentFieldName
	 *            上一级属性名称
	 */
	public static void printArrays(String[] objs, String parentFieldName) {
		String strs = null;
		if (objs.length > 0) {
			strs = "";

			/* 循环打印 objs实体内容 */
			for (int i = 0; i < objs.length; i++) {
				try {
					strs += "[" + objs[i] + "] ";
				} catch (IllegalArgumentException e) {
					log.error(e, e);
				} catch (SecurityException e) {
					log.error(e, e);
				}
			}
			// 剪掉最后的一个空格
			if (StringUtil.isNotTrimEmpty(strs)) {
				strs = strs.substring(0, strs.length() - 1);
			}
		}
		log.info(parentFieldName + ": [" + strs + "]");
	}

	/**
	 * 打印List集合中的对象值
	 * 
	 * @param list
	 * @param obj
	 * @param parentFieldName
	 *            上一级属性名称
	 */
	private static void printList(List<?> list, Object obj, String parentFieldName) {
		/* 获得对象的类型 */
		Class<?> classType = obj.getClass();

		if (list.size() > 0) {
			/* 循环打印 obj实体内容 */
			for (int i = 0; i < list.size(); i++) {
				try {
					/* 通过默认构造方法创建一个新的对象 */
					Object objectCopy = classType
							.getConstructor(new Class[] {}).newInstance(
									new Object[] {});
					objectCopy = list.get(i);
					printLog(objectCopy, parentFieldName + "[" + i + "]");
				} catch (IllegalArgumentException e) {
					log.error(e, e);
				} catch (SecurityException e) {
					log.error(e, e);
				} catch (InstantiationException e) {
					log.error(e, e);
				} catch (IllegalAccessException e) {
					log.error(e, e);
				} catch (InvocationTargetException e) {
					log.error(e, e);
				} catch (NoSuchMethodException e) {
					log.error(e, e);
				}
			}
		} else {
			log.info("List<" + classType.getName() + "> is null");
		}

	}
	
	/**
	 * 打印实体类数组的对象值
	 * @param <T>
	 * 				类型
	 * @param obj
	 * 				需要处理的数组
	 * @param parentFieldName
	 *            上一级属性名称
	 */
	@SuppressWarnings("unchecked")
	private static <T> void printArrays(Class<?> T, Object object, String parentFieldName) {
		
		T[] array_t = (T[])object;

		if (array_t.length > 0) {
			/* 循环打印 obj实体内容 */
			for (int i = 0; i < array_t.length; i++) {
				try {
					/* 通过默认构造方法创建一个新的对象 */
					T t = array_t.clone()[i];
					printLog(t, parentFieldName + "[" + i + "]");
				} catch (IllegalArgumentException e) {
					log.error(e, e);
				} catch (SecurityException e) {
					log.error(e, e);
				}
			}
		} else {
			log.info(T.getClass().getName() + " is null");
		}

	}

	/**
	 * 打印交易报文
	 * 
	 * @param obj
	 *            待打印的对象
	 */
	public static void printLog(Object obj) {
		try {
			getAllFieldAndValue(obj, 0);
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	/**
	 * 打印交易报文
	 * 
	 * @param obj
	 *            待打印的对象
	 * @param parentFieldName
	 *            上一级属性名称
	 */
	public static void printLog(Object obj, String parentFieldName) {
		try {
			getAllFieldAndValue(obj, 0, parentFieldName);
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	/**
	 * 打印出object的Field和对应的getXXX
	 * 
	 * @param object
	 *            待打印日志的对象
	 * @param count
	 *            当前递归的次数(用于控制递归循环的次数)
	 * @throws Exception
	 */
	private static void getAllFieldAndValue(Object object, int count)
			throws Exception {
		PrintTransUtil.getAllFieldAndValue(object, count, "");
	}

	/**
	 * 打印出object的Field和对应的getXXX
	 * 
	 * @param object
	 *            待打印日志的对象
	 * @param count
	 *            当前递归的次数(用于控制递归循环的次数)
	 * @param parentFieldName
	 *            上一级属性名称
	 * @throws Exception
	 */
	private static void getAllFieldAndValue(Object object, int count,
			String parentFieldName) throws Exception {

		// 增加判断，防止递归死循环
		if (count >= 5) {
			return;
		} else {
			count++;
		}

		/* 获得对象的类型 */
		Class<?> classType = object.getClass();
		
		// 如果是PrintTransConst.type_list类型，做特殊处理
		if (PrintTransUtil.type_list.contains(classType.getName() + ",")) {
			String printFieldName = StringUtil.isNotTrimEmpty(parentFieldName) ? parentFieldName : "list";
			PrintTransUtil.printList((List<?>) object, new Object(), printFieldName);
		} else {

			/* 获得对象的所有属性 */
			Field fields[] = classType.getDeclaredFields();
	
			for (int i = 0; i < fields.length; i++) {
	
				Field field = fields[i];
				/* 获得属性名 */
				String fieldName = field.getName();
				String printFieldName = StringUtil.isNotTrimEmpty(parentFieldName) ? parentFieldName
						+ "." + fieldName
						: fieldName;
				// System.out.println(fieldName);
				// 如果属性名为:__equalsCalc,__hashCodeCalc,typeDesc,serialVersionUID不打印
				if (fieldName.equals("__equalsCalc")
						|| fieldName.equals("__hashCodeCalc")) {
					continue;
				} else if (fieldName.equals("typeDesc")
						|| fieldName.equals("serialVersionUID")) {
					continue;
				} else if (fieldName.equals("MIN_VALUE")
						|| fieldName.equals("MAX_VALUE")) {
					continue;
				} else if (fieldName.equals("TYPE") || fieldName.equals("SIZE")) {
					continue;
				}
	
				// 获取属性的类型
				String fieldType = field.getType().toString();
				// fieldType的临时变量，为了处理特殊情况而设置，在fieldType的基础上增加间隔符","
				// 案例：int类型的字段fieldType为int，被包含在接口类型(interface)字段的fieldType中
				String temp_fieldType = fieldType + ",";
				
				// 判断属于哪种类型
				if (PrintTransUtil.type_array_byte.contains(temp_fieldType)) {
					// byte数组类型
					Object value = getFieldValue(object, classType, fieldName);
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						// 如果当前字段是定义的文件类型，则直接打印变量的内存地址，因为文件比较大，占用资源较多
						if(PrintTransUtil.vouTypeField.contains(fieldName)) {
							log.info(printFieldName + ": [" + value + "]");
						} else {
							log.info(printFieldName + ": ["
									+ new String((byte[])value, "UTF-8") + "]");
						}
					}
				} else if (PrintTransUtil.type_array_String.contains(temp_fieldType)) {
					// String数组类型
					Object value = getFieldValue(object, classType, fieldName);
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						PrintTransUtil.printArrays((String[]) value,
								printFieldName);
					}
				} else if (PrintTransUtil.type_base.contains(temp_fieldType)) {
					// 打印常见类型的属性值
					Object value = getFieldValue(object, classType, fieldName);
					/* 打印属性名和属性值 */
					
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						// 如果当前字段是定义的文件类型，则直接打印变量的内存地址，因为文件比较大，占用资源较多
						if(PrintTransUtil.vouTypeField.contains(fieldName)) {
							log.info(printFieldName + ": [" + value.toString().getBytes() + "]");
						} else {
							log.info(printFieldName + ": [" + value + "]");
						}
					}
				} else if (fieldType.contains(PrintTransUtil.type_entitys)) {
					// 实体类数组
					Object value = getFieldValue(object, classType, fieldName);
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						// 获取当前数组的实体类全名
						String className = fieldType.replace(PrintTransUtil.type_entitys, "").replaceAll(";", "");
						// 创建Class对象
						Class<?> c = Class.forName(className);
						// 得到对应的实体类的实例
						Object t = c.newInstance();
						
						// 打印数组
						PrintTransUtil.printArrays(t.getClass(), value, printFieldName);
						
					}
				} else if (PrintTransUtil.type_list.contains(temp_fieldType)) {
					// List类型
					Object value = getFieldValue(object, classType, fieldName);
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						PrintTransUtil.printList((List<?>) value, new Object(),
								printFieldName);
					}
				} else {
					// 其它类型(当作自定义实体类的类型)
					Object value = getFieldValue(object, classType, fieldName);
					if (value == null) {
						log.info(printFieldName + ": [" + null + "]");
					} else {
						PrintTransUtil.getAllFieldAndValue(value, count,
								printFieldName);
					}
				}
			}
		}
	}

	/**
	 * 获取属性的值
	 * 
	 * @param object
	 *            当前属性的所属对象
	 * @param classType
	 *            当前属性所属对象的Class
	 * @param fieldName
	 *            属性名
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static Object getFieldValue(Object object, Class<?> classType,
			String fieldName) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Object value = null;
		try {
			
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			
			/* 获得和属性对应的getXXX()方法的名字 */
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			
			/* 获得和属性对应的getXXX()方法 */
			Method getMethod = classType.getMethod(getMethodName, new Class[] {});
			
			/* 调用原对象的getXXX()方法 */
			value = getMethod.invoke(object, new Object[] {});
		} catch (Exception e) {
			
		}

		return value;
	}

	/**
	 * 打印交易分隔行
	 * 
	 * @param transno
	 */
	public static void printSeparateLine(String transno, String status) {
		// 交易分隔行
		log.info("===============\t" + transno + " " + status + "\t============");
	}

}
