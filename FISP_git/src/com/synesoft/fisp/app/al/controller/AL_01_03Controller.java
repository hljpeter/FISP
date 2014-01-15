package com.synesoft.fisp.app.al.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.al.model.SysAlertRcptForm;
import com.synesoft.fisp.app.al.model.SysAlertRcptForm.AL_01_01;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.domain.service.al.SysAlertRcptService;

/**
 * ¾¯±¨ÉèÖÃÐÞ¸Ä
 * @author 
 * 
 */

@Controller
@RequestMapping(value = "al01")
public class AL_01_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(AL_01_03Controller.class);

	@ModelAttribute
	public SysAlertRcptForm setInfoUpForm() {
		return new SysAlertRcptForm();
	}
	
	@RequestMapping("03/search")
	public String search(SysAlertRcptForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "al/AL_01_03";
		}
		form=SysAlertRcptService.transQuerySysAlertRcpt(form.getSysAlertRcpt().getId());
		model.addAttribute("sysAlertRcptForm", form);
		return "al/AL_01_03";
	}

	@RequestMapping("03/modify")
	public String modify(@Validated({AL_01_01.class}) SysAlertRcptForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		if (result.hasErrors()) {
			return "al/AL_01_03";
		}
		try {
			form.getSysAlertRcpt().setMaker(ContextConst.getCurrentUser().getUserid());
			form.getSysAlertRcpt().setMkTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
			SysAlertRcptService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "al/AL_01_03";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.al.0001")));
		return "al/AL_01_03";
	}

	@Autowired
	private SysAlertRcptService SysAlertRcptService;

}
