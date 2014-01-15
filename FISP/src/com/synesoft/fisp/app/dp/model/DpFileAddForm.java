package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;

/**
 * 文件定义维护-新增
 * @date 2013-11-17
 * @author YuanYeWei
 *
 */
public class DpFileAddForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static interface DpFileAddFormAdd { }

	@Valid
    private DpFile dpFile;;

	@Valid
    private List<DpFileDtl> dpFileDtlList;
    
    private String dtljson;
    
    private String flagJson;
    
	/**
	 * @return the flagJson
	 */
	public String getFlagJson() {
		return flagJson;
	}

	/**
	 * @param flagJson the flagJson to set
	 */
	public void setFlagJson(String flagJson) {
		this.flagJson = flagJson;
	}

	/**
	 * @return the dtljson
	 */
	public String getDtljson() {
		return dtljson;
	}

	/**
	 * @param dtljson the dtljson to set
	 */
	public void setDtljson(String dtljson) {
		this.dtljson = dtljson;
	}

	/**
	 * @return the dpFileDtlList
	 */
	public List<DpFileDtl> getDpFileDtlList() {
		return dpFileDtlList;
	}

	/**
	 * @param dpFileDtlList the dpFileDtlList to set
	 */
	public void setDpFileDtlList(List<DpFileDtl> dpFileDtlList) {
		this.dpFileDtlList = dpFileDtlList;
	}

	public DpFile getDpFile() {
		return dpFile;
	}

	public void setDpFile(DpFile dpFile) {
		this.dpFile = dpFile;
	}
   
	
}
