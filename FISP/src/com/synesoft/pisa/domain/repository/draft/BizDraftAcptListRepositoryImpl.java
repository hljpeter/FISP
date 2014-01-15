package com.synesoft.pisa.domain.repository.draft;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.BizDraftAcptList;

/**
 * SysChgBizLogRepository实现类
 * @author michelle.wang
 * 
 */

@Repository
public class BizDraftAcptListRepositoryImpl implements BizDraftAcptListRepository {

	@Override
	public Page<BizDraftAcptList> queryList(Pageable pageable, BizDraftAcptList bizDraftAcptList) {
		return pageH.getPage(Table.BIZ_DRAFT_ACPT_LIST, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, bizDraftAcptList, pageable);
	}
	@Override
	public BizDraftAcptList query(BizDraftAcptList bizDraftAcptList){
		return queryDAO.executeForObject(Table.BIZ_DRAFT_ACPT_LIST + "."
				+ SQLMap.SELECT_BYKEY, bizDraftAcptList, BizDraftAcptList.class);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private PageHandler<BizDraftAcptList> pageH;
}
