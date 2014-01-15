package com.synesoft.fisp.domain.service;

import java.util.List;


public interface TableCfgService {

	public List<?> getColsList(String tabelName);
	
	public List<?> getListBySql(String sql);
}
