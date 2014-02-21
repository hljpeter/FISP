package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;
import com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository;


/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
@Service
public class FTZMsgGenerateServiceImp implements FTZMsgGenerateService {

	private static LogUtil log = new LogUtil(FTZMsgGenerateServiceImp.class);

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZMsgGenerateService#getList(java.util.List)
	 */
	@Override
	public List<FtzMsgGenerateParam> getList(List<String> branchList) {
		log.debug("getList() start ...");

		ResultMessages messages = ResultMessages.error();
		
		FtzMsgGenerateParam param = new FtzMsgGenerateParam();
		param.setBranchs(branchList);
		param.setBlankFlag(CommonConst.MSG_BLANK_FLAG_NORMAL);
		
		List<FtzMsgGenerateParam> list = repository.queryList(param);
		if (null == list || list.isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZMsgGenerateService#getResultList(java.util.List)
	 */
	@Override
	public List<FtzMsgGenerateParam> getResultList(FtzMsgGenerateParam param) {
		
		List<FtzMsgGenerateParam> result = repository.queryMsgProcResult(param);

		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZMsgGenerateService#getSrc(java.lang.String)
	 */
	@Override
	public String getSrc(String orgId) {
		
		return "671100000013";
	}

	@Autowired
	private FTZMsgGenerateRepository repository;

}
