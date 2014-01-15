package com.synesoft.fisp.domain.repository.rq;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.ReportDataDetail;

@Repository
public class ReportDataDetailRepositoryImpl implements ReportDataDetailRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<ReportDataDetail> pageH;
	
	@Override
	public Page<ReportDataDetail> queryList(Pageable pageable,
			ReportDataDetail reportDataDetail) {
		return pageH.getPage(Table.REPORT_DATA_DETAIL, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, reportDataDetail, pageable);
	}

	@Override
	public ReportDataDetail query(ReportDataDetail reportDataDetail) {
		return queryDAO.executeForObject(Table.REPORT_DATA_DETAIL + "."
				+ SQLMap.SELECT_BYKEY, reportDataDetail, ReportDataDetail.class);
	}

	@Override
	public List<ReportDataDetail> queryList(ReportDataDetail reportDataDetail) {
		return  queryDAO.executeForObjectList(Table.REPORT_DATA_DETAIL + "." + SQLMap.SELECT_LIST, reportDataDetail);
	}

}
