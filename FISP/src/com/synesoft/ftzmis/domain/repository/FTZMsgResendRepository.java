/**
 * 
 */
package com.synesoft.ftzmis.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS
 * @date 2013-12-31下午04:16:11
 */
public interface FTZMsgResendRepository {

	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl);
	
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,FtzInTxnDtl ftzInTxnDtl);
	
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
}
