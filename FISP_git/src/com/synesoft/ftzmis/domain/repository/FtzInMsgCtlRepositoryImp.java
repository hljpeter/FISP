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
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;

@Repository
public class FtzInMsgCtlRepositoryImp implements FtzInMsgCtlRepository {
	private static final Logger log = LoggerFactory.getLogger(FtzInMsgCtlRepositoryImp.class);

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#queryPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public Page<FtzInMsgCtlVO> queryPage(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO) {
		log.debug("FtzInMsgCtlRepositoryImp.queryPage() start ...");
		return pageH.getPage(Table.FTZIN_MSG_CTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, ftzInMsgCtlVO, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#queryByPK(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public FtzInMsgCtl queryByPK(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FtzInMsgCtlRepositoryImp.queryByPK() start ...");
		return queryDAO.executeForObject(Table.FTZIN_MSG_CTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzInMsgCtl, FtzInMsgCtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#queryPageForApr(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO)
	 */
	public Page<FtzInMsgCtlVO> queryPageForApr(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO) {
		log.debug("FtzInMsgCtlRepositoryImp.queryPageForApr() start ...");
		return pageH.getPage(Table.FTZIN_MSG_CTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST_SELECTIVE, ftzInMsgCtlVO, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#insert(com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO)
	 */
	public int insert(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FtzInMsgCtlRepositoryImp.insert() start ...");
		return updateDAO.execute(Table.FTZIN_MSG_CTL + "." + SQLMap.INSERT, ftzInMsgCtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#updateMsg(com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO)
	 */
	public int updateMsg(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FtzInMsgCtlRepositoryImp.updateMsg() start ...");
		return updateDAO.execute(Table.FTZIN_MSG_CTL + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzInMsgCtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository#updateMsgStatus(com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO)
	 */
	public int updateMsgStatus(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FtzInMsgCtlRepositoryImp.updateMsgStatus() start ...");
		return updateDAO.execute(Table.FTZIN_MSG_CTL + "." + SQLMap.UPDATE_BY_CONDITION_SELECTIVE, ftzInMsgCtl);
	}

	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private PageHandler<FtzInMsgCtlVO> pageH;

	@Override
	public int delete(FtzInMsgCtl ftzInMsgCtl) {
		// TODO Auto-generated method stub
		log.debug("FtzInMsgCtlRepositoryImp.delete() start ...");
		return updateDAO.execute(Table.FTZIN_MSG_CTL + "." + SQLMap.DELETE_PRIMARY_KEY, ftzInMsgCtl);
	}

	public int updateAuth(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FtzInTxnDtlRepositoryImp.update() start ...");
		return updateDAO.execute(Table.FTZIN_MSG_CTL + "." + SQLMap.UPDATE_BY_CONDITION_SELECTIVE, ftzInMsgCtl);
	}
	
	//更新报文通讯和反馈状态,通讯功能使用
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl){
		log.debug("FtzInTxnDtlRepositoryImp.updateFtzInMsgCtl() start ...");
		return updateDAO.execute(Table.FTZ_IN_MSG_CTL + ".batchUpdateStatus", ftzInMsgCtl);
	}
	
	
	public List<FtzInMsgCtl> queryFtzInMsgCtlList(FtzInMsgCtl ftzInMsgCtl) {
		return queryDAO.executeForObjectList(Table.FTZIN_MSG_CTL + "."
				+ SQLMap.SELECT_LIST, ftzInMsgCtl);
	}
	
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObjectList(Table.FTZIN_TXN_DTL + "."
				+ SQLMap.SELECT_LIST, ftzInTxnDtl);
	}
}

