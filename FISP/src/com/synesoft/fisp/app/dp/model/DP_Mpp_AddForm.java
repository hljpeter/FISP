package com.synesoft.fisp.app.dp.model;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpMppCfg;

public class DP_Mpp_AddForm {

	public static interface DP_Mpp_Add {

	}

	@Valid
	private DpMppCfg dpMppCfg = new DpMppCfg();
	
	private String customize_flag ="";
	
	public String getCustomize_flag() {
		return customize_flag;
	}

	public void setCustomize_flag(String customize_flag) {
		this.customize_flag = customize_flag;
	}

	public DpMppCfg getDpMppCfg() {
		return dpMppCfg;
	}

	public void setDpMppCfg(DpMppCfg dpMppCfg) {
		this.dpMppCfg = dpMppCfg;
	}

}
