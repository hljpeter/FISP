package com.synesoft.fisp.domain.repository.rq;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.ReportDataDetail;

public interface ReportDataDetailRepository {
	
	public Page<ReportDataDetail> queryList(Pageable pageable,ReportDataDetail reportDataDetail);
	
	public ReportDataDetail query(ReportDataDetail reportDataDetail);
	
	public List<ReportDataDetail> queryList(ReportDataDetail reportDataDetail);
	
}
