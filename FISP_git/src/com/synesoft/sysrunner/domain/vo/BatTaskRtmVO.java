package com.synesoft.sysrunner.domain.vo;

import com.synesoft.sysrunner.domain.model.BatTaskRtm;

public class BatTaskRtmVO {

	private BatTaskRtm batTaskRtm;
	
	private String task_Name = "";

	private String runtimeId;

	public BatTaskRtm getBatTaskRtm() {
		return batTaskRtm;
	}

	public void setBatTaskRtm(BatTaskRtm batTaskRtm) {
		this.batTaskRtm = batTaskRtm;
	}

	public String getTask_Name() {
		return task_Name;
	}

	public void setTask_Name(String task_Name) {
		this.task_Name = task_Name;
	}

	public String getRuntimeId() {
		return runtimeId;
	}

	public void setRuntimeId(String runtimeId) {
		this.runtimeId = runtimeId;
	}
	
	
}
