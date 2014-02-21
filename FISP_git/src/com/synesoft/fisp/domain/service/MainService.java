package com.synesoft.fisp.domain.service;

import java.util.List;

import com.synesoft.fisp.domain.model.MainParam;

/**
 * 首页代办事项
 * @author yyw
 */
public interface MainService {

	/**
	 * 查询一般代办事项
	 * @return
	 */
	public List<MainParam> getGeneralTODO();
	
	/**
	 * 查询重要代办事项
	 * @return
	 */
	public List<MainParam> getImportantTODO();
}
