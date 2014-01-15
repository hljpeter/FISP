package com.synesoft.fisp.domain.repository.sm;


import java.util.List;

import com.synesoft.fisp.domain.model.MenuFunc;
/**
 * 
 * @author michelle.wang
 * 
 */

public interface MenuFuncRepository {

	/**
	 * 查询菜单信息临时表中信息
	 * 
	 * @param userInfTmp
	 * @return
	 */
	public MenuFunc query(MenuFunc menuFunc);
	
	/**
	 * 查询菜单列表
	 * @param menuFunc
	 * @return
	 */
	public List<MenuFunc> queryList(MenuFunc menuFunc);
	
}
