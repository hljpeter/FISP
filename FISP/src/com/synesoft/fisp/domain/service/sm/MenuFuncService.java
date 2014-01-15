package com.synesoft.fisp.domain.service.sm;


/**
 * @author zhongHubo
 * @date 2013年7月22日 14:16:29
 * @version 1.0
 * @Description 菜单Service
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface MenuFuncService {
	
	/**
	 * 查询菜单列表
	 * @param menuFunc
	 * @return
	 */
	public String queryList(String menuId);
	
	/**
	 * 查询菜单列表正式表
	 * @param menuFunc
	 * @return
	 */
	public String makeMenuList(String menuId);
	/**
	 * 查询菜单列表零时表
	 * @param menuFunc
	 * @return
	 */
	public String makeMenuListTmp(String menuId);

	
}
