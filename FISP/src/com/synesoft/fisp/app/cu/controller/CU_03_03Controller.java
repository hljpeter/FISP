package com.synesoft.fisp.app.cu.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.cu.model.TipsAcctInfForm;
import com.synesoft.fisp.domain.model.TipsAcctInf;
import com.synesoft.fisp.domain.service.cu.TipsAcctInfMaintenanceService;

/**
 * 账户信息
 * @author Han
 */
@Controller
@RequestMapping(value = "cu03")
public class CU_03_03Controller {
	private static final Logger logger = LoggerFactory.getLogger(CU_03_03Controller.class);

	@ModelAttribute
	public TipsAcctInfForm setUpForm() {
		return new TipsAcctInfForm();
	}
	
	@RequestMapping("03/init")
	public String init() {
		logger.info("init...");
		return "cu/CU_03_03";
	}

	@RequestMapping("03/search")
	public String search(TipsAcctInfForm listForm,@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		String acctcode=StringUtil.trim(listForm.getAcctcode());
		String acctname=StringUtil.trim(listForm.getAcctname());
		TipsAcctInf tipsAcctInf = new TipsAcctInf();
		tipsAcctInf.setAcctcode(acctcode);
		tipsAcctInf.setAcctname(acctname);

		Page<TipsAcctInf> page= tipsAcctInfMaintenanceService.transQueryTipsAcctInfList(pageable, tipsAcctInf);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute("infomsg",ResultMessages.info().add(ResultMessage.fromCode("w.sm.0001")));
		}
		return "cu/CU_03_03";
	}

	@RequestMapping("03/autoMatch")
	public String select(@ModelAttribute TipsAcctInfForm listForm, Model model, HttpServletResponse httpServletResponse) {
		logger.info("start select ...");
		String acctcode = StringUtil.trim(listForm.getAcctcode());
		
		TipsAcctInf tipsAcctInf = new TipsAcctInf();
		tipsAcctInf.setAcctcode(acctcode);

		TipsAcctInf tai = tipsAcctInfMaintenanceService.transQueryTipsAcctInf(tipsAcctInf);

		JSONObject jo=new JSONObject();
		
		if (tai!=null){
			jo.put("acctcode", tai.getAcctcode());
			jo.put("acctname", tai.getAcctname());
			jo.put("accttype", tai.getAccttype());
			jo.put("reckbankno", tai.getReckbankno());
			jo.put("genbankname", tai.getGenbankname());
		}else{
			jo.put("acctcode","");
			jo.put("acctname", "");
			jo.put("accttype", "");
			jo.put("reckbankno", "");
			jo.put("genbankname", "");
		}
		
		try {
			httpServletResponse.getWriter().write(jo.toString());
		} catch (IOException e) {
			logger.info("select error:" + e.getMessage());
		} finally {
			return null;
		}
	}
	
	@Autowired
	private TipsAcctInfMaintenanceService tipsAcctInfMaintenanceService;

}
