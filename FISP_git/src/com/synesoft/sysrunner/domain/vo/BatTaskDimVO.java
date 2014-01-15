package com.synesoft.sysrunner.domain.vo;

public class BatTaskDimVO {

	private String taskStartType;

	private String taskStartTime;

	private Short taskStartDay;

	private Short taskStartTn;

	private String taskEndTime;

	private Integer taskPollingInterval;

	private String taskEnable;

	private String dimensionId;

	private String dimTypeId;

	private String taskId;

	private String selectedFalg;

	private String dimensionName;

	public String getTaskStartType() {
		return taskStartType;
	}

	public void setTaskStartType(String taskStartType) {
		this.taskStartType = taskStartType;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Short getTaskStartDay() {
		return taskStartDay;
	}

	public void setTaskStartDay(Short taskStartDay) {
		this.taskStartDay = taskStartDay;
	}

	public Short getTaskStartTn() {
		return taskStartTn;
	}

	public void setTaskStartTn(Short taskStartTn) {
		this.taskStartTn = taskStartTn;
	}

	public String getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getTaskPollingInterval() {
		return taskPollingInterval;
	}

	public void setTaskPollingInterval(Integer taskPollingInterval) {
		this.taskPollingInterval = taskPollingInterval;
	}

	public String getTaskEnable() {
		return taskEnable;
	}

	public void setTaskEnable(String taskEnable) {
		this.taskEnable = taskEnable;
	}

	public String getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}

	public String getDimTypeId() {
		return dimTypeId;
	}

	public void setDimTypeId(String dimTypeId) {
		this.dimTypeId = dimTypeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSelectedFalg() {
		return selectedFalg;
	}

	public void setSelectedFalg(String selectedFalg) {
		this.selectedFalg = selectedFalg;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	
}
