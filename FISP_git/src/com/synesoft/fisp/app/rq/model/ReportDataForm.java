package com.synesoft.fisp.app.rq.model;

import java.io.Serializable;

import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.fisp.domain.model.ReportDataDetail;

public class ReportDataForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reportMonth;
	
	private ReportData reportData=new ReportData();
	
	private ReportDataDetail reportDataDetail=new ReportDataDetail();

	/**
	 * @return the reportMonth
	 */
	public String getReportMonth() {
		return reportMonth;
	}

	/**
	 * @param reportMonth the reportMonth to set
	 */
	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	/**
	 * @return the reportData
	 */
	public ReportData getReportData() {
		return reportData;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	/**
	 * @return the reportDataDetail
	 */
	public ReportDataDetail getReportDataDetail() {
		return reportDataDetail;
	}

	/**
	 * @param reportDataDetail the reportDataDetail to set
	 */
	public void setReportDataDetail(ReportDataDetail reportDataDetail) {
		this.reportDataDetail = reportDataDetail;
	}

}
