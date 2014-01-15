package com.synesoft.sysrunner.app.BatchManage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm;
import com.synesoft.sysrunner.common.constant.DateUtil;
import com.synesoft.sysrunner.domain.model.BatDimInfo;
import com.synesoft.sysrunner.domain.model.BatTaskDim;
import com.synesoft.sysrunner.domain.service.BatchManageService;
import com.synesoft.sysrunner.domain.vo.BatTaskDimVO;

@Controller
@RequestMapping(value = "BMG_Task_DIM_Add")
public class BMG_Task_DIM_AddController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public BatchManageForm setUpForm() {
		return new BatchManageForm();
	}

	@RequestMapping("Init")
	public String init(@RequestParam("taskId") String taskId,
			@RequestParam("dimTypeId") String dimTypeId,
			BatchManageForm batchManageForm, Model model) {

		BatDimInfo query_BatDimInfo = new BatDimInfo();
		query_BatDimInfo.setDimTypeId(dimTypeId);
		List<BatDimInfo> batDimInfos = batchManageServ
				.queryBatDimInfos(query_BatDimInfo);
		List<BatTaskDimVO> batTaskDimVOs = new ArrayList<BatTaskDimVO>();

		for (int i = 0; i < batDimInfos.size(); i++) {
			BatDimInfo batDimInfo = batDimInfos.get(i);
			BatTaskDim query_BatTaskDim = new BatTaskDim();
			query_BatTaskDim.setDimTypeId(batDimInfo.getDimTypeId().trim());
			query_BatTaskDim.setTaskId(taskId.trim());
			query_BatTaskDim.setDimensionId(batDimInfo.getDimensionId().trim());
			BatTaskDim result_BatTaskDim = batchManageServ
					.queryBatTaskDim(query_BatTaskDim);
			if (null == result_BatTaskDim) {
				BatTaskDimVO batTaskDimVO = new BatTaskDimVO();
				batTaskDimVO.setDimensionId(batDimInfo.getDimensionId().trim());
				batTaskDimVO.setTaskId(taskId.trim());
				batTaskDimVO.setDimTypeId(batDimInfo.getDimTypeId().trim());
				batTaskDimVO.setDimensionName(batDimInfo.getDimensionName());
				batTaskDimVOs.add(batTaskDimVO);
			} else {
				BatTaskDimVO batTaskDimVO = new BatTaskDimVO();
				BeanUtils.copyProperties(result_BatTaskDim, batTaskDimVO);
				batTaskDimVO
						.setTaskStartTime(DateUtil
								.getFormatTimeAddColon(batTaskDimVO
										.getTaskStartTime()));
				batTaskDimVO.setTaskEndTime(DateUtil
						.getFormatTimeAddColon(batTaskDimVO.getTaskEndTime()));
				batTaskDimVO.setDimensionName(batDimInfo.getDimensionName());
				batTaskDimVO.setDimensionId(batTaskDimVO.getDimensionId()
						.trim());
				batTaskDimVO.setTaskId(batTaskDimVO.getTaskId().trim());
				batTaskDimVO.setDimTypeId(batTaskDimVO.getDimTypeId().trim());
				batTaskDimVOs.add(batTaskDimVO);
			}
		}

		batchManageForm.setBatTaskDimVOs(batTaskDimVOs);
		return "sysrunner/BMG_Task_DIM_Add";
	}

	@RequestMapping("SubmitDim")
	public String SubmitDim(BatchManageForm batchManageForm,
			BindingResult result, Model model) {
		List<BatTaskDimVO> batTaskDimVOs = batchManageForm.getBatTaskDimVOs();
		List<String> errorDims = new ArrayList<String>();

		List<BatTaskDim> insert_BatTaskDims = new ArrayList<BatTaskDim>();

		int select_count = 0;
		for (int i = 0; i < batTaskDimVOs.size(); i++) {
			BatTaskDimVO batTaskDimVO = batTaskDimVOs.get(i);
			if ("1".equals(batTaskDimVO.getSelectedFalg())) {
				// 非空验证
				int count = 0;
				if (null == batTaskDimVO.getDimensionId()
						|| "".equals(batTaskDimVO.getDimensionId().trim())) {
					model.addAttribute(ResultMessages.error().add(
							"e.sysrunner.0024"));
					count++;
				}
				if (null == batTaskDimVO.getDimTypeId()
						|| "".equals(batTaskDimVO.getDimTypeId().trim())) {
					model.addAttribute(ResultMessages.error().add(
							"e.sysrunner.0025"));
					count++;
				}
				if (null == batTaskDimVO.getTaskId()
						|| "".equals(batTaskDimVO.getTaskId().trim())) {
					model.addAttribute(ResultMessages.error().add("e.bm.0003"));
					count++;
				}
				if (null == batTaskDimVO.getTaskStartType()
						|| "".equals(batTaskDimVO.getTaskStartType())) {
					model.addAttribute(ResultMessages.error().add(
							"e.sysrunner.0016"));
					count++;
				}
				if (null == batTaskDimVO.getTaskStartTime()
						|| "".equals(batTaskDimVO.getTaskStartTime())) {
					model.addAttribute(ResultMessages.error().add(
							"e.sysrunner.0014"));
					count++;
				}
				if (null == batTaskDimVO.getTaskEnable()
						|| "".equals(batTaskDimVO.getTaskEnable())) {
					model.addAttribute(ResultMessages.error().add(
							"e.sysrunner.0017"));
					count++;
				}
				if (count > 0) {
					errorDims.add(batTaskDimVO.getDimensionId());
				}

				BatTaskDim batTaskDim = new BatTaskDim();
				BeanUtils.copyProperties(batTaskDimVO, batTaskDim);
				batTaskDim
						.setTaskStartTime(DateUtil
								.getFormatTimeRemoveColon(batTaskDim
										.getTaskStartTime()));
				batTaskDim.setTaskEndTime(DateUtil
						.getFormatTimeRemoveColon(batTaskDim.getTaskEndTime()));
				if (null == batTaskDim.getTaskStartDay()) {
					batTaskDim.setTaskStartDay(Short.parseShort("0"));
				}

				if (null == batTaskDim.getTaskStartTn()) {
					batTaskDim.setTaskStartTn(Short.parseShort("0"));
				}

				if (null == batTaskDim.getTaskEndTime()
						|| "".equals(batTaskDim.getTaskEndTime())) {
					batTaskDim.setTaskEndTime(" ");
				}
				if (null == batTaskDim.getTaskPollingInterval()
						|| "".equals(batTaskDim.getTaskEndTime().toString())) {
					batTaskDim.setTaskPollingInterval(Integer.parseInt("0"));
				}

				insert_BatTaskDims.add(batTaskDim);

			} else {
				select_count++;
			}
		}
		if (select_count == batTaskDimVOs.size()) {
			model.addAttribute(ResultMessages.info().add("i.sysrunner.0014"));
			return "forward:/BMG_Task_DIM_Add/Init?taskId="
					+ batchManageForm.getBatTaskDimVOs().get(0).getTaskId()
					+ "&dimTypeId="
					+ batchManageForm.getBatTaskDimVOs().get(0).getDimTypeId();
		}
		if (errorDims.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < errorDims.size(); i++) {
				if (i == errorDims.size() - 1) {
					sb.append(errorDims.get(i));
				} else {
					sb.append(errorDims.get(i) + "and");
				}

			}
			model.addAttribute(ResultMessages.success().add("e.sysrunner.0028",
					sb.toString()));
			return "sysrunner/BMG_Task_DIM_Add";
		}

		int i = batchManageServ.addBatTaskDims(insert_BatTaskDims);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0029"));
			return "forward:/BMG_Task_DIM_Add/Init?taskId="
					+ batchManageForm.getBatTaskDimVOs().get(0).getTaskId()
					+ "&dimTypeId="
					+ batchManageForm.getBatTaskDimVOs().get(0).getDimTypeId();
		}
		model.addAttribute(ResultMessages.success().add("i.sysrunner.0011"));
		return "forward:/BMG_Task_DIM_Add/Init?taskId="
				+ batchManageForm.getBatTaskDimVOs().get(0).getTaskId()
				+ "&dimTypeId="
				+ batchManageForm.getBatTaskDimVOs().get(0).getDimTypeId();

	}

	@Resource
	private BatchManageService batchManageServ;

}
