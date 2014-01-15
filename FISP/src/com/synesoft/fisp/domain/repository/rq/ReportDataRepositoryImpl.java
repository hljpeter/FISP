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
import com.synesoft.fisp.domain.model.ReportData;

@Repository
public class ReportDataRepositoryImpl implements ReportDataRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<ReportData> pageH;
	
	@Override
	public Page<ReportData> queryList(Pageable pageable,
			ReportData reportData) {
		return pageH.getPage(Table.REPORT_DATA, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, reportData, pageable);
	}

	@Override
	public ReportData query(ReportData reportData) {
		return queryDAO.executeForObject(Table.REPORT_DATA + "."
				+ SQLMap.SELECT_BYKEY, reportData, ReportData.class);
	}

	@Override
	public List<ReportData> queryList(ReportData reportData) {
		return  queryDAO.executeForObjectList(Table.REPORT_DATA + "." + SQLMap.SELECT_LIST, reportData);
	}

	@Override
	public ReportData queryByCondition(ReportData reportData) {
		return queryDAO.executeForObject(Table.REPORT_DATA + "."
				+ SQLMap.SELECT_KEY, reportData, ReportData.class);
	}

}
