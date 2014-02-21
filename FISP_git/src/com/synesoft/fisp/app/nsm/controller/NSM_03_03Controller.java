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
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.app.sm.model.UserInfForm.SM_03_03_Modify;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;
import com.synesoft.fisp.domain.service.sm.RoleInfService;
import com.synesoft.fisp.domain.service.sm.SysUserRoleInfService;
import com.synesoft.fisp.domain.service.sm.UserInfService;
import com.synesoft.fisp.domain.service.sm.UserOrgInfService;
import com.synesoft.fisp.domain.service.sm.UserRoleInfMaintenanceService;

/**
 * 操作员信息维护(SM_03_03)
 * @author yyw
 * @date 2014-01-02
 */
@Controller
@RequestMapping(value = "nsm03")
public class NSM_03_03Controller {
	private static final Logger logger = LoggerFactory.getLogger(NSM_03_03Controller.class);

	@ModelAttribute
	public UserInfForm setInfoUpForm() {
		return new UserInfForm();
	}
	
	/**
	 * 操作员信息维护  - 详细
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("03/search")
	public String search(UserInfForm form,
			BindingResult result, Model model, HttpServletRequest request) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "sm1/SM_03_03";
		}
		
		UserInf userInf = form.getUserInf();
		userInf = userInfService.transQueryUserInf(userInf);
		form.setUsername(userInf.getUsername());
		form.setUserstatus(userInf.getUserstatus());
		form.setLoginorg(userInf.getLoginorg());
		form.setUserInf(userInf);
		
		queryInfo(form,result,model,request);
		
		return "sm1/SM_03_03";
	}

	/**
	 * 操作员信息维护  - 修改
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("03/modify")
	public String modify(@Validated({SM_03_03_Modify.class}) UserInfForm form,
			BindingResult result, Model model, HttpServletRequest request) {
		logger.info("start modify ...");
		
		queryInfoTmp(form,result,model,request);
		
		if (result.hasErrors()) {
			return "sm1/SM_03_03";
		}
		
		UserInf userInf = form.getUserInf();
		userInf.setUserid(StringUtil.trim(userInf.getUserid()));
		try {
			userInfService.transUpdateByMode(form, CommonConst.OPER_TYPE_UPDATE);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_03_03";
		}
		model.addAttribute("successmsg", ResultMessages
				.success().add(ResultMessage.fromCode("i.sm.0002")));
		return "sm1/SM_03_03";
	}
	
	/**
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息，从主表
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfo(UserInfForm form, BindingResult result, Model model, HttpServletRequest request){

		String roleMode = form.getRoleMode();
		
		// 1) Ready for orgnazition information
		// get orgnazition information from table	
		UserOrgInf userOrgInf = new UserOrgInf();
		userOrgInf.setUserid(form.getUserInf().getUserid().trim());
		List<UserOrgInf> userOrgList = userOrgInfService.transQueryUserOrgInfList(userOrgInf);
		
		// init - orgnazition information: availabledOrgList + selectedOrgList = all orgnazition
		List<OrgInf> selectedOrgList = null;
		List<OrgInf> availabledOrgList = null;
		// get availabled orgnazition and selected orgnazition		
		List<List<OrgInf>> orgList = orgInfMaintenanceService.transQueryAvailabledAndSelectedList2(userOrgList);
		selectedOrgList = orgList.get(0);
		availabledOrgList = orgList.get(1);
		model.addAttribute("AvailabledOrg", availabledOrgList);				// all availabed org object
		model.addAttribute("SelectedOrg", selectedOrgList);					// selected org array

		// 2) Ready for role information
		// 2.1) 机构与角色无关 - RoleMode == 1(ROLE_MODE_UNBIND_ORG)
		if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// get all role - all field of roleInf is null 
			RoleInf roleInf = new RoleInf();
			List<RoleInf> allRoleList = roleInfService.queryRolesByOrg(roleInf);

			// get selected role from temporary table
			SysUserRole temp = new SysUserRole();
			temp.setUserid(form.getUserInf().getUserid().trim());
			//temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
			List<String> roleList = sysUserRoleInfService.queryRoleIdByUserId(temp);
			String[] roleArray = new String[roleList.size()];
			roleArray = roleList.toArray(roleArray);
			
			request.setAttribute("AvailabledRole", allRoleList);			// all availabed role object
			request.setAttribute("SelectedRole", roleArray);				// selected role array

		// 2.2) 机构与角色有关 - RoleMode == 0(ROLE_MODE_BIND_ORG)
		} else if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			List<OrgInf> allOrgList = orgInfMaintenanceService.transQueryOrgInfList();						// get all orgnazition
			HashMap<String, ArrayList<RoleInf>> allOrgRoleMap = new HashMap<String, ArrayList<RoleInf>>();	// org-role Map object
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
			
			// get selected org-role information, format: orgId_roleId
			List<String> selectedOrgRole = new ArrayList<String>();
			for (int i = 0; i < selectedOrgList.size(); i++) {
				UserRoleInf userRoleInf = new UserRoleInf();
				userRoleInf.setUserid(form.getUserInf().getUserid().trim());
				userRoleInf.setOrgid(selectedOrgList.get(i).getOrgid().trim());
				List<String> list = userRoleInfMaintenanceService.transQueryUserRoleIdList(userRoleInf);
				for (int j = 0; j < list.size(); j++) {
					String str = selectedOrgList.get(i).getOrgid().trim() + "_" + list.get(j).trim();
					selectedOrgRole.add(str);
				}
			}
			
			request.setAttribute("AllOrgRoleMap", allOrgRoleMap);				// all org-role map
			request.setAttribute("SelectedOrgRole", selectedOrgRole);			// selected org-role
		}
		
		request.setAttribute("RoleMode", form.getRoleMode());
		model.addAttribute("form", form);
	}
	
	/**
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息，从主表
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfoTmp(UserInfForm form, BindingResult result, Model model,HttpServletRequest request){
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

		// 2) data process in different way accroding to RoleMode
		String roleMode = form.getRoleMode();
		
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
			
		// 2.2) Both - RoleMode == 2(ROLE_MODE_BOTH)		
		} else {
			
		}

		request.setAttribute("RoleMode", form.getRoleMode());
		model.addAttribute("form", form);
	}
	
	@Autowired
	private UserInfService userInfService;
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;
	@Autowired
	private UserOrgInfService userOrgInfService;
	@Autowired
	private UserRoleInfMaintenanceService userRoleInfMaintenanceService;
	@Autowired
	private RoleInfService roleInfService;
	@Autowired
	private SysUserRoleInfService sysUserRoleInfService;
}
