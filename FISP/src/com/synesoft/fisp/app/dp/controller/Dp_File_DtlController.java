package com.synesoft.fisp.app.dp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.DpFileDtlForm;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.service.dp.DpFileService;

/**
 * 文件定义维护-详细
 * @date 2013-11-17
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_File_Dtl")
public class Dp_File_DtlController {
	private static final Logger log = LoggerFactory.getLogger(Dp_File_DtlController.class);

	@Autowired
	private DpFileService dpFileService;
	
	@ModelAttribute
	public DpFileDtlForm setForm() {
		return new DpFileDtlForm();
	}
	
	@RequestMapping("Init")
	public String init(DpFileDtlForm form, BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("init...");
		
		try {
			List<Object> list = dpFileService.transQueryDetail(form.getDpFile().getFileId());
			DpFile dpFile = (DpFile) list.get(0);
			dpFile.setCreateTime(DateUtil.getFormatdateAddSplit(dpFile.getCreateTime()));
			dpFile.setUpdateTime(DateUtil.getFormatdateAddSplit(dpFile.getUpdateTime()));
			
			form.setDpFile(dpFile);
			form.setDpFileDtlList((List<DpFileDtl>) list.get(1));
		} catch (BusinessException e) {
			log.error("[e.dp.file] Failed to search the detail information");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_File_Qry";
		}
		
		log.info("Searching a piece of record success");
		return "dp/Dp_File_Dtl";
	}
	
}
