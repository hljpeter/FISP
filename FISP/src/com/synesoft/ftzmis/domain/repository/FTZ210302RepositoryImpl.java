package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author 李峰
 * @date 2013-12-29 上午11:56:33
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class FTZ210302RepositoryImpl implements FTZ210302Repository {

	@Autowired
	private UpdateDAO updateDAO;
	
	@Autowired
	private QueryDAO queryDAO;
	
	@Autowired
	private PageHandler<FtzOffMsgCtl> pageFtzInMsgCtl;
	
	@Autowired
	private PageHandler<FtzOffTxnDtl> pageFtzInTxnDtl;
	
	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl){
		return pageFtzInMsgCtl.getPage(Table.FTZ_OFF_MSG_CTL, SQLMap.SELECT_MODEL_COUNTS,
				SQLMap.SELECT_MODEL_LIST, query_FtzOffMsgCtl, pageable);
	}
	
	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl){
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.INSERT, insert_FtzOffMsgCtl);
	}
	
	public int getSeqNo(FtzOffTxnDtl ftzOffTxnDtl){
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_SEQ_NO, ftzOffTxnDtl, Integer.class);
	}
	
	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl){
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.INSERT, ftzOffTxnDtl);
	}
	
	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl){
		return queryDAO.executeForObject(Table.FTZ_OFF_MSG_CTL + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzOffMsgCtl, FtzOffMsgCtl.class);
	}

	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl){
		return pageFtzInTxnDtl.getPage(Table.FTZ_OFF_TXN_DTL, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzOffTxnDtl, pageable);
	}
	
	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl){
		return queryDAO.executeForObject(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_PRIMARY_KEY, ftzOffTxnDtl, FtzOffTxnDtl.class);
	}
	
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl){
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.UPDATE_PRIMARY_KEY, update_FtzOffMsgCtl);
	}
	
	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl){
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.UPDATE_PRIMARY_KEY, ftzOffTxnDtl);
	}
	
	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl){
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_PRIMARY_KEY, del_FtzOffTxnDtl);
	}
	
	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl){
		return updateDAO.execute(Table.FTZ_OFF_MSG_CTL + "." + SQLMap.DELETE_PRIMARY_KEY, del_FtzOffMsgCtl);
	}
	
	public int deleteFtzOffTxnDtl(FtzOffMsgCtl del_FtzOffMsgCtl){
		return updateDAO.execute(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.DELETE_MSG_ID, del_FtzOffMsgCtl);
	}
	
	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl){
		return queryDAO.executeForObjectList(Table.FTZ_OFF_TXN_DTL + "." + SQLMap.SELECT_LIST, query_FtzOffTxnDtl);
	}
}
