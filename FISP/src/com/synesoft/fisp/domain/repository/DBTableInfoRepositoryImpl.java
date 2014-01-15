package com.synesoft.fisp.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.vo.DBTableInfoVO;

@Repository
public class DBTableInfoRepositoryImpl implements DBTableInfoRepository {

	private static final LogUtil log = new LogUtil(DBTableInfoRepositoryImpl.class);
	
	@Resource
	private QueryDAO queryDAO;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.DBTableInfoRepository#query(java.lang.String)
	 */
	@Override
	public DBTableInfo query(String name) {
		log.info("[query] - query one record");
		return queryDAO.executeForObject(Table.DB_TABLE_INFO + "." + SQLMap.SELECT_BYKEY, name, DBTableInfo.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.DBTableInfoRepository#queryTableList(com.synesoft.fisp.domain.model.DBTableInfo)
	 */
	@Override
	public List<DBTableInfo> queryTableList(DBTableInfo info) {
		log.info("[queryTableList] - query records");
		return queryDAO.executeForObjectList(Table.DB_TABLE_INFO + "." + SQLMap.SELECT_QUERY_LIST, info);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.DBTableInfoRepository#queryColumnList(java.lang.String)
	 */
	@Override
	public List<DBTableInfoVO> queryColumnList(String name) {
		log.info("[queryColumnList] - query records ");
		return queryDAO.executeForObjectList(Table.DB_TABLE_INFO + "." + SQLMap.SELECT_LIST, name);
	}
}

