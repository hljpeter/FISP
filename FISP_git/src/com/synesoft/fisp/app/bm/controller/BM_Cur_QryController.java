package com.synesoft.fisp.app.bm.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.domain.model.SysCurrency;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.fisp.domain.service.bm.SysCurrencyService;
import com.synesoft.fisp.domain.service.bm.SysDataDictService;


/**
 * 
 * @author ljch
 * 
 */

@Controller
@RequestMapping("BM_Cur_Qry")
public class BM_Cur_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Cur_QryController.class);

	@ModelAttribute
	public Bm_Cur_QryForm setForm() {
		return new Bm_Cur_QryForm();
	}
	@RequestMapping("Init")
	public String init() {
		logger.info("init...");
		return "bm/BM_Cur_Qry";
	}

	@RequestMapping("Qry")
	public String search(Bm_Cur_QryForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		Page<SysCurrency> page= sysCurrencyService.querySysCurrencyPage(pageable, listForm);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		System.out.println(page.getContent().size());
		return "bm/BM_Cur_Qry";
	}

	
	@Autowired
	private SysCurrencyService sysCurrencyService;

}
