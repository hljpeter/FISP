package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpImpCfg;

/**
 * 数据导入映射-查询 Form
 * @date 2013-11-12
 * @author yyw
 *
 */
public class DpImpQryForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpImpQryFormDel{
		
	}
	
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
