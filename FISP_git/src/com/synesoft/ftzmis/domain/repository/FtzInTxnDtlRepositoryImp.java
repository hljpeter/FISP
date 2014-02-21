package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

@Repository
public class FtzInTxnDtlRepositoryImp implements FtzInTxnDtlRepository {
	private static final Logger log = LoggerFactory.getLogger(FtzInTxnDtlRepositoryImp.class);

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public Page<FtzInTxnDtl> queryPage(Pageable pageable, FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.queryPage() start ...");
		return pageH.getPage(Table.FTZIN_TXN_DTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, ftzInTxnDtl, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#queryList(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public List<FtzInTxnDtl> queryList(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.queryList() start ...");
		return queryDAO.executeForObjectList(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_LIST, ftzInTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryPage(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public FtzInTxnDtl queryByPK(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.queryByPK() start ...");
		return queryDAO.executeForObject(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzInTxnDtl, FtzInTxnDtl.class);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#insert(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public int insert(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.insert() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.INSERT, ftzInTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#transQueryTxnDtlMaxSeqNo(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public String queryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.transQueryTxnDtlMaxSeqNo() start ...");
		return queryDAO.executeForObject(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_SEQ_NO, ftzInTxnDtl,  String.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#update(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public int update(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzInTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#updateSatus(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public int updateStatus(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzInTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#delete(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public int delete(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.delete() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.DELETE_PRIMARY_KEY, ftzInTxnDtl);
	}
	
	
	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private PageHandler<FtzInTxnDtl> pageH;

	@Override
	public int deleteByMsgID(FtzInTxnDtl ftzInTxnDtl) {
		// TODO Auto-generated method stub
		log.debug("FtzInTxnDtlRepositoryImp.delete() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.DELETE_MSG_ID, ftzInTxnDtl);
	}
	
	public int queryCount(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.queryCount() start ...");
		return queryDAO.executeForObject(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_COUNTS, ftzInTxnDtl, Integer.class);
	}

	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryAuthByPK(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public FtzInTxnDtl queryAuthByPK(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.queryAuthByPK() start ...");
		return queryDAO.executeForObject(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_NEXT, ftzInTxnDtl, FtzInTxnDtl.class);
	}
	
	
	public int updateAuth(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FtzInTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZIN_TXN_DTL + "." + SQLMap.UPDATE_BY_CONDITION_SELECTIVE, ftzInTxnDtl);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#queryAmountSumByCDFlag(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Override
	public List<FtzInTxnDtl> queryAmountSumByCDFlag(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObjectList(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_SUM_BY_CDFLAG, ftzInTxnDtl);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository#queryDtlCountByCDFlag(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Override
	public List<FtzInTxnDtl> queryDtlCountByCDFlag(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObjectList(Table.FTZIN_TXN_DTL + "." + SQLMap.SELECT_COUNT_BY_CDFLAG, ftzInTxnDtl);
	}
}

