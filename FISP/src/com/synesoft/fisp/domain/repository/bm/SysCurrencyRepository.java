package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysCurrency;


public interface SysCurrencyRepository {	
	public Page<SysCurrency> queryInputList(Pageable pageable,SysCurrency sysCurrency);

}
