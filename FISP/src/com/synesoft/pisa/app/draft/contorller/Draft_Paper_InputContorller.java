package com.synesoft.pisa.app.draft.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.pisa.app.draft.model.Draft_Paper_Form;
import com.synesoft.pisa.domain.model.BizDraftAcptList;
import com.synesoft.pisa.domain.service.draft.BizDraftAcptListService;

@Controller
@RequestMapping(value = "Draft_Paper_Qry")
public class Draft_Paper_InputContorller {
	
		private static final Logger logger = LoggerFactory
				.getLogger(Draft_Paper_InputContorller.class);

		@ModelAttribute
		public Draft_Paper_Form setUpForm() {
			return new Draft_Paper_Form();
		}
		
		@RequestMapping("Qry")
		public String search(Draft_Paper_Form listForm,
				@PageableDefaults Pageable pageable, Model model) {
			logger.info("start search ...");
			BizDraftAcptList bizDraftAcptList=listForm.getBizDraftAcptList();
			if(bizDraftAcptList==null){
				bizDraftAcptList=new BizDraftAcptList();
			}
			Page<BizDraftAcptList> page= bizDraftAcptListService.transQueryBizDraftAcptListList(pageable, bizDraftAcptList);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.dp.0001")));
			}
			return "pisa/Draft_Paper_Query";
		}
		
		@RequestMapping("Add")
		public String add(Draft_Paper_Form form,
				BindingResult result, Model model) {
			logger.info("Add...");
			return "pisa/Draft_Paper_Add";
		}
		@RequestMapping("Delete")
		public String delete(Draft_Paper_Form form,
				BindingResult result, Model model) {
			logger.info("Delete...");
			return "pisa/Draft_Paper_Add";
		}
		@RequestMapping("Input")
		public String input(Draft_Paper_Form form,
				BindingResult result, Model model) {
			logger.info("Input...");
			BizDraftAcptList bizDraftAcptList=form.getBizDraftAcptList();
			bizDraftAcptList=bizDraftAcptListService.transQueryBizDraftAcptList(bizDraftAcptList);
			form.setBizDraftAcptList(bizDraftAcptList);
			return "pisa/Draft_Paper_Input";
		}

		@RequestMapping("Detail")
		public String detailSearch(Draft_Paper_Form form,
				BindingResult result, Model model) {
			logger.info("detailSearch...");
			BizDraftAcptList bizDraftAcptList=form.getBizDraftAcptList();
			bizDraftAcptList=bizDraftAcptListService.transQueryBizDraftAcptList(bizDraftAcptList);
			form.setBizDraftAcptList(bizDraftAcptList);
			return "pisa/Draft_Paper_Dtl";
		}
		@Autowired
		private BizDraftAcptListService bizDraftAcptListService;

}
