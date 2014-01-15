package com.synesoft.fisp.domain.repository.rq;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.ReportData;

public interface ReportDataRepository {
	
	public Page<ReportData> queryList(Pageable pageable,ReportData reportData);

	public ReportData query(ReportData reportData);
	
	public List<ReportData> queryList(ReportData reportData);
	
	public ReportData queryByCondition(ReportData reportData);
}
