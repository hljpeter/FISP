package com.synesoft.fisp.domain.repository.bm;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysCurrency;

@Repository
public class SysCurrencyRepositoryImpl implements SysCurrencyRepository {

	@Resource
	private PageHandler<SysCurrency> pageH;
	@Override
	public Page<SysCurrency> queryInputList(Pageable pageable,
			SysCurrency sysCurrency) {
		return pageH.getPage(Table.SYS_CURRENCY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysCurrency, pageable);
	}

}
