package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysCurrency;


public interface SysCurrencyRepository {	
	public Page<SysCurrency> queryInputList(Pageable pageable,SysCurrency sysCurrency);

	public SysCurrency queryInput(SysCurrency queryCur);

	public int undateCur(SysCurrency updateCur);  //修改

	public int deleteCur(SysCurrency curDel);   //删除

	public int addCur(SysCurrency addCur);  //新增

}
