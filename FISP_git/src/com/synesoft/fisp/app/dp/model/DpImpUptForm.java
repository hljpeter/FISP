package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpImpCfg;


public class DpImpUptForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpImpUptFormInit {	}
	
	@Valid
	private DpImpCfg dpImpCfg;

	/**
	 * @return the dpImpCfg
	 */
	public DpImpCfg getDpImpCfg() {
		return dpImpCfg;
	}

	/**
	 * @param dpImpCfg the dpImpCfg to set
	 */
	public void setDpImpCfg(DpImpCfg dpImpCfg) {
		this.dpImpCfg = dpImpCfg;
	}
	
    
	
}
