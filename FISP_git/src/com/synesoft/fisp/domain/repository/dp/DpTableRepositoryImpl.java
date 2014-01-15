package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

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
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.vo.DpTableVO;

/**
 * 表定义RepositoryImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Repository
public class DpTableRepositoryImpl implements DpTableRepository {

	private static final LogUtil log = new LogUtil(DpTableRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpTable> pageH;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#queryList(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public Page<DpTable> queryList(Pageable pageable, DpTable dpTable) {
		log.info("[queryList] - query one page");
		return pageH.getPage(Table.DP_TABLE, SQLMap.SELECT_QUERY_COUNTS, SQLMap.SELECT_QUERY_LIST, dpTable, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#queryForLogicKey(java.lang.String)
	 */
	@Override
	public DpTable queryForLogicKey(String name) {
		log.info("[queryForLogicKey] - query one record, param[tablename=" + name + "]");
		return queryDAO.executeForObject(Table.DP_TABLE + "." + SQLMap.SELECT_KEY, name, DpTable.class);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#query(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public DpTable query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_TABLE + "." + SQLMap.SELECT_BYKEY, id, DpTable.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#queryList(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public List<DpTable> queryList(DpTable dpTable) {
		log.info("[queryList] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_TABLE + "." + SQLMap.SELECT_QUERY_LIST, dpTable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#insert(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public int insert(DpTable dpTable) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_TABLE + "." + SQLMap.INSERT, dpTable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#update(com.synesoft.fisp.domain.model.vo.DpTableVO)
	 */
	@Override
	public int update(DpTableVO dpTableVo) {
		log.info("[update] - update one record");
		return updateDAO.execute(Table.DP_TABLE + "." + SQLMap.UPDATE_BYKEY, dpTableVo);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpTableRepository#delete(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public int delete(DpTable dpTable) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_TABLE + "." + SQLMap.DELETE_BYKEY, dpTable);
	}


}
