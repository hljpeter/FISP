package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfTmpRepository;

/**
 * 
 * @author michelle.wang
 * 
 */

@Service("userOrgInfService")
public class UserOrgInfServiceImpl implements UserOrgInfService {

	
	@Override
	public List<UserOrgInf> transQueryUserOrgInfList(UserOrgInf userOrgInf){
		return userOrgInfRepository.queryUserOrgInfList(userOrgInf);
	}
	
	@Override
	public List<UserOrgInfTmp> transQueryUserOrgInfTmpList(UserOrgInfTmp userOrgInfTmp){
		return userOrgInfTmpRepository.queryUserOrgInfTmpList(userOrgInfTmp);
	}

	@Override
	public List<UserOrgInfTmp> transQueryUserOrgInfMerge(UserOrgInfTmp userOrgInfTmp) {
		return userOrgInfRepository.transQueryUserOrgInfMerge(userOrgInfTmp);
	}

	@Autowired
	protected UserOrgInfRepository userOrgInfRepository;
	
	@Autowired
	protected UserOrgInfTmpRepository userOrgInfTmpRepository;


}
