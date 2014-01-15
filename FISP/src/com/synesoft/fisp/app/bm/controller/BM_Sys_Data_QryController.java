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
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.BM_Sys_DataForm;
import com.synesoft.fisp.domain.model.SysChgSysLog;
import com.synesoft.fisp.domain.model.SysChgSysLogWithBLOBs;
import com.synesoft.fisp.domain.service.bm.OperatorLogService;

/**
 * 系统维护操作日志查询
 * @author ljch
 * 
 */

@Controller
@RequestMapping(value = "BM_Sys_Data_Qry")
public class BM_Sys_Data_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Sys_Data_QryController.class);

	@ModelAttribute
	public BM_Sys_DataForm setUpForm() {
		return new BM_Sys_DataForm();
	}
	
	@RequestMapping("Qry")
	public String search(BM_Sys_DataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		SysChgSysLog sysChgSysLog=listForm.getSysChgSysLog();
		if(sysChgSysLog==null){
			sysChgSysLog=new SysChgSysLog();
		}
		/**对界面上日期和时间的处理**/
		if(listForm.getMinOperDate()!=null&&!listForm.getMinOperDate().equals("")){
			sysChgSysLog.setMinOperDate(listForm.getMinOperDate().replace("-", ""));
		}
		if(listForm.getMaxOperDate()!=null&&!listForm.getMaxOperDate().equals("")){
			sysChgSysLog.setMaxOperDate(listForm.getMaxOperDate().replace("-", ""));
		}
		if(listForm.getMinOperTime()!=null&&!listForm.getMinOperTime().equals("")){
			sysChgSysLog.setMinOperTime(listForm.getMinOperTime().replace(":", ""));
		}
		if(listForm.getMaxOperTime()!=null&&!listForm.getMaxOperTime().equals("")){
			sysChgSysLog.setMaxOperTime(listForm.getMaxOperTime().replace(":", ""));
		}
		sysChgSysLog.setBranchId(listForm.getBranchId());
		sysChgSysLog.setFuncId(listForm.getFuncId());
		sysChgSysLog.setOperType(listForm.getOperType());
		sysChgSysLog.setUserId(listForm.getUserId());
		Page<SysChgSysLog> page= operatorLogService.transQuerySysChgSysLogList(pageable, sysChgSysLog);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "bm/BM_Sys_Data_Qry";
	}

	@RequestMapping("Init")
	public String detailSearch(BM_Sys_DataForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysChgSysLogWithBLOBs sysChgSysLog=form.getSysChgSysLog();
		sysChgSysLog=operatorLogService.transQuerySysChgSysLog(sysChgSysLog);
		form.setSysChgSysLog(sysChgSysLog);
		return "bm/BM_Sys_Data_Dtl";
	}
	@Autowired
	private OperatorLogService operatorLogService;
}
