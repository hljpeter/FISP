package com.synesoft.fisp.app.al.controller;

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
import com.synesoft.fisp.app.al.model.SysAlertRcptForm;
import com.synesoft.fisp.domain.model.SysAlertRcpt;
import com.synesoft.fisp.domain.service.al.SysAlertRcptService;

/**
 *  æØ±®…Ë÷√√˜œ∏
 * @author 
 * 
 */

@Controller
@RequestMapping(value = "al01")
public class AL_01_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(AL_01_01Controller.class);
	
	@Autowired
	private SysAlertRcptService sysAlertRcptService;
	

	@ModelAttribute
	public SysAlertRcptForm setInfoUpForm() {
		return new SysAlertRcptForm();
	}
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "al/AL_01_01";
	}

	@RequestMapping("01/search")
	public String search(SysAlertRcptForm form,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		SysAlertRcpt dto=new SysAlertRcpt();
		dto.setBranchId(form.getBranchId().trim());
		dto.setAlertType(form.getAlertType());
		Page<SysAlertRcpt> page= sysAlertRcptService.transQuerySysAlertRcptList(pageable,dto);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.al.0001")));
		}
		return "al/AL_01_01";
	}
	
	@RequestMapping("01/del")
	public String del(SysAlertRcptForm form,
			BindingResult result, Model model) {
		logger.info("start del ...");
		if (result.hasErrors()) {
			return "al/AL_01_01";
		}
		try {
			sysAlertRcptService.transDel(form.getSysAlertRcpt().getId());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "al/AL_01_01";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.al.0003")));
		return "forward:/al01/01/search";
	}

	@RequestMapping("01/detailSearch")
	public String detailSearch(@ModelAttribute SysAlertRcptForm form,
			@PageableDefaults Pageable pageable, Model model){
		logger.info("detailSearch...");
		form=sysAlertRcptService.transQuerySysAlertRcpt(form.getSysAlertRcpt().getId());
		model.addAttribute("sysAlertRcptForm", form);
		return "al/AL_01_04";
	}	
}
