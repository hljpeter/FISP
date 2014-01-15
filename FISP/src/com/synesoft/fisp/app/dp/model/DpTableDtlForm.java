package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义维护-详细Form
 * @date 2013-11-15
 * @author yyw
 *
 */
public class DpTableDtlForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpTableDtlFormInit { }
	
	@Valid
	private DpTable dpTable;

	private List<DpTableDtl> list;
	
	/**
	 * @return the list
	 */
	public List<DpTableDtl> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<DpTableDtl> list) {
		this.list = list;
	}

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
