package com.synesoft.fisp.app.dp.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.dp.model.DpFileQryForm;
import com.synesoft.fisp.app.dp.model.DpFileQryForm.DpFileQryFormDel;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.service.dp.DpFileService;

/**
 * 文件定义维护-查询
 * @date 2013-11-17
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_File_Qry")
public class Dp_File_QryController {
	private static final Logger log = LoggerFactory.getLogger(Dp_File_QryController.class);

	@Autowired
	private DpFileService dpFileService;
	
	@ModelAttribute
	public DpFileQryForm setForm() {
		return new DpFileQryForm();
	}
	
	@RequestMapping("/Init")
	public String init() {
		log.info("init...");
		return "dp/Dp_File_Qry";
	}

	@RequestMapping("/Qry")
	public String search(DpFileQryForm form, @PageableDefaults Pageable pageable, Model model) {
		log.info("start search ...");
		
		DpFile dpFile = form.getDpFile();
		
		try {
			Page<DpFile> page= dpFileService.transQueryDpFilePage(pageable, dpFile);
			model.addAttribute("page", page);
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data");
			model.addAttribute("infomsg", e.getResultMessages());
			return "dp/Dp_File_Qry";
		}

		return "dp/Dp_File_Qry";
	}

	@RequestMapping("/Del")
	public String del(@Validated({DpFileQryFormDel.class}) DpFileQryForm form, BindingResult result, Model model) {
		log.info("start del ...");
		
		if (result.hasErrors()) {
			return "dp/Dp_File_Qry";
		}
		
		try {
			dpFileService.transDel(form.getDpFile());
		} catch (BusinessException e) {
			log.error("[e.dp.file] Failed to deleting record");
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/DP_File_Qry/Qry";
		}
		
		log.info("[i.dp.file.0043] Deleting a piece of record success");
		model.addAttribute("successmsg",ResultMessages.success().add(ResultMessage
				.fromCode("i.dp.file.0043", 1)));
		return "forward:/DP_File_Qry/Qry";
	}
	
}
