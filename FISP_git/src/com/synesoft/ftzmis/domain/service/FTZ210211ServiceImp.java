package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210211Repository;
import com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository;

@Service
public class FTZ210211ServiceImp implements FTZ210211Service {
	protected static String funcId ="FTZ_Add_210211";
	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return ftz210211Repos.queryFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210211Repos.queryFtzInMsgCtlPageInput(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210211Repos.queryFtzInMsgCtlPage(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return ftz210211Repos.queryFtzInTxnDtlPage(pageable, ftzInTxnDtl);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210211Repos.queryFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		ftz210211Repos.deleteFtzInTxnDtls(ftzInTxnDtl);

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzMsgCtl_tmp.toString(), "");
		int i = ftz210211Repos.deleteFtzInMsgCtl(ftzInMsgCtl);
		return i;
	}

	@Override
	@Transactional
	public int insertFtzInMsgCtlAndTxnDtl(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl) {
		//插入交易明细
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"",ftzInTxnDtl.toString());
		int retTxnDtl = ftz210211Repos.insertFtzInTxnDtl(ftzInTxnDtl);
		if(retTxnDtl > 0){
			//插入批量明细
			ftzInMsgCtl.setTotalCount(1);
			BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"", ftzInMsgCtl.toString());
			return ftz210211Repos.insertFtzInMsgCtl(ftzInMsgCtl);
		}
		return retTxnDtl;
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtlAndTxnDtl(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl) {
		//修改交易明细
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(ftzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(ftzInTxnDtl.getSeqNo());
		FtzInTxnDtl ftzInTxnDtl_tmp = this.queryFtzInTxnDtl(query_FtzInTxnDtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzInTxnDtl_tmp.toString(),ftzInTxnDtl.toString());
		int ret = ftz210211Repos.updateFtzInTxnDtlSelective(ftzInTxnDtl);
		if(ret > 0){
			FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
			query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
			FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
			BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
			return ftz210211Repos.updateFtzInMsgCtl(ftzInMsgCtl);
		}
		return ret;
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
		return ftz210211Repos.queryFtzActMstrs(ftzActMstr);
	}

	@Override
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210211Repos.queryFtzInTxnDtlList(ftzInTxnDtl);
	}


	@Override
	@Transactional
	public int updateFtzInMsgCtlForSubmit(FtzInMsgCtl ftzInMsgCtl) {
		ftzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		FtzInTxnDtl query_ftzInTxnDtl = new FtzInTxnDtl();
		query_ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		query_ftzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		FtzInTxnDtl update_ftzInTxnDtl = queryFtzInTxnDtl(query_ftzInTxnDtl);
		update_ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		return this.updateFtzInMsgCtlAndTxnDtl(ftzInMsgCtl,update_ftzInTxnDtl);
	}
	
	@Override
	public String queryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl) {
		return ftzInTxnDtlRepository.queryTxnDtlMaxSeqNo(ftzInTxnDtl);
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
		return ftz210211Repos.queryFtzBankCode(ftzBankCode);
	}
	
	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}

	@Autowired
	protected FTZ210211Repository ftz210211Repos;
	
	@Autowired
	protected FtzInTxnDtlRepository ftzInTxnDtlRepository;
}
