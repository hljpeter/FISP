package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.MenuFunc;
/**
 * 
 * @author michelle.wang
 * 
 */
@Repository
public class MenuFuncRepositoryImpl implements MenuFuncRepository{

	@Override
	public MenuFunc query(MenuFunc menuFunc) {
		return queryDAO.executeForObject(Table.MENUFUNC  + "."
				+ SQLMap.SELECT_BYKEY, menuFunc, MenuFunc.class);
	}
	
	/**
	 * 查询菜单列表
	 * @param menuFunc
	 * @return
	 */
	@Override
	public List<MenuFunc> queryList(MenuFunc menuFunc) {
		return queryDAO.executeForObjectList(Table.MENUFUNC + "." + SQLMap.SELECT_LIST, menuFunc);
	}
	
	@Resource
	private QueryDAO queryDAO;

}
