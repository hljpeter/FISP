package com.synesoft.fisp.app.dp.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.app.dp.model.DP_Mpp_UptForm.DP_Mpp_Upt_Init;
import com.synesoft.fisp.domain.model.DpExpCfg;

public class DP_Exp_UptForm {
	
	public static interface DP_Exp_Upt_Init {

	}

	public static interface DP_Exp_Upt_Submit {

	}
	
	@NotEmpty(groups = { DP_Mpp_Upt_Init.class}, message = "{e.dp.valid.001}")
	private String mod_ExpId = "";

	@Valid
	private DpExpCfg dpExpCfg = new DpExpCfg();
	
	private String tmp_val="";
	
	private String customize_flag="";

	public String getMod_ExpId() {
		return mod_ExpId;
	}

	public void setMod_ExpId(String mod_ExpId) {
		this.mod_ExpId = mod_ExpId;
	}

	public DpExpCfg getDpExpCfg() {
		return dpExpCfg;
	}

	public void setDpExpCfg(DpExpCfg dpExpCfg) {
		this.dpExpCfg = dpExpCfg;
	}

	public String getTmp_val() {
		return tmp_val;
	}

	public void setTmp_val(String tmp_val) {
		this.tmp_val = tmp_val;
	}

	public String getCustomize_flag() {
		return customize_flag;
	}

	public void setCustomize_flag(String customize_flag) {
		this.customize_flag = customize_flag;
	}
	
}
