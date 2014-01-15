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
import com.synesoft.fisp.domain.model.ReportDataDetail;
import com.synesoft.fisp.domain.service.rq.ReportQueryService;

@Controller
@RequestMapping(value = "rq01")
public class RQ_01_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(RQ_01_02Controller.class);

	@ModelAttribute
	public ReportDataForm setUpForm() {
		return new ReportDataForm();
	}
	@RequestMapping("02/detailSearch")
	public String detailSearch(ReportDataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("init...");
		ReportData reportData=listForm.getReportData();
		reportData=reportQueryService.transQueryReportData(reportData);
		listForm.setReportData(reportData);
		ReportDataDetail reportDataDetail=new ReportDataDetail();
		reportDataDetail.setReportId(reportData.getReportId());
		Page<ReportDataDetail> page= reportQueryService.transQueryReportDataDetailList(pageable, reportDataDetail);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.rq.0001")));
		}
		return "rq/RQ_01_02";
	}
	
	@Autowired
	private ReportQueryService reportQueryService;
}
