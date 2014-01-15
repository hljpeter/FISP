package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;

/**
 * 文件定义维护-修改
 * @date 2013-11-17
 * @author YuanYeWei
 *
 */
public class DpFileMdfForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static interface DpFileMdfFormInit { }
	public static interface DpFileMdfFormMdf { }

	@Valid
    private DpFile dpFile;;

	@Valid
    private List<DpFileDtl> dpFileDtlList;
    
    private String cutFlag;
    
	public String getCutFlag() {
		return cutFlag;
	}

	public void setCutFlag(String cutFlag) {
		this.cutFlag = cutFlag;
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
