package com.synesoft.fisp.domain.service.al;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.al.model.SysAlertRcptForm;
import com.synesoft.fisp.domain.model.SysAlertRcpt;
import com.synesoft.fisp.domain.repository.al.SysAlertRcptRepository;
import com.synesoft.fisp.domain.service.NumberService;
@Service("SysAlertRcptService")
public class SysAlertRcptServiceImpl implements SysAlertRcptService {

	@Autowired
	private SysAlertRcptRepository sysAlertRcptRepository;
	
	@Autowired
	protected NumberService numberService;
	
	@Override
	public Page<SysAlertRcpt> transQuerySysAlertRcptList(Pageable pageable,
			SysAlertRcpt sysAlertRcpt) {
		return sysAlertRcptRepository.queryList(pageable,sysAlertRcpt);
	}
	@Override
	@Transactional
	public void transDel(String id) {
		ResultMessages messages = ResultMessages.error();
		int result=sysAlertRcptRepository.delById(id);
		if(result!=1){
			messages.add("e.al.2001");
			throw new BusinessException(messages);
		}
	}
	@Override
	@Transactional
	public void transAdd(SysAlertRcptForm form) {
		ResultMessages messages = ResultMessages.error();
		SysAlertRcpt sysAlertRcpt=form.getSysAlertRcpt();
		sysAlertRcpt.setId(numberService.getSysIDSequence("0000",4));
		sysAlertRcpt.setAlertType(form.getAlertType());
		sysAlertRcpt.setBranchId(form.getBranchId());
		sysAlertRcpt.setNoticeMthd(form.getNoticeMthd());
		sysAlertRcpt.setProjId(form.getProjId());
		sysAlertRcpt.setRcptAddr(form.getRcptAddr());
		int result=sysAlertRcptRepository.insertSysAlertRcpt(sysAlertRcpt);
		if(result!=1){
			messages.add("e.al.2002");
			throw new BusinessException(messages);
		}
	}
	@Override
	public SysAlertRcptForm transQuerySysAlertRcpt(String id) {
		SysAlertRcptForm form=new SysAlertRcptForm();
		SysAlertRcpt sart=sysAlertRcptRepository.querySysAlertRcptById(id);
		form.setSysAlertRcpt(sart);
		form.setAlertType(sart.getAlertType());
		form.setBranchId(sart.getBranchId());
		form.setNoticeMthd(sart.getNoticeMthd());
		form.setProjId(sart.getProjId());
		form.setRcptAddr(sart.getRcptAddr());
		return form;
	}
	@Override
	@Transactional
	public void transUpdate(SysAlertRcptForm form) {
		ResultMessages messages = ResultMessages.error();
		SysAlertRcpt sysAlertRcpt=form.getSysAlertRcpt();
		sysAlertRcpt.setAlertType(form.getAlertType());
		sysAlertRcpt.setBranchId(form.getBranchId());
		sysAlertRcpt.setNoticeMthd(form.getNoticeMthd());
		sysAlertRcpt.setProjId(form.getProjId());
		sysAlertRcpt.setRcptAddr(form.getRcptAddr());
		int result=sysAlertRcptRepository.updateSysAlertRcpt(sysAlertRcpt);
		if(result!=1){
			messages.add("e.al.2003");
			throw new BusinessException(messages);
		}
	}

}
