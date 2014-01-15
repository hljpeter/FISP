package com.synesoft.fisp.domain.repository.sm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.SysFuncInfo;
import com.synesoft.fisp.domain.model.UserRoleInf;

@Repository
public class SysFuncInfoRepositoryImpl implements SysFuncInfoRepository{
	
	@Override
	public Map<String,String> queryAllSysFuncInfo(){
		//获取功能权限信息表对应的map
		SysFuncInfo sysFuncInfo1 = new SysFuncInfo();
		List<SysFuncInfo>  sysFuncInfoList = queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_BYKEY, sysFuncInfo1);
		Map<String,String> map = new HashMap<String,String>();
		if(sysFuncInfoList.size()>0){
			for(SysFuncInfo sysFuncInfo:sysFuncInfoList){
				map.put(StringUtil.trim(sysFuncInfo.getFuncId()), StringUtil.trim(sysFuncInfo.getFuncDesc()));
			}
		}
		return map;
	}
	@Override
	public List<SysFuncInfo> queryAllSysFuncInfo(UserRoleInf userRoleInf){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_ALL_MENUS, userRoleInf);
	}
	@Override
	public List<SysFuncInfo> queryFirstSysFuncInfo(Map<String,Object> paramMap){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_FIRST_MENUS, paramMap);
	}
	@Override
	public List<SysFuncInfo> querySecondSysFuncInfo(Map<String,Object> paramMap){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_SECOND_MENUS, paramMap);
	}
	@Override
	public List<SysFuncInfo> queryThirdSysFuncInfo(Map<String,Object> paramMap){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_THIRD_MENUS, paramMap);
	}
	@Resource
	private QueryDAO queryDAO;
	@Override
	public List<SysFuncInfo> queryAllFirMenus(){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_ALL_FIR_MENUS, "");
	}
	@Override
	public List<SysFuncInfo> queryMenusBySupId(Map<String,Object> paramMap){
		return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_MENUS_BY_SUPID, paramMap);
	}
	
	 /**
	  * 不与机构绑定
	  * @param userRoleInf
	  * @return
	  */
	 public List<SysFuncInfo> queryAllSysFuncInfoNoOrg(UserRoleInf userRoleInf){
		 return queryDAO.executeForObjectList(Table.SYS_FUNC_INFO+"."+SQLMap.SELECT_ALL_MENUS_NO_ORG, userRoleInf);
	 }
}
