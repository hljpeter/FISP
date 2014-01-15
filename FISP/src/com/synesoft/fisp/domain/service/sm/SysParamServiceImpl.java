package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.context.MemoryContextHolder;
import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.repository.sm.SysParamRepository;

@Service
public class SysParamServiceImpl implements SysParamService {
	
	@Autowired
	protected SysParamRepository sysParamRepos;
	
	@Autowired
	private QueryDAO queryDAO;

	private MessagesUtil message = MessagesUtil.getInstance();
	
	public Page<SysParam> querySysParamPage(Pageable pageable){
		return sysParamRepos.querySysParamPage(pageable);
	}
	
	public SysParam querySysParam(SysParam sysParam){
		return sysParamRepos.querySysParam(sysParam);
	}
	
	@Transactional
	public int insertSysParam(SysParam sysParam){
		int i = sysParamRepos.insertSysParam(sysParam);
		
		//刷新系统参数信息
		List<SysParam> listSysParam = queryDAO.executeForObjectList(Table.SYS_PARAM + "." + SQLMap.SELECT_LIST, new SysParam());
		MemoryContextHolder.refreshMemoryResource(MemoryResourceType.SYS_PARAM, listSysParam);
		
		return i; 
	}

	@Transactional
	public int updateSysParam(SysParam sysParam){
		String before = generateSysParamString(querySysParam(sysParam));
		int i = sysParamRepos.updateSysParam(sysParam);
		TlrLogPrint.tlrSysLogPrint("SM_Prm_Qry",
				CommonConst.DATA_LOG_OPERTYPE_MODIFY,
				before, generateSysParamString(sysParam));
		 
		//刷新系统参数信息
		List<SysParam> listSysParam = queryDAO.executeForObjectList(Table.SYS_PARAM + "." + SQLMap.SELECT_LIST, new SysParam());
		MemoryContextHolder.refreshMemoryResource(MemoryResourceType.SYS_PARAM, listSysParam);
		
		return i;
	}
	
	@Transactional
	public int delSysParam(SysParam sysParam){
		String s = generateSysParamString(querySysParam(sysParam));
		int i = sysParamRepos.delSysParam(sysParam);
		TlrLogPrint.tlrSysLogPrint("SM_Prm_Qry",
				CommonConst.DATA_LOG_OPERTYPE_DELETE,
				s, "");
		return i;
	}
	
	public List<SysParam> querySysParamList(){
		return sysParamRepos.querySysParamList();
	}
	

	@Override
	public Page<SysParam> querySysPrmList(Pageable pageable,
			SysParam sysParam) {
		return sysParamRepos.querySysParamPage(pageable,sysParam);
	}

	@Transactional
	public int add(SysParam sysParam) {
		int i = sysParamRepos.add(sysParam);
		TlrLogPrint.tlrSysLogPrint("SM_Prm_Qry",
				CommonConst.DATA_LOG_OPERTYPE_ADD,
				"", generateSysParamString(sysParam));
		return i;
	}
	
	public int queryGroupCode(SysParam sysParam){
		return sysParamRepos.queryGroupCode(sysParam);
	}
	
	private String generateSysParamString(SysParam sysParam){
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.sysParam.group"))
		.append(CommonConst.SEPARATE_KEY_VALUE)
		.append(sysParam.getParamGroup())
		.append(CommonConst.SEPARATE_TWO_FIELD)
		.append(message.getMessage("fisp.label.sysParam.code"))
		.append(CommonConst.SEPARATE_KEY_VALUE)
		.append(sysParam.getParamCode())
		.append(CommonConst.SEPARATE_TWO_FIELD)
		.append(message.getMessage("fisp.label.sysParam.name"))
		.append(CommonConst.SEPARATE_KEY_VALUE)
		.append(sysParam.getParamName())
		.append(CommonConst.SEPARATE_TWO_FIELD)
		.append(message.getMessage("fisp.label.sysParam.val"))
		.append(CommonConst.SEPARATE_KEY_VALUE)
		.append(sysParam.getParamVal())
		.append(CommonConst.SEPARATE_TWO_FIELD)
		.append(message.getMessage("fisp.label.sysParam.desc"))
		.append(CommonConst.SEPARATE_KEY_VALUE)
		.append(sysParam.getParamDesc())
		.append(CommonConst.SEPARATE_TWO_FIELD);
		return sb.toString();
	}
}
