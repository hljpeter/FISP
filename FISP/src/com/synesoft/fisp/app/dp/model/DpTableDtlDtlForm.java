package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义维护-明细详细Form
 * @date 2013-11-15
 * @author yyw
 *
 */
public class DpTableDtlDtlForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpTableDtlDtlFormInit { }
	
	@Valid
	private DpTableDtl dpTableDtl;

	private String tableName;
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the dpTableDtl
	 */
	public DpTableDtl getDpTableDtl() {
		return dpTableDtl;
	}

	/**
	 * @param dpTableDtl the dpTableDtl to set
	 */
	public void setDpTableDtl(DpTableDtl dpTableDtl) {
		this.dpTableDtl = dpTableDtl;
	}

	
}
