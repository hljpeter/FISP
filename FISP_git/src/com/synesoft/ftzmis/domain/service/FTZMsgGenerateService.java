package com.synesoft.ftzmis.domain.service;

import java.util.List;

import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;


/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
public interface FTZMsgGenerateService {

	/**
	 * 查询机构，报文编号下不同批量状态（正在录入，录入完成，审核通过，审核失败）的数据量
	 * @param branchList
	 * @return
	 */
	public List<FtzMsgGenerateParam> getList(List<String> branchList);

	/**
	 * 查询报文发送结果
	 * @param branchList
	 * @return
	 */
	public List<FtzMsgGenerateParam> getResultList(FtzMsgGenerateParam param);

	public String getSrc(String orgId);
	
}
