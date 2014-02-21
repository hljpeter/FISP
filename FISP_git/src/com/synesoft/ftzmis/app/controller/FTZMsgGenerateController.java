package com.synesoft.ftzmis.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.dataproc.common.util.AppCtxUtil;
import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.model.FTZMsgGenerateForm;
import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;
import com.synesoft.ftzmis.domain.service.FTZMsgGenManager;
import com.synesoft.ftzmis.domain.service.FTZMsgGenerateService;

/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
@Controller
@RequestMapping(value = "FTZMsgGenerate")
public class FTZMsgGenerateController {

	private LogUtil log = new LogUtil(FTZMsgGenerateController.class);

	@ModelAttribute
	public FTZMsgGenerateForm setForm() {
		return new FTZMsgGenerateForm();
	}

	// 统计页面初始化
	@RequestMapping("Init")
	public String init(Model model, FTZMsgGenerateForm form) {
		log.debug("init() start ...");

		List<String> orgList = new ArrayList<String>();
		List<UserOrgInf> list = ContextConst.getUserOrgList();
		for (UserOrgInf inf: list) {
			orgList.add(inf.getOrgid());
		}
		
		List<FtzMsgGenerateParam> reulstList = service.getList(orgList);
		form.setList(reulstList);
		
		return "ftzmis/FTZMsgGen_Qry";
	}

	// 报文生成
	@RequestMapping("Gen")
	public String generate(Model model, FTZMsgGenerateForm form) {
		log.debug("generate() start ...");

		try {
			String[] selecteds = form.getSelecteds();
			if (null != selecteds && 0 != selecteds.length) {

				List<String> orgList = new ArrayList<String>();
				List<String> msgNoList = new ArrayList<String>();
				for (int i = 0; i < selecteds.length; i++) {
					String[] keys = selecteds[i].split("_");
					orgList.add(keys[0]);
					msgNoList.add(keys[1]);
				}

				FtzMsgGenerateParam param = new FtzMsgGenerateParam();
				param.setBranchs(orgList);
				param.setMsgNos(msgNoList);
				param.setBlankFlag(CommonConst.MSG_BLANK_FLAG_NORMAL);
				
				FTZMsgGenManager manager = (FTZMsgGenManager) AppCtxUtil.getCtx().getBean("ftzMsgGenManager"); 
				manager.setParam(param);
				manager.run();
			}
		} catch(BusinessException e) {
			return "ftzmis/FTZMsgGen_Qry_Ret";
		}
		
		return "forward:/FTZMsgGenerate/Refresh";
	}

	// 报文结果页面刷新
	@RequestMapping("Refresh")
	public String refresh(Model model, FTZMsgGenerateForm form) {
		log.debug("refresh() start ...");

		String[] selecteds = form.getSelecteds();
		if (null != selecteds && 0 != selecteds.length) {

			List<String> orgList = new ArrayList<String>();
			List<String> msgNoList = new ArrayList<String>();
			for (int i = 0; i < selecteds.length; i++) {
				String[] keys = selecteds[i].split("_");
				orgList.add(service.getSrc(keys[0]));
				msgNoList.add(keys[1]);
			}

			FtzMsgGenerateParam param = new FtzMsgGenerateParam();
			param.setBranchs(orgList);
			param.setMsgNos(msgNoList);
			param.setWorkDate(procCommonService.queryWorkDate());
			
			List<FtzMsgGenerateParam> reulstList = service.getResultList(param);
			
			form.setList(reulstList);
		}
		return "ftzmis/FTZMsgGen_Qry_Ret";
	}

	@Autowired
	private FTZMsgGenerateService service;
	@Autowired
	private ProcCommonService procCommonService;

}
