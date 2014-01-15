package com.synesoft.fisp.app.sm.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.app.sm.model.UserInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserInfTmp;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;
import com.synesoft.fisp.domain.service.sm.RoleInfService;
import com.synesoft.fisp.domain.service.sm.SysUserRoleInfService;
import com.synesoft.fisp.domain.service.sm.UserInfService;
import com.synesoft.fisp.domain.service.sm.UserOrgInfService;
import com.synesoft.fisp.domain.service.sm.UserRoleInfMaintenanceService;

/**
 * 操作员详细信息查询（SM_03_05 & SM_03_06）
 * @author yyw
 * @date 2014-01-02
 */
@Controller
@RequestMapping(value = "sm03")
public class SM_03_05Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_03_05Controller.class);

	/**
	 * 操作员信息查询 - 详情按钮
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("05/detailSearch_01")
	public String detailSearch_01(@ModelAttribute UserInfForm form, BindingResult result, Model model, HttpServletRequest request) {
		logger.info("detailSearch_01...");
		
		UserInf userInf = form.getUserInf();
		userInf = userInfService.transQueryUserInf(userInf);
		form.setUserInf(userInf);
		queryInfo(form, result, model, request);
		
		return "sm/SM_03_05";
	}
	
	/**
	 * 操作员信息维护审核  - 操作按钮
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("05/detailSearch_04")
	public String detailSearch_04(@ModelAttribute UserInfTmpForm form, BindingResult result, Model model, HttpServletRequest request) {
		logger.info("detailSearch_04...");

		// get user information from the temporary table
		UserInfTmp userInfTmp = form.getUserInfTmp();
		userInfTmp = userInfService.transQueryUserInfTmp(userInfTmp);
		form.setUserInfTmp(userInfTmp);
		
		queryInfoTmp(form, result, model, request);
		
		return "sm/SM_03_06";
	}
	
	/**
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息，从主表
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfo(UserInfForm form, BindingResult result, Model model, HttpServletRequest request) {

		String roleMode = form.getRoleMode();
		
		// 1) Ready for orgnazition information
		// get orgnazition information from temporary table	
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
			List<String> tmpRoleList = sysUserRoleInfService.queryRoleIdByUserId(temp);
			String[] tmpRoleArray = new String[tmpRoleList.size()];
			tmpRoleArray = tmpRoleList.toArray(tmpRoleArray);
			
			request.setAttribute("AvailabledRole", allRoleList);			// all availabed role object
			request.setAttribute("SelectedRole", tmpRoleArray);				// selected role array

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
	 * 根据角色模式查询用户机构信息，用户机构角色信息或者用户角色信息，从主表和临时表
	 * @param form
	 * @param result
	 * @param model
	 * @param request
	 */
	private void queryInfoTmp(UserInfTmpForm form, BindingResult result, Model model,HttpServletRequest request){
		// 1) Ready for data
		// 1.1) get orgnazition information from table and temporary
		UserOrgInfTmp userOrgInfTmp = new UserOrgInfTmp();
		userOrgInfTmp.setUserid(form.getUserInfTmp().getUserid().trim());
		userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
		List<UserOrgInfTmp> userOrgList = userOrgInfService.transQueryUserOrgInfMerge(userOrgInfTmp);
		
		// init - orgnazition information: availabledOrgList + selectedOrgList = all orgnazition
		// get availabled orgnazition and selected orgnazition		
		List<List<OrgInf>> orgList = orgInfMaintenanceService.transQueryAvailabledAndSelectedList3(userOrgList);
		model.addAttribute("AvailabledOrg", orgList.get(1));				// all availabed org object
		model.addAttribute("SelectedOrg", userOrgList);						// selected org array

		// 1.2) 获取所有机构对应的角色
		HashMap<String, ArrayList<RoleInf>> roleOrgAllMap = new HashMap<String, ArrayList<RoleInf>>();
		List<OrgInf> orgAlllist = orgInfMaintenanceService.transQueryOrgInfList();
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

			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserid(form.getUserInfTmp().getUserid().trim());
			sysUserRole.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
			String[] roleArray = sysUserRoleInfService.queryRoleListMerge(sysUserRole);
			
			request.setAttribute("AvailabledRole", allRoleList);				// all availabed role object
			request.setAttribute("SelectedRole", roleArray);					// selected role array

		// 2.2) 机构与角色有关 - RoleMode == 0(ROLE_MODE_BIND_ORG)	
		} else if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			List<OrgInf> allOrgList = orgInfMaintenanceService.transQueryOrgInfList();						// get all orgnazition
			HashMap<String, ArrayList<RoleInf>> allOrgRoleMap = new HashMap<String, ArrayList<RoleInf>>();	// org-role Map object
			if (allOrgList.size() > 0 ) {
				// 获取所有机构对应的角色
				for (int i = 0; i < allOrgList.size(); i++) {
					String orgId = StringUtil.trim(allOrgList.get(i).getOrgid());
					ArrayList<RoleInf> roleList = (ArrayList<RoleInf>) roleInfService.queryRolesByOrgRec(orgId);
					allOrgRoleMap.put(orgId, roleList);
				}
			} else {
				model.addAttribute("infomsg", ResultMessages.info().add(ResultMessage.fromCode("w.sm.0001")));
			}
			
			// get org-role information from table and temporary
			UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
			userRoleInfTmp.setUserid(form.getUserInfTmp().getUserid().trim());
			userRoleInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
			List<UserRoleInfTmp> mergeRoleList = userRoleInfMaintenanceService.transQueryRoleListMerge(userRoleInfTmp);
			
			// get selected org-role information, format: orgId_roleId
			List<String> selectedOrgRole = new ArrayList<String>();
			for (int i = 0; i < mergeRoleList.size(); i++) {
				UserRoleInfTmp inf = mergeRoleList.get(i);
				String str = inf.getOrgid().trim() + "_" + inf.getRoleid().trim();
				selectedOrgRole.add(str);
			}
			
			request.setAttribute("AllOrgRoleMap", allOrgRoleMap);				// all org-role map
			request.setAttribute("SelectedOrgRole", selectedOrgRole);			// selected org-role
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
