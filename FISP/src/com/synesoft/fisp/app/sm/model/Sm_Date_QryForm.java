package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

/**
 * @author lijingchang
 * @date 2013年11月13日 10:55:22
 * @version 1.0
 * @Description 节假日查询Form
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class Sm_Date_QryForm implements Serializable {

	private static final long serialVersionUID = 851689038717868701L;

	/*维护年份下限*/
	private String floorYear;
	/*维护年份上限*/
	private String upperYear;
	/*维护年份*/
	private String modYear;
	/*节假日天数*/
	private String holidays;
	/*工作日天数*/
	private String workdays;
	
	public String getFloorYear() {
		return floorYear;
	}
	public void setFloorYear(String floorYear) {
		this.floorYear = floorYear;
	}
	public String getUpperYear() {
		return upperYear;
	}
	public void setUpperYear(String upperYear) {
		this.upperYear = upperYear;
	}
	public String getModYear() {
		return modYear;
	}
	public void setModYear(String modYear) {
		this.modYear = modYear;
	}
	public String getHolidays() {
		return holidays;
	}
	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}
	public String getWorkdays() {
		return workdays;
	}
	public void setWorkdays(String workdays) {
		this.workdays = workdays;
	}
	
	
	
}
