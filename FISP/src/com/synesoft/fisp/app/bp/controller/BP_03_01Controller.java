package com.synesoft.fisp.app.bp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.rq.model.ReportDataForm;
import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.fisp.domain.model.ReportDataDetail;
import com.synesoft.fisp.domain.service.rq.ReportQueryService;
import com.synesoft.pisa.domain.service.sheet.ItemService;

@Controller
@RequestMapping(value = "bp03")
public class BP_03_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_03_01Controller.class);

	@ModelAttribute
	public ReportDataForm setUpForm() {
		return new ReportDataForm();
	}
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("01/init")
	public String init(ReportDataForm listForm,Model model) {
		logger.info("init...");
		
		return "forward:/bp03/01/search";
	}
	
	@RequestMapping("01/search")
	public String search(ReportDataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("init...");
		
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
		listForm.setReportMonth(DateUtil.getDateToString(cal.getTime(),DateUtil.MONTH_PATTEN));
		
		ReportData reportData=listForm.getReportData();
		if(reportData==null){
			reportData=new ReportData();
		}
		if(listForm.getReportMonth()!=null&&!listForm.getReportMonth().equals("")){
			reportData.setReportMonth(listForm.getReportMonth().replace("-", ""));
		}
		reportData=reportQueryService.transQueryReportDataByCondition(reportData);
		if(reportData!=null){
			reportData.setReportMonth(DateUtil.formatStringToDatePattern(reportData.getReportMonth()));
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
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.rq.0001")));
		}
		return "bp/BP_03_01";
	}
	
	@RequestMapping("01/searchIndex")
	public String searchIndex(ReportDataForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("init...");
		
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
		listForm.setReportMonth(DateUtil.getDateToString(cal.getTime(),DateUtil.MONTH_PATTEN));
		
		ReportData reportData=listForm.getReportData();
		if(reportData==null){
			reportData=new ReportData();
		}
		if(listForm.getReportMonth()!=null&&!listForm.getReportMonth().equals("")){
			reportData.setReportMonth(listForm.getReportMonth().replace("-", ""));
		}
		
		
		reportData.setReportStatus("1");
//		reportData=reportQueryService.transQueryReportDataByCondition(reportData);
//		if(reportData!=null){
//			reportData.setReportMonth(DateUtil.formatStringToDatePattern(reportData.getReportMonth()));
//			listForm.setReportData(reportData);
//			ReportDataDetail reportDataDetail=new ReportDataDetail();
//			reportDataDetail.setReportId(reportData.getReportId());
			Page page = itemService.queryCounts(pageable,reportData);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.rq.0001")));
			}
//		}else{
//			model.addAttribute(
//					"infomsg",
//					ResultMessages.info().add(
//							ResultMessage.fromCode("w.rq.0001")));
//		}
		return "bp/BP_03_01_index";
	}
	
	@Autowired
	private ReportQueryService reportQueryService;
}
