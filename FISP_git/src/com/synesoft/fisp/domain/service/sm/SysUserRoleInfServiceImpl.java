package com.synesoft.fisp.domain.service.sm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.repository.sm.SysUserRoleRepository;
import com.synesoft.fisp.domain.repository.sm.SysUserRoleTmpRepository;

/**
 * @author yyw
 * @date 2013-12-23
 * @version 1.0
 * @Description 用户角色Service（角色不绑定机构）
 */
@Service("sysUserRoleInfService")
public class SysUserRoleInfServiceImpl implements SysUserRoleInfService {
	
	private static LogUtil log=new LogUtil(SysUserRoleInfServiceImpl.class);
	
	@Autowired
	protected SysUserRoleRepository sysUserRoleRepository;
	@Autowired
	protected SysUserRoleTmpRepository sysUserRoleTmpRepository;
	
	@Override
	public List<SysUserRoleTmp> queryByUserIdFromTmp(SysUserRoleTmp sysUserRoleTmp) {
		log.debug("queryByUserIdFromTmp start");
		
		List<SysUserRoleTmp> list = sysUserRoleTmpRepository.querylist(sysUserRoleTmp);
		
		return list;
	}

	@Override
	public List<String> queryRoleIdByUserIdFromTmp(SysUserRoleTmp sysUserRoleTmp) {
		log.debug("queryRoleIdByUserIdFromTmp start");
		
		List<String> list = new ArrayList<String>();
		
		List<SysUserRoleTmp> resultList = queryByUserIdFromTmp(sysUserRoleTmp);
		for (int i = 0; i < resultList.size(); i++) {
			SysUserRoleTmp tmp = resultList.get(i);
			list.add(tmp.getRoleid());
		}
		
		return list;
	}

	@Override
	public List<SysUserRole> queryByUserId(SysUserRole sysUserRole) {
		log.debug("queryByUserId start");
		
		List<SysUserRole> list = sysUserRoleRepository.querylist(sysUserRole);
		
		return list;
	}

	@Override
	public List<String> queryRoleIdByUserId(SysUserRole sysUserRole) {
		log.debug("queryRoleIdByUserId start");
		
		List<String> list = new ArrayList<String>();
		
		List<SysUserRole> resultList = queryByUserId(sysUserRole);
		for (int i = 0; i < resultList.size(); i++) {
			SysUserRole tmp = resultList.get(i);
			list.add(tmp.getRoleid());
		}
		
		return list;
	}

	@Override
	public List<SysUserRoleTmp> queryRoleListMerge(SysUserRoleTmp sysUserRoleTmp) {
		log.debug("queryRoleListMerge start");
		
		List<SysUserRoleTmp> list = sysUserRoleRepository.queryRoleListMerge(sysUserRoleTmp);
		
		return list;
	}

	@Override
	public String[] queryRoleListMerge(SysUserRole sysUserRole) {
		log.debug("queryRoleListMerge start");
		
		SysUserRoleTmp sysUserRoleTmp = new SysUserRoleTmp();
		sysUserRoleTmp.setUserid(sysUserRole.getUserid().trim());
		sysUserRoleTmp.setOptstatus(sysUserRole.getOptstatus().trim());
		
		List<SysUserRoleTmp> list = queryRoleListMerge(sysUserRoleTmp);
		if (null != list && !list.isEmpty()) {
			String[] resultArray = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				SysUserRoleTmp tmp = list.get(i);
				resultArray[i] = tmp.getRoleid().trim();
			}
			
			return resultArray;
		}
		
		return null;
	}

}
