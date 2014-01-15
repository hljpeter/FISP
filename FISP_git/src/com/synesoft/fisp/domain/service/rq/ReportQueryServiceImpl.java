package com.synesoft.fisp.domain.service.rq;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.fisp.domain.model.ReportDataDetail;
import com.synesoft.fisp.domain.repository.rq.ReportDataDetailRepository;
import com.synesoft.fisp.domain.repository.rq.ReportDataRepository;

@Service("reportQueryService")
public class ReportQueryServiceImpl implements ReportQueryService {

	@Autowired
	protected ReportDataDetailRepository reportDataDetailRepository;
	
	@Autowired
	protected ReportDataRepository reportDataRepository;
	
	
	@Override
	public ReportData transQueryReportData(ReportData reportData) {
		return reportDataRepository.query(reportData);
	}

	@Override
	public Page<ReportData> transQueryReportDataList(
			Pageable pageable,ReportData reportData) {
		return reportDataRepository.queryList(pageable, reportData);
	}
	
	@Override
	public Page<ReportDataDetail> transQueryReportDataDetailList(
			Pageable pageable,ReportDataDetail reportDataDetail) {
		return reportDataDetailRepository.queryList(pageable, reportDataDetail);
	}

	@Override
	public List<ReportData> transQueryReportDataList(ReportData reportData) {
		return reportDataRepository.queryList(reportData);
	}

	@Override
	public ReportData transQueryReportDataByCondition(ReportData reportData) {
		return reportDataRepository.queryByCondition(reportData);
	}

}
