package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义维护-新增Form
 * @date 2013-11-15
 * @author yyw
 *
 */
public class DpTableAddForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static interface DpTableAddFormAdd { }
	
	@Valid
	private DpTable DpTable;

	private List<DBTableInfo> dbTableList;

	@Valid
	private List<DpTableDtl> dpTableList;
	
	/**
	 * @return the dpTableList
	 */
	public List<DpTableDtl> getDpTableList() {
		return dpTableList;
	}

	/**
	 * @param dpTableList the dpTableList to set
	 */
	public void setDpTableList(List<DpTableDtl> dpTableList) {
		this.dpTableList = dpTableList;
	}

	/**
	 * @return the dbTableList
	 */
	public List<DBTableInfo> getDbTableList() {
		return dbTableList;
	}

	/**
	 * @param dbTableList the dbTableList to set
	 */
	public void setDbTableList(List<DBTableInfo> dbTableList) {
		this.dbTableList = dbTableList;
	}

	/**
	 * @return the dpTable
	 */
	public DpTable getDpTable() {
		return DpTable;
	}

	/**
	 * @param dpTable the dpTable to set
	 */
	public void setDpTable(DpTable dpTable) {
		DpTable = dpTable;
	}
	
}
