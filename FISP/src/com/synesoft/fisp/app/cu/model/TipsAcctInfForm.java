package com.synesoft.fisp.app.cu.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.TipsAcctInf;

public class TipsAcctInfForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TipsAcctInf tipsAcctInf;
	
	private String acctcode;
	
	private String acctname;
	
	public TipsAcctInf getTipsAcctInf() {
		return tipsAcctInf;
	}

	public void setTipsAcctInf(TipsAcctInf tipsAcctInf) {
		this.tipsAcctInf = tipsAcctInf;
	}

	public String getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
	}

	public String getAcctname() {
		return acctname;
	}

	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	
}
