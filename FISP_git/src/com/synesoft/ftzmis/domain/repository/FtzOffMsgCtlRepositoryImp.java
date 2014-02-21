package com.synesoft.ftzmis.domain.repository;

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
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

/**
 * 表外报文控制表(DAT_OFF_MSG_CTL)
 * @author yyw
 * @date 2013-12-25
 */
@Repository
public class FtzOffMsgCtlRepositoryImp implements FtzOffMsgCtlRepository {
	private static final Logger log = LoggerFactory.getLogger(FtzOffMsgCtlRepositoryImp.class);

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#queryPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public Page<FtzOffMsgCtlVO> queryPage(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FtzOffMsgCtlRepositoryImp.queryPage() start ...");
		return pageH.getPage(Table.FTZ_OFF_MSG_CTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST, ftzOffMsgCtlVO, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#queryByPK(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public FtzOffMsgCtl queryByPK(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FtzOffMsgCtlRepositoryImp.queryByPK() start ...");
		return queryDAO.executeForObject(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzOffMsgCtl, FtzOffMsgCtl.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#queryPageForApr(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO)
	 */
	public Page<FtzOffMsgCtlVO> queryPageForApr(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FtzOffMsgCtlRepositoryImp.queryPageForApr() start ...");
		return pageH.getPage(Table.FTZ_OFF_MSG_CTL, SQLMap.SELECT_COUNTS, SQLMap.SELECT_LIST_SELECTIVE, ftzOffMsgCtlVO, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#insert(com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO)
	 */
	public int insert(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FtzOffMsgCtlRepositoryImp.insert() start ...");
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.INSERT, ftzOffMsgCtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#updateMsg(com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO)
	 */
	public int updateMsg(FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FtzOffMsgCtlRepositoryImp.updateMsg() start ...");
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.UPDATE_BY_CONDITION_SELECTIVE, ftzOffMsgCtlVO);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#updateMsgStatus(com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO)
	 */
	public int updateMsgStatus(FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FtzOffMsgCtlRepositoryImp.updateMsgStatus() start ...");
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzOffMsgCtlVO);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository#deleteMsg(com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO)
	 */
	public int deleteMsg(FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FtzOffMsgCtlRepositoryImp.deleteMsg() start ...");
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.DELETE_PRIMARY_KEY, ftzOffMsgCtlVO);
	}

	public int batchUpdateStatus(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FtzOffMsgCtlRepositoryImp.batchUpdateStatus() start ...");
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + ".batchUpdateStatus", ftzOffMsgCtl);
	}
	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private PageHandler<FtzOffMsgCtlVO> pageH;

}
