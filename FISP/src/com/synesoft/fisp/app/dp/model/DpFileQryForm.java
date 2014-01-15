package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.fisp.domain.model.DpFile;

/**
 * 文件定义维护-查询
 * @date 2013-11-17
 * @author YuanYeWei
 *
 */
public class DpFileQryForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static interface DpFileQryFormDel { }

	@Valid
    private DpFile dpFile;;

	public DpFile getDpFile() {
		return dpFile;
	}

	public void setDpFile(DpFile dpFile) {
		this.dpFile = dpFile;
	}
   
	
}
