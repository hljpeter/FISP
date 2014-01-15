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

import com.synesoft.fisp.app.dp.model.DpFileMdfForm;
import com.synesoft.fisp.app.dp.model.DpFileMdfForm.DpFileMdfFormMdf;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.service.dp.DpFileService;

/**
 * 文件定义维护-修改
 * @date 2013-11-17
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_File_Mdf")
public class Dp_File_MdfController {
	private static final Logger log = LoggerFactory.getLogger(Dp_File_MdfController.class);

	@Autowired
	private DpFileService dpFileService;
	
	@ModelAttribute
	public DpFileMdfForm setForm() {
		return new DpFileMdfForm();
	}
	
	@RequestMapping("Init")
	public String init(DpFileMdfForm form, BindingResult result, Model model) {
		log.info("init...");
		
		try {
			List<Object> list = dpFileService.transQueryDetailForUpt(form.getDpFile().getFileId());
			DpFile dpFile = (DpFile) list.get(0);
			form.setDpFile(dpFile);
			form.setDpFileDtlList((List<DpFileDtl>) list.get(1));
			Boolean flag = (Boolean) list.get(2);
			if (!flag) {
				model.addAttribute("infomsg", ResultMessages.success().add(ResultMessage
						.fromCode("e.dp.file.0085", dpFile.getFileName())));
			}
			
		} catch (BusinessException e) {
			log.error("[e.dp.file] Failed to add record");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_File_Mdf";
		}
		
		return "dp/Dp_File_Mdf";
	}

	@RequestMapping("Mdf")
	public String mdf(@Validated({DpFileMdfFormMdf.class}) DpFileMdfForm form, BindingResult result, 
			Model model) {
		log.info("start del ...");
		
		if (result.hasErrors()) {
			return "dp/Dp_File_Mdf";
		}
		
		DpFile dpFile = form.getDpFile();
		List<DpFileDtl> list = form.getDpFileDtlList();
		
		try {
			dpFileService.transUpt(dpFile, list);
		} catch (BusinessException e) {
			log.error("[] Failed to deleting record");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_File_Mdf";
		}
		
		log.info("[] Deleting a piece of record success");
		model.addAttribute("successmsg",ResultMessages.success().add(ResultMessage
				.fromCode("i.dp.file.0058", form.getDpFile().getFileName())));
		return "dp/Dp_File_Mdf";
	}
	
}
