package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpTable;

/**
 * 表定义维护-查询Form
 * @date 2013-11-15
 * @author yyw
 *
 */
public class DpTableQryForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpTableQryFormDel { }
	
	@Valid
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
