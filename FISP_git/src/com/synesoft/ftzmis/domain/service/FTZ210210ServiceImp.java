package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210210Repository;
import com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository;

@Service
public class FTZ210210ServiceImp implements FTZ210210Service {
	protected static String funcId ="FTZ_Add_210210";

	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return ftz210210Repos.queryFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210210Repos.queryFtzInMsgCtlPageInput(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210210Repos.queryFtzInMsgCtlPage(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return ftz210210Repos.queryFtzInTxnDtlPage(pageable, ftzInTxnDtl);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210210Repos.queryFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		ftz210210Repos.deleteFtzInTxnDtls(ftzInTxnDtl);

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzMsgCtl_tmp.toString(), "");
		return ftz210210Repos.deleteFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	@Transactional
	public int insertFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"", ftzInMsgCtl.toString());
		return ftz210210Repos.insertFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzMsgCtl_tmp.toString(), ftzInMsgCtl.toString());
		return ftz210210Repos.updateFtzInMsgCtl(ftzInMsgCtl);
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
		this.updateFtzInMsgCtl(update_FtzInMsgCtl);

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(ftzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(ftzInTxnDtl.getSeqNo());
		FtzInTxnDtl ftzInTxnDtl_tmp = this.queryFtzInTxnDtl(query_FtzInTxnDtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE,ftzInTxnDtl_tmp.toString(),"");
		return ftz210210Repos.deleteFtzInTxnDtl(ftzInTxnDtl);
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
		return ftz210210Repos.queryFtzActMstrs(ftzActMstr);
	}

	@Override
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210210Repos.queryFtzInTxnDtlList(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int insertFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210210Repos
				.queryFtzInMsgCtl(ftzInMsgCtl);
		FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
		update_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		update_FtzInMsgCtl
				.setTotalCount(result_FtzInMsgCtl.getTotalCount() + 1);
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftz210210Repos.updateFtzInMsgCtl(update_FtzInMsgCtl);
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD,"",ftzInTxnDtl.toString());
		return ftz210210Repos.insertFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int updateFtzInTxnDtlSelective(FtzInTxnDtl ftzInTxnDtl) {
		if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(ftzInTxnDtl
				.getChkStatus())) {
			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			this.updateFtzInMsgCtl(update_FtzInMsgCtl);
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(ftzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(ftzInTxnDtl.getSeqNo());
		FtzInTxnDtl ftzInTxnDtl_tmp = this.queryFtzInTxnDtl(query_FtzInTxnDtl);
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY,ftzInTxnDtl_tmp.toString(),ftzInTxnDtl.toString());
		return ftz210210Repos.updateFtzInTxnDtlSelective(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtlForSubmit(FtzInMsgCtl ftzInMsgCtl) {
		ftzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = this.updateFtzInMsgCtl(ftzInMsgCtl);
		return i;
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
		return ftz210210Repos.queryFtzBankCode(ftzBankCode);
	}
	
	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}

	@Autowired
	protected FTZ210210Repository ftz210210Repos;
	
	@Autowired
	protected FtzInTxnDtlRepository ftzInTxnDtlRepository;
}
