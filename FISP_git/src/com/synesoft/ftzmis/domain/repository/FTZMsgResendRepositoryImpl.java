/**
 * 
 */
package com.synesoft.ftzmis.domain.repository;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS
 * @date 2013-12-31下午04:16:53
 */
@Repository
public class FTZMsgResendRepositoryImpl implements FTZMsgResendRepository {

	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private PageHandler<FtzInMsgCtl> pageFtzInMsgCtl;
	
	@Resource
	private PageHandler<FtzInTxnDtl> pageFtzInTxnDtl;
	
	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return pageFtzInMsgCtl.getPage(Table.FTZ_IN_MSG_CTL, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzInMsgCtl, pageable);
	}
	
	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return queryDAO.executeForObject(Table.FTZ_IN_MSG_CTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzInMsgCtl, FtzInMsgCtl.class);
	}
	
	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return pageFtzInTxnDtl.getPage(Table.FTZ_IN_TXN_DTL, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzInTxnDtl, pageable);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return queryDAO.executeForObject(Table.FTZ_IN_TXN_DTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzInTxnDtl, FtzInTxnDtl.class);
	}
}
