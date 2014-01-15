package com.synesoft.sysrunner.domain.vo;

import java.util.List;

import com.synesoft.sysrunner.domain.model.BatStepInfo;

public class BatStepInfoVO {

	private BatStepInfo batStepInfo;

	private List<String> dependencyStepList;

	private List<String> unDependencyStepList;
	
	private List<String> excludeStepList;

	private List<String> unExcludeStepList;
	
	

	public List<String> getExcludeStepList() {
		return excludeStepList;
	}

	public void setExcludeStepList(List<String> excludeStepList) {
		this.excludeStepList = excludeStepList;
	}

	public List<String> getUnExcludeStepList() {
		return unExcludeStepList;
	}

	public void setUnExcludeStepList(List<String> unExcludeStepList) {
		this.unExcludeStepList = unExcludeStepList;
	}

	public BatStepInfo getBatStepInfo() {
		return batStepInfo;
	}

	public void setBatStepInfo(BatStepInfo batStepInfo) {
		this.batStepInfo = batStepInfo;
	}

	public List<String> getDependencyStepList() {
		return dependencyStepList;
	}

	public void setDependencyStepList(List<String> dependencyStepList) {
		this.dependencyStepList = dependencyStepList;
	}

	public List<String> getUnDependencyStepList() {
		return unDependencyStepList;
	}

	public void setUnDependencyStepList(List<String> unDependencyStepList) {
		this.unDependencyStepList = unDependencyStepList;
	}
	
	

}
