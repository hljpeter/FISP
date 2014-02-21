package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import com.synesoft.ftzmis.domain.model.FtzMsgBatch;
import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;
import com.synesoft.ftzmis.domain.model.FtzMsgProc;

/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
public interface FTZMsgGenerateRepository {

	/**
	 * 查询机构，报文编号下不同批量状态（正在录入，录入完成，审核通过，审核失败）的数据量
	 * @param param
	 * @return
	 */
	public List<FtzMsgGenerateParam> queryList(FtzMsgGenerateParam param);

	/**
	 * 查询出表内和表外需要报送批量的申报日期()
	 * @param branchList
	 * @return
	 */
	public List<String> querySubmitDateList(FtzMsgGenerateParam obj);

	/**
	 * 查询出表内和表外每个批量报送新增/修改/删除的明细数量
	 * @param branchList
	 * @return
	 */
	public List<FtzMsgGenerateParam> queryTxnList(FtzMsgGenerateParam msgGen);

	/**
	 * 根据MSG_ID（批量号）查询映射表，判断批量是否已经存在
	 * @param branchList
	 * @return
	 */
	public Integer queryMsg(FtzMsgGenerateParam param);

	/**
	 * 查询报文表
	 * @param proc
	 * @return
	 */
	public FtzMsgProc queryMsgProc(FtzMsgProc proc);

	/**
	 * 插入报文批量映射表
	 * @param proc
	 * @return
	 */
	public Integer insertMsgBatch(FtzMsgBatch msgBatch);

	/**
	 * 插入报文表
	 * @param proc
	 * @return
	 */
	public Integer insertMsgProc(FtzMsgProc proc);

	/**
	 * 查询报文结果
	 * @param proc
	 * @return
	 */
	public List<FtzMsgGenerateParam> queryMsgProcResult(FtzMsgGenerateParam param);

	/**
	 * 获得MSG_PROC id
	 * @return
	 */
	public Long getMsgProcIdSeq();
	
	/**
	 * 调用存储过程，将一个账号+子账号+日期下所有报文类型的空报文记录插入到批量表中
	 * @param param
	 */
	public int callBlankBatchProc(FtzMsgGenerateParam param);
}
