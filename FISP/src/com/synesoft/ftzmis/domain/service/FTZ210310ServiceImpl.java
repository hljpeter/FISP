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
 * @system FTZMIS(远期结售汇)
 * @date 2014-1-3上午10:07:36
 */
@Service
public class FTZ210310ServiceImpl implements FTZ210310Service {

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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer afterData = new StringBuffer();
		afterData.append("新增了报文标识号为：");
		afterData.append(insert_FtzOffMsgCtl.getMsgId());
		afterData.append(",机构ID为：");
		afterData.append(insert_FtzOffMsgCtl.getBranchId());
		afterData.append(",版本号为：");
		afterData.append(insert_FtzOffMsgCtl.getVer());
		afterData.append(",发起节点代码为：");
		afterData.append(insert_FtzOffMsgCtl.getSrc());
		afterData.append(",接收节点代码为：");
		afterData.append(insert_FtzOffMsgCtl.getDes());
		afterData.append(",应用名称为：");
		afterData.append(insert_FtzOffMsgCtl.getApp());
		afterData.append(",报文编号为：");
		afterData.append(insert_FtzOffMsgCtl.getMsgNo());
		afterData.append(",报文参考号为：");
		afterData.append(insert_FtzOffMsgCtl.getMsgRef());
		afterData.append(",工作日期为：");
		afterData.append(insert_FtzOffMsgCtl.getWorkDate());
		afterData.append(",操作标识为：");
		afterData.append(insert_FtzOffMsgCtl.getEditFlag());
		afterData.append(",预留字段为：");
		afterData.append(insert_FtzOffMsgCtl.getReserve());
		afterData.append(",接收应答报文·处理结果为：");
		afterData.append(insert_FtzOffMsgCtl.getResult());
		afterData.append(",接收应答报文·附言为：");
		afterData.append(insert_FtzOffMsgCtl.getAddWord());
		afterData.append(",报文状态为：");
		afterData.append(insert_FtzOffMsgCtl.getMsgStatus());
		afterData.append(",录入操作员为：");
		afterData.append(insert_FtzOffMsgCtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(insert_FtzOffMsgCtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(insert_FtzOffMsgCtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(insert_FtzOffMsgCtl.getChkDatetime());
		afterData.append(",发送操作员为：");
		afterData.append(insert_FtzOffMsgCtl.getSndUserId());
		afterData.append(",发送日期时间：");
		afterData.append(insert_FtzOffMsgCtl.getSndDatetime());
		afterData.append(",接收应答日期时间为：");
		afterData.append(insert_FtzOffMsgCtl.getAckDatetime());
		afterData.append(",处理应答日期时间为：");
		afterData.append(insert_FtzOffMsgCtl.getErrDatetime() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());
		
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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer afterData = new StringBuffer();
		afterData.append("新增了报文标识号为：");
		afterData.append(ftzOffTxnDtl.getMsgId());
		afterData.append(",明细序号为：");
		afterData.append(ftzOffTxnDtl.getSeqNo());
		afterData.append(",报表编码为：");
		afterData.append(ftzOffTxnDtl.getReportCode());
		afterData.append(",申报日期为：");
		afterData.append(ftzOffTxnDtl.getSubmitDate());
		afterData.append(",所属机构代码为：");
		afterData.append(ftzOffTxnDtl.getAccOrgCode());
		afterData.append(",金额为：");
		afterData.append(ftzOffTxnDtl.getAmount());
		afterData.append(",货币为：");
		afterData.append(ftzOffTxnDtl.getCurrency());
		afterData.append(",账号为：");
		afterData.append(ftzOffTxnDtl.getAccountNo());
		afterData.append(",户名为：");
		afterData.append(ftzOffTxnDtl.getAccountName());
		afterData.append(",机构代码为：");
		afterData.append(ftzOffTxnDtl.getInstitutionCode());
		afterData.append(",境内外对手行代码为：");
		afterData.append(ftzOffTxnDtl.getSwiftCode());
		afterData.append(",国别代码为：");
		afterData.append(ftzOffTxnDtl.getCountryCode());
		afterData.append(",国内地区码为：");
		afterData.append(ftzOffTxnDtl.getDistrictCode());
		afterData.append(",交易类型为：");
		afterData.append(ftzOffTxnDtl.getTranType());
		afterData.append(",期限长度为：");
		afterData.append(ftzOffTxnDtl.getTermLength());
		afterData.append(",期限单位为：");
		afterData.append(ftzOffTxnDtl.getTermUnit());
		afterData.append(",业务日期1为：");
		afterData.append(ftzOffTxnDtl.getTranDate());
		afterData.append(",业务日期2为：");
		afterData.append(ftzOffTxnDtl.getExpirationDate());
		afterData.append(",利率为：");
		afterData.append(ftzOffTxnDtl.getInterestRate());
		afterData.append(",外汇买卖类型为：");
		afterData.append(ftzOffTxnDtl.getExchangeType());
		afterData.append(",买入币种为：");
		afterData.append(ftzOffTxnDtl.getBuyCurr());
		afterData.append(",买入金额为：");
		afterData.append(ftzOffTxnDtl.getBuyAmt());
		afterData.append(",买入牌价为：");
		afterData.append(ftzOffTxnDtl.getBuyRate());
		afterData.append(",卖出币种为：");
		afterData.append(ftzOffTxnDtl.getSellCurr());
		afterData.append(",卖出金额为：");
		afterData.append(ftzOffTxnDtl.getSellAmt());
		afterData.append(",卖出牌价为：");
		afterData.append(ftzOffTxnDtl.getSellRate());
		afterData.append(",债券代码为：");
		afterData.append(ftzOffTxnDtl.getBondsCode());
		afterData.append(",债券名称为：");
		afterData.append(ftzOffTxnDtl.getBondsName());
		afterData.append(",利率类型为：");
		afterData.append(ftzOffTxnDtl.getInterestType());
		afterData.append(",定价基准为：");
		afterData.append(ftzOffTxnDtl.getBenchmark());
		afterData.append(",浮动点差为：");
		afterData.append(ftzOffTxnDtl.getFloatRate());
		afterData.append(",期限条件为：");
		afterData.append(ftzOffTxnDtl.getTermCondition());
		afterData.append(",保函类型为：");
		afterData.append(ftzOffTxnDtl.getLgType());
		afterData.append(",结售汇交易类型为：");
		afterData.append(ftzOffTxnDtl.getTranGenre());
		afterData.append(",记录复核状态为：");
		afterData.append(ftzOffTxnDtl.getChkStatus());
		afterData.append(",录入操作员为：");
		afterData.append(ftzOffTxnDtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(ftzOffTxnDtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(ftzOffTxnDtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(ftzOffTxnDtl.getChkDatetime());
		afterData.append(",记录复核附言为：");
		afterData.append(ftzOffTxnDtl.getChkAddWord() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());
		
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
			//
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNowInputDateTime();
			StringBuffer beforeData = new StringBuffer();
			
			beforeData.append("将报文标识号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgId());
			beforeData.append(",机构ID为：");
			beforeData.append(ftzOffMsgCtl_tmp.getBranchId());
			beforeData.append(",版本号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getVer());
			beforeData.append(",发起节点代码为：");
			beforeData.append(ftzOffMsgCtl_tmp.getSrc());
			beforeData.append(",接收节点代码为：");
			beforeData.append(ftzOffMsgCtl_tmp.getDes());
			beforeData.append(",应用名称为：");
			beforeData.append(ftzOffMsgCtl_tmp.getApp());
			beforeData.append(",报文编号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgNo());
			beforeData.append(",报文参考号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgRef());
			beforeData.append(",工作日期为：");
			beforeData.append(ftzOffMsgCtl_tmp.getWorkDate());
			beforeData.append(",操作标识为：");
			beforeData.append(ftzOffMsgCtl_tmp.getEditFlag());
			beforeData.append(",预留字段为：");
			beforeData.append(ftzOffMsgCtl_tmp.getReserve());
			beforeData.append(",接收应答报文·处理结果为：");
			beforeData.append(ftzOffMsgCtl_tmp.getResult());
			beforeData.append(",接收应答报文·附言为：");
			beforeData.append(ftzOffMsgCtl_tmp.getAddWord());
			beforeData.append(",报文状态为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgStatus());
			beforeData.append(",录入操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMakUserId());
			beforeData.append(",录入日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMakDatetime());
			beforeData.append(",复核操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getChkUserId());
			beforeData.append(",复核日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getChkDatetime());
			beforeData.append(",发送操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getSndUserId());
			beforeData.append(",发送日期时间：");
			beforeData.append(ftzOffMsgCtl_tmp.getSndDatetime());
			beforeData.append(",接收应答日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getAckDatetime());
			beforeData.append(",处理应答日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getErrDatetime() + "的元素");
			
			StringBuffer afterData = new StringBuffer();
			afterData.append("更新了报文标识号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgId());
			afterData.append(",机构ID为：");
			afterData.append(update_FtzOffMsgCtl.getBranchId());
			afterData.append(",版本号为：");
			afterData.append(update_FtzOffMsgCtl.getVer());
			afterData.append(",发起节点代码为：");
			afterData.append(update_FtzOffMsgCtl.getSrc());
			afterData.append(",接收节点代码为：");
			afterData.append(update_FtzOffMsgCtl.getDes());
			afterData.append(",应用名称为：");
			afterData.append(update_FtzOffMsgCtl.getApp());
			afterData.append(",报文编号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgNo());
			afterData.append(",报文参考号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgRef());
			afterData.append(",工作日期为：");
			afterData.append(update_FtzOffMsgCtl.getWorkDate());
			afterData.append(",操作标识为：");
			afterData.append(update_FtzOffMsgCtl.getEditFlag());
			afterData.append(",预留字段为：");
			afterData.append(update_FtzOffMsgCtl.getReserve());
			afterData.append(",接收应答报文·处理结果为：");
			afterData.append(update_FtzOffMsgCtl.getResult());
			afterData.append(",接收应答报文·附言为：");
			afterData.append(update_FtzOffMsgCtl.getAddWord());
			afterData.append(",报文状态为：");
			afterData.append(update_FtzOffMsgCtl.getMsgStatus());
			afterData.append(",录入操作员为：");
			afterData.append(update_FtzOffMsgCtl.getMakUserId());
			afterData.append(",录入日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getMakDatetime());
			afterData.append(",复核操作员为：");
			afterData.append(update_FtzOffMsgCtl.getChkUserId());
			afterData.append(",复核日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getChkDatetime());
			afterData.append(",发送操作员为：");
			afterData.append(update_FtzOffMsgCtl.getSndUserId());
			afterData.append(",发送日期时间：");
			afterData.append(update_FtzOffMsgCtl.getSndDatetime());
			afterData.append(",接收应答日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getAckDatetime());
			afterData.append(",处理应答日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getErrDatetime() + "的元素");
			
			TlrLogPrint.tlrBizLogPrint("FTZ_Add_210301", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "M",
					time.substring(0, 8), time.substring(8, 14),
					beforeData.toString(), afterData.toString());
			
			return ftz210310Repo.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		} else {
			for (FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
				this.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
			}
			
			FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
			query_FtzOffMsgCtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
			FtzOffMsgCtl ftzOffMsgCtl_tmp = this.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
			//
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNowInputDateTime();
			StringBuffer beforeData = new StringBuffer();
			
			beforeData.append("将报文标识号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgId());
			beforeData.append(",机构ID为：");
			beforeData.append(ftzOffMsgCtl_tmp.getBranchId());
			beforeData.append(",版本号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getVer());
			beforeData.append(",发起节点代码为：");
			beforeData.append(ftzOffMsgCtl_tmp.getSrc());
			beforeData.append(",接收节点代码为：");
			beforeData.append(ftzOffMsgCtl_tmp.getDes());
			beforeData.append(",应用名称为：");
			beforeData.append(ftzOffMsgCtl_tmp.getApp());
			beforeData.append(",报文编号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgNo());
			beforeData.append(",报文参考号为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgRef());
			beforeData.append(",工作日期为：");
			beforeData.append(ftzOffMsgCtl_tmp.getWorkDate());
			beforeData.append(",操作标识为：");
			beforeData.append(ftzOffMsgCtl_tmp.getEditFlag());
			beforeData.append(",预留字段为：");
			beforeData.append(ftzOffMsgCtl_tmp.getReserve());
			beforeData.append(",接收应答报文·处理结果为：");
			beforeData.append(ftzOffMsgCtl_tmp.getResult());
			beforeData.append(",接收应答报文·附言为：");
			beforeData.append(ftzOffMsgCtl_tmp.getAddWord());
			beforeData.append(",报文状态为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMsgStatus());
			beforeData.append(",录入操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMakUserId());
			beforeData.append(",录入日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getMakDatetime());
			beforeData.append(",复核操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getChkUserId());
			beforeData.append(",复核日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getChkDatetime());
			beforeData.append(",发送操作员为：");
			beforeData.append(ftzOffMsgCtl_tmp.getSndUserId());
			beforeData.append(",发送日期时间：");
			beforeData.append(ftzOffMsgCtl_tmp.getSndDatetime());
			beforeData.append(",接收应答日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getAckDatetime());
			beforeData.append(",处理应答日期时间为：");
			beforeData.append(ftzOffMsgCtl_tmp.getErrDatetime() + "的元素");
			
			StringBuffer afterData = new StringBuffer();
			afterData.append("更新了报文标识号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgId());
			afterData.append(",机构ID为：");
			afterData.append(update_FtzOffMsgCtl.getBranchId());
			afterData.append(",版本号为：");
			afterData.append(update_FtzOffMsgCtl.getVer());
			afterData.append(",发起节点代码为：");
			afterData.append(update_FtzOffMsgCtl.getSrc());
			afterData.append(",接收节点代码为：");
			afterData.append(update_FtzOffMsgCtl.getDes());
			afterData.append(",应用名称为：");
			afterData.append(update_FtzOffMsgCtl.getApp());
			afterData.append(",报文编号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgNo());
			afterData.append(",报文参考号为：");
			afterData.append(update_FtzOffMsgCtl.getMsgRef());
			afterData.append(",工作日期为：");
			afterData.append(update_FtzOffMsgCtl.getWorkDate());
			afterData.append(",操作标识为：");
			afterData.append(update_FtzOffMsgCtl.getEditFlag());
			afterData.append(",预留字段为：");
			afterData.append(update_FtzOffMsgCtl.getReserve());
			afterData.append(",接收应答报文·处理结果为：");
			afterData.append(update_FtzOffMsgCtl.getResult());
			afterData.append(",接收应答报文·附言为：");
			afterData.append(update_FtzOffMsgCtl.getAddWord());
			afterData.append(",报文状态为：");
			afterData.append(update_FtzOffMsgCtl.getMsgStatus());
			afterData.append(",录入操作员为：");
			afterData.append(update_FtzOffMsgCtl.getMakUserId());
			afterData.append(",录入日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getMakDatetime());
			afterData.append(",复核操作员为：");
			afterData.append(update_FtzOffMsgCtl.getChkUserId());
			afterData.append(",复核日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getChkDatetime());
			afterData.append(",发送操作员为：");
			afterData.append(update_FtzOffMsgCtl.getSndUserId());
			afterData.append(",发送日期时间：");
			afterData.append(update_FtzOffMsgCtl.getSndDatetime());
			afterData.append(",接收应答日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getAckDatetime());
			afterData.append(",处理应答日期时间为：");
			afterData.append(update_FtzOffMsgCtl.getErrDatetime() + "的元素");
			
			TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "M",
					time.substring(0, 8), time.substring(8, 14),
					beforeData.toString(), afterData.toString());
			
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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("将报文标识号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMsgId());
		beforeData.append(",明细序号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSeqNo());
		beforeData.append(",报表编码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getReportCode());
		beforeData.append(",申报日期为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSubmitDate());
		beforeData.append(",所属机构代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccOrgCode());
		beforeData.append(",金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAmount());
		beforeData.append(",货币为：");
		beforeData.append(ftzOffTxnDtl_tmp.getCurrency());
		beforeData.append(",账号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccountNo());
		beforeData.append(",户名为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccountName());
		beforeData.append(",机构代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInstitutionCode());
		beforeData.append(",境内外对手行代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSwiftCode());
		beforeData.append(",国别代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getCountryCode());
		beforeData.append(",国内地区码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getDistrictCode());
		beforeData.append(",交易类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranType());
		beforeData.append(",期限长度为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermLength());
		beforeData.append(",期限单位为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermUnit());
		beforeData.append(",业务日期1为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranDate());
		beforeData.append(",业务日期2为：");
		beforeData.append(ftzOffTxnDtl_tmp.getExpirationDate());
		beforeData.append(",利率为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInterestRate());
		beforeData.append(",外汇买卖类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getExchangeType());
		beforeData.append(",买入币种为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyCurr());
		beforeData.append(",买入金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyAmt());
		beforeData.append(",买入牌价为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyRate());
		beforeData.append(",卖出币种为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellCurr());
		beforeData.append(",卖出金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellAmt());
		beforeData.append(",卖出牌价为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellRate());
		beforeData.append(",债券代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBondsCode());
		beforeData.append(",债券名称为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBondsName());
		beforeData.append(",利率类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInterestType());
		beforeData.append(",定价基准为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBenchmark());
		beforeData.append(",浮动点差为：");
		beforeData.append(ftzOffTxnDtl_tmp.getFloatRate());
		beforeData.append(",期限条件为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermCondition());
		beforeData.append(",保函类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getLgType());
		beforeData.append(",结售汇交易类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranGenre());
		beforeData.append(",记录复核状态为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkDatetime());
		beforeData.append(",记录复核附言为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkAddWord() + "的元素");
		
		StringBuffer afterData = new StringBuffer();
		afterData.append("更新为报文标识号为：");
		afterData.append(ftzOffTxnDtl.getMsgId());
		afterData.append(",明细序号为：");
		afterData.append(ftzOffTxnDtl.getSeqNo());
		afterData.append(",报表编码为：");
		afterData.append(ftzOffTxnDtl.getReportCode());
		afterData.append(",申报日期为：");
		afterData.append(ftzOffTxnDtl.getSubmitDate());
		afterData.append(",所属机构代码为：");
		afterData.append(ftzOffTxnDtl.getAccOrgCode());
		afterData.append(",金额为：");
		afterData.append(ftzOffTxnDtl.getAmount());
		afterData.append(",货币为：");
		afterData.append(ftzOffTxnDtl.getCurrency());
		afterData.append(",账号为：");
		afterData.append(ftzOffTxnDtl.getAccountNo());
		afterData.append(",户名为：");
		afterData.append(ftzOffTxnDtl.getAccountName());
		afterData.append(",机构代码为：");
		afterData.append(ftzOffTxnDtl.getInstitutionCode());
		afterData.append(",境内外对手行代码为：");
		afterData.append(ftzOffTxnDtl.getSwiftCode());
		afterData.append(",国别代码为：");
		afterData.append(ftzOffTxnDtl.getCountryCode());
		afterData.append(",国内地区码为：");
		afterData.append(ftzOffTxnDtl.getDistrictCode());
		afterData.append(",交易类型为：");
		afterData.append(ftzOffTxnDtl.getTranType());
		afterData.append(",期限长度为：");
		afterData.append(ftzOffTxnDtl.getTermLength());
		afterData.append(",期限单位为：");
		afterData.append(ftzOffTxnDtl.getTermUnit());
		afterData.append(",业务日期1为：");
		afterData.append(ftzOffTxnDtl.getTranDate());
		afterData.append(",业务日期2为：");
		afterData.append(ftzOffTxnDtl.getExpirationDate());
		afterData.append(",利率为：");
		afterData.append(ftzOffTxnDtl.getInterestRate());
		afterData.append(",外汇买卖类型为：");
		afterData.append(ftzOffTxnDtl.getExchangeType());
		afterData.append(",买入币种为：");
		afterData.append(ftzOffTxnDtl.getBuyCurr());
		afterData.append(",买入金额为：");
		afterData.append(ftzOffTxnDtl.getBuyAmt());
		afterData.append(",买入牌价为：");
		afterData.append(ftzOffTxnDtl.getBuyRate());
		afterData.append(",卖出币种为：");
		afterData.append(ftzOffTxnDtl.getSellCurr());
		afterData.append(",卖出金额为：");
		afterData.append(ftzOffTxnDtl.getSellAmt());
		afterData.append(",卖出牌价为：");
		afterData.append(ftzOffTxnDtl.getSellRate());
		afterData.append(",债券代码为：");
		afterData.append(ftzOffTxnDtl.getBondsCode());
		afterData.append(",债券名称为：");
		afterData.append(ftzOffTxnDtl.getBondsName());
		afterData.append(",利率类型为：");
		afterData.append(ftzOffTxnDtl.getInterestType());
		afterData.append(",定价基准为：");
		afterData.append(ftzOffTxnDtl.getBenchmark());
		afterData.append(",浮动点差为：");
		afterData.append(ftzOffTxnDtl.getFloatRate());
		afterData.append(",期限条件为：");
		afterData.append(ftzOffTxnDtl.getTermCondition());
		afterData.append(",保函类型为：");
		afterData.append(ftzOffTxnDtl.getLgType());
		afterData.append(",结售汇交易类型为：");
		afterData.append(ftzOffTxnDtl.getTranGenre());
		afterData.append(",记录复核状态为：");
		afterData.append(ftzOffTxnDtl.getChkStatus());
		afterData.append(",录入操作员为：");
		afterData.append(ftzOffTxnDtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(ftzOffTxnDtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(ftzOffTxnDtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(ftzOffTxnDtl.getChkDatetime());
		afterData.append(",记录复核附言为：");
		afterData.append(ftzOffTxnDtl.getChkAddWord() + "的元素");
		
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "M",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), afterData.toString());
		
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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了报文标识号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMsgId());
		beforeData.append(",明细序号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSeqNo());
		beforeData.append(",报表编码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getReportCode());
		beforeData.append(",申报日期为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSubmitDate());
		beforeData.append(",所属机构代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccOrgCode());
		beforeData.append(",金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAmount());
		beforeData.append(",货币为：");
		beforeData.append(ftzOffTxnDtl_tmp.getCurrency());
		beforeData.append(",账号为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccountNo());
		beforeData.append(",户名为：");
		beforeData.append(ftzOffTxnDtl_tmp.getAccountName());
		beforeData.append(",机构代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInstitutionCode());
		beforeData.append(",境内外对手行代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSwiftCode());
		beforeData.append(",国别代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getCountryCode());
		beforeData.append(",国内地区码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getDistrictCode());
		beforeData.append(",交易类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranType());
		beforeData.append(",期限长度为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermLength());
		beforeData.append(",期限单位为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermUnit());
		beforeData.append(",业务日期1为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranDate());
		beforeData.append(",业务日期2为：");
		beforeData.append(ftzOffTxnDtl_tmp.getExpirationDate());
		beforeData.append(",利率为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInterestRate());
		beforeData.append(",外汇买卖类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getExchangeType());
		beforeData.append(",买入币种为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyCurr());
		beforeData.append(",买入金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyAmt());
		beforeData.append(",买入牌价为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBuyRate());
		beforeData.append(",卖出币种为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellCurr());
		beforeData.append(",卖出金额为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellAmt());
		beforeData.append(",卖出牌价为：");
		beforeData.append(ftzOffTxnDtl_tmp.getSellRate());
		beforeData.append(",债券代码为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBondsCode());
		beforeData.append(",债券名称为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBondsName());
		beforeData.append(",利率类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getInterestType());
		beforeData.append(",定价基准为：");
		beforeData.append(ftzOffTxnDtl_tmp.getBenchmark());
		beforeData.append(",浮动点差为：");
		beforeData.append(ftzOffTxnDtl_tmp.getFloatRate());
		beforeData.append(",期限条件为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTermCondition());
		beforeData.append(",保函类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getLgType());
		beforeData.append(",结售汇交易类型为：");
		beforeData.append(ftzOffTxnDtl_tmp.getTranGenre());
		beforeData.append(",记录复核状态为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzOffTxnDtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkDatetime());
		beforeData.append(",记录复核附言为：");
		beforeData.append(ftzOffTxnDtl_tmp.getChkAddWord() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), "");
		
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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了报文标识号为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMsgId());
		beforeData.append(",机构ID为：");
		beforeData.append(ftzOffMsgCtl_tmp.getBranchId());
		beforeData.append(",版本号为：");
		beforeData.append(ftzOffMsgCtl_tmp.getVer());
		beforeData.append(",发起节点代码为：");
		beforeData.append(ftzOffMsgCtl_tmp.getSrc());
		beforeData.append(",接收节点代码为：");
		beforeData.append(ftzOffMsgCtl_tmp.getDes());
		beforeData.append(",应用名称为：");
		beforeData.append(ftzOffMsgCtl_tmp.getApp());
		beforeData.append(",报文编号为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMsgNo());
		beforeData.append(",报文参考号为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMsgRef());
		beforeData.append(",工作日期为：");
		beforeData.append(ftzOffMsgCtl_tmp.getWorkDate());
		beforeData.append(",操作标识为：");
		beforeData.append(ftzOffMsgCtl_tmp.getEditFlag());
		beforeData.append(",预留字段为：");
		beforeData.append(ftzOffMsgCtl_tmp.getReserve());
		beforeData.append(",接收应答报文·处理结果为：");
		beforeData.append(ftzOffMsgCtl_tmp.getResult());
		beforeData.append(",接收应答报文·附言为：");
		beforeData.append(ftzOffMsgCtl_tmp.getAddWord());
		beforeData.append(",报文状态为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMsgStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzOffMsgCtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzOffMsgCtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzOffMsgCtl_tmp.getChkDatetime());
		beforeData.append(",发送操作员为：");
		beforeData.append(ftzOffMsgCtl_tmp.getSndUserId());
		beforeData.append(",发送日期时间：");
		beforeData.append(ftzOffMsgCtl_tmp.getSndDatetime());
		beforeData.append(",接收应答日期时间为：");
		beforeData.append(ftzOffMsgCtl_tmp.getAckDatetime());
		beforeData.append(",处理应答日期时间为：");
		beforeData.append(ftzOffMsgCtl_tmp.getErrDatetime() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210310", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), "");
		
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
	
}
