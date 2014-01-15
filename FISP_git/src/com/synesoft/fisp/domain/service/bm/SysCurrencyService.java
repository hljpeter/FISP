package com.synesoft.fisp.domain.service.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.domain.model.SysCurrency;


public interface SysCurrencyService {

	public Page<SysCurrency> querySysCurrencyPage(Pageable pageable,Bm_Cur_QryForm form);

}
