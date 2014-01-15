package com.synesoft.fisp.domain.service.bm;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.Bm_Data_AddForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_UpdForm;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.repository.bm.SysDataDictRepository;

@Service("sysDataDictService")
public class SysDataDictServiceImpl implements SysDataDictService {
	@Resource
	SysDataDictRepository sysDataDictRepository;	

	
	@Override
	@Transactional
	public int deleteSysDataDict(SysDataDict sysDataDict) {		
		ResultMessages messages = ResultMessages.error();
		if (sysDataDictRepository.deleteSysDataDict(sysDataDict)!=1){
			messages.add("e.bm.1001");
			throw new BusinessException(messages);
		}
		/*记录日志*/
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time=DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer afterData = new StringBuffer();
		afterData.append("删除了元素组名为：");
		afterData.append(sysDataDict.getGroupCode());
		afterData.append(",元素语言为：");
		afterData.append(sysDataDict.getMetaLan());
		afterData.append(",元素值为：");
		afterData.append(sysDataDict.getMetaVal()+"的元素");
		TlrLogPrint.tlrSysLogPrint("BM_Data_Qry", orgInfo.getOrgid().trim(), userInfo.getUserid(), 
				userInfo.getUsername(), "D",time.substring(0, 8), time.substring(8, 14),"", afterData.toString());
		return 0;
	}
	
	@Override
	@Transactional
	public int insertSysDataDict(Bm_Data_AddForm form) {
		ResultMessages messages = ResultMessages.error();
		String time=DateUtil.getNow("yyyyMMddHHmmss");
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		SysDataDict sysDataDict=form.getSysDataDict();
		sysDataDict.setCreateTime(time);
		sysDataDict.setCreateUser(userInfo.getUsername());
		sysDataDict.setGroupCode(form.getGroupCode());
		sysDataDict.setGroupName(form.getGroupName());
		sysDataDict.setMetaDesc(form.getMetaDesc());
		sysDataDict.setMetaLan(form.getMetaLan());
		sysDataDict.setMetaName(form.getMetaName());
		sysDataDict.setMetaVal(form.getMetaVal());
		if(null==sysDataDictRepository.query(sysDataDict)){
			int ret=sysDataDictRepository.insertSysDataDict(sysDataDict);
			if(ret!=1){
				//插入失败
				messages.add("e.bm.1003");
				throw new BusinessException(messages);
			}
		}else{
			//已经存在相同的记录
			messages.add("e.bm.1004");
			throw new BusinessException(messages);
		}
			
		StringBuffer afterData = new StringBuffer();
		afterData.append("新增数据字典元素: ");
		afterData.append("groupCode:");
		afterData.append(form.getGroupCode());
		afterData.append(",metaName:");
		afterData.append(form.getMetaName());
		afterData.append(",metaVal:");
		afterData.append(form.getMetaVal());
		afterData.append(",metaLan:");
		afterData.append(form.getMetaLan());
		TlrLogPrint.tlrSysLogPrint("BM_Data_Qry", orgInfo.getOrgid().trim(), userInfo.getUserid(), 
				userInfo.getUsername(), "A", time.substring(0, 8), time.substring(8, 14),"", afterData.toString());
		return 0;
	}
	
	@Override
	@Transactional
	public int updateSysDataDict(Bm_Data_UpdForm form) {
		ResultMessages messages = ResultMessages.error();
		String time=DateUtil.getNow("yyyyMMddHHmmss");
		SysDataDict sysDataDict=form.getSysDataDict();
		sysDataDict.setMetaVal(form.getMetaVal().trim());
		sysDataDict.setMetaName(form.getMetaName().trim());
		UserInf userInfo = ContextConst.getCurrentUser();
		sysDataDict.setUpdateUser(userInfo.getUsername());
		sysDataDict.setUpdateTime(time);
		System.out.println("metaVal"+sysDataDict.getMetaVal());
		int result=sysDataDictRepository.updateSysDataDict(sysDataDict);
		if(1!=result){
			messages.add("e.bm.1002");
			throw new BusinessException(messages);
		}
		/*记录日志*/
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		StringBuffer beforeData=new StringBuffer();
		StringBuffer afterData=new StringBuffer();
		beforeData.append("groupCode:");
		beforeData.append(form.getSysDataDict().getGroupCode());
		beforeData.append(",metaName:");
		beforeData.append(form.getBeforeMetaName());
		beforeData.append(",metaVal:");
		beforeData.append(form.getBeforeMetaVal());
		
		afterData.append("groupCode:");
		afterData.append(form.getSysDataDict().getGroupCode());
		afterData.append(",metaName:");
		afterData.append(form.getMetaName());
		afterData.append(",metaVal:");
		afterData.append(form.getMetaVal());
		TlrLogPrint.tlrSysLogPrint("BM_Data_Qry", orgInfo.getOrgid().trim(), userInfo.getUserid(), 
				userInfo.getUsername(), "M", time.substring(0, 8), time.substring(8, 14),beforeData.toString(), afterData.toString());
		return 0;
	}

	@Override
	public Page<SysDataDict> querySysDataDictGroupPage(Pageable pageable,
			Bm_Data_QryForm form) {		
		return sysDataDictRepository.queryInputList(pageable, form);
	}

	@Override
	public SysDataDict querySysDataDictByKey(SysDataDict sysDataDict) {		
		return sysDataDictRepository.query(sysDataDict);
	}

}
