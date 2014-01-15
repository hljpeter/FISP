package com.synesoft.fisp.app.dp.controller;

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

import com.synesoft.fisp.app.dp.model.DepositBalanceCheckForm;
import com.synesoft.fisp.domain.model.DmDepositBalanceCheck;
import com.synesoft.fisp.domain.service.dp.DmDepositBalanceCheckService;

@Controller
@RequestMapping(value = "dp06")
public class DP_06_01Controller {
	private static final Logger logger = LoggerFactory
	.getLogger(DP_06_01Controller.class);
	
	@ModelAttribute
	public DepositBalanceCheckForm setUpForm() {
		return new DepositBalanceCheckForm();
}
	
	@RequestMapping("01/init")
	public String init(@PageableDefaults Pageable pageable,Model model) {
			logger.info("init...");
			DmDepositBalanceCheck depositBalanceCheck=new DmDepositBalanceCheck();
			depositBalanceCheck.setCensusMonth("201309");
			Page<DmDepositBalanceCheck> page= dmDepositBalanceCheckService.QueryDmDepositBalanceCheckQueryList(pageable, depositBalanceCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}
			return "dp/DP_06_01";
	}

	@RequestMapping("01/search")
	public String search(DepositBalanceCheckForm listForm,@PageableDefaults Pageable pageable, Model model) {
			logger.info("start search ...");
			DmDepositBalanceCheck depositBalanceCheck=new DmDepositBalanceCheck();
			listForm.setCensusMonth("201309"); //TODO ERROR-NEEDS TO MODIFY 
			depositBalanceCheck.setCensusMonth(listForm.getCensusMonth().replace("-", ""));
			
			Page<DmDepositBalanceCheck> page= dmDepositBalanceCheckService.QueryDmDepositBalanceCheckQueryList(pageable, depositBalanceCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.sm.0001")));
			}
			return "dp/DP_06_01";
	}
	
//	//明细查询
//	@RequestMapping("01/detailSearch")
//	public String detailSearch(@ModelAttribute ImpFileCfgForm form, BindingResult result, Model model) {
//		logger.info("detailSearch...");
//		String fileId=form.getFileId();
//		ImpFileCfg impfilecfg =new ImpFileCfg();
//		impfilecfg.setFileId(fileId);
//	    impfilecfg=impFileCfgService.queryImpFileCfg(impfilecfg);
//		model.addAttribute("impFileCfg", impfilecfg);
//		return "ifc/IFC_01_02";
//	}
	
	@Autowired
	private DmDepositBalanceCheckService dmDepositBalanceCheckService;
}
