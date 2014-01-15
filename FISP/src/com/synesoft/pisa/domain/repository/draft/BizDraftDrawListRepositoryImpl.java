package com.synesoft.pisa.domain.repository.draft;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.BizDraftDrawList;

/**
 * SysChgBizLogRepository实现类
 * @author michelle.wang
 * 
 */

@Repository
public class BizDraftDrawListRepositoryImpl implements BizDraftDrawListRepository {

	@Override
	public Page<BizDraftDrawList> queryList(Pageable pageable, BizDraftDrawList bizDraftDrawList) {
		return pageH.getPage(Table.BIZ_DRAFT_ACPT_LIST, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, bizDraftDrawList, pageable);
	}
	@Override
	public BizDraftDrawList query(BizDraftDrawList bizDraftDrawList){
		return queryDAO.executeForObject(Table.BIZ_DRAFT_ACPT_LIST + "."
				+ SQLMap.SELECT_BYKEY, bizDraftDrawList, BizDraftDrawList.class);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<BizDraftDrawList> pageH;
}
