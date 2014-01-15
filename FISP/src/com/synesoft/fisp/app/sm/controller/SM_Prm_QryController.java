package com.synesoft.fisp.app.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.sm.model.SM_Prm_QryForm;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.service.sm.SysParamService;

/**
 * @author 李峰
 * @date 2013-11-19 上午10:19:07
 * @version 1.0
 * @description 
 * @system FISP
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("SM_Prm_Qry")
public class SM_Prm_QryController {

	private static final Logger logger = LoggerFactory.getLogger(SM_Prm_QryController.class);
	
	@Autowired
	private SysParamService sysParamServ;
	
	/**
	 * 查询系统参数
	 * @param smDateForm
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("Qry")
	public String search(SM_Prm_QryForm form,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		SysParam sysParam = new SysParam();
		if(null!=form.getParamGroup()){
			sysParam.setParamGroup(form.getParamGroup());
			sysParam.setParamCode(form.getParamCode());
			sysParam.setParamName(form.getParamName());
		}
		Page<SysParam> page= sysParamServ.querySysPrmList(pageable, sysParam);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "sm/SM_Prm_Qry";
	}
	
	/**
	 * 删除
	 * @param form
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("Del")
	public String del(SM_Prm_QryForm form,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start del ...");
		SysParam sysParam = form.getSysParam();
		if(null==sysParam){
			return "forward:/SM_Prm_Qry/Qry";
		}
		try {
			sysParamServ.delSysParam(sysParam);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/SM_Prm_Qry/Qry";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0003")));
		form.setSysParam(new SysParam());
		return "forward:/SM_Prm_Qry/Qry";
	}
}
