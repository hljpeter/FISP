package com.synesoft.fisp.app.sm.model;

import java.util.List;
import java.util.Map;
/**
 * @author lijingchang
 * @date 2013年11月13日 14:00:22
 * @version 1.0
 * @Description 节假日详细Form
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class Sm_Date_DtlForm {
	/*维护年份*/
	private String modYear;
	/*返回的节假日，map对象*/
	private Map<String, List<String>> holiMap;
	
	public String getModYear() {
		return modYear;
	}
	public void setModYear(String modYear) {
		this.modYear = modYear;
	}
	public Map<String, List<String>> getHoliMap() {
		return holiMap;
	}
	public void setHoliMap(Map<String, List<String>> holiMap) {
		this.holiMap = holiMap;
	}
}
