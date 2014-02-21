package com.synesoft.fisp.app.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.model.MainForm;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.MainParam;
import com.synesoft.fisp.domain.model.SysLoginLog;
import com.synesoft.fisp.domain.service.MainService;
import com.synesoft.fisp.domain.service.SysLoginLogService;

/**
 * @author yyw
 *
 */
@Controller
@RequestMapping(value = "main")
public class MainController {
	private final static LogUtil log = new LogUtil(MainController.class);

	@Autowired
	private MainService ftzMainService;
	@Autowired
	private SysLoginLogService sysLoginLogService;

	@ModelAttribute
	public MainForm setForm() {
		return new MainForm();
	}
	
	@RequestMapping("/init")
	public String init(HttpServletRequest request) {
		log.info("init...");
		
		List<MainParam> generalList = ftzMainService.getGeneralTODO();
		List<MainParam> importantList = ftzMainService.getImportantTODO();
		request.setAttribute("generalList", generalList);
		request.setAttribute("importantList", importantList);
		
		return "layout/rightFrame";
	}

	@RequestMapping("/loginLogDtl")
	public String loginLogDtl(Model model, MainForm form, @PageableDefaults Pageable pageable) {
		log.info("loginLogDtl...");
		
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setUserid(ContextConst.getCurrentUser().getUserid());
		
		Page<SysLoginLog> page = sysLoginLogService.getPage(pageable, loginLog);
		if (page.getContent().size() == 0) {
			
		} else {
			model.addAttribute("page", page);
		}
		
		return "layout/login_log_dtl";
	}
}
