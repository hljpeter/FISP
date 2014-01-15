package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

/**
 * @author zhongHubo
 * @date 2013年7月22日 13:55:22
 * @version 1.0
 * @Description 菜单Form
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class MenuForm implements Serializable {

	private static final long serialVersionUID = 851689038717868701L;

	/** 菜单Id **/
	private String menuId;

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}
	
	
}
