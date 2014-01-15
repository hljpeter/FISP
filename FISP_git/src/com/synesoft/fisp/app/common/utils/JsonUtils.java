package com.synesoft.fisp.app.common.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>上海恩梯梯数据晋恒软件有限公司</p>
 * @author CJUN
 * @version 1.0
 */
public class JsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	/**
	 * 将一个实体类对象转换成Json数据格式
	 * 
	 * @param bean
	 *            需要转换的实体类对象
	 * @param flag 
	 *            转换后的json是否要求有flag前缀
	 * @return 转换后的Json格式字符串
	 */
	public static String beanToJson(Object bean,String... flag) throws IntrospectionException,Exception {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					try {
						String tmpName = "";
						if(flag.length > 0){
							tmpName = flag[0]+"."+props[i].getName();
						}else{
							tmpName = props[i].getName();
						}
						String name = objectToJson(tmpName);
						String value = objectToJson(props[i].getReadMethod()
								.invoke(bean));
						json.append(name);
						json.append(":");
						json.append(value);
						json.append(",");
					} catch (Exception e) {
					}
				}
				json.setCharAt(json.length() - 1, '}');
			} else {
				json.append("}");
			}
		} catch (IntrospectionException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception ex){
			logger.error(ex.getMessage());
			throw ex;
		}
		return json.toString();
	}
	
	

	/**
	 * 将一个List对象转换成Json数据格式返回
	 * 
	 * @param list
	 *            需要进行转换的List对象
	 * @return 转换后的Json数据格式字符串
	 * @throws IntrospectionException 
	 */
	public static String listToJson(List<?> list) throws IntrospectionException,Exception {
		StringBuilder json = new StringBuilder();
		try{
			json.append("[");
			if (list != null && list.size() > 0) {
				for (Object obj : list) {
					json.append(objectToJson(obj));
					json.append(",");
				}
				json.setCharAt(json.length() - 1, ']');
			} else {
				json.append("]");
			}
		}catch(IntrospectionException e){
			logger.error(e.getMessage());
			throw e;
		}
		return json.toString();
	}

	/**
	 * 将一个对象数组转换成Json数据格式返回
	 * 
	 * @param array
	 *            需要进行转换的数组对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String arrayToJson(Object[] array) throws Exception {
		StringBuilder json = new StringBuilder();
		try{
			json.append("[");
			if (array != null && array.length > 0) {
				for (Object obj : array) {
					json.append(objectToJson(obj));
					json.append(",");
				}
				json.setCharAt(json.length() - 1, ']');
			} else {
				json.append("]");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return json.toString();
	}

	/**
	 * 将一个Map对象转换成Json数据格式返回
	 * 
	 * @param map
	 *            需要进行转换的Map对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String mapToJson(Map<?, ?> map) throws Exception {
		StringBuilder json = new StringBuilder();
		try{
			json.append("{");
			if (map != null && map.size() > 0) {
				for (Object key : map.keySet()) {
					json.append(objectToJson(key));
					json.append(":");
					json.append(objectToJson(map.get(key)));
					json.append(",");
				}
				json.setCharAt(json.length() - 1, '}');
			} else {
				json.append("}");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return json.toString();
	}

	/**
	 * 将一个Set对象转换成Json数据格式返回
	 * 
	 * @param set
	 *            需要进行转换的Set对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String setToJson(Set<?> set) throws Exception {
		StringBuilder json = new StringBuilder();
		try{
			json.append("[");
			if (set != null && set.size() > 0) {
				for (Object obj : set) {
					json.append(objectToJson(obj));
					json.append(",");
				}
				json.setCharAt(json.length() - 1, ']');
			} else {
				json.append("]");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return json.toString();
	}

	private static String numberToJson(Number number) {
		return number.toString();
	}

	private static String booleanToJson(Boolean bool) {
		return bool.toString();
	}

	private static String nullToJson() {
		return "";
	}

	/**
	 * 将一个字符串（包含特殊的字符）转换为JSON格式的字符串
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private static String stringToJson(String s) throws Exception {
		if (s == null) {
			return nullToJson();
		}
		StringBuilder sb = new StringBuilder();
		try{
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				switch (ch) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '/':
					sb.append("\\/");
					break;
				default:
					if (ch >= '\u0000' && ch <= '\u001F') {
						String ss = Integer.toHexString(ch);
						sb.append("\\u");
						for (int k = 0; k < 4 - ss.length(); k++) {
							sb.append('0');
						}
						sb.append(ss.toUpperCase());
					} else {
						sb.append(ch);
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return sb.toString();
	}

	/**
	 * 将一个java对象转换为JSON格式字符串
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private static String objectToJson(Object obj) throws Exception {
		StringBuilder json = new StringBuilder();
		try{
			if (obj == null) {
				json.append("\"\"");
			} else if (obj instanceof Number) {
				json.append(numberToJson((Number) obj));
			} else if (obj instanceof Boolean) {
				json.append(booleanToJson((Boolean) obj));
			} else if (obj instanceof String) {
				json.append("\"").append(stringToJson(obj.toString())).append("\"");
			} else if (obj instanceof Object[]) {
				json.append(arrayToJson((Object[]) obj));
			} else if (obj instanceof List) {
				json.append(listToJson((List<?>) obj));
			} else if (obj instanceof Map) {
				json.append(mapToJson((Map<?, ?>) obj));
			} else if (obj instanceof Set) {
				json.append(setToJson((Set<?>) obj));
			} else {
				json.append(beanToJson(obj));
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return json.toString();
	}

	// ============================================================================================

	/**
	 * 将Json格式的字符串转换成指定的对象返回
	 * 
	 * @param jsonString
	 *            Json格式的字符串
	 * @param pojoCalss
	 *            转换后的对象类型
	 * @return 转换后的对象
	 */
	public static Object json2Object(String jsonString, Class pojoCalss) throws Exception {
		Object pojo;
		try{
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			pojo = JSONObject.toBean(jsonObject, pojoCalss);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return pojo;
	}

	/**
	 * 将Json格式的字符串转换成Map<String,Object>对象返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Map<String,Object>对象
	 */
	public static Map<String, Object> json2Map(String jsonString) throws Exception {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		try{
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			Iterator keyIter = jsonObject.keys();
			String key;
			Object value;
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return valueMap;
	}

	/**
	 * 将Json格式的字符串转换成对象数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的对象数组
	 */
	public static Object[] json2ObjectArray(String jsonString) throws Exception {
		Object[] obj ;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			obj = jsonArray.toArray();
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return obj;
	}

	/**
	 * 将Json格式的字符串转换成指定对象组成的List返回
	 * 
	 * @param jsonString
	 *            Json格式的字符串
	 * @param pojoClass
	 *            转换后的List中对象类型
	 * @return 转换后的List对象
	 */
	public static List json2List(String jsonString, Class pojoClass) throws Exception {
		List list = new ArrayList();
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			JSONObject jsonObject;
			Object pojoValue;
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				pojoValue = JSONObject.toBean(jsonObject, pojoClass);
				list.add(pojoValue);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return list;
	}

	/**
	 * 将Json格式的字符串转换成字符串数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的字符串数组
	 */
	public static String[] json2StringArray(String jsonString) throws Exception {
		String[] stringArray;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			stringArray = new String[jsonArray.size()];
			for (int i = 0; i < jsonArray.size(); i++) {
				stringArray[i] = jsonArray.getString(i);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return stringArray;
	}

	/**
	 * 将Json格式的字符串转换成Long数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Long数组
	 */
	public static Long[] json2LongArray(String jsonString) throws Exception {
		Long[] longArray ;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			longArray = new Long[jsonArray.size()];
			for (int i = 0; i < jsonArray.size(); i++) {
				longArray[i] = jsonArray.getLong(i);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return longArray;
	}

	/**
	 * 将Json格式的字符串转换成Integer数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Integer数组
	 */
	public static Integer[] json2IntegerArray(String jsonString) throws Exception {
		Integer[] integerArray ;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			integerArray = new Integer[jsonArray.size()];
			for (int i = 0; i < jsonArray.size(); i++) {
				integerArray[i] = jsonArray.getInt(i);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return integerArray;
	}

	/**
	 * 将Json格式的字符串转换成日期数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @param DataFormat
	 *            返回的日期格式
	 * @return 转换后的日期数组
	 */
	public static Date[] json2DateArray(String jsonString, String DataFormat) throws Exception {
		Date[] dateArray ;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			dateArray = new Date[jsonArray.size()];
			String dateString;
			Date date;
			for (int i = 0; i < jsonArray.size(); i++) {
				dateString = jsonArray.getString(i);
				try {
					date = new SimpleDateFormat(DataFormat).parse(dateString);
					dateArray[i] = date;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return dateArray;
	}

	/**
	 * 将Json格式的字符串转换成Double数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Double数组
	 */
	public static Double[] json2DoubleArray(String jsonString) throws Exception {
		Double[] doubleArray ;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			doubleArray = new Double[jsonArray.size()];
			for (int i = 0; i < jsonArray.size(); i++) {
				doubleArray[i] = jsonArray.getDouble(i);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		return doubleArray;
	}

	// ============================================================================================

	public static void main(String[] args) {
		String strJson = "{'testObj':[{'name':'a','sex':'m'},{'name':'b','sex':'f'}]}";
		JSONObject jsonObject = JSONObject.fromObject(strJson);
		Iterator keyIter = jsonObject.keys();
		String key = (String) keyIter.next();
		JSONArray jarrayValue = (JSONArray) jsonObject.get(key);
		JSONObject jsonItemObject;
		for (int i = 0; i < jarrayValue.size(); i++) {
			jsonItemObject = jarrayValue.getJSONObject(i);
			System.out.println(jsonItemObject);
		}
		String strtest = "[rest][0][name]";
		strtest = strtest.substring(strtest.indexOf("[") + 1, strtest
				.indexOf("]"));
		System.out.println(strtest);
	}

}
