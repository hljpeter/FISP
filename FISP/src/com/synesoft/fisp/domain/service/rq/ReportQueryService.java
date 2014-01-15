package com.synesoft.fisp.domain.service.rq;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.fisp.domain.model.ReportDataDetail;

public interface ReportQueryService {
	
	public ReportData transQueryReportData(ReportData reportData);
	
	public Page<ReportData> transQueryReportDataList(Pageable pageable,ReportData reportData);
	
	public Page<ReportDataDetail> transQueryReportDataDetailList(Pageable pageable,ReportDataDetail reportDataDetail);
	
	public List<ReportData> transQueryReportDataList(ReportData reportData);
	
	public ReportData transQueryReportDataByCondition(ReportData reportData);
}
