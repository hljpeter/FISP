package com.synesoft.fisp.app.sm.model;


/**
 * @author lijingchang
 * @date 2013年11月14日 09:00:22
 * @version 1.0
 * @Description 节假日修改Form
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class Sm_Date_MdfForm {	

	/*维护年份*/
	private String modYear;
	/*新增的假节日 记录操作员日志用*/
	private String addHolidays;
	/*取消的节假日 记录操作员日志用*/
	private String removeHolidays;
	
	public String getModYear() {
		return modYear;
	}
	public void setModYear(String modYear) {
		this.modYear = modYear;
	}
	public String getAddHolidays() {
		return addHolidays;
	}
	public void setAddHolidays(String addHolidays) {
		this.addHolidays = addHolidays;
	}
	public String getRemoveHolidays() {
		return removeHolidays;
	}
	public void setRemoveHolidays(String removeHolidays) {
		this.removeHolidays = removeHolidays;
	}
	
}
