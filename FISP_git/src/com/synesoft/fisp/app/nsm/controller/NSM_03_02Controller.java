package com.synesoft.fisp.app.nsm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.context.CommonResourceHelper;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.UserInfTmpForm;
import com.synesoft.fisp.app.sm.model.UserInfTmpForm.SM_03_02_Add;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.UserInfTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;
import com.synesoft.fisp.domain.service.sm.RoleInfService;
import com.synesoft.fisp.domain.service.sm.UserInfService;

/**
 * 操作员信息维护 - 新增(SM_03_02)
 * @author yyw
 * @date 2014-01-02
 */
@Controller
@RequestMapping(value = "nsm03")
public class NSM_03_02Controller {
	private static final Logger logger = LoggerFactory.getLogger(NSM_03_02Controller.class);
	
	@ModelAttribute
	public UserInfTmpForm setInfoUpForm() {
		return new UserInfTmpForm();
	}
	
	/**
	 * 新增初始化
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("02/init")
	public String init(UserInfTmpForm form,
			BindingResult result, Model model, HttpServletRequest request) {
		logger.info("init...");
		
		UserInfTmp userInfTmp = new UserInfTmp();
		userInfTmp.setCreateorg(ContextConst.getCurrentUser().getLoginorg());
		userInfTmp.setUserstatus("01");
		form.setUserInfTmp(userInfTmp);
		form.setUserid("");
		form.setLoginorg("");
		form.setUsername("");
		queryInfo(form, result, model, request);
		
		return "sm1/SM_03_02";
	}

	/**
	 * 新增提交
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("02/add")
	public String add(UserInfTmpForm form,
			BindingResult result, Model model,HttpServletRequest request) {
		logger.info("start add ...");
		
		if (result.hasErrors()) {
			UserInfTmp userInfTmp = new UserInfTmp();
			userInfTmp.setCreateorg(ContextConst.getCurrentUser().getLoginorg());
			userInfTmp.setUserstatus("01");
			form.setUserInfTmp(userInfTmp);
			form.setUserid("");
			form.setLoginorg("");
			form.setUsername("");
			queryInfo(form, result, model, request);
			return "sm1/SM_03_02";
		}
		
		queryInfoTmp(form, result, model, request);
		try {
			
			nuserInfService.transAddByMode(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_03_02";
		}
		model.addAttribute("successmsg", ResultMessages
				.success().add(ResultMessage.fromCode("i.sm.0001")));
		
		request.setAttribute("RoleMode", form.getRoleMode());
		
		OrgInf orgInf = ContextConst.getOrgInfByUser();

		// 获取当前用户所登录机构拥有的所有角色
		UserRoleInf userRoleInf = new UserRoleInf();
		userRoleInf.setUserid(ContextConst.getCurrentUser().getUserid().trim());
		userRoleInf.setOrgid(orgInf.getOrgid().trim());
		List<UserRoleInf> list_userrole = userRoleInfRepository
		.queryUserRoleIdList(userRoleInf);
		
		UserRoleInf roleInfo=list_userrole.get(0);
		request.setAttribute("RoleID", roleInfo.getRoleid().trim());
		request.setAttribute("OrgID", orgInf.getOrgid().trim());
		request.setAttribute("OrgName", orgInf.getOrgname().trim());
		List<SysParam> list= CommonResourceHelper.getSysParam(roleInfo.getRoleid());
		request.setAttribute("RoleRank", list);
		request.setAttribute("OwnerRole",  form.getRoleOrgArray());
		return "sm1/SM_03_02";
	}
	
	/**
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfo(UserInfTmpForm form, BindingResult result, Model model, HttpServletRequest request){
		
		OrgInf orgInf = ContextConst.getOrgInfByUser();

		// 获取当前用户所登录机构拥有的所有角色
		UserRoleInf userRoleInf = new UserRoleInf();
		userRoleInf.setUserid(ContextConst.getCurrentUser().getUserid().trim());
		userRoleInf.setOrgid(orgInf.getOrgid().trim());
		List<UserRoleInf> list_userrole = userRoleInfRepository
		.queryUserRoleIdList(userRoleInf);
		
		UserRoleInf roleInfo=list_userrole.get(0);
		request.setAttribute("RoleID", roleInfo.getRoleid().trim());
		request.setAttribute("OrgID", orgInf.getOrgid().trim());
		request.setAttribute("OrgName", orgInf.getOrgname().trim());
		List<SysParam> list= CommonResourceHelper.getSysParam(roleInfo.getRoleid());
		request.setAttribute("RoleRank", list);
		
		String roleMode = form.getRoleMode();

		// 1) Ready for orgnazition information
		// init - orgnazition information: availabledOrgList + selectedOrgList = all orgnazition
		List<OrgInf> selectedOrgList = null;
		List<OrgInf> availabledOrgList = null;
		if (form.getUserOrgInfArr() != null && !form.getUserOrgInfArr().isEmpty()) {
			// get availabled orgnazition and selected orgnazition		
			List<List<OrgInf>> orgList = orgInfMaintenanceService.transQueryAvailabledAndSelectedList(form.getUserOrgInfArr());
			selectedOrgList = orgList.get(0);
			availabledOrgList = orgList.get(1);
		} else {
			availabledOrgList = orgInfMaintenanceService.transQueryOrgInfList();
		}
		model.addAttribute("AvailabledOrg", availabledOrgList);				// all availabed org object
		//model.addAttribute("SelectedOrg", selectedOrgList);					// selected org array
		

		// 2) Ready for role information
		// 2.1) 机构与角色无关 - RoleMode == 1(ROLE_MODE_UNBIND_ORG)
		if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// get all role - all field of roleInf is null 
			RoleInf roleInf = new RoleInf();
			List<RoleInf> allRoleList = roleInfService.queryRolesByOrg(roleInf);

			request.setAttribute("AvailabledRole", allRoleList);				// all availabed role object
			request.setAttribute("SelectedRole", form.getRoleArray());			// selected role array

		// 2.2) 机构与角色有关 - RoleMode == 0(ROLE_MODE_BIND_ORG)
		} else if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// 获取所有机构对应的角色
			List<OrgInf> allOrgList = orgInfMaintenanceService.transQueryOrgInfList();						// get all orgnazition
			HashMap<String, ArrayList<RoleInf>> allOrgRoleMap = new HashMap<String, ArrayList<RoleInf>>();	// org-role Map
			if (allOrgList.size() > 0 ) {
				//model.addAttribute("AvailableOrgList", allOrgList);
				// 获取所有机构对应的角色
				for (int i = 0; i < allOrgList.size(); i++) {
					String orgId = StringUtil.trim(allOrgList.get(i).getOrgid());
					ArrayList<RoleInf> roleList = (ArrayList<RoleInf>) roleInfService.queryRolesByOrgRec(orgId);
					allOrgRoleMap.put(orgId, roleList);
				}
			} else {
				model.addAttribute("infomsg", ResultMessages.info().add(ResultMessage.fromCode("w.sm.0001")));
			}
			
			request.setAttribute("AllOrgRoleMap", allOrgRoleMap);				// all org-role map
			request.setAttribute("SelectedOrgRole", form.getRoleOrgArray());	// init - selected org-role is null
		}
		
		request.setAttribute("RoleMode", form.getRoleMode());
		model.addAttribute("form", form);
			
	}

	/**
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfoTmp(UserInfTmpForm form, BindingResult result, Model model,HttpServletRequest request){
		// 1) Ready for data
		// 1.1) get all orgnazition, selectedOrgIdList is selected by user, selectedOrgIdList + avaliabledOrgList = all orgnazition
		List<OrgInf> selectedOrgList = null;
		List<OrgInf> avaliabledOrgList = null;
		if (form.getUserOrgInfArr() != null && !form.getUserOrgInfArr().isEmpty()) {
			// get availabled orgnazition and selected orgnazition		
			List<List<OrgInf>> orgList = orgInfMaintenanceService.transQueryAvailabledAndSelectedList(form.getUserOrgInfArr());
			selectedOrgList = orgList.get(0);
			avaliabledOrgList = orgList.get(1);
		} else {
			avaliabledOrgList = orgInfMaintenanceService.transQueryOrgInfList();
		}
		model.addAttribute("AvailabledOrg", avaliabledOrgList);				// all availabed org object
		model.addAttribute("SelectedOrg", selectedOrgList);					// selected org list
		
		// 1.2) 获取所有机构对应的角色
		HashMap<String, ArrayList<RoleInf>> roleOrgAllMap = new HashMap<String, ArrayList<RoleInf>>();
		List<OrgInf> orgAlllist= orgInfMaintenanceService.transQueryOrgInfList();
		for(int i = 0 ;i<orgAlllist.size();i++){
			String orgId = StringUtil.trim(orgAlllist.get(i).getOrgid());
			ArrayList<RoleInf> roleList=(ArrayList<RoleInf>) roleInfService.queryRolesByOrgRec(orgId);
			roleOrgAllMap.put(orgId, roleList);
		}
		request.setAttribute("AllOrgRoleMap", roleOrgAllMap);
		
		String roleMode = form.getRoleMode();
		
		// 2) data process in different way accroding to RoleMode
		// 2.1) 机构与角色无关 - RoleMode == 1(ROLE_MODE_UNBIND_ORG)
		if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// get all role - all field of roleInf is null 
			RoleInf roleInf = new RoleInf();
			List<RoleInf> allRoleList = roleInfService.queryRolesByOrg(roleInf);

			request.setAttribute("AvailabledRole", allRoleList);				// all availabed role object
			request.setAttribute("SelectedRole", form.getRoleArray());			// selected role array
			form.setRoleArray(form.getRoleArray());

		// 2.2) 机构与角色有关 - RoleMode == 0(ROLE_MODE_BIND_ORG)	
		} else if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			List<String> selectedOrgRole = new ArrayList<String>();
			for(int i = 0 ; i < form.getRoleOrgArray().length ; i++){
				selectedOrgRole.add(form.getRoleOrgArray()[i]);
			}
			request.setAttribute("SelectedOrgRole", selectedOrgRole);
			
			UserInfTmp userInfTmp = new UserInfTmp();
			userInfTmp.setCreateorg(ContextConst.getCurrentUser().getLoginorg());
			userInfTmp.setUserstatus("01");
			form.setUserInfTmp(userInfTmp);
			
		// 2.2) Both - RoleMode == 2(ROLE_MODE_BOTH)		
		} else {
			
		}
		
		model.addAttribute("form", form);
	}
	
	@Autowired
	private UserInfService userInfService;
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;
	@Autowired
	private RoleInfService roleInfService;
	
	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;
	
	@Autowired
	private UserInfService nuserInfService;
}
