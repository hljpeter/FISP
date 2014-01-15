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

import com.synesoft.fisp.app.bm.model.BM_Oper_LogForm;
import com.synesoft.fisp.domain.model.SysOperLog;
import com.synesoft.fisp.domain.service.bm.OperatorLogService;

/**
 * 系统操作日志查询
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "BM_Oper_Log_Qry")
public class BM_Oper_Log_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Oper_Log_QryController.class);

	@ModelAttribute
	public BM_Oper_LogForm setUpForm() {
		return new BM_Oper_LogForm();
	}
	
	@RequestMapping("Qry")
	public String search(BM_Oper_LogForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		SysOperLog sysOperLog=listForm.getSysOperLog();
		if(sysOperLog==null){
			sysOperLog=new SysOperLog();
		}
		/**对界面上日期和时间的处理**/
		if(listForm.getMinOperDate()!=null&&!listForm.getMinOperDate().equals("")){
			sysOperLog.setMinOperDate(listForm.getMinOperDate().replace("-", ""));
		}
		if(listForm.getMaxOperDate()!=null&&!listForm.getMaxOperDate().equals("")){
			sysOperLog.setMaxOperDate(listForm.getMaxOperDate().replace("-", ""));
		}
		if(listForm.getMinOperTime()!=null&&!listForm.getMinOperTime().equals("")){
			sysOperLog.setMinOperTime(listForm.getMinOperTime().replace(":", ""));
		}
		if(listForm.getMaxOperTime()!=null&&!listForm.getMaxOperTime().equals("")){
			sysOperLog.setMaxOperTime(listForm.getMaxOperTime().replace(":", ""));
		}
		sysOperLog.setBranchId(listForm.getBranchId());
		sysOperLog.setFuncName(listForm.getFuncName());
		sysOperLog.setOperType(listForm.getOperType());
		sysOperLog.setUserId(listForm.getUserId());
		Page<SysOperLog> page= operatorLogService.transQuerySysOperLogList(pageable, sysOperLog);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "bm/BM_Oper_Log_Qry";
	}

	@RequestMapping("Init")
	public String detailSearch(BM_Oper_LogForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysOperLog sysOperLog=form.getSysOperLog();
		sysOperLog=operatorLogService.transQuerySysOperLog(sysOperLog);
		form.setSysOperLog(sysOperLog);
		return "bm/BM_Oper_Log_Dtl";
	}
	@Autowired
	private OperatorLogService operatorLogService;

}
