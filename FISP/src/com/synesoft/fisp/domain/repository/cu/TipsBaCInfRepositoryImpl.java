package com.synesoft.fisp.domain.repository.cu;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.context.MemoryContextHolder;
import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.TipsBaCInf;


@Repository
public class TipsBaCInfRepositoryImpl implements TipsBaCInfRepository {

	@Override
	public Page<TipsBaCInf> queryList(Pageable pageable, TipsBaCInf tipsBaCInf) {
//		return pageH.getPage(Table.TIPS_BACINF, SQLMap.SELECT_COUNTS,
//				SQLMap.SELECT_LIST, tipsBaCInf, pageable);
		return pageH.getPageFromMemory(MemoryResourceType.TipsBaCInf, tipsBaCInf.getReckbankno(), tipsBaCInf.getGenbankname(), pageable);
	}

	@Override
	public TipsBaCInf query(String reckbankno) {
//		return queryDAO.executeForObject(Table.TIPS_BACINF + "."
//				+ SQLMap.SELECT_BYKEY, reckbankno, TipsBaCInf.class);
		return (TipsBaCInf) MemoryContextHolder.getMemoryResourceByCode(MemoryResourceType.TipsBaCInf, reckbankno);
	}

	@Override
	public int insertTipsBaCInf(TipsBaCInf tipsBaCInf) {
		return updateDAO.execute(Table.TIPS_BACINF + "." + SQLMap.INSERT, tipsBaCInf);
	}

	@Override
	public int updateTipsBaCInf(TipsBaCInf tipsBaCInf) {
		return updateDAO.execute(Table.TIPS_BACINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, tipsBaCInf);
	}

	@Override
	public int deleteTipsBaCInf(TipsBaCInf tipsBaCInf) {
		return updateDAO.execute(Table.TIPS_BACINF + "." + SQLMap.DELETE_BYKEY,
				tipsBaCInf);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<TipsBaCInf> pageH;
}
