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
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.vo.DpImpCfgVO;

@Repository
public class DpImpCfgRepositoryImpl implements DpImpCfgRepository {

	private static final LogUtil log = new LogUtil(DpImpCfgRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpImpCfg> pageH;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#queryList(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public Page<DpImpCfg> queryList(Pageable pageable, DpImpCfg dpImpCfg) {
		log.info("[queryList] - query one page");
		return pageH.getPage(Table.DP_IMP_CFG, SQLMap.SELECT_QUERY_COUNTS, SQLMap.SELECT_QUERY_LIST, dpImpCfg, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#query(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public DpImpCfg query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_IMP_CFG + "." + SQLMap.SELECT_BYKEY, id, DpImpCfg.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#query(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public DpImpCfg query(DpImpCfg dpImpCfg) {
		log.info("[query] - query one record, param[projId=" + dpImpCfg.getProjId() 
				+ ", branchId=" + dpImpCfg.getBranchId() + ", batchNo=" + dpImpCfg.getBranchId() 
				+ ", seqNo=" + dpImpCfg.getSeqNo() + "]");
		return queryDAO.executeForObject(Table.DP_IMP_CFG + "." + SQLMap.SELECT_KEY, dpImpCfg, DpImpCfg.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#queryList(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public List<DpImpCfg> queryList(DpImpCfg dpImpCfg) {
		log.info("[queryList] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_IMP_CFG + "." + SQLMap.SELECT_QUERY_LIST, dpImpCfg);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#insert(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public int insert(DpImpCfg dpImpCfg) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_IMP_CFG + "." + SQLMap.INSERT, dpImpCfg);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#update(com.synesoft.fisp.domain.model.vo.DpImpCfgVO)
	 */
	@Override
	public int update(DpImpCfgVO dpImpCfgVO) {
		log.info("[update] - update one record");
		return updateDAO.execute(Table.DP_IMP_CFG + "." + SQLMap.UPDATE_BYKEY, dpImpCfgVO);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository#delete(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public int delete(DpImpCfg dpImpCfg) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_IMP_CFG + "." + SQLMap.DELETE_BYKEY, dpImpCfg);
	}

}
