package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

public interface FTZ210102Service {

	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl);

	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,FtzInTxnDtl ftzInTxnDtl);
	
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
	
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public int insertFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl,List<FtzInTxnDtl> ftzInTxnDtls);
	
	public List<FtzActMstr> queryFtzActMstrs(FtzActMstr ftzActMstr);
	
	public int deleteFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
	
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl);
	
	public int insertFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
	
	public int updateFtzInTxnDtlSelective(FtzInTxnDtl ftzInTxnDtl);
	
	public int updateFtzInMsgCtlForSubmit(FtzInMsgCtl ftzInMsgCtl);
	
	public FtzBankCode queryFtzBankCode(FtzBankCode ftzBankCode);
}
