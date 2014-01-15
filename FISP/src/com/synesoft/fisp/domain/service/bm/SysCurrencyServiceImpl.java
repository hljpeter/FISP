package com.synesoft.fisp.domain.service.bm;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.domain.model.SysCurrency;
import com.synesoft.fisp.domain.repository.bm.SysCurrencyRepository;
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

}
