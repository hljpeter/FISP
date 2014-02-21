package com.synesoft.fisp.app.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyw
 *
 */
public class MainSysMapping {

	private final static String NOT_SELECT_TODO_FLAG = "F";
	private final static String SELECT_TODO_FLAG = "T";
	private static Map<String, String> menuMap;
	private static Map<String, String> menuSqlMap;
	
	static {
		menuMap = new HashMap<String, String>();
		menuMap.put("SM01_02", SELECT_TODO_FLAG); // 机构
		menuMap.put("SM02_02", SELECT_TODO_FLAG); // 角色
		menuMap.put("SM_Uchk_Qry", SELECT_TODO_FLAG); // 用户
		menuSqlMap = new HashMap<String, String>();
		menuSqlMap.put("SM01_02", "selectOrgInfo");
		menuSqlMap.put("SM02_02", "selectRoleInfo");
		menuSqlMap.put("SM_Uchk_Qry", "selectUserInfo");
	}
	
	public static void setNotSelectTODO(String menuId) {
		menuMap.put(menuId, NOT_SELECT_TODO_FLAG);
	}

	public static void setSelectTODO(String menuId) {
		menuMap.put(menuId, SELECT_TODO_FLAG);
	}
	
	public static String isSelectTODO(String menuId) {
		if (menuMap.containsKey(menuId)) {
			return menuMap.get(menuId);
		} else {
			return NOT_SELECT_TODO_FLAG;
		}
	}
	
	public static boolean checkFlag(String flag) {
		return null == flag? false: SELECT_TODO_FLAG.equals(flag);
	}
	
	public static String getSqlId(String menuId) {
		return menuSqlMap.get(menuId);
	}
}
