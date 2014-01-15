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
import com.synesoft.fisp.domain.model.TipsAcctInf;


@Repository
public class TipsAcctInfRepositoryImpl implements TipsAcctInfRepository {

	@Override
	public Page<TipsAcctInf> queryList(Pageable pageable, TipsAcctInf tipsAcctInf) {
//		return pageH.getPage(Table.TIPS_ACCTINF, SQLMap.SELECT_COUNTS,
//				SQLMap.SELECT_LIST, tipsAcctInf, pageable);
		return pageH.getPageFromMemory(MemoryResourceType.TipsAcctInf, tipsAcctInf.getAcctcode(), tipsAcctInf.getAcctname(), pageable);
	}

	@Override
	public TipsAcctInf query(String acctcode) {
//		return queryDAO.executeForObject(Table.TIPS_ACCTINF + "."
//				+ SQLMap.SELECT_BYKEY, acctcode, TipsAcctInf.class);
		return (TipsAcctInf) MemoryContextHolder.getMemoryResourceByCode(MemoryResourceType.TipsAcctInf, acctcode);
	}

	@Override
	public int insertTipsAcctInf(TipsAcctInf tipsAcctInf) {
		return updateDAO.execute(Table.TIPS_ACCTINF + "." + SQLMap.INSERT, tipsAcctInf);
	}

	@Override
	public int updateTipsAcctInf(TipsAcctInf tipsAcctInf) {
		return updateDAO.execute(Table.TIPS_ACCTINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, tipsAcctInf);
	}

	@Override
	public int deleteTipsAcctInf(TipsAcctInf tipsAcctInf) {
		return updateDAO.execute(Table.TIPS_ACCTINF + "." + SQLMap.DELETE_BYKEY,
				tipsAcctInf);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<TipsAcctInf> pageH;
}
