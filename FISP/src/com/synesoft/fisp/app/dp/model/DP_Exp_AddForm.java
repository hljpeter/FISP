package com.synesoft.fisp.app.dp.model;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpExpCfg;

public class DP_Exp_AddForm {
	
	public static interface DP_Exp_Add {

	}
	
	@Valid
	private DpExpCfg dpExpCfg = new DpExpCfg();
	
	private String tmp_val="";
	
	private String customize_flag="";

	public String getTmp_val() {
		return tmp_val;
	}

	public void setTmp_val(String tmp_val) {
		this.tmp_val = tmp_val;
	}

	public DpExpCfg getDpExpCfg() {
		return dpExpCfg;
	}

	public void setDpExpCfg(DpExpCfg dpExpCfg) {
		this.dpExpCfg = dpExpCfg;
	}

	public String getCustomize_flag() {
		return customize_flag;
	}

	public void setCustomize_flag(String customize_flag) {
		this.customize_flag = customize_flag;
	}


}
