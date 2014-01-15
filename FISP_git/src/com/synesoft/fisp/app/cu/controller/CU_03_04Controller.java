package com.synesoft.fisp.app.cu.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.cu.model.TipsBaCInfForm;
import com.synesoft.fisp.domain.model.TipsBaCInf;
import com.synesoft.fisp.domain.service.cu.TipsBaCInfMaintenanceService;

/**
 * 银行代码
 * @author Han
 */
@Controller
@RequestMapping(value = "cu03")
public class CU_03_04Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(CU_03_04Controller.class);

	@ModelAttribute
	public TipsBaCInfForm setUpForm() {
		return new TipsBaCInfForm();
	}

	@RequestMapping("04/init")
	public String init() {
		logger.info("init...");
		return "cu/CU_03_04";
	}

	@RequestMapping("04/search")
	public String search(TipsBaCInfForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		String reckbankno = StringUtil.trim(listForm.getReckbankno());
		String genbankname = StringUtil.trim(listForm.getGenbankname());
		TipsBaCInf tipsBaCInf = new TipsBaCInf();
		tipsBaCInf.setReckbankno(reckbankno);
		tipsBaCInf.setGenbankname(genbankname);

		Page<TipsBaCInf> page = tipsBaCInfMaintenanceService
				.transQueryTipsBaCInfList(pageable, tipsBaCInf);
		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "cu/CU_03_04";
	}

	@RequestMapping("04/autoMatch")
	public String select(@ModelAttribute TipsBaCInfForm listForm, Model model,
			HttpServletResponse httpServletResponse) {
		logger.info("start select ...");
		String reckbankno = StringUtil.trim(listForm.getReckbankno());

		TipsBaCInf tipsBaCInf = new TipsBaCInf();
		tipsBaCInf.setReckbankno(reckbankno);

		TipsBaCInf tbi = tipsBaCInfMaintenanceService
				.transQueryTipsBaCInf(tipsBaCInf);

		JSONObject jo = new JSONObject();

		if (tbi != null) {
			jo.put("reckbankno", tbi.getReckbankno());
			jo.put("genbankname", tbi.getGenbankname());
			jo.put("ofnodecode", tbi.getOfnodecode());
		} else {
			jo.put("reckbankno", "");
			jo.put("genbankname", "");
			jo.put("ofnodecode", "");
		}

		try {
			httpServletResponse.getWriter().write(jo.toString());
		} catch (IOException e) {
			logger.info("select error:" + e.getMessage());
		} finally {
			return null;
		}
	}

	@Autowired
	private TipsBaCInfMaintenanceService tipsBaCInfMaintenanceService;

}
