package com.synesoft.fisp.app.cu.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.TipsBaCInf;

public class TipsBaCInfForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TipsBaCInf tipsBaCInf;
	
	private String reckbankno;
	
	private String genbankname;
	
	public TipsBaCInf getTipsBaCInf() {
		return tipsBaCInf;
	}

	public void setTipsBaCInf(TipsBaCInf tipsBaCInf) {
		this.tipsBaCInf = tipsBaCInf;
	}

	public String getReckbankno() {
		return reckbankno;
	}

	public void setReckbankno(String reckbankno) {
		this.reckbankno = reckbankno;
	}

	public String getGenbankname() {
		return genbankname;
	}

	public void setGenbankname(String genbankname) {
		this.genbankname = genbankname;
	}

}
