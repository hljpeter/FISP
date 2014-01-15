package com.synesoft.fisp.domain.repository.dp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义明细RepositoryImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Repository
public class DpTableDtlRepositoryImpl implements DpTableDtlRepository {

	private static final LogUtil log = new LogUtil(DpTableDtlRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<DpTableDtl> pageH;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#queryList(java.lang.String)
	 */
	@Override
	public List<DpTableDtl> queryList(String tableId) {
		log.info("[queryList] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_QUERY_LIST, tableId);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#query(java.lang.String)
	 */
	@Override
	public DpTableDtl query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_BYKEY, id, DpTableDtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#insert(com.synesoft.fisp.domain.model.DpTableDtl)
	 */
	@Override
	public int insert(DpTableDtl dpTableDtl) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_TABLE_DTL + "." + SQLMap.INSERT, dpTableDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#delete(java.lang.String)
	 */
	@Override
	public int delete(String tableId) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_TABLE_DTL + "." + SQLMap.DELETE_BYKEY, tableId);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#queryListForName(java.lang.String)
	 */
	@Override
	public List<DpTableDtl> queryListForName(String tableName) {
		log.info("[queryListForName] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_LIST, tableName);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#queryNotUsedListForName(java.lang.String)
	 */
	@Override
	public List<DpTableDtl> queryNotUsedListForName(String tableName) {
		log.info("[queryNotUsedListForName] - query not used records for requirement");
		return queryDAO.executeForObjectList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_LIST_FORM, tableName);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#queryPage(org.springframework.data.domain.Pageable, java.lang.String)
	 */
	@Override
	public Page<DpTableDtl> queryPage(Pageable pageable, String tableId) {
		log.info("[queryPage] - query one page");
		return pageH.getPage(Table.DP_TABLE_DTL, SQLMap.SELECT_QUERY_COUNTS, SQLMap.SELECT_QUERY_LIST, tableId, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository#queryMap(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> queryMap(String tableName) {
		log.info("[queryMap] - query records for requirement");
		return queryDAO.executeForMapList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_MAP, tableName);
	}

}
