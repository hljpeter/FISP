package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS(远期结售汇)
 * @date 2014-1-3上午10:05:01
 */
public interface FTZ210310Service {
	
	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl);
	
	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl);
	
	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl);
	
	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);
	
	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);
	
	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl);
	
	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);
	
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl, List<FtzOffTxnDtl> ftzOffTxnDtls);
	
	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl);
	
	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl);

	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl);
	
	public int updateFtzOffMsgCtlForSubmit(FtzOffMsgCtl ftzOffMsgCtl);
	
	public FtzBankCode queryBankCode(FtzBankCode ftzBankCode);
}
