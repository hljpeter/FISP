package com.synesoft.fisp.domain.repository;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;

@Repository
public class NumberRepositoryImpl implements NumberRepository{

	@Override
	public Integer getSysIDSequence() {
		return  queryDAO.executeForObject("Numbering."
				+ SQLMap.IDSEQUENCE, null, Integer.class);
	}
	@Resource
	private QueryDAO queryDAO;
}
