package com.synesoft.fisp.app.rq.controller;

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

import com.synesoft.fisp.app.rq.model.ReportDataForm;
import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.fisp.domain.service.rq.ReportQueryService;

@Controller
@RequestMapping(value = "rq01")
public class RQ_01_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(RQ_01_01Controller.class);

	@ModelAttribute
	public ReportDataForm setUpForm() {
		return new ReportDataForm();
	}
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "rq/RQ_01_01";
	}
	
	@RequestMapping("01/search")
	public String search(ReportDataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		ReportData reportData=listForm.getReportData();
		if(reportData==null){
			reportData=new ReportData();
		}
		if(reportData.getReportMonth()!=null&&!reportData.getReportMonth().equals("")){
			reportData.setReportMonth(reportData.getReportMonth().replace("-", ""));
		}
		Page<ReportData> page= reportQueryService.transQueryReportDataList(pageable, reportData);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.rq.0001")));
		}
		return "rq/RQ_01_01";
	}
	
	@Autowired
	private ReportQueryService reportQueryService;
}
