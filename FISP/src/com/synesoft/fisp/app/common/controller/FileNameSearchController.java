package com.synesoft.fisp.app.common.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.fisp.app.common.model.FileNameSearchForm;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.service.dp.DpFileService;

/**
 * 文件名的查询
 * @date 2013-11-14
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "search")
public class FileNameSearchController {

	private static final LogUtil log = new LogUtil(FileNameSearchController.class);

	@Autowired
	private DpFileService dpFileService;

	@ModelAttribute
	public FileNameSearchForm setForm() {
		return new FileNameSearchForm();
	}
	
	@RequestMapping("filename/init")
	public String init(FileNameSearchForm form, @RequestParam("ioFlag") String ioFlag) {
		log.info("init...");
		
		DpFile dpFile = new DpFile();
		dpFile.setIoFlag(ioFlag == null? "":ioFlag.trim());
		
		form.setDpFile(dpFile);
		
		return "common/filenamesearch";
	}
	
	@RequestMapping("filename/search")
	public String search(FileNameSearchForm form, @PageableDefaults Pageable pageable, 
			Model model) {
		log.info("search filename start...");
		
		// getting parameters from DpImpQryForm 
		DpFile dpFile = form.getDpFile();
		
		try {
			Page<DpFile> page = dpFileService.transQueryDpFilePage(pageable, dpFile);
		
			model.addAttribute("page", page);
		} catch (BusinessException e){
			log.info("[w.dp.0001] No data");
			model.addAttribute("infomsg", e.getResultMessages());
			return "common/filenamesearch";
		}

		log.info("searching information success, dispaly result on the page");
		return "common/filenamesearch";
	}

}
