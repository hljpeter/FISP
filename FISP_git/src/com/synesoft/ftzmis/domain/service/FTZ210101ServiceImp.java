package com.synesoft.ftzmis.domain.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210101Repository;

@Service
public class FTZ210101ServiceImp implements FTZ210101Service {
	
	protected static String funcId ="FTZ_Add_210101";

	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return ftz210101Repos.queryFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210101Repos.queryFtzInMsgCtlPageInput(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210101Repos.queryFtzInMsgCtlPage(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return ftz210101Repos.queryFtzInTxnDtlPage(pageable, ftzInTxnDtl);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210101Repos.queryFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		ftz210101Repos.deleteFtzInTxnDtls(ftzInTxnDtl);

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzMsgCtl_tmp.toString(), "");
		
		int i = ftz210101Repos.deleteFtzInMsgCtl(ftzInMsgCtl);
		return i;
	}

	@Override
	@Transactional
	public int insertFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"", ftzInMsgCtl.toString());
		return ftz210101Repos.insertFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl,
			List<FtzInTxnDtl> ftzInTxnDtls) {
		int i = 0;
		if (null == ftzInTxnDtls) {
			FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
			query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
			FtzInMsgCtl ftzMsgCtl_tmp = this
					.queryFtzInMsgCtl(query_FtzInMsgCtl);
			if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzInMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzInMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}else{
				BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}
			
			i=ftz210101Repos.updateFtzInMsgCtl(ftzInMsgCtl);
		} else {
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				this.updateFtzInTxnDtlSelective(ftzInTxnDtl);
			}
			FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
			query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
			FtzInMsgCtl ftzMsgCtl_tmp = this
					.queryFtzInMsgCtl(query_FtzInMsgCtl);
			if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzInMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzInMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}else{
				BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			}
			i= ftz210101Repos.updateFtzInMsgCtl(ftzInMsgCtl);
		}
		if (ftzInMsgCtl.getMsgStatus().equals(
				CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
			generateXml.submitMsg(ftzInMsgCtl.getMsgNo(),
					ftzInMsgCtl.getMsgId());
		}
		return i;
	}

	@Override
	@Transactional
	public int deleteFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = this
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		int totalCount = result_FtzInMsgCtl.getTotalCount();
		totalCount = totalCount - 1;
		FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
		update_FtzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		update_FtzInMsgCtl.setTotalCount(totalCount);
		this.updateFtzInMsgCtl(update_FtzInMsgCtl, null);

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(ftzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(ftzInTxnDtl.getSeqNo());
		FtzInTxnDtl ftzInTxnDtl_tmp = this.queryFtzInTxnDtl(query_FtzInTxnDtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzInTxnDtl_tmp.toString(), "");

		return ftz210101Repos.deleteFtzInTxnDtl(ftzInTxnDtl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synesoft.ftzmis.domain.service.FTZ210101Service#queryFtzActMstrs(
	 * com.synesoft.ftzmis.domain.model.FtzActMstr)
	 */
	@Override
	public List<FtzActMstr> queryFtzActMstrs(FtzActMstr ftzActMstr) {
		return ftz210101Repos.queryFtzActMstrs(ftzActMstr);
	}

	@Override
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210101Repos.queryFtzInTxnDtlList(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int insertFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210101Repos
				.queryFtzInMsgCtl(ftzInMsgCtl);
		FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
		update_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		update_FtzInMsgCtl
				.setTotalCount(result_FtzInMsgCtl.getTotalCount() + 1);
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftz210101Repos.updateFtzInMsgCtl(update_FtzInMsgCtl);

		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"",ftzInTxnDtl.toString());

		return ftz210101Repos.insertFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int updateFtzInTxnDtlSelective(FtzInTxnDtl ftzInTxnDtl) {
		if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzInTxnDtl
				.getChkStatus())) {
			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
			// UserInf userInfo = ContextConst.getCurrentUser();
			// update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
			// update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			this.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(ftzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(ftzInTxnDtl.getSeqNo());
		FtzInTxnDtl ftzInTxnDtl_tmp = this.queryFtzInTxnDtl(query_FtzInTxnDtl);
		if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzInTxnDtl.getChkStatus())){
			BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzInTxnDtl_tmp.toString(), ftzInTxnDtl.toString());
		}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzInTxnDtl.getChkStatus())){
			BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzInTxnDtl_tmp.toString(), ftzInTxnDtl.toString());
		}else{
			BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzInTxnDtl_tmp.toString(), ftzInTxnDtl.toString());
		}
		return ftz210101Repos.updateFtzInTxnDtlSelective(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtlForSubmit(FtzInMsgCtl ftzInMsgCtl) {
		ftzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = this.updateFtzInMsgCtl(ftzInMsgCtl, null);
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synesoft.ftzmis.domain.service.FTZ210101Service#queryFtzBankCode(
	 * com.synesoft.ftzmis.domain.model.FtzBankCode)
	 */
	@Override
	public FtzBankCode queryFtzBankCode(FtzBankCode ftzBankCode) {
		return ftz210101Repos.queryFtzBankCode(ftzBankCode);
	}
	
	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}

	@Resource
	protected FTZ210101Repository ftz210101Repos;
	
	@Resource
	protected FtzMsgProcService generateXml;
}
