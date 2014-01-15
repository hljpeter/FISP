package com.synesoft.fisp.app.dp.controller;

import java.util.List;

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

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.dp.model.DpFileAddForm;
import com.synesoft.fisp.app.dp.model.DpFileAddForm.DpFileAddFormAdd;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.service.dp.DpFileService;

/**
 * 文件定义维护-新增
 * @date 2013-11-17
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_File_Add")
public class Dp_File_AddController {
	private static final Logger log = LoggerFactory.getLogger(Dp_File_AddController.class);

	@Autowired
	private DpFileService dpFileService;
	
	@ModelAttribute
	public DpFileAddForm setForm() {
		return new DpFileAddForm();
	}
	
	@RequestMapping("Init")
	public String init() {
		log.info("init...");
		return "dp/Dp_File_Add";
	}

	@RequestMapping("Add")
	public String add(@Validated({DpFileAddFormAdd.class}) DpFileAddForm form, BindingResult result, 
			Model model) {
		log.info("start add ...");

		if (result.hasErrors()) {
			return "dp/Dp_File_Add";
		}
		
		// getting parameter from DpFileAddForm
		DpFile dpFile = form.getDpFile();
		List<DpFileDtl> list = form.getDpFileDtlList();
		if (!StringUtil.isNotTrimEmpty(list.get(0).getCutFlag())) {
			String[] flgs = form.getFlagJson().split(",");
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setCutFlag(flgs[i]);
			}
		}
		
		try {
			dpFileService.transAdd(dpFile, list);
		} catch (BusinessException e) {
			log.error("[e.dp.file] Failed to add record");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_File_Add";
		}
		
		log.info("[i.dp.file.0047] adding record success");
		model.addAttribute("successmsg",ResultMessages.success().add(ResultMessage
				.fromCode("i.dp.file.0047", dpFile.getFileName(), list == null? 0:list.size())));
		return "dp/Dp_File_Add";
	}
	
}
