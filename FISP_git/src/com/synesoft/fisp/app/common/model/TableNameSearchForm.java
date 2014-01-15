package com.synesoft.fisp.app.common.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.DpTable;


public class TableNameSearchForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DpTable dpTable;

	/**
	 * @return the dpTable
	 */
	public DpTable getDpTable() {
		return dpTable;
	}

	/**
	 * @param dpTable the dpTable to set
	 */
	public void setDpTable(DpTable dpTable) {
		this.dpTable = dpTable;
	}

	
}
