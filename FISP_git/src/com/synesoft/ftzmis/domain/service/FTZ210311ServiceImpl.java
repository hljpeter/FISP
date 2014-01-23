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
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210310Repository;

/**
 * @author hb_huang
 * @system FTZMIS(汇率掉期业务)
 * @date 2014-1-7上午11:15:36
 */
@Service
public class FTZ210311ServiceImpl implements FTZ210311Service {
	
	protected static String funcId = "FTZ_Add_210311";

	@Resource
	protected FTZ210310Repository ftz210310Repo;
	
	@Override
	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl) {
		return ftz210310Repo.queryFtzOffMsgCtlPage(pageable, query_FtzOffMsgCtl);
	}

	@Override
	@Transactional
	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl) {
		//添加日志
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"", insert_FtzOffMsgCtl.toString());
		return ftz210310Repo.insertFtzOffMsgCtl(insert_FtzOffMsgCtl);
	}

	@Override
	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl) {
		return ftz210310Repo.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
	}

	@Override
	@Transactional
	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		//记录日志
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"",ftzOffTxnDtl.toString());
		
		return ftz210310Repo.insertFtzOffTxnDtl(ftzOffTxnDtl);
	}

	@Override
	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		return ftz210310Repo.queryFtzOffMsgCtl(ftzOffMsgCtl);
	}

	@Override
	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl) {
		return ftz210310Repo.queryFtzOffTxnDtlPage(pageable, ftzOffTxnDtl);
	}

	@Override
	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		return ftz210310Repo.queryFtzOffTxnDtl(ftzOffTxnDtl);
	}

	@Override
	@Transactional
	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl,
			List<FtzOffTxnDtl> ftzOffTxnDtls) {
		if (null == ftzOffTxnDtls) {
			FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
			query_FtzOffMsgCtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
			FtzOffMsgCtl ftzOffMsgCtl_tmp = this.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
			//添加日志
			if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(update_FtzOffMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(update_FtzOffMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}else{
				BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}
			
			return ftz210310Repo.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		} else {
			for (FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
				this.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
			}
			
			FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
			query_FtzOffMsgCtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
			FtzOffMsgCtl ftzOffMsgCtl_tmp = this.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
			//
			if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(update_FtzOffMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(update_FtzOffMsgCtl.getMsgStatus())){
				BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}else{
				BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzOffMsgCtl_tmp.toString(), update_FtzOffMsgCtl.toString());
			}
			
			return ftz210310Repo.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		}
	}

	@Override
	@Transactional
	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl) {
		if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzOffTxnDtl.getChkStatus())) {
			FtzOffMsgCtl update_FtzOffMsgCtl = new FtzOffMsgCtl();
			update_FtzOffMsgCtl.setMsgId(ftzOffTxnDtl.getMsgId());
			update_FtzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			this.updateFtzOffMsgCtl(update_FtzOffMsgCtl, null);
		}
		//记录日志
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(ftzOffTxnDtl.getMsgId());
		query_FtzOffTxnDtl.setSeqNo(ftzOffTxnDtl.getSeqNo());
		FtzOffTxnDtl ftzOffTxnDtl_tmp = this.queryFtzOffTxnDtl(query_FtzOffTxnDtl);
		if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzOffTxnDtl.getChkStatus())){
			BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK,ftzOffTxnDtl_tmp.toString(), ftzOffTxnDtl.toString());
		}else if(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzOffTxnDtl.getChkStatus())){
			BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT,ftzOffTxnDtl_tmp.toString(), ftzOffTxnDtl.toString());
		}else{
			BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzOffTxnDtl_tmp.toString(), ftzOffTxnDtl.toString());
		}
		
		return ftz210310Repo.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl) {
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(del_FtzOffTxnDtl.getMsgId());
		query_FtzOffTxnDtl.setSeqNo(del_FtzOffTxnDtl.getSeqNo());
		FtzOffTxnDtl ftzOffTxnDtl_tmp = this.queryFtzOffTxnDtl(query_FtzOffTxnDtl);
		//记录日志
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzOffTxnDtl_tmp.toString(), "");
		
		return ftz210310Repo.deleteFtzOffTxnDtl(del_FtzOffTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl) {
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(del_FtzOffMsgCtl.getMsgId());
		ftz210310Repo.deleteFtzOffTxnDtls(ftzOffTxnDtl);
		
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(del_FtzOffMsgCtl.getMsgId());
		FtzOffMsgCtl ftzOffMsgCtl_tmp = this.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzOffMsgCtl_tmp.toString(), "");
		
		int i = ftz210310Repo.deleteFtzOffMsgCtl(del_FtzOffMsgCtl);
		return i;
	}

	@Override
	@Transactional
	public int updateFtzOffMsgCtlForSubmit(FtzOffMsgCtl ftzOffMsgCtl) {
		ftzOffMsgCtl.setMsgStatus("02");
		int i = this.updateFtzOffMsgCtl(ftzOffMsgCtl, null);
		return i;
	}

	@Override
	public FtzBankCode queryBankCode(FtzBankCode ftzBankCode) {
		return ftz210310Repo.queryFtzBankCode(ftzBankCode);
	}
	
	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}
}
