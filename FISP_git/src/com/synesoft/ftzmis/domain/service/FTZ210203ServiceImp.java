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
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FTZ210203Repository;

@Service
public class FTZ210203ServiceImp implements FTZ210203Service {

	@Override
	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		return ftz210203Repos.queryFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210203Repos.queryFtzInMsgCtlPageInput(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl) {
		return ftz210203Repos.queryFtzInMsgCtlPage(pageable, ftzInMsgCtl);
	}

	@Override
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,
			FtzInTxnDtl ftzInTxnDtl) {
		return ftz210203Repos.queryFtzInTxnDtlPage(pageable, ftzInTxnDtl);
	}

	@Override
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210203Repos.queryFtzInTxnDtl(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		ftz210203Repos.deleteFtzInTxnDtls(ftzInTxnDtl);

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		FtzInMsgCtl ftzMsgCtl_tmp = this.queryFtzInMsgCtl(query_FtzInMsgCtl);
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了报文标识号为：");
		beforeData.append(ftzMsgCtl_tmp.getMsgId());
		beforeData.append(",机构ID为：");
		beforeData.append(ftzMsgCtl_tmp.getBranchId());
		beforeData.append(",版本号为：");
		beforeData.append(ftzMsgCtl_tmp.getVer());
		beforeData.append(",发起节点代码为：");
		beforeData.append(ftzMsgCtl_tmp.getSrc());
		beforeData.append(",接收节点代码为：");
		beforeData.append(ftzMsgCtl_tmp.getDes());
		beforeData.append(",应用名称为：");
		beforeData.append(ftzMsgCtl_tmp.getApp());
		beforeData.append(",报文编号为：");
		beforeData.append(ftzMsgCtl_tmp.getMsgNo());
		beforeData.append(",报文参考号为：");
		beforeData.append(ftzMsgCtl_tmp.getMsgRef());
		beforeData.append(",工作日期为：");
		beforeData.append(ftzMsgCtl_tmp.getWorkDate());
		beforeData.append(",操作标识为：");
		beforeData.append(ftzMsgCtl_tmp.getEditFlag());
		beforeData.append(",预留字段为：");
		beforeData.append(ftzMsgCtl_tmp.getReserve());
		beforeData.append(",主帐号/账号为：");
		beforeData.append(ftzMsgCtl_tmp.getAccountNo());
		beforeData.append(",货币为：");
		beforeData.append(ftzMsgCtl_tmp.getCurrency());
		beforeData.append(",日终余额为：");
		beforeData.append(ftzMsgCtl_tmp.getBalance());
		beforeData.append(",资产负债指标代码为：");
		beforeData.append(ftzMsgCtl_tmp.getBalanceCode());
		beforeData.append(",申报日期为：");
		beforeData.append(ftzMsgCtl_tmp.getSubmitDate());
		beforeData.append(",总记录条数为：");
		beforeData.append(ftzMsgCtl_tmp.getTotalCount());
		beforeData.append(",所属机构代码为：");
		beforeData.append(ftzMsgCtl_tmp.getAccOrgCode());
		beforeData.append(",户名为：");
		beforeData.append(ftzMsgCtl_tmp.getAccountName());
		beforeData.append(",类别为：");
		beforeData.append(",接收应答报文·处理结果为：");
		beforeData.append(ftzMsgCtl_tmp.getResult());
		beforeData.append(",接收应答报文·附言为：");
		beforeData.append(ftzMsgCtl_tmp.getAddWord());
		beforeData.append(",报文状态为：");
		beforeData.append(ftzMsgCtl_tmp.getMsgStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzMsgCtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzMsgCtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzMsgCtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzMsgCtl_tmp.getChkDatetime());
		beforeData.append(",发送操作员为：");
		beforeData.append(ftzMsgCtl_tmp.getSndUserId());
		beforeData.append(",发送日期时间：");
		beforeData.append(ftzMsgCtl_tmp.getSndDatetime());
		beforeData.append(",接收应答日期时间为：");
		beforeData.append(ftzMsgCtl_tmp.getAckDatetime());
		beforeData.append(",处理应答日期时间为：");
		beforeData.append(ftzMsgCtl_tmp.getErrDatetime() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210101", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), "");

		int i = ftz210203Repos.deleteFtzInMsgCtl(ftzInMsgCtl);
		return i;
	}

	@Override
	@Transactional
	public int insertFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer afterData = new StringBuffer();
		afterData.append("新增了报文标识号为：");
		afterData.append(ftzInMsgCtl.getMsgId());
		afterData.append(",机构ID为：");
		afterData.append(ftzInMsgCtl.getBranchId());
		afterData.append(",版本号为：");
		afterData.append(ftzInMsgCtl.getVer());
		afterData.append(",发起节点代码为：");
		afterData.append(ftzInMsgCtl.getSrc());
		afterData.append(",接收节点代码为：");
		afterData.append(ftzInMsgCtl.getDes());
		afterData.append(",应用名称为：");
		afterData.append(ftzInMsgCtl.getApp());
		afterData.append(",报文编号为：");
		afterData.append(ftzInMsgCtl.getMsgNo());
		afterData.append(",报文参考号为：");
		afterData.append(ftzInMsgCtl.getMsgRef());
		afterData.append(",工作日期为：");
		afterData.append(ftzInMsgCtl.getWorkDate());
		afterData.append(",操作标识为：");
		afterData.append(ftzInMsgCtl.getEditFlag());
		afterData.append(",预留字段为：");
		afterData.append(ftzInMsgCtl.getReserve());
		afterData.append(",主帐号/账号为：");
		afterData.append(ftzInMsgCtl.getAccountNo());
		afterData.append(",货币为：");
		afterData.append(ftzInMsgCtl.getCurrency());
		afterData.append(",日终余额为：");
		afterData.append(ftzInMsgCtl.getBalance());
		afterData.append(",资产负债指标代码为：");
		afterData.append(ftzInMsgCtl.getBalanceCode());
		afterData.append(",申报日期为：");
		afterData.append(ftzInMsgCtl.getSubmitDate());
		afterData.append(",总记录条数为：");
		afterData.append(ftzInMsgCtl.getTotalCount());
		afterData.append(",所属机构代码为：");
		afterData.append(ftzInMsgCtl.getAccOrgCode());
		afterData.append(",户名为：");
		afterData.append(ftzInMsgCtl.getAccountName());
		afterData.append(",接收应答报文·处理结果为：");
		afterData.append(ftzInMsgCtl.getResult());
		afterData.append(",接收应答报文·附言为：");
		afterData.append(ftzInMsgCtl.getAddWord());
		afterData.append(",报文状态为：");
		afterData.append(ftzInMsgCtl.getMsgStatus());
		afterData.append(",录入操作员为：");
		afterData.append(ftzInMsgCtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(ftzInMsgCtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(ftzInMsgCtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(ftzInMsgCtl.getChkDatetime());
		afterData.append(",发送操作员为：");
		afterData.append(ftzInMsgCtl.getSndUserId());
		afterData.append(",发送日期时间：");
		afterData.append(ftzInMsgCtl.getSndDatetime());
		afterData.append(",接收应答日期时间为：");
		afterData.append(ftzInMsgCtl.getAckDatetime());
		afterData.append(",处理应答日期时间为：");
		afterData.append(ftzInMsgCtl.getErrDatetime() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210101", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());
		return ftz210203Repos.insertFtzInMsgCtl(ftzInMsgCtl);
	}

	@Override
	@Transactional
	public int updateFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl,
			List<FtzInTxnDtl> ftzInTxnDtls) {
		if (null == ftzInTxnDtls) {
			FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
			query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
			FtzInMsgCtl ftzMsgCtl_tmp = this
					.queryFtzInMsgCtl(query_FtzInMsgCtl);
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNowInputDateTime();
			StringBuffer beforeData = new StringBuffer();
			beforeData.append("将报文标识号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgId());
			beforeData.append(",机构ID为：");
			beforeData.append(ftzMsgCtl_tmp.getBranchId());
			beforeData.append(",版本号为：");
			beforeData.append(ftzMsgCtl_tmp.getVer());
			beforeData.append(",发起节点代码为：");
			beforeData.append(ftzMsgCtl_tmp.getSrc());
			beforeData.append(",接收节点代码为：");
			beforeData.append(ftzMsgCtl_tmp.getDes());
			beforeData.append(",应用名称为：");
			beforeData.append(ftzMsgCtl_tmp.getApp());
			beforeData.append(",报文编号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgNo());
			beforeData.append(",报文参考号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgRef());
			beforeData.append(",工作日期为：");
			beforeData.append(ftzMsgCtl_tmp.getWorkDate());
			beforeData.append(",操作标识为：");
			beforeData.append(ftzMsgCtl_tmp.getEditFlag());
			beforeData.append(",预留字段为：");
			beforeData.append(ftzMsgCtl_tmp.getReserve());
			beforeData.append(",主帐号/账号为：");
			beforeData.append(ftzMsgCtl_tmp.getAccountNo());
			beforeData.append(",货币为：");
			beforeData.append(ftzMsgCtl_tmp.getCurrency());
			beforeData.append(",日终余额为：");
			beforeData.append(ftzMsgCtl_tmp.getBalance());
			beforeData.append(",资产负债指标代码为：");
			beforeData.append(ftzMsgCtl_tmp.getBalanceCode());
			beforeData.append(",申报日期为：");
			beforeData.append(ftzMsgCtl_tmp.getSubmitDate());
			beforeData.append(",总记录条数为：");
			beforeData.append(ftzMsgCtl_tmp.getTotalCount());
			beforeData.append(",所属机构代码为：");
			beforeData.append(ftzMsgCtl_tmp.getAccOrgCode());
			beforeData.append(",户名为：");
			beforeData.append(ftzMsgCtl_tmp.getAccountName());
			beforeData.append(",类别为：");
			beforeData.append(ftzMsgCtl_tmp.getAccType());;
			beforeData.append(",接收应答报文·处理结果为：");
			beforeData.append(ftzMsgCtl_tmp.getResult());
			beforeData.append(",接收应答报文·附言为：");
			beforeData.append(ftzMsgCtl_tmp.getAddWord());
			beforeData.append(",报文状态为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgStatus());
			beforeData.append(",录入操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getMakUserId());
			beforeData.append(",录入日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getMakDatetime());
			beforeData.append(",复核操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getChkUserId());
			beforeData.append(",复核日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getChkDatetime());
			beforeData.append(",发送操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getSndUserId());
			beforeData.append(",发送日期时间：");
			beforeData.append(ftzMsgCtl_tmp.getSndDatetime());
			beforeData.append(",接收应答日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getAckDatetime());
			beforeData.append(",处理应答日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getErrDatetime() + "的元素");

			StringBuffer afterData = new StringBuffer();
			afterData.append("更新了报文标识号为：");
			afterData.append(ftzInMsgCtl.getMsgId());
			afterData.append(",机构ID为：");
			afterData.append(ftzInMsgCtl.getBranchId());
			afterData.append(",版本号为：");
			afterData.append(ftzInMsgCtl.getVer());
			afterData.append(",发起节点代码为：");
			afterData.append(ftzInMsgCtl.getSrc());
			afterData.append(",接收节点代码为：");
			afterData.append(ftzInMsgCtl.getDes());
			afterData.append(",应用名称为：");
			afterData.append(ftzInMsgCtl.getApp());
			afterData.append(",报文编号为：");
			afterData.append(ftzInMsgCtl.getMsgNo());
			afterData.append(",报文参考号为：");
			afterData.append(ftzInMsgCtl.getMsgRef());
			afterData.append(",工作日期为：");
			afterData.append(ftzInMsgCtl.getWorkDate());
			afterData.append(",操作标识为：");
			afterData.append(ftzInMsgCtl.getEditFlag());
			afterData.append(",预留字段为：");
			afterData.append(ftzInMsgCtl.getReserve());
			afterData.append(",主帐号/账号为：");
			afterData.append(ftzInMsgCtl.getAccountNo());
			afterData.append(",货币为：");
			afterData.append(ftzInMsgCtl.getCurrency());
			afterData.append(",日终余额为：");
			afterData.append(ftzInMsgCtl.getBalance());
			afterData.append(",资产负债指标代码为：");
			afterData.append(ftzInMsgCtl.getBalanceCode());
			afterData.append(",申报日期为：");
			afterData.append(ftzInMsgCtl.getSubmitDate());
			afterData.append(",总记录条数为：");
			afterData.append(ftzInMsgCtl.getTotalCount());
			afterData.append(",所属机构代码为：");
			afterData.append(ftzInMsgCtl.getAccOrgCode());
			afterData.append(",户名为：");
			afterData.append(ftzInMsgCtl.getAccountName());
			afterData.append(",接收应答报文·处理结果为：");
			afterData.append(ftzInMsgCtl.getResult());
			afterData.append(",接收应答报文·附言为：");
			afterData.append(ftzInMsgCtl.getAddWord());
			afterData.append(",报文状态为：");
			afterData.append(ftzInMsgCtl.getMsgStatus());
			afterData.append(",录入操作员为：");
			afterData.append(ftzInMsgCtl.getMakUserId());
			afterData.append(",录入日期时间为：");
			afterData.append(ftzInMsgCtl.getMakDatetime());
			afterData.append(",复核操作员为：");
			afterData.append(ftzInMsgCtl.getChkUserId());
			afterData.append(",复核日期时间为：");
			afterData.append(ftzInMsgCtl.getChkDatetime());
			afterData.append(",发送操作员为：");
			afterData.append(ftzInMsgCtl.getSndUserId());
			afterData.append(",发送日期时间：");
			afterData.append(ftzInMsgCtl.getSndDatetime());
			afterData.append(",接收应答日期时间为：");
			afterData.append(ftzInMsgCtl.getAckDatetime());
			afterData.append(",处理应答日期时间为：");
			afterData.append(ftzInMsgCtl.getErrDatetime() + "的元素");

			TlrLogPrint.tlrBizLogPrint("FTZ_Add_210101", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "M",
					time.substring(0, 8), time.substring(8, 14),
					beforeData.toString(), afterData.toString());

			return ftz210203Repos.updateFtzInMsgCtl(ftzInMsgCtl);
		} else {
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				this.updateFtzInTxnDtlSelective(ftzInTxnDtl);
			}
			FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
			query_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
			FtzInMsgCtl ftzMsgCtl_tmp = this
					.queryFtzInMsgCtl(query_FtzInMsgCtl);
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNowInputDateTime();
			StringBuffer beforeData = new StringBuffer();
			beforeData.append("将报文标识号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgId());
			beforeData.append(",机构ID为：");
			beforeData.append(ftzMsgCtl_tmp.getBranchId());
			beforeData.append(",版本号为：");
			beforeData.append(ftzMsgCtl_tmp.getVer());
			beforeData.append(",发起节点代码为：");
			beforeData.append(ftzMsgCtl_tmp.getSrc());
			beforeData.append(",接收节点代码为：");
			beforeData.append(ftzMsgCtl_tmp.getDes());
			beforeData.append(",应用名称为：");
			beforeData.append(ftzMsgCtl_tmp.getApp());
			beforeData.append(",报文编号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgNo());
			beforeData.append(",报文参考号为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgRef());
			beforeData.append(",工作日期为：");
			beforeData.append(ftzMsgCtl_tmp.getWorkDate());
			beforeData.append(",操作标识为：");
			beforeData.append(ftzMsgCtl_tmp.getEditFlag());
			beforeData.append(",预留字段为：");
			beforeData.append(ftzMsgCtl_tmp.getReserve());
			beforeData.append(",主帐号/账号为：");
			beforeData.append(ftzMsgCtl_tmp.getAccountNo());
			beforeData.append(",货币为：");
			beforeData.append(ftzMsgCtl_tmp.getCurrency());
			beforeData.append(",日终余额为：");
			beforeData.append(ftzMsgCtl_tmp.getBalance());
			beforeData.append(",资产负债指标代码为：");
			beforeData.append(ftzMsgCtl_tmp.getBalanceCode());
			beforeData.append(",申报日期为：");
			beforeData.append(ftzMsgCtl_tmp.getSubmitDate());
			beforeData.append(",总记录条数为：");
			beforeData.append(ftzMsgCtl_tmp.getTotalCount());
			beforeData.append(",所属机构代码为：");
			beforeData.append(ftzMsgCtl_tmp.getAccOrgCode());
			beforeData.append(",户名为：");
			beforeData.append(ftzMsgCtl_tmp.getAccountName());
			beforeData.append(",接收应答报文·处理结果为：");
			beforeData.append(ftzMsgCtl_tmp.getResult());
			beforeData.append(",接收应答报文·附言为：");
			beforeData.append(ftzMsgCtl_tmp.getAddWord());
			beforeData.append(",报文状态为：");
			beforeData.append(ftzMsgCtl_tmp.getMsgStatus());
			beforeData.append(",录入操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getMakUserId());
			beforeData.append(",录入日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getMakDatetime());
			beforeData.append(",复核操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getChkUserId());
			beforeData.append(",复核日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getChkDatetime());
			beforeData.append(",发送操作员为：");
			beforeData.append(ftzMsgCtl_tmp.getSndUserId());
			beforeData.append(",发送日期时间：");
			beforeData.append(ftzMsgCtl_tmp.getSndDatetime());
			beforeData.append(",接收应答日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getAckDatetime());
			beforeData.append(",处理应答日期时间为：");
			beforeData.append(ftzMsgCtl_tmp.getErrDatetime() + "的元素");

			StringBuffer afterData = new StringBuffer();
			afterData.append("更新了报文标识号为：");
			afterData.append(ftzInMsgCtl.getMsgId());
			afterData.append(",机构ID为：");
			afterData.append(ftzInMsgCtl.getBranchId());
			afterData.append(",版本号为：");
			afterData.append(ftzInMsgCtl.getVer());
			afterData.append(",发起节点代码为：");
			afterData.append(ftzInMsgCtl.getSrc());
			afterData.append(",接收节点代码为：");
			afterData.append(ftzInMsgCtl.getDes());
			afterData.append(",应用名称为：");
			afterData.append(ftzInMsgCtl.getApp());
			afterData.append(",报文编号为：");
			afterData.append(ftzInMsgCtl.getMsgNo());
			afterData.append(",报文参考号为：");
			afterData.append(ftzInMsgCtl.getMsgRef());
			afterData.append(",工作日期为：");
			afterData.append(ftzInMsgCtl.getWorkDate());
			afterData.append(",操作标识为：");
			afterData.append(ftzInMsgCtl.getEditFlag());
			afterData.append(",预留字段为：");
			afterData.append(ftzInMsgCtl.getReserve());
			afterData.append(",主帐号/账号为：");
			afterData.append(ftzInMsgCtl.getAccountNo());
			afterData.append(",货币为：");
			afterData.append(ftzInMsgCtl.getCurrency());
			afterData.append(",日终余额为：");
			afterData.append(ftzInMsgCtl.getBalance());
			afterData.append(",资产负债指标代码为：");
			afterData.append(ftzInMsgCtl.getBalanceCode());
			afterData.append(",申报日期为：");
			afterData.append(ftzInMsgCtl.getSubmitDate());
			afterData.append(",总记录条数为：");
			afterData.append(ftzInMsgCtl.getTotalCount());
			afterData.append(",所属机构代码为：");
			afterData.append(ftzInMsgCtl.getAccOrgCode());
			afterData.append(",户名为：");
			afterData.append(ftzInMsgCtl.getAccountName());
			afterData.append(",接收应答报文·处理结果为：");
			afterData.append(ftzInMsgCtl.getResult());
			afterData.append(",接收应答报文·附言为：");
			afterData.append(ftzInMsgCtl.getAddWord());
			afterData.append(",报文状态为：");
			afterData.append(ftzInMsgCtl.getMsgStatus());
			afterData.append(",录入操作员为：");
			afterData.append(ftzInMsgCtl.getMakUserId());
			afterData.append(",录入日期时间为：");
			afterData.append(ftzInMsgCtl.getMakDatetime());
			afterData.append(",复核操作员为：");
			afterData.append(ftzInMsgCtl.getChkUserId());
			afterData.append(",复核日期时间为：");
			afterData.append(ftzInMsgCtl.getChkDatetime());
			afterData.append(",发送操作员为：");
			afterData.append(ftzInMsgCtl.getSndUserId());
			afterData.append(",发送日期时间：");
			afterData.append(ftzInMsgCtl.getSndDatetime());
			afterData.append(",接收应答日期时间为：");
			afterData.append(ftzInMsgCtl.getAckDatetime());
			afterData.append(",处理应答日期时间为：");
			afterData.append(ftzInMsgCtl.getErrDatetime() + "的元素");

			TlrLogPrint.tlrBizLogPrint("FTZ_Add_210101", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "M",
					time.substring(0, 8), time.substring(8, 14),
					beforeData.toString(), afterData.toString());
			return ftz210203Repos.updateFtzInMsgCtl(ftzInMsgCtl);
		}

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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了报文标识号为：");
		beforeData.append(ftzInTxnDtl_tmp.getMsgId());
		beforeData.append(",明细序号为：");
		beforeData.append(ftzInTxnDtl_tmp.getSeqNo());
		beforeData.append(",出/入账标志为：");
		beforeData.append(ftzInTxnDtl_tmp.getCdFlag());
		beforeData.append(",记帐日期为：");
		beforeData.append(ftzInTxnDtl_tmp.getTranDate());
		beforeData.append(",原始交易日期为：");
		beforeData.append(ftzInTxnDtl_tmp.getOrgTranDate());
		beforeData.append(",金额为：");
		beforeData.append(ftzInTxnDtl_tmp.getAmount());
		beforeData.append(",对方账号为：");
		beforeData.append(ftzInTxnDtl_tmp.getOppAccount());
		beforeData.append(",对方户名/对方机构为：");
		beforeData.append(ftzInTxnDtl_tmp.getOppName());
		beforeData.append(",国别代码为：");
		beforeData.append(ftzInTxnDtl_tmp.getCountryCode());
		beforeData.append(",国内地区码为：");
		beforeData.append(ftzInTxnDtl_tmp.getDisitrictCode());
		beforeData.append(",交易性质为：");
		beforeData.append(ftzInTxnDtl_tmp.getTranType());
		beforeData.append(",记录复核状态为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzInTxnDtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzInTxnDtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkDatetime());
		beforeData.append(",记录复核附言为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkAddWord() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210203", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), "");

		return ftz210203Repos.deleteFtzInTxnDtl(ftzInTxnDtl);
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
		return ftz210203Repos.queryFtzActMstrs(ftzActMstr);
	}

	@Override
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl) {
		return ftz210203Repos.queryFtzInTxnDtlList(ftzInTxnDtl);
	}

	@Override
	@Transactional
	public int insertFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210203Repos
				.queryFtzInMsgCtl(ftzInMsgCtl);
		FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
		update_FtzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		update_FtzInMsgCtl
				.setTotalCount(result_FtzInMsgCtl.getTotalCount() + 1);
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftz210203Repos.updateFtzInMsgCtl(update_FtzInMsgCtl);

		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer afterData = new StringBuffer();
		afterData.append("新增了报文标识号为：");
		afterData.append(ftzInTxnDtl.getMsgId());
		afterData.append(",明细序号为：");
		afterData.append(ftzInTxnDtl.getSeqNo());
		afterData.append(",出/入账标志为：");
		afterData.append(ftzInTxnDtl.getCdFlag());
		afterData.append(",记帐日期为：");
		afterData.append(ftzInTxnDtl.getTranDate());
		afterData.append(",原始交易日期为：");
		afterData.append(ftzInTxnDtl.getOrgTranDate());
		afterData.append(",金额为：");
		afterData.append(ftzInTxnDtl.getAmount());
		afterData.append(",对方账号为：");
		afterData.append(ftzInTxnDtl.getOppAccount());
		afterData.append(",对方户名/对方机构为：");
		afterData.append(ftzInTxnDtl.getOppName());
		afterData.append(",国别代码为：");
		afterData.append(ftzInTxnDtl.getCountryCode());
		afterData.append(",国内地区码为：");
		afterData.append(ftzInTxnDtl.getDisitrictCode());
		afterData.append(",交易性质为：");
		afterData.append(ftzInTxnDtl.getTranType());		
		afterData.append(",记录复核状态为：");
		afterData.append(ftzInTxnDtl.getChkStatus());
		afterData.append(",录入操作员为：");
		afterData.append(ftzInTxnDtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(ftzInTxnDtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(ftzInTxnDtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(ftzInTxnDtl.getChkDatetime());
		afterData.append(",记录复核附言为：");
		afterData.append(ftzInTxnDtl.getChkAddWord() + "的元素");
		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210203", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());

		return ftz210203Repos.insertFtzInTxnDtl(ftzInTxnDtl);
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
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNowInputDateTime();
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("将报文标识号为：");
		beforeData.append(ftzInTxnDtl_tmp.getMsgId());
		beforeData.append(",明细序号为：");
		beforeData.append(ftzInTxnDtl_tmp.getSeqNo());
		beforeData.append(",出/入账标志为：");
		beforeData.append(ftzInTxnDtl_tmp.getCdFlag());
		beforeData.append(",记帐日期为：");
		beforeData.append(ftzInTxnDtl_tmp.getTranDate());
		beforeData.append(",原始交易日期为：");
		beforeData.append(ftzInTxnDtl_tmp.getOrgTranDate());
		beforeData.append(",金额为：");
		beforeData.append(ftzInTxnDtl_tmp.getAmount());
		beforeData.append(",对方账号为：");
		beforeData.append(ftzInTxnDtl_tmp.getOppAccount());
		beforeData.append(",对方户名/对方机构为：");
		beforeData.append(ftzInTxnDtl_tmp.getOppName());
		beforeData.append(",国别代码为：");
		beforeData.append(ftzInTxnDtl_tmp.getCountryCode());
		beforeData.append(",国内地区码为：");
		beforeData.append(ftzInTxnDtl_tmp.getDisitrictCode());
		beforeData.append(",交易性质为：");
		beforeData.append(ftzInTxnDtl_tmp.getTranType());
		beforeData.append(",记录复核状态为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkStatus());
		beforeData.append(",录入操作员为：");
		beforeData.append(ftzInTxnDtl_tmp.getMakUserId());
		beforeData.append(",录入日期时间为：");
		beforeData.append(ftzInTxnDtl_tmp.getMakDatetime());
		beforeData.append(",复核操作员为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkUserId());
		beforeData.append(",复核日期时间为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkDatetime());
		beforeData.append(",记录复核附言为：");
		beforeData.append(ftzInTxnDtl_tmp.getChkAddWord() + "的元素");

		StringBuffer afterData = new StringBuffer();
		afterData.append("更新为报文标识号为：");
		afterData.append(ftzInTxnDtl.getMsgId());
		afterData.append(",明细序号为：");
		afterData.append(ftzInTxnDtl.getSeqNo());
		afterData.append(",出/入账标志为：");
		afterData.append(ftzInTxnDtl.getCdFlag());
		afterData.append(",记帐日期为：");
		afterData.append(ftzInTxnDtl.getTranDate());
		afterData.append(",原始交易日期为：");
		afterData.append(ftzInTxnDtl.getOrgTranDate());
		afterData.append(",金额为：");
		afterData.append(ftzInTxnDtl.getAmount());
		afterData.append(",对方账号为：");
		afterData.append(ftzInTxnDtl.getOppAccount());
		afterData.append(",对方户名/对方机构为：");
		afterData.append(ftzInTxnDtl.getOppName());
		afterData.append(",国别代码为：");
		afterData.append(ftzInTxnDtl.getCountryCode());
		afterData.append(",国内地区码为：");
		afterData.append(ftzInTxnDtl.getDisitrictCode());
		afterData.append(",交易性质为：");
		afterData.append(ftzInTxnDtl.getTranType());
		afterData.append(",录入操作员为：");
		afterData.append(ftzInTxnDtl.getMakUserId());
		afterData.append(",录入日期时间为：");
		afterData.append(ftzInTxnDtl.getMakDatetime());
		afterData.append(",复核操作员为：");
		afterData.append(ftzInTxnDtl.getChkUserId());
		afterData.append(",复核日期时间为：");
		afterData.append(ftzInTxnDtl.getChkDatetime());
		afterData.append(",记录复核附言为：");
		afterData.append(ftzInTxnDtl.getChkAddWord() + "的元素");

		TlrLogPrint.tlrBizLogPrint("FTZ_Add_210101", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "M",
				time.substring(0, 8), time.substring(8, 14),
				beforeData.toString(), afterData.toString());

		return ftz210203Repos.updateFtzInTxnDtlSelective(ftzInTxnDtl);
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
		return ftz210203Repos.queryFtzBankCode(ftzBankCode);
	}

	@Resource
	protected FTZ210203Repository ftz210203Repos;
}
