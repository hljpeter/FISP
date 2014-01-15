package com.synesoft.ftzmis.domain.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.util.Validator;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * 6.3.1　代理发债（210301）
 * @author yyw
 * @date 2013-12-25
 */
@Service("ftz210301Service")
public class FTZ210301ServiceImp extends FTZOffCommonServiceImp {
	private static final Logger log = LoggerFactory.getLogger(FTZ210301ServiceImp.class);

	private String msgNo;

	public FTZ210301ServiceImp() {
		super.funcId = CommonConst.FUNCTION_FTZ_Add_210301;
		msgNo = CommonConst.MSG_NO_210301;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#addMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void addMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210301ServiceImp.addMsgLogic() start ...");

		ftzOffMsgCtl.setMsgNo(msgNo);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#updateMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void updateMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210301ServiceImp.updateMsgLogic() start ...");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#deleteMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void deleteMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210301ServiceImp.deleteMsgLogic() start ...");
		
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#addTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void addTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210301ServiceImp.addTxnLogic() start ...");

		ResultMessages messages = ResultMessages.error();

		String countryCode = StringUtil.trim(ftzOffTxnDtl.getCountryCode());
		String districtCode = StringUtil.trim(ftzOffTxnDtl.getDistrictCode());
		// 对方机构为境外机构时，国内地区码必填
		if (!Validator.CheckDistrictCode(countryCode, districtCode)) {
			log.error("[e.ftzmis.210301.0016] DistrictCode cannot be empty!"); 
			messages.add("e.ftzmis.210301.0016");								
			throw new BusinessException(messages);
		}

		// 利率类型为固定利率时，固定利率，定价基准，浮动点差不能为空
		if (CommonConst.INTEREST_TYPE_FIXED.equals(ftzOffTxnDtl.getInterestType())) {
			if (null == ftzOffTxnDtl.getInterestRate() || 
					null == ftzOffTxnDtl.getFloatRate() || 
					!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getBenchmark())) {
				log.error("[e.ftzmis.210301.0015] INTEREST_RATE, BENCHMARK, FLOAT_RATE cannot be empty!"); 
				messages.add("e.ftzmis.210301.0015");								
				throw new BusinessException(messages);
			}
		}

		// 校验金额
		BigDecimal amount = ftzOffTxnDtl.getAmount();
		if (!Validator.CheckAmount(amount)) {
			log.error("[e.ftzmis.210301.0017] The format of amount is wrong!"); 
			messages.add("e.ftzmis.210301.0017");								
			throw new BusinessException(messages);
		}
		
		ftzOffTxnDtl.setReportCode(msgNo);
		ftzOffTxnDtl.setCountryCode(countryCode);
		ftzOffTxnDtl.setDistrictCode(districtCode);
		ftzOffTxnDtl.setAccOrgCode(StringUtil.trim(ftzOffTxnDtl.getAccOrgCode()));
		ftzOffTxnDtl.setInstitutionCode(StringUtil.trim(ftzOffTxnDtl.getInstitutionCode()));
		ftzOffTxnDtl.setSwiftCode(StringUtil.trim(ftzOffTxnDtl.getSwiftCode()));
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#updateTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void updateTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210301ServiceImp.updateTxnLogic() start ...");
		
		ResultMessages messages = ResultMessages.error();

		String countryCode = ftzOffTxnDtl.getCountryCode();
		String districtCode = ftzOffTxnDtl.getDistrictCode();
		// 对方机构为境外机构时，国内地区码必填
		if (!Validator.CheckDistrictCode(countryCode, districtCode)) {
			log.error("[e.ftzmis.210301.0016] DistrictCode cannot be empty!"); 
			messages.add("e.ftzmis.210301.0016");								
			throw new BusinessException(messages);
		}

		// 利率类型为固定利率时，固定利率，定价基准，浮动点差不能为空
		if (CommonConst.INTEREST_TYPE_FIXED.equals(ftzOffTxnDtl.getInterestType())) {
			if (null == ftzOffTxnDtl.getInterestRate() || 
					null == ftzOffTxnDtl.getFloatRate() || 
					!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getBenchmark())) {
				log.error("[e.ftzmis.210301.0015] INTEREST_RATE, BENCHMARK, FLOAT_RATE cannot be empty!"); 
				messages.add("e.ftzmis.210301.0015");								
				throw new BusinessException(messages);
			}
		}

		// 校验金额
		BigDecimal amount = ftzOffTxnDtl.getAmount();
		if (!Validator.CheckAmount(amount)) {
			log.error("[e.ftzmis.210301.0017] The format of amount is wrong!"); 
			messages.add("e.ftzmis.210301.0017");								
			throw new BusinessException(messages);
		}
		
		ftzOffTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getMakDatetime()));
		ftzOffTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getChkDatetime()));
		ftzOffTxnDtl.setCountryCode(countryCode);
		ftzOffTxnDtl.setDistrictCode(districtCode);
		ftzOffTxnDtl.setAccOrgCode(StringUtil.trim(ftzOffTxnDtl.getAccOrgCode()));
		ftzOffTxnDtl.setInstitutionCode(StringUtil.trim(ftzOffTxnDtl.getInstitutionCode()));
		ftzOffTxnDtl.setSwiftCode(StringUtil.trim(ftzOffTxnDtl.getSwiftCode()));
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#deleteTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void deleteTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210301ServiceImp.deleteTxnLogic() start ...");
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#checkTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void checkTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210301ServiceImp.deleteTxnLogic() start ...");
		super.funcId = CommonConst.FUNCTION_FTZ_AUTH_2103;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#validateTxn(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void validateTxn(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210301ServiceImp.validateTxn() start ...");

		ResultMessages messages = ResultMessages.error();
	
		// 债券代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getBondsCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0001");
			messages.addAll(msg);
		}
		// 债券名称
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getBondsName())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0002");
			messages.addAll(msg);
		}
		// 发债企业账号
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getAccountNo())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0003");
			messages.addAll(msg);
		}
		// 货币
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getCurrency())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0004");
			messages.addAll(msg);
		}
		// 申报日期
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getSubmitDate())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0005");
			messages.addAll(msg);
		}
		// 交易机构代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getInstitutionCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0006");
			messages.addAll(msg);
		}
		// 结算日期
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getTranDate())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0007");
			messages.addAll(msg);
		}
		// 期限长度
		if (null == ftzOffTxnDtl.getTermLength()) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0008");
			messages.addAll(msg);
		}
		// 期限单位
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getTermUnit())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0009");
			messages.addAll(msg);
		}
		// 到期日
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getExpirationDate())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0010");
			messages.addAll(msg);
		}
		// 发行总量
		if (ftzOffTxnDtl.getAmount() == null) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0011");
			messages.addAll(msg);
		}
		// 国别代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getCountryCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0012");
			messages.addAll(msg);
		}
		// 交易性质
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getTranType())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0013");
			messages.addAll(msg);
		}
		// 利率类型
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getInterestType())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210301.0014");
			messages.addAll(msg);
		}
		if (messages.isNotEmpty()) {
			throw new BusinessException(messages);
		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#validateMsg(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void validateMsg(FtzOffMsgCtl ftzOffMsgCtl) {
	}

	
	
}
