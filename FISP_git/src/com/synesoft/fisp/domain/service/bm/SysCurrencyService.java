package com.synesoft.fisp.domain.service.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.domain.model.SysCurrency;


public interface SysCurrencyService {

	public Page<SysCurrency> querySysCurrencyPage(Pageable pageable,Bm_Cur_QryForm form);

	public SysCurrency querySysCurrency(SysCurrency queryCur);  //修改查询初始化

	public int updateCur(SysCurrency updateCur);  //修改

	public int deleteCur(SysCurrency curDel);     //删除

	public void addCur(SysCurrency addCur);       //新增


}
