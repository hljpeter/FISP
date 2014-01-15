package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.SysParam;

/**
 * @author 李峰
 * @date 2013-11-19 上午10:44:48
 * @version 1.0
 * @description 
 * @system FISP
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public class SM_Prm_QryForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632333956220859923L;
	
	private SysParam sysParam=new SysParam();

	/**
	 * 系统参数组号 
	 */
	private String paramGroup;
	
	/**
	 * 系统参数编号
	 */
	private String paramCode;
	
	/**
	 * 系统参数属性值 
	 */
	private String paramVal;
	
	/**
	 * 系统参数名称 
	 */
	private String paramName;
	
	/**
	 * 系统参数描述 
	 */
	private String paramDesc;

	/**
	 * @return the paramGroup
	 */
	public String getParamGroup() {
		return paramGroup;
	}

	/**
	 * @param paramGroup the paramGroup to set
	 */
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * @return the paramCode
	 */
	public String getParamCode() {
		return paramCode;
	}

	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	/**
	 * @return the paramVal
	 */
	public String getParamVal() {
		return paramVal;
	}

	/**
	 * @param paramVal the paramVal to set
	 */
	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramDesc
	 */
	public String getParamDesc() {
		return paramDesc;
	}

	/**
	 * @param paramDesc the paramDesc to set
	 */
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	/**
	 * @return the sysParam
	 */
	public SysParam getSysParam() {
		return sysParam;
	}

	/**
	 * @param sysParam the sysParam to set
	 */
	public void setSysParam(SysParam sysParam) {
		this.sysParam = sysParam;
	}
	
	
}
