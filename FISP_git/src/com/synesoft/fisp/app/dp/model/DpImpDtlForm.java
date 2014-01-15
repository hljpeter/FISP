package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpImpCfgDtl;
import com.synesoft.fisp.domain.model.DpTableDtl;


public class DpImpDtlForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpImpDtlFormInit {
		
	}
	
	@Valid
	private DpImpCfg dpImpCfg;

	private List<DpImpCfgDtl> cList;
	
	private List<DpFileDtl> fList;
	
	private List<DpTableDtl> tList;
	
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

	/**
	 * @return the cList
	 */
	public List<DpImpCfgDtl> getcList() {
		return cList;
	}

	/**
	 * @param cList the cList to set
	 */
	public void setcList(List<DpImpCfgDtl> cList) {
		this.cList = cList;
	}

	/**
	 * @return the fList
	 */
	public List<DpFileDtl> getfList() {
		return fList;
	}

	/**
	 * @param fList the fList to set
	 */
	public void setfList(List<DpFileDtl> fList) {
		this.fList = fList;
	}

	/**
	 * @return the tList
	 */
	public List<DpTableDtl> gettList() {
		return tList;
	}

	/**
	 * @param tList the tList to set
	 */
	public void settList(List<DpTableDtl> tList) {
		this.tList = tList;
	}

	
}
