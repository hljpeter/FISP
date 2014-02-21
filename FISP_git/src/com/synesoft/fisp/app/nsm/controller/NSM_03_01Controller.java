package com.synesoft.fisp.app.nsm.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.service.sm.SysUserRoleInfService;
import com.synesoft.fisp.domain.service.sm.UserInfService;
import com.synesoft.fisp.domain.service.sm.UserOrgInfService;
import com.synesoft.fisp.domain.service.sm.UserRoleInfMaintenanceService;

/**
 * 操作员信息维护
 * @author yyw
 * @date 2014-01-03
 */
@Controller
@RequestMapping(value = "nsm03")
public class NSM_03_01Controller {
	private static final Logger logger = LoggerFactory.getLogger(NSM_03_01Controller.class);

	@ModelAttribute
	public UserInfForm setUpForm() {
		return new UserInfForm();
	}
	
	/**
	 * 页面初始化
	 * @return
	 */
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "sm1/SM_03_01";
	}

	/**
	 * 查询
	 * @param listForm
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping("01/search")
	public String search(UserInfForm listForm, @PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		
		String userId = StringUtil.trim(listForm.getUserid());
		String userName = StringUtil.trim(listForm.getUsername());
		UserInf userInf = listForm.getUserInf();
		if (userInf == null) {
			userInf = new UserInf();
		}
		userInf.setUserid(userId);
		userInf.setUsername(userName);
		Page<UserInf> page = userInfService.transQueryUserInfList(pageable, userInf);
		if (page.getContent().size() > 0 ) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute("infomsg", ResultMessages
					.info().add(ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm1/SM_03_01";
	}

	/**
	 * 删除
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping("01/del")
	public String del(UserInfForm form, BindingResult result, Model model) {
		logger.info("start del ...");
		
		if (result.hasErrors()) {
			return "forward:/nsm03/01/search";
		}
		
		queryInfo(form);		
		try {
			userInfService.transUpdateByMode(form, CommonConst.OPER_TYPE_DELETE);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/nsm03/01/search";
		}
		model.addAttribute("successmsg", ResultMessages
				.success().add(ResultMessage.fromCode("i.sm.0003")));
		return "forward:/nsm03/01/search";
	}
	
	private void queryInfo(UserInfForm form) {

		String roleMode = form.getRoleMode();
		
		// 1) Ready for orgnazition information
		// get orgnazition information from table	
		UserOrgInf userOrgInf = new UserOrgInf();
		userOrgInf.setUserid(form.getUserInf().getUserid().trim());
		List<UserOrgInf> userOrgList = userOrgInfService.transQueryUserOrgInfList(userOrgInf);
		List<String> userOrgIdList = new ArrayList<String>();
		for (int i = 0; i < userOrgList.size(); i++) {
			UserOrgInf inf = userOrgList.get(i);
			userOrgIdList.add(inf.getOrgid());
		}
		form.setUserOrgInfArr(userOrgIdList);
		
		// 2) Ready for role information
		// 2.1) 机构与角色无关 - RoleMode == 1(ROLE_MODE_UNBIND_ORG)
		if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// get selected role from table
			SysUserRole temp = new SysUserRole();
			temp.setUserid(form.getUserInf().getUserid().trim());
			//temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
			List<String> roleList = sysUserRoleInfService.queryRoleIdByUserId(temp);
			String[] roleArray = new String[roleList.size()];
			roleArray = roleList.toArray(roleArray);
			
			form.setRoleArray(roleArray);

		// 2.2) 机构与角色有关 - RoleMode == 0(ROLE_MODE_BIND_ORG)
		} else if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// get selected org-role information, format: orgId_roleId
			List<String> selectedOrgRole = new ArrayList<String>();
			for (int i = 0; i < userOrgList.size(); i++) {
				UserOrgInf orgInf = userOrgList.get(i);
				UserRoleInf userRoleInf = new UserRoleInf();
				userRoleInf.setUserid(form.getUserInf().getUserid().trim());
				userRoleInf.setOrgid(orgInf.getOrgid().trim());
				List<String> list = userRoleInfMaintenanceService.transQueryUserRoleIdList(userRoleInf);
				for (int j = 0; j < list.size(); j++) {
					String str = orgInf.getOrgid().trim() + "_" + list.get(j).trim();
					selectedOrgRole.add(str);
				}
			}
			form.setRoleOrgList(selectedOrgRole);
		}
	}
	@Autowired
	private UserInfService userInfService;
	@Autowired
	private UserOrgInfService userOrgInfService;
	@Autowired
	private SysUserRoleInfService sysUserRoleInfService;
	@Autowired
	private UserRoleInfMaintenanceService userRoleInfMaintenanceService;

}
