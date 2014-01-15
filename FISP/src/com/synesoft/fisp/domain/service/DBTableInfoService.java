package com.synesoft.fisp.domain.service;

import java.util.List;

import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.vo.DBTableInfoVO;

/**
 * 数据库表信息Service
 * @date 2013-11-15
 * @author yyw
 *
 */
public interface DBTableInfoService {

	/**
	 * 查询数据库表信息
	 * @return
	 * 		List<DBTableInfo>	结果List
	 */
	public List<DBTableInfo> transQueryTableList();

	/**
	 * 查询数据库表信息
	 * @param tableName	表名
	 * @return
	 * 		DBTableInfo	结果对象
	 */
	public DBTableInfo transQueryTable(String tableName);
	
	/**
	 * 查询数据库表列信息，根据表名
	 * @param tableName	表名
	 * @return
	 * 		List<DBTableInfoVO>	结果List
	 */
	public List<DBTableInfoVO> transQueryTableColList(String tableName);
}
