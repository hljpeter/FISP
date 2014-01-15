package com.synesoft.fisp.domain.service.sm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.Sm_Date_DtlForm;
import com.synesoft.fisp.app.sm.model.Sm_Date_QryForm;




/**
 * 
 * @author ljch
 * 
 */
public interface HolidayMaintainService {
	
	/**
	 * 节假日维护查询功能
	 * @param pageable
	 * @param orgInf
	 * @return
	 */
	public Page<Sm_Date_QryForm> transQueryHolidayList(Pageable pageable,
			Sm_Date_QryForm smDateForm);
		

	
	/**
	 * 查询维护年份的节假日明细/操作
	 * 1.根据传入的维护年份，查询出该年份的所有节假日记录
	 * 2.把该年份的节假日信息填入成map对象
	 * @param form
	 * @return
	 */
	public Sm_Date_DtlForm transMaintainDetail(Sm_Date_DtlForm form);
	
	/**
	 * 维护年份的节假日更新/操作
	 * 1.将传入年份的日期全部改成工作日
	 * 2.根据传入的节假日更新数据库
	 * @param form
	 * @return
	 */
	public void transUpdate(String addHolidays,String removeHolidays);
	
	/**
	 * 维护年份的节假日更新/操作
	 * 1.文字版修改的日期列表 格式"yyyymmdd~yyyymmdd#Y"
	 * @param form
	 * @return
	 */
	public void transUpdate2(String dayList);
}
