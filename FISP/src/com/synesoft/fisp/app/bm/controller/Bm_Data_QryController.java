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

import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.fisp.domain.service.bm.SysDataDictService;


/**
 * 
 * @author ljch
 * 
 */

@Controller
@RequestMapping("BM_Data_Qry")
public class Bm_Data_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(Bm_Data_QryController.class);

	@ModelAttribute
	public Bm_Data_QryForm setDataForm() {
		return new Bm_Data_QryForm();
	}
	@RequestMapping("Init")
	public String init() {
		logger.info("init...");
		return "bm/BM_Data_Qry";
	}

	@RequestMapping("Qry")
	public String search(Bm_Data_QryForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		Page<SysDataDict> page= sysDataDictService.querySysDataDictGroupPage(pageable, listForm);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		System.out.println(page.getContent().size());
		return "bm/BM_Data_Qry";
	}

	@RequestMapping("Del")
	public String del(Bm_Data_QryForm form,
			BindingResult result, Model model) {
		logger.info("start del ...");
		if (result.hasErrors()) {
			return "bm/BM_Data_Qry";
		}
		try{
			sysDataDictService.deleteSysDataDict(form.getSysDataDict());
		}catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bm/BM_Data_Qry";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0003")));
		return  "forward:/BM_Data_Qry/Qry";
	}
	
	@Autowired
	private SysDataDictService sysDataDictService;

}
