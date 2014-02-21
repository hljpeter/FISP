package com.synesoft.fisp.app.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyw
 *
 */
public class MainBizMapping {

	private final static String NOT_SELECT_TODO_FLAG = "F";
	private final static String SELECT_TODO_FLAG = "T";
	private static Map<String, String> menuMap;
	private static Map<String, String> menuMsgNoMap;
	
	static {
		menuMap = new HashMap<String, String>();
		menuMap.put("FTZ_Add_210101", SELECT_TODO_FLAG); // 单位存款录入
		menuMap.put("FTZ_Add_210102", SELECT_TODO_FLAG); // FTU内部活动-金融债券录入
		menuMap.put("FTZ_Add_210103", SELECT_TODO_FLAG); // FTU内部活动-中长期借款录入
		menuMap.put("FTZ_Add_210104", SELECT_TODO_FLAG); // 应付及暂收款
		menuMap.put("FTZ_Add_210105", SELECT_TODO_FLAG); // 卖出回购资产
		menuMap.put("FTZ_Add_210106", SELECT_TODO_FLAG); // 向中央银行借款
		menuMap.put("FTZ_Add_210107", SELECT_TODO_FLAG); // 同业往来
		menuMap.put("FTZ_Add_210108", SELECT_TODO_FLAG); // 系统内资金往来
		menuMap.put("FTZ_Add_210109", SELECT_TODO_FLAG); // 外汇买卖
		menuMap.put("FTZ_Add_210110", SELECT_TODO_FLAG); // FTU委托存款及委托投资基金
		menuMap.put("FTZ_Add_210111", SELECT_TODO_FLAG); // 代理金融机构委托贷款基金
		menuMap.put("FTZ_Add_210112", SELECT_TODO_FLAG); // 各项准备
		menuMap.put("FTZ_Add_210201", SELECT_TODO_FLAG); // 各项贷款
		menuMap.put("FTZ_Add_210202", SELECT_TODO_FLAG); // 有价证券
		menuMap.put("FTZ_Add_210203", SELECT_TODO_FLAG); // 股权及其他投资
		menuMap.put("FTZ_Add_210204", SELECT_TODO_FLAG); // 应收及预付款
		menuMap.put("FTZ_Add_210205", SELECT_TODO_FLAG); // 买入返售资产
		menuMap.put("FTZ_Add_210206", SELECT_TODO_FLAG); // 存放中央准备金存款
		menuMap.put("FTZ_Add_210207", SELECT_TODO_FLAG); // 存放中央银行特种存款
		menuMap.put("FTZ_Add_210208", SELECT_TODO_FLAG); // 缴存中央银行财政性存款
		menuMap.put("FTZ_Add_210209", SELECT_TODO_FLAG); // 同业往来(运用方)
		menuMap.put("FTZ_Add_210210", SELECT_TODO_FLAG); // 系统内资金往来
		menuMap.put("FTZ_Add_210211", SELECT_TODO_FLAG); // 库存现金
		menuMap.put("FTZ_Add_210212", SELECT_TODO_FLAG); // 外汇买卖
		menuMap.put("FTZ_Add_210301", SELECT_TODO_FLAG); // 代理发债
		menuMap.put("FTZ_Add_210302", SELECT_TODO_FLAG); // 应付信用证(进口开证)
		menuMap.put("FTZ_Add_210303", SELECT_TODO_FLAG); // 应付保函/备用证(保函/备用证开立)
		menuMap.put("FTZ_Add_210304", SELECT_TODO_FLAG); // 信用证保兑(进口应付信用证加保)
		menuMap.put("FTZ_Add_210305", SELECT_TODO_FLAG); // 应付银行承兑汇票
		menuMap.put("FTZ_Add_210306", SELECT_TODO_FLAG); // 应收信用证(出口交单)
		menuMap.put("FTZ_Add_210307", SELECT_TODO_FLAG); // 应收保函/备用证(保函通知,收到境外保函)
		menuMap.put("FTZ_Add_210308", SELECT_TODO_FLAG); // 信用证保兑(出口应收信用证加保)
		menuMap.put("FTZ_Add_210309", SELECT_TODO_FLAG); // 应收银行承兑汇票
		menuMap.put("FTZ_Add_210310", SELECT_TODO_FLAG); // 远期结售汇
		menuMap.put("FTZ_Add_210311", SELECT_TODO_FLAG); // 汇率掉期业务(远期未交割部分)
		menuMap.put("FTZ_Add_210401", SELECT_TODO_FLAG); // 表外理财
		menuMap.put("FTZ_Add_210501", SELECT_TODO_FLAG); // 账户信息录入
		menuMsgNoMap = new HashMap<String, String>();
		menuMsgNoMap.put("FTZ_Add_210101", "210101"); // 单位存款录入
		menuMsgNoMap.put("FTZ_Add_210102", "210102"); // FTU内部活动-金融债券录入
		menuMsgNoMap.put("FTZ_Add_210103", "210103"); // FTU内部活动-中长期借款录入
		menuMsgNoMap.put("FTZ_Add_210104", "210104"); // 应付及暂收款
		menuMsgNoMap.put("FTZ_Add_210105", "210105"); // 卖出回购资产
		menuMsgNoMap.put("FTZ_Add_210106", "210106"); // 向中央银行借款
		menuMsgNoMap.put("FTZ_Add_210107", "210107"); // 同业往来
		menuMsgNoMap.put("FTZ_Add_210108", "210108"); // 系统内资金往来
		menuMsgNoMap.put("FTZ_Add_210109", "210109"); // 外汇买卖
		menuMsgNoMap.put("FTZ_Add_210110", "210110"); // FTU委托存款及委托投资基金
		menuMsgNoMap.put("FTZ_Add_210111", "210111"); // 代理金融机构委托贷款基金
		menuMsgNoMap.put("FTZ_Add_210112", "210112"); // 各项准备
		menuMsgNoMap.put("FTZ_Add_210201", "210201"); // 各项贷款
		menuMsgNoMap.put("FTZ_Add_210202", "210202"); // 有价证券
		menuMsgNoMap.put("FTZ_Add_210203", "210203"); // 股权及其他投资
		menuMsgNoMap.put("FTZ_Add_210204", "210204"); // 应收及预付款
		menuMsgNoMap.put("FTZ_Add_210205", "210205"); // 买入返售资产
		menuMsgNoMap.put("FTZ_Add_210206", "210206"); // 存放中央准备金存款
		menuMsgNoMap.put("FTZ_Add_210207", "210207"); // 存放中央银行特种存款
		menuMsgNoMap.put("FTZ_Add_210208", "210208"); // 缴存中央银行财政性存款
		menuMsgNoMap.put("FTZ_Add_210209", "210209"); // 同业往来(运用方)
		menuMsgNoMap.put("FTZ_Add_210210", "210210"); // 系统内资金往来
		menuMsgNoMap.put("FTZ_Add_210211", "210211"); // 库存现金
		menuMsgNoMap.put("FTZ_Add_210212", "210212"); // 外汇买卖
		menuMsgNoMap.put("FTZ_Add_210301", "210301"); // 代理发债
		menuMsgNoMap.put("FTZ_Add_210302", "210302"); // 应付信用证(进口开证)
		menuMsgNoMap.put("FTZ_Add_210303", "210303"); // 应付保函/备用证(保函/备用证开立)
		menuMsgNoMap.put("FTZ_Add_210304", "210304"); // 信用证保兑(进口应付信用证加保)
		menuMsgNoMap.put("FTZ_Add_210305", "210305"); // 应付银行承兑汇票
		menuMsgNoMap.put("FTZ_Add_210306", "210306"); // 应收信用证(出口交单)
		menuMsgNoMap.put("FTZ_Add_210307", "210307"); // 应收保函/备用证(保函通知,收到境外保函)
		menuMsgNoMap.put("FTZ_Add_210308", "210308"); // 信用证保兑(出口应收信用证加保)
		menuMsgNoMap.put("FTZ_Add_210309", "210309"); // 应收银行承兑汇票
		menuMsgNoMap.put("FTZ_Add_210310", "210310"); // 远期结售汇
		menuMsgNoMap.put("FTZ_Add_210311", "210311"); // 汇率掉期业务(远期未交割部分)
		menuMsgNoMap.put("FTZ_Add_210401", "210401"); // 表外理财
		menuMsgNoMap.put("FTZ_Add_210501", "210501"); // 账户信息录入
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
	
	public static String getMsgNo(String menuId) {
		return menuMsgNoMap.get(menuId);
	}
	
}
