package com.synesoft.fisp.domain.model.vo;

import com.synesoft.fisp.domain.model.DpImpCfgDtl;

public class DpImpCfgDtlVO extends DpImpCfgDtl {

    private String colLen;

    private Short fieldLen;

	/**
	 * @return the colLen
	 */
	public String getColLen() {
		return colLen;
	}

	/**
	 * @param colLen the colLen to set
	 */
	public void setColLen(String colLen) {
		this.colLen = colLen;
	}

	/**
	 * @return the fieldLen
	 */
	public Short getFieldLen() {
		return fieldLen;
	}

	/**
	 * @param fieldLen the fieldLen to set
	 */
	public void setFieldLen(Short fieldLen) {
		this.fieldLen = fieldLen;
	}
    
    
}