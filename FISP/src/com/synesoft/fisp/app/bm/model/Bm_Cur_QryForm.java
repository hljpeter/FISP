package com.synesoft.fisp.app.bm.model;

import com.synesoft.fisp.domain.model.SysCurrency;
public class Bm_Cur_QryForm {
	private static final long serialVersionUID = 1L;
	/*币种*/
	private String currCode;
	/*币种名称*/
	private String currName;
	
	private  SysCurrency sysCurrency= new SysCurrency();

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

	
}
