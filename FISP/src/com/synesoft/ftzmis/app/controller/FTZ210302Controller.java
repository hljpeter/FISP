package com.synesoft.ftzmis.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZ210302Form;
import com.synesoft.ftzmis.app.model.FTZ210302Form.FTZ210302FormAddDtl;
import com.synesoft.ftzmis.app.model.FTZ210302Form.FTZ210302FormAddDtlDtl;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210302Service;

/**
 * 应付信用证录入/修改
 * @author 李峰
 * @date 2013-12-29 上午11:13:50
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("FTZ210302")
public class FTZ210302Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private NumberService numberService;
	
	@Autowired
	private FTZ210302Service ftz210302Service;
	
	@RequestMapping("AddQry")
	public String AddQuery(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("应付信用证查询开始...");

		// trans form to queryObject
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(DateUtil
				.getFormatDateRemoveSprit(form.getQuery_workDate_start()));
		query_FtzOffMsgCtl.setRsv2(DateUtil
				.getFormatDateRemoveSprit(form.getQuery_workDate_end()));
		query_FtzOffMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210302);
		if (!StringUtil.isNotTrimEmpty(form.getQuery_msgStatus())) {
		query_FtzOffMsgCtl.setMsgStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
				CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL });
		}
		//Query
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210302Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("应付信用证查询结束...");
			return "ftzmis/FTZ210302_Input_Qry";
		}
		
		return "ftzmis/FTZ210302_Input_Qry";
	}
	
	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210302Form form) {
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgStatus("01");
		form.setFtzOffMsgCtl(ftzOffMsgCtl);
		return "ftzmis/FTZ210302_Input_Add";
	}
	
	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model, @Validated({FTZ210302FormAddDtl.class}) FTZ210302Form form, BindingResult result) {
		logger.info("应付信用证批量录入开始...");
		if (result.hasErrors()) {
			return "ftzmis/FTZ210302_Input_Add";
		}
		// 获取录入信息
		FtzOffMsgCtl insert_FtzOffMsgCtl = form.getFtzOffMsgCtl();
		insert_FtzOffMsgCtl.setMsgId(numberService.getSysIDSequence(16));
		insert_FtzOffMsgCtl.setWorkDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzOffMsgCtl.getWorkDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		insert_FtzOffMsgCtl.setMakUserId(userInfo.getUserid());
		insert_FtzOffMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzOffMsgCtl.setMsgStatus("01");
		insert_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210302);
		// 插入信息
		int i = ftz210302Service.insertFtzOffMsgCtl(insert_FtzOffMsgCtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		}
		form.getFtzOffMsgCtl().setWorkDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffMsgCtl()
						.getWorkDate()));
		logger.info("应付信用证录入结束...");
		return "ftzmis/FTZ210302_Input_Add";
	}
	
	@RequestMapping("AddDtlDtlInit")
	public String AddDtlDtlInit(Model model, FTZ210302Form form) {
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(form.getSelected_msgId());
		ftzOffTxnDtl.setChkStatus("01");
		form.setFtzOffTxnDtl(ftzOffTxnDtl);
		form.setSelected_msgId("");
		return "ftzmis/FTZ210302_Input_Add_Dtl";
	}
	
	@RequestMapping("AddDtlDtlSubmit")
	public String AddDtlDtlSubmit(Model model, @Validated({FTZ210302FormAddDtlDtl.class}) FTZ210302Form form, BindingResult result) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210302_Input_Add_Dtl";
		}
		FtzOffTxnDtl ftzOffTxnDtl = form.getFtzOffTxnDtl();
		ftzOffTxnDtl.setAccOrgCode(ftzOffTxnDtl.getAccOrgCode().trim());
		ftzOffTxnDtl.setInstitutionCode(ftzOffTxnDtl.getInstitutionCode().trim());
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		ftzOffTxnDtl.setMakUserId(userInfo.getUserid());
		ftzOffTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftzOffTxnDtl.setChkStatus("01");
		if("1".equals(ftzOffTxnDtl.getTermCondition())){
			ftzOffTxnDtl.setTermLength((short) 0);
		}
		int i = ftz210302Service.insertFtzOffTxnDtl(ftzOffTxnDtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		}
		form.getFtzOffTxnDtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getSubmitDate()));
		form.getFtzOffTxnDtl().setExpirationDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getExpirationDate()));
		form.getFtzOffTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getTranDate()));
		return "ftzmis/FTZ210302_Input_Add_Dtl";
	}
	
	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("应付信用证初始化开始...");
		// 检查批量是否存在
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl ftzOffMsgCtl = ftz210302Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == ftzOffMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("应付信用证初始化结束...");
			return "ftzmis/FTZ210302_Input_Upd";
		}
		ftzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
		form.setFtzOffMsgCtl(ftzOffMsgCtl);
		// 将查询数据放入form
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzOffTxnDtl> page = ftz210302Service.queryFtzOffTxnDtlPage(pageable,query_FtzOffTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
			for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
				FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("应付信用证初始化结束...");
		return "ftzmis/FTZ210302_Input_Upd";
	}
	
	@RequestMapping("QryDtlDtl")
	public String queryDtlDtl(Model model, FTZ210302Form form) {
		logger.info("应付信用证明细查询开始...");
		// 组装查询信息
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210302Service.queryFtzOffTxnDtl(query_FtzOffTxnDtl);

		if (null == result_FtzOffTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("应付信用证明细查询结束...");
			return "ftzmis/FTZ210302_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzOffTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setTranDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		logger.info("应付信用证明细查询结束 ..");
		return "ftzmis/FTZ210302_Qry_Dtl_Dtl";
	}
	
	@RequestMapping("UpdDtlDtl")
	public String updateDtlDtl(Model model, FTZ210302Form form) {
		logger.info("应付信用证明细查询开始...");
		// 组装查询信息
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210302Service.queryFtzOffTxnDtl(query_FtzOffTxnDtl);

		if (null == result_FtzOffTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("应付信用证明细查询结束...");
			return "ftzmis/FTZ210302_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzOffTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setTranDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		logger.info("应付信用证明细查询结束 ..");
		return "ftzmis/FTZ210302_Qry_Dtl_Upd";
	}
	
	@RequestMapping("UpdDtlSubmit")
	public String UpdDtlSubmit(Model model, @Validated({FTZ210302FormAddDtl.class}) FTZ210302Form form, BindingResult result,@PageableDefaults Pageable pageable) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210302_Input_Upt";
		}
		FtzOffMsgCtl update_FtzOffMsgCtl = form.getFtzOffMsgCtl();
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzOffMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzOffMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateRemoveSprit(update_FtzOffMsgCtl.getWorkDate()));
		int i = ftz210302Service.updateFtzOffMsgCtl(update_FtzOffMsgCtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		}
		// 将查询数据放入form
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
		// 查询明细数据列表
		Page<FtzOffTxnDtl> page = ftz210302Service.queryFtzOffTxnDtlPage(pageable,query_FtzOffTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
			for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
				FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("应付信用证初始化结束...");
		return "ftzmis/FTZ210302_Input_Upd";
	}
	
	@RequestMapping("UpdDtlDtlSubmit")
	public String UpdDtlDtlSubmit(Model model, @Validated({FTZ210302FormAddDtlDtl.class}) FTZ210302Form form, BindingResult result) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210302_Qry_Dtl_Dtl";
		}
		FtzOffTxnDtl ftzOffTxnDtl = form.getFtzOffTxnDtl();
		ftzOffTxnDtl.setAccOrgCode(ftzOffTxnDtl.getAccOrgCode().trim());
		ftzOffTxnDtl.setInstitutionCode(ftzOffTxnDtl.getInstitutionCode().trim());
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getExpirationDate()));
		if("1".equals(ftzOffTxnDtl.getTermCondition())){
			ftzOffTxnDtl.setTermLength((short) 0);
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		ftzOffTxnDtl.setMakUserId(userInfo.getUserid());
		ftzOffTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		int i = ftz210302Service.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		}
		form.getFtzOffTxnDtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getSubmitDate()));
		form.getFtzOffTxnDtl().setExpirationDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getExpirationDate()));
		form.getFtzOffTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl()
						.getTranDate()));
		form.getFtzOffTxnDtl().setChkStatus("01");
		return "ftzmis/FTZ210302_Qry_Dtl_Upd";
	}
	
	@RequestMapping("InputDtlDel")
	public String delDtlDtl(Model model, FTZ210302Form form) {
		FtzOffTxnDtl del_FtzOffTxnDtl = new FtzOffTxnDtl();
		del_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		del_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		int i = ftz210302Service.deleteFtzOffTxnDtl(del_FtzOffTxnDtl);

		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_seqNo(null);
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_seqNo(null);
			return "forward:/FTZ210302/UptDtlInit";
		}

		return "forward:/FTZ210302/UptDtlInit";
	}
	
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210302Form form) {
		logger.info("应付信用证删除开始...");
		FtzOffMsgCtl del_FtzOffMsgCtl = new FtzOffMsgCtl();
		del_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());

		int i = ftz210302Service.deleteFtzOffMsgCtl(del_FtzOffMsgCtl);

		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_msgId("");
			logger.info("应付信用证批删除结束...");
			return "forward:/FTZ210302/AddQry";
		}

		logger.info("应付信用证删除结束...");
		return "forward:/FTZ210302/AddQry";
	}
	
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("单位存款批量查询开始...");
		// 组装查询信息
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210302Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == result_FtzOffMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			logger.error("查询无记录！");
			logger.info("应付信用证查询结束...");
			return "ftzmis/FTZ210302_Qry";
		} else {
			// 有数据则进行数据转换，查询明细数据
			result_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(result_FtzOffMsgCtl.getWorkDate()));
			form.setFtzOffMsgCtl(result_FtzOffMsgCtl);
			FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
			query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
			Page<FtzOffTxnDtl> page = ftz210302Service.queryFtzOffTxnDtlPage(
					pageable, query_FtzOffTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
				for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
					FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
				}
				model.addAttribute("page", page);
				form.setSelected_msgId("");
				form.setSelected_seqNo(null);
			}
			logger.info("单位存款批量查询结束...");
			return "ftzmis/FTZ210302_Qry_Dtl";
		}
	}
	
	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210302Form form) {
		FtzOffMsgCtl FtzOffMsgCtl = new FtzOffMsgCtl();
		FtzOffMsgCtl.setMsgId(form.getSelected_msgId());

		int i = ftz210302Service.updateFtzOffMsgCtlForSubmit(FtzOffMsgCtl);

		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210302/AddQry";
		}

		model.addAttribute(ResultMessages.success().add("i.ftzmis.210101.0001"));
		return "forward:/FTZ210302/AddQry";
	}
	
	@RequestMapping("Qry")
	public String query(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("应付信用证查询开始...");

		// trans form to queryObject
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(form.getQuery_workDate_start());
		query_FtzOffMsgCtl.setRsv2(form.getQuery_workDate_end());
		query_FtzOffMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210302);
		
		//Query
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210302Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("应付信用证查询结束...");
			return "ftzmis/FTZ210302_Qry";
		}
		
		return "ftzmis/FTZ210302_Qry";
	}
	
	@RequestMapping("AuthQry")
	public String AuthQuery(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("应付信用证查询开始...");

		// trans form to queryObject
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(form.getQuery_workDate_start());
		query_FtzOffMsgCtl.setRsv2(form.getQuery_workDate_end());
		query_FtzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		query_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210302);
		
		//Query
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210302Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("应付信用证查询结束...");
			return "ftzmis/FTZ210302_Auth_Qry";
		}
		
		return "ftzmis/FTZ210302_Auth_Qry";
	}
	
	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210302Form form,
			@PageableDefaults Pageable pageable) {
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210302Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == result_FtzOffMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "ftzmis/FTZ210302_Auth_Qry_Dtl";
		} else {
			result_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(result_FtzOffMsgCtl.getWorkDate()));
			form.setFtzOffMsgCtl(result_FtzOffMsgCtl);
			FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
			query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
			// 查询待审核数据
			if ("1".equals(form.getUnAuthFlag())) {
				query_FtzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
				Page<FtzOffTxnDtl> page = ftz210302Service.queryFtzOffTxnDtlPage(
						pageable, query_FtzOffTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
					for (FtzOffTxnDtl FtzOffTxnDtl :FtzOffTxnDtls) {
						FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}
			// 查询全部数据
			else{
				Page<FtzOffTxnDtl> page = ftz210302Service.queryFtzOffTxnDtlPage(pageable, query_FtzOffTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
					for (FtzOffTxnDtl FtzOffTxnDtl :FtzOffTxnDtls) {
						FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}
			

			return "ftzmis/FTZ210302_Auth_Qry_Dtl";
		}
	}

	@RequestMapping("AuthDtlSubmit")
	public String AuthDtlSubmit(Model model, FTZ210302Form form) {
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setChkStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED});
		List<FtzOffTxnDtl> ftzOffTxnDtls = ftz210302Service.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if(ftzOffTxnDtls.size()>0){
			model.addAttribute(ResultMessages.info().add("e.ftzmis.audit.not.detail"));
			return "forward:/FTZ210302/QryAuthDtl";
		}
		query_FtzOffTxnDtl.setChkStatuss(null);
		query_FtzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		List<FtzOffTxnDtl> ftzOffTxnDtls_fail = ftz210302Service.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if(ftzOffTxnDtls_fail.size()>0){
			ftzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}else{
			ftzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		ftzOffMsgCtl.setChkUserId(userInfo.getUserid());
		ftzOffMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
		ftzOffMsgCtl.setMsgId(form.getSelected_msgId());
		int i = ftz210302Service.updateFtzOffMsgCtl(ftzOffMsgCtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210301.0005"));
		}
		return "forward:/FTZ210302/QryAuthDtl";
	}

	@RequestMapping("QryAuthDtlDtl")
	public String queryAuthDtlDtl(Model model, FTZ210302Form form) {
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210302Service
				.queryFtzOffTxnDtl(query_FtzOffTxnDtl);

		if (null == result_FtzOffTxnDtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210302_Auth_Qry_Dtl";
		}
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setTranDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		result_FtzOffTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzOffTxnDtl
						.getMakDatetime()));
		result_FtzOffTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzOffTxnDtl
						.getChkDatetime()));
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		return "ftzmis/FTZ210302_Auth_Qry_Dtl_Dtl";
	}

	@RequestMapping("AuthDtlDtlSubmit")
	public String AuthDtlDtlSubmit(Model model, FTZ210302Form form) {
		FtzOffTxnDtl FtzOffTxnDtl = new FtzOffTxnDtl();
		FtzOffTxnDtl.setMsgId(form.getFtzOffTxnDtl().getMsgId());
		FtzOffTxnDtl.setSeqNo(form.getFtzOffTxnDtl().getSeqNo());

		UserInf userInfo = ContextConst.getCurrentUser();
		FtzOffTxnDtl.setChkUserId(userInfo.getUserid());
		FtzOffTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
		FtzOffTxnDtl.setChkAddWord(form.getFtzOffTxnDtl().getChkAddWord());

		if ("1".equals(form.getAuthStat())) {
			FtzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		} else if ("0".equals(form.getAuthStat())) {
			FtzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}
		int i = ftz210302Service.updateFtzOffTxnDtlSelective(FtzOffTxnDtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210301.0005"));
			form.setAuthFinishFlag("1");
		}
		
		

		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210302Service
				.queryFtzOffTxnDtl(FtzOffTxnDtl);

		if (null == result_FtzOffTxnDtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210302_Auth_Qry_Dtl";
		}
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setTranDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		result_FtzOffTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzOffTxnDtl
						.getMakDatetime()));
		result_FtzOffTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzOffTxnDtl
						.getChkDatetime()));
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		return "ftzmis/FTZ210302_Auth_Qry_Dtl_Dtl";
	}
}
