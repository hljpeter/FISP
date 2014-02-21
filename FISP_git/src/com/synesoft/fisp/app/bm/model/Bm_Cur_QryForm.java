package com.synesoft.fisp.app.bm.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.SysCurrency;
/**
 * @author NB19
 *
 */
public class Bm_Cur_QryForm implements Serializable{
	private static final long serialVersionUID = 1L;
	/*币种*/
	private String currCode;
	/*币种名称*/
	private String currName;
	  
	/*国际化语言*/
	private String currLan;
	
	private String beforeCurrCode;
	
	private String beforeCurrLan;
	
	private  SysCurrency sysCurrency = new SysCurrency() ;

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getCurrName() {
		return currName;
	}

	public void setCurrName(String currName) {
		this.currName = currName;
	}

	public SysCurrency getSysCurrency() {
		return sysCurrency;
	}

	public String getCurrLan() {
		return currLan;
	}

	public void setCurrLan(String currLan) {
		this.currLan = currLan;
	}

	public void setSysCurrency(SysCurrency sysCurrency) {
		this.sysCurrency = sysCurrency;
	}

	public String getBeforeCurrCode() {
		return beforeCurrCode;
	}

	public void setBeforeCurrCode(String beforeCurrCode) {
		this.beforeCurrCode = beforeCurrCode;
	}

	public String getBeforeCurrLan() {
		return beforeCurrLan;
	}

	public void setBeforeCurrLan(String beforeCurrLan) {
		this.beforeCurrLan = beforeCurrLan;
	}

	
}
