/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.UrlMap;
import com.synesoft.fisp.app.common.constants.UserConst;
import com.synesoft.fisp.app.common.context.CommonResourceHelper;
import com.synesoft.fisp.app.common.listener.SyneSessionListener;
import com.synesoft.fisp.app.common.model.LoginForm;
import com.synesoft.fisp.app.common.model.SessionForm;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.GenRdmGUID;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.encryp.SHAEncrypt;
import com.synesoft.fisp.app.sm.model.PwdChgForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.SysPasswordHistorys;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.service.cm.CommonService;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;
import com.synesoft.fisp.domain.service.sm.SysPasswordHistorysService;
import com.synesoft.fisp.domain.service.sm.UserInfService;

/**
 * @file LoginController.java
 * @author Jon
 * @date 2013-3-24
 * @description TODO
 * @tag 1.0.0
 * 
 */
@Controller
public class LoginController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserInfService userInfService;
	
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SysPasswordHistorysService sysPasswordHistorysService;

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * redirect to login page
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "/login";
	}

	/**
	 * 1.用户名/密码输入错误，给出提示。
	 * 2.密码输错达到累积最大次数，锁定该用户。
	 * 3.用户名和密码验证通过，检查用户的状态，锁定/冻结状态给出对应提示。
	 * 4.当前用户是否已经登陆，若在其他机器已登陆，需要给出提示，并可将其踢下线。
	 * 5.密码将要过期，给出提示。
	 * 6.若是初始化密码(新建用户/重置密码)强制要求修改密码，并用新密码重新登录。
	 * @param form
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("login")
	public String login(@Valid @ModelAttribute LoginForm form, BindingResult result, Model model, HttpSession session) {
		logger.debug("Login controller execute login.");
		
		UserInf userInfo = null;

		// 判断当前session是否已经登陆了用户
		userInfo = ContextConst.getCurrentUser();
		if(userInfo != null && UserConst.STATUS_NOLOGIN.equals(userInfo.getStatus())) {
			return "redirect:" + UrlMap.INDEX_URL;
		}
		
		if (result.hasErrors()) {
			return UrlMap.LOGIN_URL;
		}
		
		// 根据用户输入的登录id,获取用户信息
		String userid = form.getUserId();
		if(null != userid && !"".equals(userid.trim())) {
			userInfo = userInfService.queryUser(userid);
		}
		
		// 用户不存在
		if (userInfo == null) {
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1010")));
			return UrlMap.LOGIN_URL;
		}
		
		// 去掉用户名的空格
		userInfo.setUserid(userInfo.getUserid().trim()); 
		
		//校验密码错误(SHA1加密算法)
		if (!SHAEncrypt.stringToSHA1(form.getPassword()).equals(userInfo.getPassword())) { 
			Long faillogincnt = userInfo.getFaillogincnt();
			if(faillogincnt == null || faillogincnt <= 0) {
				faillogincnt = 0L;
			}
			
			//达到最大次数,提示 用户名/密码输入错误
			if (faillogincnt == UserConst.getPASSWORD_MAX_FAILURE_COUNT()) {
				model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1008")));
				return UrlMap.LOGIN_URL;
			}
			
			// 错误次数+1
			faillogincnt ++;  
			
			//达到最大密码错误次数,将用户锁定
			if (faillogincnt>=UserConst.getPASSWORD_MAX_FAILURE_COUNT() ){
				userInfo.setUserstatus(UserConst.USER_STATUS_LOCK);
			}
			
			userInfo.setFaillogincnt(faillogincnt);
			
			// 更新用户状态和密码错误信息
			userInfService.updateUser(userInfo); 

			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1002", UserConst.getPASSWORD_MAX_FAILURE_COUNT(), faillogincnt)));
			return UrlMap.LOGIN_URL;
		}
		
		//判断用户状态(是否已锁定)
		if(UserConst.USER_STATUS_LOCK.equals(userInfo.getUserstatus())) {
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1006")));
			return UrlMap.LOGIN_URL;
		} 

		//判断用户状态(是否已冻结)	
		if(UserConst.USER_STATUS_LOGOFF.equals(userInfo.getUserstatus())) {
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1009")));
			return UrlMap.LOGIN_URL;
		} 
		
		// 将用户上一次的登录信息存入Session,用于主界面显示
		UserInf user_old = new UserInf();
		BeanUtils.copyProperties(userInfo, user_old);
		ContextConst.setAttribute(CommonConst.USER_OLD_SESSION, user_old);
		String current_loginorg = userInfo.getLoginorg();
		
		//输入的用户名已登录
		if(UserConst.STATUS_LOGINED.equals(userInfo.getStatus())) {
			//session存在
			if (SyneSessionListener.getSessionMap().containsKey(userid.trim())){
				//若当前机器IP和Session创建IP不相同
				if (!ContextConst.getCurrentIP().equals(SyneSessionListener.getSessionMap().get(userid).getIp())){
					//系统参数设置,不可以强制踢对方下线
					if ("FALSE".equals(UserConst.getDUPLICATE_LOGIN_ALLOW_KICK().toUpperCase())){
						model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1025",userid, SyneSessionListener.getSessionMap().get(userid).getIp())));
						return UrlMap.LOGIN_URL;
					}else{
						//销毁对方已登录的session
						SyneSessionListener.sessionInvalidate(userid);
					}
				}else{
					//销毁已存在的session
					SyneSessionListener.sessionInvalidate(userid);
				}
			}
		}
		
		//初始密码 需要强制修改密码
		if (UserConst.PASSWORD_STATUS.INITIAL.toString().equals(userInfo.getPasswordstatus())){
			//将用户登录信息保存在USER_OLD_SESSION里
			ContextConst.setAttribute(CommonConst.USER_SESSION_MODPWD, userInfo);
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1012")));
			return UrlMap.PWD_CHG_UTL;
		}
		
		//重置密码 需要强制修改密码
		if (UserConst.PASSWORD_STATUS.RESETTED.toString().equals(userInfo.getPasswordstatus())){
			ContextConst.setAttribute(CommonConst.USER_SESSION_MODPWD, userInfo);
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1013")));
			return "redirect:"+UrlMap.PWD_CHG_UTL;
		}
		
		//密码将要到期,给出提醒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date pwdChgDate = null;
		try {
			pwdChgDate = sdf.parse(userInfo.getPwdchangetime());
		} catch (ParseException e) {
			logger.error("格式化上次修改密码时间出错:"+e.getMessage());
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1022")));
			return UrlMap.LOGIN_URL;
		}
		int pwdValidataDays = UserConst.getPASSWORD_VALID_DAYS();
		int pwdOverdueDays = UserConst.getPASSWORD_OVERDUE_WARN_DAYS();
		
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(pwdChgDate);
		int lastChgDay = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(new Date());
		int today = aCalendar.get(Calendar.DAY_OF_YEAR);
		int overDueDays = today - lastChgDay;
		
		//密码已经过期
		if (overDueDays > pwdValidataDays) {
			ContextConst.setAttribute(CommonConst.USER_SESSION_MODPWD, userInfo);
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1023")));
			return "redirect:" + UrlMap.PWD_CHG_UTL;
		} else 
		//密码到达提醒期
		if (overDueDays >= (pwdValidataDays - pwdOverdueDays)) {
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1024", pwdValidataDays-overDueDays)));
			// 登录成功需要做的操作
			loginSuccessToDo(session, userInfo, current_loginorg);
			return "forward:" + UrlMap.INDEX_URL;
		}
		
		// 登录成功需要做的操作
		loginSuccessToDo(session, userInfo, current_loginorg);
		
		return "redirect:" + UrlMap.INDEX_URL;
	}
	
	/**
	 * 修改密码
	 * @param form
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/sm/pwdChg/init")
	public String pwdChg(@Valid @ModelAttribute PwdChgForm form, BindingResult result, Model model,HttpSession session) {
		return "sm/SM_Pwd_Chg";
	}	
	
	/**
	 * 修改密码,并校验密码规则
	 * @param form
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/sm/pwdChg/submit")
	public String pwdChgSubmit(@Valid @ModelAttribute PwdChgForm form, BindingResult result, Model model,HttpSession session) {
		Boolean isForceChgPwd = false; //是否是强制修改密码
		
		UserInf userInfo = null;
		// 初始登录,强制修改密码
		if (session.getAttribute(CommonConst.USER_SESSION_MODPWD) != null) {
			userInfo = (UserInf) session.getAttribute(CommonConst.USER_SESSION_MODPWD);
			isForceChgPwd = true;
		} else {// 登录成功后,修改密码
			userInfo = (UserInf) session.getAttribute(CommonConst.USER_SESSION);
		}

		String oldPwd = form.getOldPwd();
		String newPwd = form.getNewPwd();
		String newPwdAgain = form.getNewPwdAgain();
		
		//检查旧密码是否正确
		if (!SHAEncrypt.stringToSHA1(oldPwd).equals(userInfo.getPassword())) { 
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1017")));
			return "redirect:"+"sm/SM_Pwd_Chg";
		}
		
		//检查两次新密码输入是否一致
		if (!newPwd.equals(newPwdAgain)){
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1018")));
			return "sm/SM_Pwd_Chg";
		}
		
		//检查密码是否满足规则
		List<SysParam> lSysparam = CommonResourceHelper.getSysParam("0002");
		if (lSysparam != null && lSysparam.size() > 0) {
			for (int i=0; i<lSysparam.size(); i++){
				Pattern p = Pattern.compile(lSysparam.get(i).getParamVal());
				Matcher m = p.matcher(newPwd);
				if (!m.matches()){
					model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1019")));
					return "sm/SM_Pwd_Chg";
				}
			}
		}
		//不得已用户名开头或结尾
		if (userInfo.getUserid().trim().startsWith(newPwd) || userInfo.getUserid().trim().endsWith(newPwd)){
			model.addAttribute("infomsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1019")));
			return "sm/SM_Pwd_Chg";
		}
		
		//校验是否重复使用历史密码
		int password_history_count = UserConst.getPASSWORD_HISTORY_COUNT();
		
		SysPasswordHistorys tmpSysPasswordHistorys = new SysPasswordHistorys();
		tmpSysPasswordHistorys.setUserId(userInfo.getUserid().trim());
		List<SysPasswordHistorys> lSysPasswordHistorys = sysPasswordHistorysService.querySysPasswordHistorysList(tmpSysPasswordHistorys);
		if (lSysPasswordHistorys != null && lSysPasswordHistorys.size() > 0) {
			int it_count = 0;
			if (password_history_count >= lSysPasswordHistorys.size())
				it_count = lSysPasswordHistorys.size();
			else
				it_count = password_history_count;
			
			for (int i = 0; i< it_count; i++){
				if (SHAEncrypt.stringToSHA1(newPwd).equals(lSysPasswordHistorys.get(i).getPwdVal().trim())) { 
					model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1020")));
					return "sm/SM_Pwd_Chg";
				}
			}
		}
		
		//记录更改密码记录
		userInfo.setPwdchangetime(DateUtil.getCurrentDateToString("yyyyMMddHHmmss"));
		userInfo.setPwdchangeuser(userInfo.getUserid().trim());
		userInfo.setPassword(SHAEncrypt.stringToSHA1(newPwd));
		userInfo.setPasswordstatus(UserConst.PASSWORD_STATUS.COMMITTED.toString());
		
		userInfService.updateUser(userInfo);
		
		//插入历史密码表
		SysPasswordHistorys ph = new SysPasswordHistorys();
		ph.setUserId(userInfo.getUserid().trim());
		ph.setPwdVal(SHAEncrypt.stringToSHA1(oldPwd));
		ph.setChgTime(DateUtil.getCurrentDateToString("yyyyMMddHHmmss"));
		ph.setPwdId(GenRdmGUID.getGUID());
		sysPasswordHistorysService.insertSysPasswordHistorys(ph);
		
		
		//强制修改密码,是否需要重新登录
		if (isForceChgPwd){
			String relogin = UserConst.getPASSWORD_MODIFY_RELOGIN();
			if ("TRUE".equals(relogin.toUpperCase())){
				model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.cm.1021")));
				session.setAttribute(CommonConst.USER_SESSION_MODPWD, null);
				return UrlMap.LOGIN_URL;
			}
		}
		
		// 登录成功需要做的操作
		loginSuccessToDo(session, userInfo, userInfo.getLoginorg());
		return "redirect:" + UrlMap.INDEX_URL;
	}	
	
	/**
	 * 用户机构切换
	 * 
	 * @return
	 */
	@RequestMapping("reLogin")
	public String reLogin(@ModelAttribute LoginForm form,
			BindingResult result, Model model,HttpSession session) {
		
		// 获取当前用户
		UserInf userInfo = ContextConst.getCurrentUser();
		
		// 获取用户切换的机构
		String current_loginorg = form.getUserlogorg();
		
		// 登录成功需要做的操作
		loginSuccessToDo(session, userInfo, current_loginorg);
		
		return "redirect:" + UrlMap.INDEX_URL;
	}

	/**
	 * 登录成功需要做的操作
	 * @param session			session
	 * @param userInfo			当前用户信息
	 * @param current_loginorg	用户当前的登录机构
	 */
	private void loginSuccessToDo(HttpSession session, UserInf userInfo, String current_loginorg) {
		//登录成功，修改状态、上次登录时间、登录时间、登录IP、重置登录失败的次数 
		userInfo.setStatus(UserConst.STATUS_LOGINED);
		// 获取当前时间
		String nowDateTime = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
		// 获取最后一次登录的时间
		String logintime = userInfo.getLogintime();
		if(logintime != null && !"".equals(logintime)) {
			userInfo.setLastlogintime(logintime);
		} else {
			userInfo.setLastlogintime(nowDateTime);
		}
		userInfo.setLogintime(nowDateTime);
		// IP
		userInfo.setIpaddress(ContextConst.getCurrentIP());
		userInfo.setFaillogincnt(0L); //登陆成功,将失败次数清0
		
		// 修改用户信息
		userInfService.updateUser(userInfo);
		
		// 将用户信息存入Session中
		ContextConst.setAttribute(CommonConst.USER_SESSION, userInfo);
		
		// 获取机构信息存入Session中
		OrgInf userOrgInf=new OrgInf();
		userOrgInf.setOrgid(StringUtil.trim(current_loginorg));
		userOrgInf=orgInfMaintenanceService.transQueryOrgInf(userOrgInf);
		if (userOrgInf != null) {
			userOrgInf.setOrgid(userOrgInf.getOrgid().trim());
		}
		ContextConst.setAttribute(CommonConst.ORG_SESSION, userOrgInf);
		
		// 获取当前用户的功能权限
		commonService.queryAuthority();
		
		// 获取菜单信息存入Session中
		String[] menu_all = commonService.transQueryMenuList(userInfo);
		ContextConst.setAttribute(CommonConst.MENU_SESSION, menu_all[0]);
		ContextConst.setAttribute(CommonConst.LEFT_MENU_SESSION, menu_all[1]);
		
		// 获取用户机构信息列表存入Session中
		List<UserOrgInf> list = commonService.queryUserOrgList(userInfo);
		ContextConst.setAttribute(CommonConst.USERORGLIST_SESSION, list);
		Locale local = (Locale)session.getAttribute(SessionForm.LOCAL);
		if(local == null) {
//			local = new Locale("en","US");
			local = new Locale("zh","CN");
			session.setAttribute(SessionForm.LOCAL, local);
		}
		//将session保存起来
		session.setAttribute("SyneSessionBindingListener", new SyneSessionListener());
	}
	
	/**
	 * 用户退出
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(Model model, HttpSession session){
		//销毁session
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo != null) {
			SyneSessionListener.sessionInvalidate(userInfo.getUserid().trim());
		}
		return UrlMap.LOGIN_URL;
	}

	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpSession session ,Model model, HttpServletResponse response) {
		session.setAttribute(SessionForm.CUSTOMER_REF_FORM, null);
		try {
			request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("header")
	public String header(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/layout/header.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("main")
	public String main(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("leftFrame")
	public String leftFrame(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/layout/leftFrame.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("hideBar")
	public String hideBar(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/layout/hideBar.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("rightFrame")
	public String rightFrame(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/layout/rightFrame.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("footbar")
	public String footbar(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/views/layout/footbar.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("notFound")
	public String notFound(HttpServletRequest request, HttpSession session) {
		return UrlMap.NOTFOUND_URL;
	}

	@RequestMapping("language")
	public String language(@RequestParam("locale") String locale,HttpSession session,LoginForm form) {
		Locale local = null;
		if(CommonConst.ENGLISH.equals(locale)){
			local = new Locale("en","US");
			session.setAttribute(SessionForm.LOCAL, local);
		}else if(CommonConst.JAPANESE.equals(locale)){
			local = new Locale("ja","JP");
			session.setAttribute(SessionForm.LOCAL, local);
		}else if(CommonConst.CHINESE.equals(locale)){
			local = new Locale("zh","CN");
			session.setAttribute(SessionForm.LOCAL, local);
		}
		
		return UrlMap.LOGIN_URL;
	}
	
}
