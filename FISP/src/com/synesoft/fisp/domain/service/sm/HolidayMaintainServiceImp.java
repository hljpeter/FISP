package com.synesoft.fisp.domain.service.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.sm.model.Sm_Date_DtlForm;
import com.synesoft.fisp.app.sm.model.Sm_Date_QryForm;
import com.synesoft.fisp.domain.repository.sm.HolidayMainRepository;
@Service("HolidayMaintainService")
public class HolidayMaintainServiceImp implements HolidayMaintainService {

	@Autowired
	protected HolidayMainRepository holidayMainRepository;
	
	@Override
	public Page<Sm_Date_QryForm> transQueryHolidayList(Pageable pageable,
			Sm_Date_QryForm smDateForm) {
		// TODO Auto-generated method stub
		return holidayMainRepository.queryInputList(pageable, smDateForm);
	}


	@Override
	public Sm_Date_DtlForm transMaintainDetail(Sm_Date_DtlForm form) {
		List<Map<String,Object>> list = holidayMainRepository.queryDetailList(form.getModYear());
		Sm_Date_DtlForm resultForm = new Sm_Date_DtlForm();
		Iterator<Map<String,Object>> it = list.iterator();
		String holidayTmp = null;
		//StringBuffer holiday = null;
		Map<String,List<String>> map =new HashMap<String,List<String>>();	
		
		while(it.hasNext()){
			Map<String,Object> map2=(Map<String,Object>) it.next();
			Iterator<Object> it2=map2.values().iterator();
			while(it2.hasNext()){
				holidayTmp=it2.next().toString();
				/*holiday = new StringBuffer();
				holiday.append("\"");
				holiday.append(holidayTmp.substring(0, 4));
				holiday.append("-");
				holiday.append(holidayTmp.substring(4, 6));
				holiday.append("-");
				holiday.append(holidayTmp.substring(6, 8));
				holiday.append("\"");*/
				if(map.get(holidayTmp.substring(0, 6)) == null){
					//System.out.println(holidayTmp.substring(0, 6));
					map.put(holidayTmp.substring(0, 6),new ArrayList<String>());
				}
				map.get(holidayTmp.substring(0, 6)).add(holidayTmp.toString());
				
			}
			
		}	
		
		resultForm.setHoliMap(map);	
		
		return resultForm;
	}

	@Override
	@Transactional
	public void transUpdate(String addHolidays, String removeHolidays) {
		holidayMainRepository.update(addHolidays, removeHolidays);
	}


	@Override
	@Transactional
	public void transUpdate2(String dayList) {		
		String day[] = dayList.split(",");		
		for(int i=0;i<day.length;i++){
			String strtmp[] = day[i].split("#");		
			String daytmp[] = strtmp[0].split("~");			
			String strday = daytmp[0];
			String endday = daytmp[1];
			String holiFlg = strtmp[1];
			
			holidayMainRepository.update2(strday, endday,holiFlg);
		}
		
	}

}
