package com.synesoft.fisp.app.common.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.model.OrgSearchForm;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

@Controller
@RequestMapping("OrgSearch")
public class OrgSearchController {

	private static final Logger logger = LoggerFactory
			.getLogger(OrgSearchController.class);

	@ModelAttribute
	public OrgSearchForm setUpForm() {
		return new OrgSearchForm();
	}

	@RequestMapping("Init")
	public String init(Model model ,@RequestParam("flag") String flag) {
		logger.info("init...");
		if(null != flag && "1".equals(flag)){
			model.addAttribute("flag", flag);
		}
		return "common/OrgSearch";
	}

	@RequestMapping("Qry")
	public String query(Model model, OrgSearchForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");
		
		OrgInf orgInf = new OrgInf();
		orgInf.setOrgid(form.getQuery_orgid());
		orgInf.setOrgname(form.getQuery_orgname());
		UserInf userInfo = ContextConst.getCurrentUser();
		orgInf.setRsv1(userInfo.getUserid());
		Page<OrgInf> page = dp_Mpp_Service.queryOrgInfPage(pageable, orgInf);
		
		model.addAttribute("page",page);
		
		return "common/OrgSearch";
	}
	
	@RequestMapping("QryAll")
	public String queryAll(Model model, OrgSearchForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");
		
		OrgInf orgInf = new OrgInf();
		orgInf.setOrgid(form.getQuery_orgid());
		orgInf.setOrgname(form.getQuery_orgname());
		UserInf userInfo = ContextConst.getCurrentUser();
		orgInf.setRsv1(userInfo.getUserid());
		Page<OrgInf> page = orgInfMaintenanceService.transQueryOrgInfList(pageable, orgInf);
		model.addAttribute("flag", "1");
		
		model.addAttribute("page",page);
		
		return "common/OrgSearch";
	}
	
	@RequestMapping("OrgCheck")
	public @ResponseBody String orgCheck(@RequestParam("orgId") String orgId, Model model) {
		logger.info("OrgCheck...");
		String query_OrgId = StringUtil.trim(orgId);
		if (orgId != null && !"".equals(orgId)) {
			JSONObject jo = new JSONObject();
			OrgInf query_orgInf = new OrgInf();
			query_orgInf.setOrgid(query_OrgId);
			OrgInf result_OrgInf = orgInfMaintenanceService
					.transQueryOrgInf(query_orgInf);
			if (null == result_OrgInf) {
				jo.put("orgExist", false);
			} else {
				jo.put("orgExist", true);
				jo.put("orgid",StringUtil.trim(result_OrgInf.getOrgid()));
				jo.put("orgname", result_OrgInf.getOrgname());
			}
			return jo.toString();
			// response.getWriter().write(jo.toString());
		}
		return null;
	}
	
	@RequestMapping("check")
	public String select(@ModelAttribute OrgSearchForm form, Model model, HttpServletResponse httpServletResponse) {
		logger.info("start select ...");
		String orgid = StringUtil.trim(form.getQuery_orgid());
		
		OrgInf orginf = new OrgInf();
		orginf.setOrgid(orgid);

		orginf = orgInfMaintenanceService.transQueryOrgInf(orginf);

		JSONObject jo=new JSONObject();
		
		if (orginf!=null){
			jo.put("existflag", "true");
			jo.put("orgid", orginf.getOrgid());
			jo.put("orgname", orginf.getOrgname());
		}else{
			jo.put("existflag", "false");
			jo.put("orgid","");
			jo.put("orgname", "");
		}
		try {
			httpServletResponse.getWriter().write(jo.toString());
		} catch (IOException e) {
			logger.info("select error:" + e.getMessage());
		} 
			return null;
		}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	private OrgInfMaintenanceService orgInfMaintenanceService;
}
