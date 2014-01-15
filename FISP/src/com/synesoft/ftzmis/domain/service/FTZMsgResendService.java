/**
 * 
 */
package com.synesoft.ftzmis.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS
 * @date 2013-12-31下午04:14:56
 */
public interface FTZMsgResendService {

	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl query_FtzInMsgCtl);

	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,FtzInTxnDtl ftzInTxnDtl);
	
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
}
