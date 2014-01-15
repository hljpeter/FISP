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
 *  警报设置新增
 * @author 
 * 
 */

@Controller
@RequestMapping(value = "al01")
public class AL_01_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(AL_01_02Controller.class);
	@Autowired
	private SysAlertRcptService  sysAlertRcptService;
	
	

	@ModelAttribute
	public SysAlertRcptForm setInfoUpForm() {
		return new SysAlertRcptForm();
	}
	@RequestMapping("02/init")
	public String init() {
		logger.info("init...");
		return "al/AL_01_02";
	}

	@RequestMapping("02/add")
	public String add(@Validated({AL_01_01.class}) SysAlertRcptForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		if (result.hasErrors()) {
			return "al/AL_01_02";
		}
		try {
			form.getSysAlertRcpt().setMaker(ContextConst.getCurrentUser().getUserid());
			form.getSysAlertRcpt().setMkTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
			sysAlertRcptService.transAdd(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "al/AL_01_02";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.al.0002")));
		return "al/AL_01_02";
	}

}
