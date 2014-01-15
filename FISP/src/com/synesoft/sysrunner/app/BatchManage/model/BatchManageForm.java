package com.synesoft.sysrunner.app.BatchManage.model;

import java.util.List;

import javax.validation.Valid;

import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatStepInfo;
import com.synesoft.sysrunner.domain.model.BatTaskInfo;
import com.synesoft.sysrunner.domain.vo.BatTaskDimVO;

public class BatchManageForm {
	
	public static interface BMG_TASK_Add {

	}
	public static interface BMG_STEP_Add {

	}
	public static interface BMG_DIM_Add {

	}
	
	private String query_task_start_day = "";

	private String query_task_name = "";

	private String query_task_type = "";

	private String query_taskStartTimeStart = "";

	private String query_taskStartTimeEnd = "";
	
	private String query_cfg_name = "";
	
	private String query_cfg_type = "";

	@Valid
	private BatTaskInfo batTaskInfo;
	@Valid
	private BatStepInfo batStepInfo;
	
	private String del_task_id = "";

	private String del_step_id = "";
	
	private String query_dimensionId = "";

	private String query_dimTypeId = "";
	
	private String query_dimensionName = "";
	
	private String del_dimensionId = "";

	private String del_dimTypeId = "";
	
	private List<String> dependencyStepList;

	private List<String> unDependencyStepList;

	private List<BatStepInfo> dependencyStepListShow;

	private List<BatStepInfo> unDependencyStepListShow;
	
	private List<String> excludeStepList;

	private List<String> unExcludeStepList;

	private List<BatStepInfo> excludeStepListShow;

	private List<BatStepInfo> unExcludeStepListShow;
	
	private List<BatTaskDimVO> batTaskDimVOs;
	
	@Valid
	private BatDimInfo batDimInfo;
	
	private List<String> dimTaskList;

	private List<String> unDimTaskList;

	private List<BatTaskInfo> dimTaskListShow;

	private List<BatTaskInfo> unDimTaskListShow;
	
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
	public List<BatStepInfo> getExcludeStepListShow() {
		return excludeStepListShow;
	}
	public void setExcludeStepListShow(List<BatStepInfo> excludeStepListShow) {
		this.excludeStepListShow = excludeStepListShow;
	}
	public List<BatStepInfo> getUnExcludeStepListShow() {
		return unExcludeStepListShow;
	}
	public void setUnExcludeStepListShow(List<BatStepInfo> unExcludeStepListShow) {
		this.unExcludeStepListShow = unExcludeStepListShow;
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
	public List<BatStepInfo> getDependencyStepListShow() {
		return dependencyStepListShow;
	}
	public void setDependencyStepListShow(List<BatStepInfo> dependencyStepListShow) {
		this.dependencyStepListShow = dependencyStepListShow;
	}
	public List<BatStepInfo> getUnDependencyStepListShow() {
		return unDependencyStepListShow;
	}
	public void setUnDependencyStepListShow(
			List<BatStepInfo> unDependencyStepListShow) {
		this.unDependencyStepListShow = unDependencyStepListShow;
	}
	public String getQuery_task_start_day() {
		return query_task_start_day;
	}
	public void setQuery_task_start_day(String query_task_start_day) {
		this.query_task_start_day = query_task_start_day;
	}
	public String getQuery_task_name() {
		return query_task_name;
	}
	public void setQuery_task_name(String query_task_name) {
		this.query_task_name = query_task_name;
	}
	public String getQuery_task_type() {
		return query_task_type;
	}
	public void setQuery_task_type(String query_task_type) {
		this.query_task_type = query_task_type;
	}
	public String getQuery_taskStartTimeStart() {
		return query_taskStartTimeStart;
	}
	public void setQuery_taskStartTimeStart(String query_taskStartTimeStart) {
		this.query_taskStartTimeStart = query_taskStartTimeStart;
	}
	public String getQuery_taskStartTimeEnd() {
		return query_taskStartTimeEnd;
	}
	public void setQuery_taskStartTimeEnd(String query_taskStartTimeEnd) {
		this.query_taskStartTimeEnd = query_taskStartTimeEnd;
	}
	public BatTaskInfo getBatTaskInfo() {
		return batTaskInfo;
	}
	public void setBatTaskInfo(BatTaskInfo batTaskInfo) {
		this.batTaskInfo = batTaskInfo;
	}
	public BatStepInfo getBatStepInfo() {
		return batStepInfo;
	}
	public void setBatStepInfo(BatStepInfo batStepInfo) {
		this.batStepInfo = batStepInfo;
	}
	public String getDel_task_id() {
		return del_task_id;
	}
	public void setDel_task_id(String del_task_id) {
		this.del_task_id = del_task_id;
	}
	public String getDel_step_id() {
		return del_step_id;
	}
	public void setDel_step_id(String del_step_id) {
		this.del_step_id = del_step_id;
	}
	public String getQuery_cfg_name() {
		return query_cfg_name;
	}
	public void setQuery_cfg_name(String query_cfg_name) {
		this.query_cfg_name = query_cfg_name;
	}
	public String getQuery_cfg_type() {
		return query_cfg_type;
	}
	public void setQuery_cfg_type(String query_cfg_type) {
		this.query_cfg_type = query_cfg_type;
	}
	
	public List<BatTaskDimVO> getBatTaskDimVOs() {
		return batTaskDimVOs;
	}
	public void setBatTaskDimVOs(List<BatTaskDimVO> batTaskDimVOs) {
		this.batTaskDimVOs = batTaskDimVOs;
	}
	public List<String> getDimTaskList() {
		return dimTaskList;
	}
	public void setDimTaskList(List<String> dimTaskList) {
		this.dimTaskList = dimTaskList;
	}
	public List<String> getUnDimTaskList() {
		return unDimTaskList;
	}
	public void setUnDimTaskList(List<String> unDimTaskList) {
		this.unDimTaskList = unDimTaskList;
	}
	public List<BatTaskInfo> getDimTaskListShow() {
		return dimTaskListShow;
	}
	public void setDimTaskListShow(List<BatTaskInfo> dimTaskListShow) {
		this.dimTaskListShow = dimTaskListShow;
	}
	public List<BatTaskInfo> getUnDimTaskListShow() {
		return unDimTaskListShow;
	}
	public void setUnDimTaskListShow(List<BatTaskInfo> unDimTaskListShow) {
		this.unDimTaskListShow = unDimTaskListShow;
	}
	public String getDel_dimensionId() {
		return del_dimensionId;
	}
	public void setDel_dimensionId(String del_dimensionId) {
		this.del_dimensionId = del_dimensionId;
	}
	public String getDel_dimTypeId() {
		return del_dimTypeId;
	}
	public void setDel_dimTypeId(String del_dimTypeId) {
		this.del_dimTypeId = del_dimTypeId;
	}
	public BatDimInfo getBatDimInfo() {
		return batDimInfo;
	}
	public void setBatDimInfo(BatDimInfo batDimInfo) {
		this.batDimInfo = batDimInfo;
	}
	public String getQuery_dimensionId() {
		return query_dimensionId;
	}
	public void setQuery_dimensionId(String query_dimensionId) {
		this.query_dimensionId = query_dimensionId;
	}
	public String getQuery_dimTypeId() {
		return query_dimTypeId;
	}
	public void setQuery_dimTypeId(String query_dimTypeId) {
		this.query_dimTypeId = query_dimTypeId;
	}
	public String getQuery_dimensionName() {
		return query_dimensionName;
	}
	public void setQuery_dimensionName(String query_dimensionName) {
		this.query_dimensionName = query_dimensionName;
	}
	
}
