package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;

public class DpImpCfgForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpImpCfgFormInit { }
	public static interface DpImpCfgFormSubmit { }
	
	@Valid
	private DpImpCfg dpImpCfg;

	private List<DpImpCfgDtlVO> cList;
	
	private List<DpFileDtl> fList;
	
	private List<DpTableDtl> tList;

	private List<DpMapDict> mList;
	
	private List<String> fileSelectedList;

	private List<String> tableSelectedList;
	
	private String fListJson;
	
	private String cListJson;
	
	private String tListJson;
	
	private String fileSelectedStr;
	
	private String tableSelectedStr;
	
	/**
	 * @return the mList
	 */
	public List<DpMapDict> getmList() {
		return mList;
	}

	/**
	 * @param mList the mList to set
	 */
	public void setmList(List<DpMapDict> mList) {
		this.mList = mList;
	}

	/**
	 * @return the fileSelectedStr
	 */
	public String getFileSelectedStr() {
		return fileSelectedStr;
	}

	/**
	 * @param fileSelectedStr the fileSelectedStr to set
	 */
	public void setFileSelectedStr(String fileSelectedStr) {
		this.fileSelectedStr = fileSelectedStr;
	}

	/**
	 * @return the tableSelectedStr
	 */
	public String getTableSelectedStr() {
		return tableSelectedStr;
	}

	/**
	 * @param tableSelectedStr the tableSelectedStr to set
	 */
	public void setTableSelectedStr(String tableSelectedStr) {
		this.tableSelectedStr = tableSelectedStr;
	}

	/**
	 * @return the fListJson
	 */
	public String getfListJson() {
		return fListJson;
	}

	/**
	 * @param fListJson the fListJson to set
	 */
	public void setfListJson(String fListJson) {
		this.fListJson = fListJson;
	}

	/**
	 * @return the cListJson
	 */
	public String getcListJson() {
		return cListJson;
	}

	/**
	 * @param cListJson the cListJson to set
	 */
	public void setcListJson(String cListJson) {
		this.cListJson = cListJson;
	}

	/**
	 * @return the tListJson
	 */
	public String gettListJson() {
		return tListJson;
	}

	/**
	 * @param tListJson the tListJson to set
	 */
	public void settListJson(String tListJson) {
		this.tListJson = tListJson;
	}

	/**
	 * @return the fileSelectedList
	 */
	public List<String> getFileSelectedList() {
		return fileSelectedList;
	}

	/**
	 * @param fileSelectedList the fileSelectedList to set
	 */
	public void setFileSelectedList(List<String> fileSelectedList) {
		this.fileSelectedList = fileSelectedList;
	}

	/**
	 * @return the tableSelectedList
	 */
	public List<String> getTableSelectedList() {
		return tableSelectedList;
	}

	/**
	 * @param tableSelectedList the tableSelectedList to set
	 */
	public void setTableSelectedList(List<String> tableSelectedList) {
		this.tableSelectedList = tableSelectedList;
	}

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
	public List<DpImpCfgDtlVO> getcList() {
		return cList;
	}

	/**
	 * @param cList the cList to set
	 */
	public void setcList(List<DpImpCfgDtlVO> cList) {
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