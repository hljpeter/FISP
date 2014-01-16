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
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffTxnDtlVO;

/**
 * 表外交易明细表(DAT_OFF_TXN_DTL)
 * @author yyw
 * @date 2013-12-25
 */
@Repository
public class FtzOffTxnDtlRepositoryImp implements FtzOffTxnDtlRepository {
	private static final Logger log = LoggerFactory.getLogger(FtzOffTxnDtlRepositoryImp.class);

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryCount(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public int queryCount(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryCount() start ...");
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_COUNTS, ftzOffTxnDtl, Integer.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public Page<FtzOffTxnDtl> queryPage(Pageable pageable, FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryPage() start ...");
		return pageH.getPage(Table.FTZ_OFF_TXN_DTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, ftzOffTxnDtl, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#queryList(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public List<FtzOffTxnDtl> queryList(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryList() start ...");
		return queryDAO.executeForObjectList(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_LIST, ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#queryAuthList(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public List<FtzOffTxnDtl> queryAuthList(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryAuthList() start ...");
		return queryDAO.executeForObjectList(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_LIST_SELECTIVE, ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryPage(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public FtzOffTxnDtl queryByPK(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryByPK() start ...");
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzOffTxnDtl, FtzOffTxnDtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FTZ210301Repository#queryAuthByPK(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public FtzOffTxnDtl queryAuthByPK(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.queryAuthByPK() start ...");
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_NEXT, ftzOffTxnDtl, FtzOffTxnDtl.class);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#insert(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public int insert(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.insert() start ...");
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.INSERT, ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#transQueryTxnDtlMaxSeqNo(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public String queryTxnDtlMaxSeqNo(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.transQueryTxnDtlMaxSeqNo() start ...");
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_SEQ_NO, ftzOffTxnDtl, String.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#update(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public int update(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY, ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#updateSatus(com.synesoft.ftzmis.domain.model.vo.FtzOffTxnDtlVO)
	 */
	public int updateStatus(FtzOffTxnDtlVO ftzOffTxnDtlVO) {
		log.debug("FtzOffTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzOffTxnDtlVO);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#delete(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public int delete(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.delete() start ...");
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_PRIMARY_KEY, ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository#delete(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public int deleteByMsgId(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FtzOffTxnDtlRepositoryImp.deleteByMsgId() start ...");
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_BY_MSG_ID, ftzOffTxnDtl);
	}
	
	
	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private PageHandler<FtzOffTxnDtl> pageH;

}
