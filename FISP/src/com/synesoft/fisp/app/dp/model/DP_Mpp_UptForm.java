package com.synesoft.fisp.app.dp.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.DpMppCfg;

public class DP_Mpp_UptForm {

	public static interface DP_Mpp_Upt_Init {

	}

	public static interface DP_Mpp_Upt_Submit {

	}

	@NotEmpty(groups = { DP_Mpp_Upt_Init.class}, message = "{e.dp.valid.001}")
	private String mod_MppId = "";

	@Valid
	private DpMppCfg dpMppCfg = new DpMppCfg();
	
	private String customize_flag ="";

	public DpMppCfg getDpMppCfg() {
		return dpMppCfg;
	}

	public void setDpMppCfg(DpMppCfg dpMppCfg) {
		this.dpMppCfg = dpMppCfg;
	}

	public String getMod_MppId() {
		return mod_MppId;
	}

	public void setMod_MppId(String mod_MppId) {
		this.mod_MppId = mod_MppId;
	}

	public String getCustomize_flag() {
		return customize_flag;
	}

	public void setCustomize_flag(String customize_flag) {
		this.customize_flag = customize_flag;
	}

}
