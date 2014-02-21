package com.synesoft.fisp.domain.repository;

import java.util.List;

import com.synesoft.fisp.domain.model.MainParam;

public interface MainRepository {
	
	/**
	 * 根据SQL ID查询系统待处理记录数
	 * @param sqlId
	 * @return
	 */
	public MainParam getSysGeneralTODO(String sqlId);
	
	/**
	 * 查询业务待处理记录数
	 * @param param
	 * @return
	 */
	public List<MainParam> getBizGeneralTODO(MainParam param);

	/**
	 * 查询待生成报文记录数
	 * @return
	 */
	public int getBatchToMsg();
	
	/**
	 * 查询报文结果记录数，发送失败，反馈错误
	 * @return
	 */
	public List<MainParam> getMsg();

	/**
	 * 查询申报日期下，账户信息记录数
	 * @param date
	 * @return
	 */
	public int getActMstr(String date);
}
