package com.synesoft.ftzmis.domain.repository;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
@Repository
public class FTZCMAccountQryRepositoryImpl implements FTZCMAccountQryRepository {

	@Override
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable,
			FtzActMstr ftzActMstr) {
		return pageFtzActMstr.getPage(Table.FTZ_ACT_MSTR, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzActMstr, pageable);
	}

	@Override
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr) {
		return queryDAO.executeForObject(Table.FTZ_ACT_MSTR + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzActMstr, FtzActMstr.class);
	}
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<FtzActMstr> pageFtzActMstr;

}
