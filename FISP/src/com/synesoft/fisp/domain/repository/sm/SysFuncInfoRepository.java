package com.synesoft.fisp.domain.repository.sm;

import java.util.List;
import java.util.Map;

import com.synesoft.fisp.domain.model.SysFuncInfo;
import com.synesoft.fisp.domain.model.UserRoleInf;

public interface SysFuncInfoRepository {
	
	public Map<String,String> queryAllSysFuncInfo();
	/**
	 * 获取用户一级菜单
	 * 
	 * */
	 public List<SysFuncInfo> queryFirstSysFuncInfo(Map<String,Object> paramMap);
	/**
	 * 获取用户二级菜单
	 * 
	 * */
	 public List<SysFuncInfo> querySecondSysFuncInfo(Map<String,Object> paramMap);
	/**
	 * 获取用户三级菜单
	 * 
	 * */
	 public List<SysFuncInfo> queryThirdSysFuncInfo(Map<String,Object> paramMap);
	/**
	 * 获取所有一级的菜单
	 * 
	 * */
	 public List<SysFuncInfo> queryAllFirMenus();
	 /**
	 * 根据上级菜单获取所有下级的菜单
	 * 
	 * */
	 public List<SysFuncInfo> queryMenusBySupId(Map<String,Object> paramMap);
	 
	 public List<SysFuncInfo> queryAllSysFuncInfo(UserRoleInf userRoleInf);
	 /**
	  * 不与机构绑定
	  * @param userRoleInf
	  * @return
	  */
	 public List<SysFuncInfo> queryAllSysFuncInfoNoOrg(UserRoleInf userRoleInf);
}
