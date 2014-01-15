package com.synesoft.fisp.app.common.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.DpFile;


public class FileNameSearchForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DpFile dpFile;

	/**
	 * @return the dpFile
	 */
	public DpFile getDpFile() {
		return dpFile;
	}

	/**
	 * @param dpFile the dpFile to set
	 */
	public void setDpFile(DpFile dpFile) {
		this.dpFile = dpFile;
	}

	
}
