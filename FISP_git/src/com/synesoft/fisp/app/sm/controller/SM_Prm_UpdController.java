package com.synesoft.fisp.app.sm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.sm.model.SM_Prm_QryForm;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.sm.SysParamService;

/**
 * @author 李峰
 * @date 2013-11-19 下午4:29:36
 * @version 1.0
 * @description 
 * @system FISP
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("SM_Prm_Upd")
public class SM_Prm_UpdController {
	
	private static final Logger logger = LoggerFactory.getLogger(SM_Prm_UpdController.class);
	
	@Autowired
	private SysParamService sysParamServ;
	
	/**
	 * 新增返回页面
	 * @return
	 */
	@RequestMapping("Init")
	public String init(SM_Prm_QryForm form,
			BindingResult result, Model model){
		logger.info("detailSearch...");
		SysParam sysParam=form.getSysParam();
		String tmp_group = CommonUtil.decryptor(sysParam.getParamGroup());
		String tmp_code = CommonUtil.decryptor(sysParam.getParamCode());		
		sysParam.setParamGroup(tmp_group);
		sysParam.setParamCode(tmp_code);		
		SysParam sysParamd=sysParamServ.querySysParam(sysParam);
		form.setSysParam(sysParamd);
		return "sm/SM_Prm_Upd";
	}
	
	/**
	 * 修改
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping("Update")
	public String update(SM_Prm_QryForm form,BindingResult result, Model model) {
		logger.info("start update ...");
		SysParam sysParam = form.getSysParam();
		UserInf u = ContextConst.getCurrentUser();
		sysParam.setUpdateUser(u.getUserid());
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
		String d = sdf.format(new Date());
		sysParam.setUpdateTime(d);
		try {
			sysParamServ.updateSysParam(sysParam);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_Prm_Qry";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0002")));
		return "sm/SM_Prm_Upd";
	}

}
