package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpImpCfgDtl;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;

@Repository
public class DpImpCfgDtlRepositoryImpl implements DpImpCfgDtlRepository {

	private static final LogUtil log = new LogUtil(DpImpCfgDtlRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgDtlRepository#query(java.lang.String)
	 */
	@Override
	public DpImpCfgDtl query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_IMP_CFG_DTL + "." + SQLMap.SELECT_BYKEY, id, DpImpCfgDtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgDtlRepository#queryList(java.lang.String)
	 */
	@Override
	public List<DpImpCfgDtlVO> queryList(String impId) {
		log.info("[queryList] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_IMP_CFG_DTL + "." + SQLMap.SELECT_QUERY_LIST, impId);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgDtlRepository#insert(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public int insert(DpImpCfgDtl dpImpCfgDtl) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_IMP_CFG_DTL + "." + SQLMap.INSERT, dpImpCfgDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgDtlRepository#delete(java.lang.String)
	 */
	@Override
	public int delete(String impId) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_IMP_CFG_DTL + "." + SQLMap.DELETE_BYKEY, impId);
	}

}
