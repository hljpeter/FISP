package com.synesoft.fisp.app.sm.model;

import com.synesoft.fisp.domain.model.BAT_DATE_INFO;


/**
 * @author lijingchang
 * @date 2013年11月29日 09:00:22
 * @version 1.0
 * @Description 节假日文字版修改Form
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class Sm_Date_UpdForm {	

	/*日期列表*/
	private String updList;	
	
	private BAT_DATE_INFO batDateInfo;

	public String getUpdList() {
		return updList;
	}

	public void setUpdList(String updList) {
		this.updList = updList;
	}

	public BAT_DATE_INFO getBatDateInfo() {
		return batDateInfo;
	}

	public void setBatDateInfo(BAT_DATE_INFO batDateInfo) {
		this.batDateInfo = batDateInfo;
	}
	
	
}
