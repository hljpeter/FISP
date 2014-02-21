package com.synesoft.fisp.domain.service.bm;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.SysCurrency;
import com.synesoft.fisp.domain.repository.bm.SysCurrencyRepository;
import com.synesoft.ftzmis.app.common.util.DateUtil;
@Service("sysCurrencyService")
public class SysCurrencyServiceImpl implements SysCurrencyService {
	@Resource
	SysCurrencyRepository sysCurrencyRepository;
	@Override
	public Page<SysCurrency> querySysCurrencyPage(Pageable pageable,
			Bm_Cur_QryForm form) {		
		SysCurrency sysCurrency = form.getSysCurrency();		
		return sysCurrencyRepository.queryInputList(pageable, sysCurrency);
	}
	@Override
	public SysCurrency querySysCurrency(SysCurrency queryCur) {
		return sysCurrencyRepository.queryInput(queryCur);
	}
	@Override
	@Transactional
	public int updateCur(SysCurrency updateCur) {
		ResultMessages messages = ResultMessages.error();
		int result = sysCurrencyRepository.undateCur(updateCur);
		if(1!=result){
			messages.add("e.bm.curr.005");
			throw new BusinessException(messages);
		}
		return 0;
	}
	@Override
	@Transactional
	public int deleteCur(SysCurrency curDel) {
		ResultMessages messages = ResultMessages.error();
		if(sysCurrencyRepository.deleteCur(curDel)!=1){
			messages.add("e.bm.curr.006");
			throw new BusinessException(messages);
		}
		return 0;
	}
	@Override
	@Transactional
	public void addCur(SysCurrency addCur) {
		// TODO Auto-generated method stub
		ResultMessages messages = ResultMessages.error();
		addCur.setCreateUser(ContextConst.getCurrentUser().getUsername());
		addCur.setCreateTime(DateUtil.getNowInputDateTime());
		 SysCurrency currencyDel=sysCurrencyRepository.queryInput(addCur);;
		 if(currencyDel==null){
			 if(sysCurrencyRepository.addCur(addCur)!=1){
					messages.add("e.bm.curr.007");
					throw new BusinessException(messages);
				}
		 }else{				
			 //已经存在相同的记录
			 messages.add("e.bm.curr.004");
			 throw new BusinessException(messages);
		 }
	}
}
