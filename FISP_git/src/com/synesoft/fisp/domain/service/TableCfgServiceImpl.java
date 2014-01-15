package com.synesoft.fisp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.dataproc.jdbc.JdbcOperation;

@Service("tableCfgService")
public class TableCfgServiceImpl implements TableCfgService {

	@Autowired
	protected JdbcOperation jdbcOperation;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public List<?> getColsList(String tabelName) {
		String sql="select * from user_col_comments where table_name="+"\'"+tabelName+"\'";
		return jdbcOperation.queryForList(sql);
	}

	@Override
	@Transactional
	public List<?> getListBySql(String sql) {
		return jdbcOperation.queryForList(sql);
	}

}
