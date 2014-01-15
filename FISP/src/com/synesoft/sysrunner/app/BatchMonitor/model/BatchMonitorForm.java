package com.synesoft.sysrunner.app.BatchMonitor.model;

import com.synesoft.fisp.domain.model.DpExpLog;
import com.synesoft.fisp.domain.model.DpImpLog;
import com.synesoft.fisp.domain.model.DpMppLog;


public class BatchMonitorForm {

	private String day_task_flag = "";

	private String month_task_flag = "";

	private String season_task_flag = "";

	private String year_task_flag = "";

	private String cus_task_flag = "";

	private String query_bat_date = "";

	private String runtimeId = "";

	private String taskId = "";

	private String stepId = "";
	
	private DpImpLog dpImpLog;
	
	private DpExpLog dpExpLog;
	
	private DpMppLog dpMppLog;
	
	public String getRuntimeId() {
		return runtimeId;
	}

	public void setRuntimeId(String runtimeId) {
		this.runtimeId = runtimeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getDay_task_flag() {
		return day_task_flag;
	}

	public void setDay_task_flag(String day_task_flag) {
		this.day_task_flag = day_task_flag;
	}

	public String getMonth_task_flag() {
		return month_task_flag;
	}

	public void setMonth_task_flag(String month_task_flag) {
		this.month_task_flag = month_task_flag;
	}

	public String getSeason_task_flag() {
		return season_task_flag;
	}

	public void setSeason_task_flag(String season_task_flag) {
		this.season_task_flag = season_task_flag;
	}

	public String getYear_task_flag() {
		return year_task_flag;
	}

	public void setYear_task_flag(String year_task_flag) {
		this.year_task_flag = year_task_flag;
	}

	public String getCus_task_flag() {
		return cus_task_flag;
	}

	public void setCus_task_flag(String cus_task_flag) {
		this.cus_task_flag = cus_task_flag;
	}

	public String getQuery_bat_date() {
		return query_bat_date;
	}

	public void setQuery_bat_date(String query_bat_date) {
		this.query_bat_date = query_bat_date;
	}

	public DpImpLog getDpImpLog() {
		return dpImpLog;
	}

	public void setDpImpLog(DpImpLog dpImpLog) {
		this.dpImpLog = dpImpLog;
	}

	public DpExpLog getDpExpLog() {
		return dpExpLog;
	}

	public void setDpExpLog(DpExpLog dpExpLog) {
		this.dpExpLog = dpExpLog;
	}

	public DpMppLog getDpMppLog() {
		return dpMppLog;
	}

	public void setDpMppLog(DpMppLog dpMppLog) {
		this.dpMppLog = dpMppLog;
	}

}
