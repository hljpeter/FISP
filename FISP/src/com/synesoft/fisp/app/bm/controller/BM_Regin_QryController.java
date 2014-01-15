package com.synesoft.fisp.app.bm.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;
import com.synesoft.fisp.app.bm.model.Bm_Regin_QryForm;
import com.synesoft.fisp.domain.model.SysRegionInfo;
import com.synesoft.fisp.domain.service.bm.SysRegionService;


/**
 * 
 * @author ljch
 * 
 */

@Controller
@RequestMapping("BM_Area_Qry")
public class BM_Regin_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Regin_QryController.class);

	@ModelAttribute
	public Bm_Regin_QryForm setForm() {
		return new Bm_Regin_QryForm();
	}
	@RequestMapping("Init")
	public String init() {
		logger.info("init...");
		return "bm/BM_Region_Qry";
	}

	@RequestMapping("Qry")
	public String search(Bm_Regin_QryForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		Page<SysRegionInfo> page= sysRegionService.querySysCurrencyPage(pageable, listForm);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}		
		return "bm/BM_Region_Qry";
	}

	
	@Autowired
	private SysRegionService sysRegionService;

}
