package com.synesoft.fisp.domain.repository;

import java.util.List;

import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.vo.DBTableInfoVO;

public interface DBTableInfoRepository {
	

	/**
	 * 查询一条记录，根据主键
	 * @param name	主键（TABLE_NAME）
	 * @return
	 * 		DBTableInfo	一条记录的对象
	 */
	public DBTableInfo query(String name);
	
	/**
	 * 查询数据库表信息
	 * @param info	条件对象	
	 * @return
	 * 		List<DBTableInfo>	结果List
	 */
	public List<DBTableInfo> queryTableList(DBTableInfo info);

	/**
	 * 查询一张表下所有列信息，根据表名
	 * @param name	表名
	 * @return
	 * 		List<DBTableInfoVO>	结果List
	 */
	public List<DBTableInfoVO> queryColumnList(String name);
}
