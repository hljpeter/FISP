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

import com.synesoft.fisp.app.bm.model.BM_Biz_DataForm;
import com.synesoft.fisp.domain.model.SysChgBizLog;
import com.synesoft.fisp.domain.model.SysChgBizLogWithBLOBs;
import com.synesoft.fisp.domain.service.bm.OperatorLogService;

/**
 * 系统业务操作日志查询
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "BM_Biz_Data_Qry")
public class BM_Biz_Data_QryController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Biz_Data_QryController.class);

	@ModelAttribute
	public BM_Biz_DataForm setUpForm() {
		return new BM_Biz_DataForm();
	}
	
	@RequestMapping("Qry")
	public String search(BM_Biz_DataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		SysChgBizLog sysChgBizLog=listForm.getSysChgBizLog();
		if(sysChgBizLog==null){
			sysChgBizLog=new SysChgBizLog();
		}
		/**对界面上日期和时间的处理**/
		if(listForm.getMinOperDate()!=null&&!listForm.getMinOperDate().equals("")){
			sysChgBizLog.setMinOperDate(listForm.getMinOperDate().replace("-", ""));
		}
		if(listForm.getMaxOperDate()!=null&&!listForm.getMaxOperDate().equals("")){
			sysChgBizLog.setMaxOperDate(listForm.getMaxOperDate().replace("-", ""));
		}
		if(listForm.getMinOperTime()!=null&&!listForm.getMinOperTime().equals("")){
			sysChgBizLog.setMinOperTime(listForm.getMinOperTime().replace(":", ""));
		}
		if(listForm.getMaxOperTime()!=null&&!listForm.getMaxOperTime().equals("")){
			sysChgBizLog.setMaxOperTime(listForm.getMaxOperTime().replace(":", ""));
		}
		sysChgBizLog.setBranchId(listForm.getBranchId());
		sysChgBizLog.setFuncId(listForm.getFuncId());
		sysChgBizLog.setOperType(listForm.getOperType());
		sysChgBizLog.setUserId(listForm.getUserId());
		Page<SysChgBizLog> page= operatorLogService.transQuerySysChgBizLogList(pageable, sysChgBizLog);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "bm/BM_Biz_Data_Qry";
	}

	@RequestMapping("Init")
	public String detailSearch(BM_Biz_DataForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysChgBizLogWithBLOBs sysChgBizLog=form.getSysChgBizLog();
		sysChgBizLog=operatorLogService.transQuerySysChgBizLog(sysChgBizLog);
		form.setSysChgBizLog(sysChgBizLog);
		return "bm/BM_Biz_Data_Dtl";
	}
	
	@Autowired
	private OperatorLogService operatorLogService;

}
