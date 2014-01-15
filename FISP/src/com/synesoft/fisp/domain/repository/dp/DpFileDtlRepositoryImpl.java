package com.synesoft.fisp.domain.repository.dp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpFileDtl;

/**
 * 文件定义明细RepositoryImpl
 * @date 2013-11-13
 * @author yyw
 *
 */
@Repository
public class DpFileDtlRepositoryImpl implements DpFileDtlRepository {

	private static final LogUtil log = new LogUtil(DpFileDtlRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#queryList(java.lang.String)
	 */
	@Override
	public List<DpFileDtl> queryList(String fileId) {
		log.info("[queryList] - query all records");
		return queryDAO.executeForObjectList(Table.DP_FILE_DTL + "." + SQLMap.SELECT_QUERY_LIST, fileId);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#query(java.lang.String)
	 */
	@Override
	public DpFileDtl query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_FILE_DTL + "." + SQLMap.SELECT_BYKEY, id, DpFileDtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#insert(com.synesoft.fisp.domain.model.DpFileDtl)
	 */
	@Override
	public int insert(DpFileDtl dpFileDtl) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_FILE_DTL + "." + SQLMap.INSERT, dpFileDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_FILE_DTL + "." + SQLMap.DELETE_BYKEY, id);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#queryNotUsedList(java.lang.String)
	 */
	@Override
	public List<DpFileDtl> queryNotUsedList(String fileId) {
		log.info("[queryNotUsedList] - query not used records for requirements");
		return queryDAO.executeForObjectList(Table.DP_FILE_DTL + "." + SQLMap.SELECT_LIST_FORM, fileId);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository#queryMap(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryMap(String fileId) {
		log.info("[queryMap] - query all records for requirements");
		return queryDAO.executeForMapList(Table.DP_FILE_DTL + "." + SQLMap.SELECT_MAP, fileId);
	}

}
