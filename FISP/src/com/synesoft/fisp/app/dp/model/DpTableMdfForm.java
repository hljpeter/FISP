package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义维护-修改Form
 * @date 2013-11-18
 * @author yyw
 *
 */
public class DpTableMdfForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public interface DpTableMdfFormInit { }
	public interface DpTableMdfFormMdf { }
	
	@Valid
	private DpTable DpTable;

	private DBTableInfo dbTableInfo;

	@Valid
	private List<DpTableDtl> dpTableList;
	
	private Boolean flag;
	
	/**
	 * @return the flag
	 */
	public Boolean getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

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
	 * @return the dbTableInfo
	 */
	public DBTableInfo getDbTableInfo() {
		return dbTableInfo;
	}

	/**
	 * @param dbTableInfo the dbTableInfo to set
	 */
	public void setDbTableInfo(DBTableInfo dbTableInfo) {
		this.dbTableInfo = dbTableInfo;
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
