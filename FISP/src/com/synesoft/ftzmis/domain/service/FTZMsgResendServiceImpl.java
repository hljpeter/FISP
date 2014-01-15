/**
 * 
 */
package com.synesoft.ftzmis.domain.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZMsgResendRepository;

/**
 * @author hb_huang
 * @system FTZMIS
 * @date 2013-12-31下午04:15:30
 */
@Service
public class FTZMsgResendServiceImpl implements FTZMsgResendService {

	@Resource
	protected FTZMsgResendRepository ftzMsgResendRepo;
	
	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		
		return ftzMsgResendRepo.queryFtzInMsgCtlPage(pageable, ftzInMsgCtl);
	}
	
	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return ftzMsgResendRepo.queryFtzInMsgCtl(ftzInMsgCtl);
	}
	
	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return ftzMsgResendRepo.queryFtzInTxnDtlPage(pageable, ftzInTxnDtl);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return ftzMsgResendRepo.queryFtzInTxnDtl(ftzInTxnDtl);
	}
}
