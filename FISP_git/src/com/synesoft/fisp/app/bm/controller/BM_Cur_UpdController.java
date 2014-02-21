package com.synesoft.fisp.app.bm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.Bm_Cur_QryForm;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.SysCurrency;
import com.synesoft.fisp.domain.service.bm.SysCurrencyService;
import com.synesoft.ftzmis.app.common.util.DateUtil;


/**
 * 
 * description:货币修改删除
 * 
 * @author wjl
 * @version 2014-2-18
 */
@Controller
@RequestMapping("BM_Cur_Upd")
public class BM_Cur_UpdController {

	private static final Logger logger = LoggerFactory
			.getLogger(BM_Cur_QryController.class);

	@ModelAttribute
	public Bm_Cur_QryForm setForm() {
		return new Bm_Cur_QryForm();
	}

	/**
	 * 新增初始化
	 * @return
	 */
	@RequestMapping("AddInit")
	public String Addinit() {
		logger.info("init...");
		return "bm/BM_Cur_Add";
	}
	/**
	 * 新增
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("Insert")
	public String insert(Model model, Bm_Cur_QryForm form) {
		logger.info("CurAddstart...");
		SysCurrency addCur = new SysCurrency();
		addCur.setCurrCode(form.getCurrCode());
		addCur.setCurrLan(form.getCurrLan());
		addCur.setCurrName(form.getCurrName());
		ResultMessages resultMessages = ResultMessages.error();
		// 验证
		if ("".equals(addCur.getCurrCode())|| null == addCur.getCurrCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.001");
			resultMessages.add(resultMessage);
		}

		if ("".equals(addCur.getCurrName())|| null == addCur.getCurrName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.002");
			resultMessages.add(resultMessage);
		}

		if ("".equals(addCur.getCurrLan()) || null == addCur.getCurrLan()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.003");
			resultMessages.add(resultMessage);
		}

		if (resultMessages.isNotEmpty()) {
			model.addAttribute("errmsg",resultMessages);
			return "bm/BM_Cur_Upd";
		}
		try{
		sysCurrencyService.addCur(addCur);
		
		}catch (BusinessException e) {
				model.addAttribute("errmsg", e.getResultMessages());
				return "bm/BM_Cur_Add";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		return "bm/BM_Cur_Add";
	}
	/**
	 * 修改初始化页面
	 * 
	 * @return
	 */
	@RequestMapping("Init")
	public String init(Bm_Cur_QryForm form, BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysCurrency queryCur = form.getSysCurrency();
		queryCur.setCurrCode(form.getCurrCode());
		queryCur.setCurrLan(form.getCurrLan());
		form.setCurrCode(form.getCurrCode());
		form.setCurrLan(form.getCurrLan());
		SysCurrency currencyDel = sysCurrencyService.querySysCurrency(queryCur);
		form.setSysCurrency(currencyDel);
		return "bm/BM_Cur_Upd";
	}

	@RequestMapping("Update")
	public String update(Model model, Bm_Cur_QryForm form) {
		logger.info("UpdateCurstart...");

		SysCurrency updateCur = new SysCurrency();
		updateCur.setCurrCode(form.getSysCurrency().getCurrCode());
		updateCur.setCurrLan(form.getSysCurrency().getCurrLan());
		updateCur.setCurrName(form.getSysCurrency().getCurrName());

		ResultMessages resultMessages = ResultMessages.error();
		updateCur.setRsv1(form.getCurrCode());// 修改条件
		updateCur.setRsv2(form.getCurrLan());

		// 验证
		if ("".equals(updateCur.getCurrCode())|| null == updateCur.getCurrCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.001");
			resultMessages.add(resultMessage);
		}

		if ("".equals(updateCur.getCurrName())|| null == updateCur.getCurrName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.002");
			resultMessages.add(resultMessage);
		}

		if ("".equals(updateCur.getCurrLan()) || null == updateCur.getCurrLan()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.bm.curr.003");
			resultMessages.add(resultMessage);
		}

		if (resultMessages.isNotEmpty()) {
			model.addAttribute("errmsg",resultMessages);
			return "bm/BM_Cur_Upd";
		}

		updateCur.setUpdateUser(ContextConst.getCurrentUser().getUsername());
		updateCur.setUpdateTime(DateUtil.getNowInputDateTime());
		if(form.getCurrCode().equals(updateCur.getCurrCode())&& form.getCurrLan().equals(updateCur.getCurrLan())){			
			try {
				sysCurrencyService.updateCur(updateCur);
				model.addAttribute("successmsg",
						ResultMessages.success().add("i.sm.0002"));
			} catch (BusinessException e) {
				model.addAttribute("errmsg", e.getResultMessages());
				return "bm/BM_Cur_Upd";
			}
		}else{
			 SysCurrency currencyDel=sysCurrencyService.querySysCurrency(updateCur);
			 if(currencyDel!=null){
				 model.addAttribute("errmsg",ResultMessages.info().add("e.bm.curr.004"));
				 logger.info("CurEnd...");
				 return "bm/BM_Cur_Upd";
		}
	}	
		try {
			sysCurrencyService.updateCur(updateCur);
			model.addAttribute("successmsg",
					ResultMessages.success().add("i.sm.0002"));
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bm/BM_Cur_Upd";
		}
		form.setCurrCode(updateCur.getCurrCode());
		form.setCurrLan(updateCur.getCurrLan());
		logger.info("UpdataCurEnd...");
		return "bm/BM_Cur_Upd";
	}
	

	// 删除
	@RequestMapping("Delete")
	public String delete(Model model, Bm_Cur_QryForm form) {
		logger.info("deletestart...");
		SysCurrency curDel = new SysCurrency();
		curDel.setCurrCode(form.getCurrCode());
		curDel.setCurrLan(form.getCurrLan());
		try {
			sysCurrencyService.deleteCur(curDel);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bm/BM_Cur_Upd";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0003")));
		return "forward:/BM_Cur_Qry/Qry";
	}

	@Autowired
	private SysCurrencyService sysCurrencyService;
}
