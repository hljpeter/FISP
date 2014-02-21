package com.synesoft.fisp.domain.repository.bm;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

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
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Override
	public Page<SysCurrency> queryInputList(Pageable pageable,
			SysCurrency sysCurrency) {
		return pageH.getPage(Table.SYS_CURRENCY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysCurrency, pageable);
	}
	@Override
	public SysCurrency queryInput(SysCurrency queryCur) {
		// TODO Auto-generated method stub
		return queryDAO.executeForObject(Table.SYS_CURRENCY+ "."
				+SQLMap.SELECT_QUERYDEL,queryCur,SysCurrency.class);
	}
	@Override
	public int undateCur(SysCurrency updateCur) {
		// TODO Auto-generated method stub
		return updateDAO.execute(Table.SYS_CURRENCY + "."
				+ SQLMap.UPDATE_CURRENCY, updateCur);
	}
	@Override
	public int  deleteCur(SysCurrency curDel) {		
		return updateDAO.execute(Table.SYS_CURRENCY + "." + SQLMap.DELETE_CUR,
				curDel);
	}
	@Override
	public int addCur(SysCurrency addCur) {
		// TODO Auto-generated method stub
		return updateDAO.execute(Table.SYS_CURRENCY + "." + SQLMap.INSERT_CUR,
				addCur);
	}

}
