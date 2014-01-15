package com.synesoft.fisp.domain.repository.sm;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.Sm_Date_QryForm;


public interface HolidayMainRepository {	
	public Page<Sm_Date_QryForm> queryInputList(Pageable pageable,Sm_Date_QryForm smDateForm);
	public List<Map<String,Object>> queryDetailList(String dtlYear);
	public void update(String addHolidays,String removeHolidays);
	public void update2(String strday,String endday,String holiflg);
}
