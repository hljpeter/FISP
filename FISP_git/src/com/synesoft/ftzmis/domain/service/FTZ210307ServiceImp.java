package com.synesoft.ftzmis.domain.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.util.Validator;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * 6.3.7　应收保函/备用证（210307）
 * @author yyw
 * @date 2014-01-07
 */
@Service("ftz210307Service")
public class FTZ210307ServiceImp extends FTZOffCommonServiceImp {
	private static final Logger log = LoggerFactory.getLogger(FTZ210307ServiceImp.class);
	
	private String msgNo;

	public FTZ210307ServiceImp() {
		super.funcId = CommonConst.FUNCTION_FTZ_Add_210305;
		msgNo = CommonConst.MSG_NO_210307;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#addMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public void addMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210307ServiceImp.addMsgLogic() start ...");

		ftzOffMsgCtl.setMsgNo(msgNo);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#updateMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void updateMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210307ServiceImp.updateMsgLogic() start ...");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#deleteMsgLogic(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	protected void deleteMsgLogic(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZ210307ServiceImp.deleteMsgLogic() start ...");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#deleteTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void deleteTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210307ServiceImp.deleteTxnLogic() start ...");
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#addTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void addTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210307ServiceImp.addTxnLogic() start ...");

		ResultMessages messages = ResultMessages.error();

		String countryCode = ftzOffTxnDtl.getCountryCode();
		String districtCode = ftzOffTxnDtl.getDistrictCode();
		// 对方机构为境外机构时，国内地区码必填
		if (!Validator.CheckDistrictCode(countryCode, districtCode)) {
			log.error("[e.ftzmis.210307.0011] GuarantorDistrictCode cannot be empty!"); 
			messages.add("e.ftzmis.210307.0011");									
			throw new BusinessException(messages);
		}

		// 校验所属机构代码是否为本行机构
		String accOrgCode = StringUtil.trim(ftzOffTxnDtl.getAccOrgCode());
		if (!Validator.CheckAccOrgCode(orgInfRepository, accOrgCode)) {
			log.error("[e.ftzmis.210307.0012] AccOrgCode is not belong to this bank!"); 
			messages.add("e.ftzmis.210307.0012");								
			throw new BusinessException(messages);
		}

		// 校验金额
		BigDecimal amount = ftzOffTxnDtl.getAmount();
		if (!Validator.CheckAmount(amount)) {
			log.error("[e.ftzmis.210307.0013] The format of amount is wrong!"); 
			messages.add("e.ftzmis.210307.0013");								
			throw new BusinessException(messages);
		}
		
		ftzOffTxnDtl.setReportCode(msgNo);
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
		ftzOffTxnDtl.setCountryCode(countryCode);
		ftzOffTxnDtl.setDistrictCode(districtCode);
		ftzOffTxnDtl.setAccOrgCode(accOrgCode);
		ftzOffTxnDtl.setInstitutionCode(StringUtil.trim(ftzOffTxnDtl.getInstitutionCode()));
		ftzOffTxnDtl.setSwiftCode(StringUtil.trim(ftzOffTxnDtl.getSwiftCode()));
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#updateTxnLogic(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	protected void updateTxnLogic(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210307ServiceImp.updateTxnLogic() start ...");

		ResultMessages messages = ResultMessages.error();

		String countryCode = ftzOffTxnDtl.getCountryCode();
		String districtCode = ftzOffTxnDtl.getDistrictCode();
		// 对方机构为境外机构时，国内地区码必填
		if (!Validator.CheckDistrictCode(countryCode, districtCode)) {
			log.error("[e.ftzmis.210307.0011] GuarantorDistrictCode cannot be empty!"); 
			messages.add("e.ftzmis.210307.0011");								
			throw new BusinessException(messages);
		}

		// 校验所属机构代码是否为本行机构
		String accOrgCode = StringUtil.trim(ftzOffTxnDtl.getAccOrgCode());
		if (!Validator.CheckAccOrgCode(orgInfRepository, accOrgCode)) {
			log.error("[e.ftzmis.210307.0012] AccOrgCode is not belong to this bank!"); 
			messages.add("e.ftzmis.210307.0012");								
			throw new BusinessException(messages);
		}

		// 校验金额
		BigDecimal amount = ftzOffTxnDtl.getAmount();
		if (!Validator.CheckAmount(amount)) {
			log.error("[e.ftzmis.210307.0013] The format of amount is wrong!"); 
			messages.add("e.ftzmis.210307.0013");								
			throw new BusinessException(messages);
		}
		
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
		ftzOffTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getMakDatetime()));
		ftzOffTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getChkDatetime()));
		ftzOffTxnDtl.setCountryCode(countryCode);
		ftzOffTxnDtl.setDistrictCode(districtCode);
		ftzOffTxnDtl.setAccOrgCode(accOrgCode);
		ftzOffTxnDtl.setInstitutionCode(StringUtil.trim(ftzOffTxnDtl.getInstitutionCode()));
		ftzOffTxnDtl.setSwiftCode(StringUtil.trim(ftzOffTxnDtl.getSwiftCode()));
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonServiceImp#validateTxn(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Override
	protected void validateTxn(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZ210307ServiceImp.validateTxn() start ...");

		ResultMessages messages = ResultMessages.error();
		// 申报日期
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getSubmitDate())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0001");	
			messages.addAll(msg);
		}
		// 所属机构代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getAccOrgCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0002");								
			messages.addAll(msg);
		}
		// 金额
		if (ftzOffTxnDtl.getAmount() == null) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0003");								
			messages.addAll(msg);
		}
		// 货币
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getCurrency())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0004");								
			messages.addAll(msg);
		}
		// 境内外对手行代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getSwiftCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0005");								
			messages.addAll(msg);
		}
		// 担保权人名称
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getAccountName())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0006");								
			messages.addAll(msg);
		}
		// 担保权人机构代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getInstitutionCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0007");								
			messages.addAll(msg);
		}
		// 担保权人国别代码
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getCountryCode())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0008");								
			messages.addAll(msg);
		}
		// 保函到期日
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getExpirationDate())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0009");								
			messages.addAll(msg);
		}
		// 保函类型
		if (!StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getLgType())) {
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.210307.0010");								
			messages.addAll(msg);
		}
		if (messages.isNotEmpty()) {
			throw new BusinessException(messages);
		}
		
	}

	@Autowired
	protected OrgInfRepository orgInfRepository;
	
}
