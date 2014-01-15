package com.synesoft.fisp.app.sm.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.sm.model.Sm_Date_DtlForm;
import com.synesoft.fisp.domain.service.sm.HolidayMaintainService;


/**
 * @author 李敬昌
 * @date 2013年11月13日 14:51:22
 * @version 1.0
 * @Description 节假日信息详细
 * @System 
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */

@Controller
@RequestMapping("SM_Date_Dtl")
public class SM_Date_DtlController {
	
	private static final Logger logger = LoggerFactory.getLogger(SM_Date_DtlController.class);

	@ModelAttribute
	public Sm_Date_DtlForm setDateForm() {
		return new Sm_Date_DtlForm();
	}
	

	/**
	 * 查询节假日明细
	 * @return
	 */
	@RequestMapping("Dtl")
	public String detailSearch(Sm_Date_DtlForm form,
			BindingResult result, Model model,HttpServletRequest request) {
		logger.info("detailSearch...");		
		List<Object> list = new ArrayList<Object>();		
		Sm_Date_DtlForm dtlList=holidayService.transMaintainDetail(form);
		if(dtlList.getHoliMap().isEmpty()){
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
			return "sm/SM_Date_Qry";
		}	
		Map<String,List<String>> fMap=new HashMap<String,List<String>>();
		Map<String, List<String>> tMap=dtlList.getHoliMap();
		Set<String> ks = tMap.keySet();
		Iterator it = ks.iterator();
		while(it.hasNext()){
			String key=(String) it.next();
			if(key.subSequence(4, 6).equals("01")){
				fMap.put("month1", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("02")){
				fMap.put("month2", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("03")){
				fMap.put("month3", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("04")){
				fMap.put("month4", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("05")){
				fMap.put("month5", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("06")){
				fMap.put("month6", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("07")){
				fMap.put("month7", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("08")){
				fMap.put("month8", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("09")){
				fMap.put("month9", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("10")){
				fMap.put("month10", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("11")){
				fMap.put("month11", tMap.get(key));
			}
			if(key.subSequence(4, 6).equals("12")){
				fMap.put("month12", tMap.get(key));
			}
		}
		
		list.add(fMap);
		JSONArray json = new JSONArray();
		json.addAll(list);
		json.listIterator();	
		ListIterator  li = json.listIterator();
		while(li.hasNext()){
			String str =li.next().toString();
			request.setAttribute("holiJson", str);
			System.out.println(str);			
		}
		
		return "sm/SM_Date_Dtl";
	}
	
	@Autowired
	private HolidayMaintainService holidayService;

}
