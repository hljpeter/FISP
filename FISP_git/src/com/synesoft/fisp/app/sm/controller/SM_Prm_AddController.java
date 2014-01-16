package com.synesoft.fisp.app.sm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.sm.model.SM_Prm_QryForm;
import com.synesoft.fisp.app.sm.model.SM_Prm_QryForm.SM_Prm_QryFormAdd;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.sm.SysParamService;

/**
 * @author 李峰
 * @date 2013-11-19 下午3:20:09
 * @version 1.0
 * @description 
 * @system FISP
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("SM_Prm_Add")
public class SM_Prm_AddController {

	private static final Logger logger = LoggerFactory.getLogger(SM_Prm_AddController.class);
	
	@Autowired
	private SysParamService sysParamServ;
	
	@ModelAttribute
	public SM_Prm_QryForm setUpForm() {
		return new SM_Prm_QryForm();
	}
	
	/**
	 * 新增返回页面
	 * @return
	 */
	@RequestMapping("Init")
	public String init(){
		logger.info("init...");
		return "sm/SM_Prm_Add";
	}
	
	/**
	 * 新增
	 * @param smDateForm
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("Add")
	public String add(@Validated({SM_Prm_QryFormAdd.class})SM_Prm_QryForm form,BindingResult result, Model model) {
		logger.info("start add ...");
		if (result.hasErrors()) {
			return "sm/SM_Prm_Add";
		}
		SysParam sysParam = form.getSysParam();
		//判断系统参数组号              
		String paramGroup = sysParam.getParamGroup();
		
		if(paramGroup.indexOf("'")>0||paramGroup.indexOf("\"")>0){
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.sm.7004")));
			return "sm/SM_Prm_Add";
		}
		//系统参数编号 
		String paramCode = sysParam.getParamCode();
		if(paramCode.indexOf("'")>0||paramCode.indexOf("\"")>0){
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.sm.7005")));
			return "sm/SM_Prm_Add";
		}
		//验证组号 编号是否存在
		int i = sysParamServ.queryGroupCode(sysParam);
		if(i>0){
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.sm.7003",sysParam.getParamGroup(),sysParam.getParamCode())));
			return "sm/SM_Prm_Add";
		}
		//
		UserInf u = ContextConst.getCurrentUser();
		sysParam.setCreateUser(u.getUserid());
		sysParam.setUpdateUser(u.getUserid());
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
		String d = sdf.format(new Date());
		sysParam.setCreateTime(d);
		sysParam.setUpdateTime(d);
		try {
			sysParamServ.add(sysParam);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_Prm_Qry";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		return "sm/SM_Prm_Add";
	}
	
}
