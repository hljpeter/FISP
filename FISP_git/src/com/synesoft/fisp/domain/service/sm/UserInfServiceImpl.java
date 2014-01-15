package com.synesoft.fisp.domain.service.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.GenRdmPwd;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.app.common.utils.encryp.SHAEncrypt;
import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.app.sm.model.UserInfTmpForm;
import com.synesoft.fisp.app.sm.model.UserRoleInfForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.OrgRoleInf;
import com.synesoft.fisp.domain.model.OrgRoleInfTmp;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserInfTmp;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.SysUserRoleRepository;
import com.synesoft.fisp.domain.repository.sm.SysUserRoleTmpRepository;
import com.synesoft.fisp.domain.repository.sm.UserInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserInfTmpRepository;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfTmpRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfTmpRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * @author zhongHubo
 * @date 2013年7月8日 15:57:17
 * @version 1.0
 * @Description 用户操作ServiceImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("userInfService")
public class UserInfServiceImpl implements UserInfService {
	
	private static LogUtil log=new LogUtil(UserInfServiceImpl.class);

	@Autowired
	protected UserInfRepository userInfRepository;
	@Autowired
	protected OrgInfRepository orgInfRepository;
	
	@Autowired
	protected UserInfTmpRepository userInfTmpRepository;
	
	@Autowired
	protected UserOrgInfService userOrgInfService;
	@Autowired
	protected UserOrgInfRepository userOrgInfRepository;
	
	@Autowired
	protected UserOrgInfTmpRepository userOrgInfTmpRepository;
	@Autowired
	protected NumberService numberService;
	
	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;
	@Autowired
	protected RoleInfRepository roleInfRepository;
	@Autowired
	protected UserRoleInfTmpRepository userRoleInfTmpRepository;
	@Autowired
	protected SysUserRoleRepository sysUserRoleRepository;
	@Autowired
	protected SysUserRoleTmpRepository sysUserRoleTmpRepository;
	
	private MessagesUtil message = MessagesUtil.getInstance();
	/**
	 * 根据用户Id查询用户信息
	 * @param userid	用户Id
	 * @return			UserInf
	 */
	@Override
	public UserInf queryUser(String userid) {
		return userInfRepository.queryUser(userid);
	}
	
	@Override
	public UserInfTmp transQueryUserInfTmp(UserInfTmp userInfTmp){
		return userInfTmpRepository.query(userInfTmp);
	}
	/**
	 * 修改用户信息
	 * @param userInf	待修改的用户信息
	 * @return			成功与否
	 */
	@Transactional
	@Override
	public boolean updateUser(UserInf userInf) {
		
		boolean flag = true;
		
		int res = userInfRepository.updateUser(userInf);
		if (res < 1) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public Page<UserInf> transQueryUserInfList(Pageable pageable,
			UserInf userInf) {
		Page<UserInf> page = userInfRepository.queryList(pageable, userInf);
		return page;
	}
	
	@Override
	public Page<UserInfTmp> transQueryUserInfTmpList(Pageable pageable,
			UserInfTmp userInfTmp) {
		Page<UserInfTmp> page = userInfTmpRepository.queryList(pageable, userInfTmp);
		return page;
	}

	@Override
	@Transactional
	public int transAdd(UserInfTmpForm form){
		ResultMessages messages = ResultMessages.error();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		String userId = form.getUserid();
		userInfTmp.setUserid(userId);
//		//金额特殊处理
//		String amt=form.getAuthamt().replace(",", "");
//		if(amt.contains(".")){
//			userInfTmp.setAuthamt(Long.valueOf(amt.substring(0, amt.length()-3)));
//		}else{
//			userInfTmp.setAuthamt(Long.valueOf(amt));
//		}
		userInfTmp.setLoginorg(form.getLoginorg());
		//查询用户表是否有相同记录
		if (null==userInfRepository.queryUser(userId)) {
			//查询用户临时表是否存在相同未授权记录
			if (userInfTmpRepository.queryUserInfo(userInfTmp)==0) {
				userInfTmp.setId(numberService.getSysIDSequence("0000",8));
				userInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOpttype("01");
				userInfTmp.setOptstatus("01");
				//插入用户临时表
				if (1 == userInfTmpRepository.insertUserInfTmp(userInfTmp)) {
					//从表单中获取机构列表信息
					UserOrgInf userOrgInf =new UserOrgInf();
					List<String> userOrgInfList=form.getUserOrgInfArr();
					for (int i = 0; i < userOrgInfList.size(); i++) {
						String userOrgId=StringUtil.trim(userOrgInfList.get(i));
						userOrgInf.setUserid(userId);
						userOrgInf.setOrgid(userOrgId);
						OrgInf orgInf=orgInfRepository.query(userOrgId);
						//查询用户机构表中是否存在相同记录信息
						if(null==userOrgInfRepository.queryUserOrgInf(userOrgInf)){
							UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp.setUserid(userId);
							userOrgInfTmp.setOrgid(userOrgId);
							//查询用户机构临时表中是否存在相同未授权记录
							if (userOrgInfTmpRepository.queryUserOrgInfTmpCnt(userOrgInfTmp)==0) {
								userOrgInfTmp.setOrgname(orgInf.getOrgname());
								userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
								userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
								userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								userOrgInfTmp.setOpttype("01");
								userOrgInfTmp.setOptstatus("01");
								//插入用户机构临时表
								if (1 != userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp)) {
									//插入错误，异常处理
									messages.add("e.sm.4037");
									throw new BusinessException(messages);
								}
							//用户机构临时表存在相同未授权记录
							}else{
								messages.add("e.sm.4038");
								throw new BusinessException(messages);
							}
						//用户机构表存在相同记录
						}else{
							messages.add("e.sm.4039");
							throw new BusinessException(messages);
						}
					}
				//插入用户临时表错误
				} else {
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
			//用户临时表存在相同未授权记录
			}else{
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		//用户表存在相同记录
		}else{
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
		return 0;
	}
	

	/** ======================================= add ======================================= */
	@Override
	@Transactional
	public void transAddByMode(UserInfTmpForm form){
		log.debug("UserInfService transAddByMode start");
				
		String userId = StringUtil.trim(form.getUserid());
		String userName = StringUtil.trim(form.getUsername());
		String roleMode = StringUtil.trim(form.getRoleMode());
		
		UserInfTmp temp = form.getUserInfTmp();
		temp.setUserid(userId);
		temp.setUsername(userName);
		temp.setLoginorg(form.getLoginorg());
		
		// add user information to temporary table 'SYS_USER_INF_TMP'
		addUser(temp);

		// add user-orgnization information to temporary table 'SYS_USER_ORG_INF_TMP'
		Map<String, OrgInf> orgMap = addUserOrg(form.getUserOrgInfArr(), userId);

		if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// add user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF_TMP'
			addUserOrgRole(userId, orgMap, form.getRoleOrgArray());
		} else if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// add user-role information to temporary table 'SYS_USER_ROLE_TMP'
			addUserRole(userId, form.getRoleArray());
		} else {
			// add user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF_TMP'
			addUserOrgRole(userId, orgMap, form.getRoleOrgArray());
			
			// add user-role information to temporary table 'SYS_USER_ROLE_TMP'
			addUserRole(userId, form.getRoleArray());
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 新增用户信息(为新增)
	 * @param temp
	 */
	private void addUser(UserInfTmp temp) {
		ResultMessages messages = ResultMessages.error();
		
		// 1) Whether the user is existed or not
		UserInf user = userInfRepository.queryUser(temp.getUserid());
		if (null == user) {
			log.debug("Whether the user is existed or not, userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
			
			// 2) 查询用户临时表是否存在相同未授权记录
			int ret = userInfTmpRepository.queryUserInfo(temp);
			if (ret == 0) {
				log.debug("The user had been added in temporary table and not be approved, userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
				
				// 3) add user in the user temporary table
				temp.setId(numberService.getSysIDSequence("0000", 8));
				temp.setCreater(ContextConst.getCurrentUser().getUserid());
				temp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				temp.setOpttype(CommonConst.OPER_TYPE_ADD);	
				temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
				temp.setLoginorg(StringUtil.trim(temp.getLoginorg()));
				ret = userInfTmpRepository.insertUserInfTmp(temp);
				if (ret != 1) { // 3) failure
					log.error("[e.sm.4029] Add the user failure, userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
				
				log.info("Add the user successfully, userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
			} else { // 2) failure
				log.error("[e.sm.4034] The user had been added in temporary table and not be approved, userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		} else { // 1) failure
			log.error("[e.sm.4033] The user had been added, , userId=[" + temp.getUserid() + "] userName=[" + temp.getUsername() + "]");
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 新增用户机构信息(为新增)
	 * @param userOrgInfList
	 * @param userId
	 * @return
	 * 		用户选择的机构信息Map
	 */
	private Map<String, OrgInf> addUserOrg(List<String> userOrgInfList, String userId) {

		ResultMessages messages = ResultMessages.error();
		UserOrgInf userOrgInf = new UserOrgInf();
		Map<String, OrgInf> orgMap = new HashMap<String, OrgInf>();
		
		for (int i = 0; i < userOrgInfList.size(); i++) {
			String orgId = StringUtil.trim(userOrgInfList.get(i));

			// 1) Whether the orgnization is existed or not
			OrgInf orgInf = orgInfRepository.query(orgId);
			if (null != orgInf) {
				log.debug("The orgnization is existed, orgId=[" + orgId + "]");
				orgMap.put(orgInf.getOrgid().trim(), orgInf);
				
				// 2) Whether the user orgnization is existed or not
				userOrgInf.setUserid(userId);
				userOrgInf.setOrgid(orgId);
				UserOrgInf result = userOrgInfRepository.queryUserOrgInf(userOrgInf);
				if (null == result) {
					log.debug("The user orgnization is not existed, userId=[" + userId + "] orgId=[" + orgId + "]");
					
					// 3) 查询用户机构临时表中是否存在相同未授权记录
					UserOrgInfTmp temp = new UserOrgInfTmp();
					temp.setUserid(userId.trim());
					temp.setOrgid(orgId.trim());
					int ret = userOrgInfTmpRepository.queryUserOrgInfTmpCnt(temp);
					if (ret == 0) {
						log.debug("The user orgnization is existed in the temporary table, userId=[" + userId + "] orgId=[" + orgId + "]");

						// 4) insert the orgnization information into the temporary table
						temp.setOrgname(orgInf.getOrgname());
						temp.setId(numberService.getSysIDSequence("0000",8));
						temp.setCreater(ContextConst.getCurrentUser().getUserid());
						temp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						temp.setOpttype(CommonConst.OPER_TYPE_ADD);	
						temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);	
						ret = userOrgInfTmpRepository.insertUserOrgInfTmp(temp);
						if (ret != 1) {
							log.error("[e.sm.4037] Add the user orgnization failure, userId=[" + userId + "] orgId=[" + orgId + "]");
							messages.add("e.sm.4037");
							throw new BusinessException(messages);
						}
						
						log.info("Add the user orgnization successfully, userId=[" + userId + "] orgId=[" + orgId + "]");
					} else { // 3) failure
						log.error("[e.sm.4038] The user orgnization is existed in the temporary table and cannot be added again, userId=[" + userId + "] orgId=[" + orgId + "]");
						messages.add("e.sm.4038");
						throw new BusinessException(messages);
					}
				} else { // 2) failure
					log.error("[e.sm.4039] The user orgnization is existed and cannot be added again, userId=[" + userId + "] orgId=[" + orgId + "]");
					messages.add("e.sm.4039");
					throw new BusinessException(messages);
				}
			} else { // 1) failure
				log.error("[e.sm.4039] The orgnization is not existed, orgId=[" + orgId + "]");
				messages.add("e.sm.4039");
				throw new BusinessException(messages);
			}
		}
		return orgMap;
	}
	
	/**
	 * yyw updated
	 * 
	 * 新增用户机构角色信息(为新增)
	 * @param userId
	 * @param orgMap
	 * @param roleOrgArray
	 */
	private void addUserOrgRole(String userId, Map<String, OrgInf> orgMap, String[] roleOrgArray) {

		ResultMessages messages = ResultMessages.error();

		for(int i = 0 ; i < roleOrgArray.length;i++){
			String[] roleOrgArr = roleOrgArray[i].split("_");
			String orgId = roleOrgArr[0].trim();
			String roleId = roleOrgArr[1].trim();

			// get orgnazition information
			OrgInf orgInf = orgMap.get(orgId);
			// get role information
			RoleInf roleInf = roleInfRepository.query(roleId);
			
			// 1) Whether the user-org-role information is existed or not in table 'SYS_USER_ROLE_INF'
			UserRoleInf uor = new UserRoleInf();
			uor.setUserid(userId);
			uor.setOrgid(orgId);
			uor.setRoleid(roleId);
			UserRoleInf info = userRoleInfRepository.query(uor);
			if (null == info) {
				log.debug("The user-org-role information is not existed, userId=[" + userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
				
				// 2) 查询临时表中是否有待授权记录，无记录可新增
				UserRoleInfTmp temp = new UserRoleInfTmp();
				temp.setUserid(userId);
				temp.setRoleid(roleId);
				temp.setOrgid(orgId);
				temp.setOrgname(orgInf.getOrgname());
				int ret = userRoleInfTmpRepository.queryUserRoleInfo(temp);
				if (ret == 0) {
					log.debug("The user-org-role information is not existed in temporary table 'USERROLEINFTMP', userId=[" 
							+ userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
					
					// add information in the temporary table 'SYS_USER_ROLE_INF_TMP'
					temp.setId(numberService.getSysIDSequence("0000",8));
					temp.setRolename(roleInf.getRolename());
					temp.setCreater(ContextConst.getCurrentUser().getUserid());
					temp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					temp.setOpttype(CommonConst.OPER_TYPE_ADD);		
					temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);	
					ret = userRoleInfTmpRepository.insertUserRoleInfTmp(temp);
					if (ret != 1) {
						log.error("[e.sm.4022] Add user-org-role information into table 'USERROLEINFTMP' failure, userId=[" 
								+ userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
						messages.add("e.sm.4022");
						throw new BusinessException(messages);
					}
					
					log.info("Add user-org-role information into table 'USERROLEINFTMP' successfully, userId=[" 
								+ userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
				} else { // 2) failure
					log.error("[e.sm.4043] The user-org-role information is existed in temporary table 'USERROLEINFTMP' and cannot be added again, userId=[" 
							+ userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
					messages.add("e.sm.4043");
					throw new BusinessException(messages);
				}
			} else { // 1) failure
				log.error("[e.sm.4044] The user-org-role information is existed in table 'USERROLEINF' and cannot be added again, userId=[" 
						+ userId + "] orgId=[" + orgId + "] roleId=[" + roleId + "]");
				messages.add("e.sm.4044");
				throw new BusinessException(messages);
			}
			
		}
	}

	/**
	 * yyw updated
	 * 
	 * 新增用户角色信息(为新增)
	 * @param userId
	 * @param roleArray
	 */
	private void addUserRole(String userId, String[] roleArray) {

		ResultMessages messages = ResultMessages.error();

		for(int i = 0 ; i < roleArray.length;i++){
			String roleId = roleArray[i];

			// get role information
			RoleInf roleInf = roleInfRepository.query(roleId);
			
			// 1) Whether the user-role information is existed or not in table 'SYS_USER_ROLE'
			SysUserRole sur = new SysUserRole();
			sur.setUserid(userId);
			sur.setRoleid(roleId);
			SysUserRole info = sysUserRoleRepository.query(sur); 
			if (null == info) {
				log.debug("The user-role information is not existed, userId=[" + userId + "] roleId=[" + roleId + "]");
				
				// 2) 查询临时表中是否有待授权记录，无记录可新增
				SysUserRoleTmp temp = new SysUserRoleTmp();
				temp.setUserid(userId);
				temp.setRoleid(roleId);
				int ret = sysUserRoleTmpRepository.queryCount(temp); 
				if (ret == 0) {
					log.debug("The user-role information is not existed in temporary table 'SYS_USER_ROLE_TMP', userId=[" 
							+ userId + "] roleId=[" + roleId + "]");
					
					// add information in the temporary table 'SYS_USER_ROLE_TMP'
					temp.setId(numberService.getSysIDSequence("0000",8));
					temp.setRolename(roleInf.getRolename());
					temp.setCreater(ContextConst.getCurrentUser().getUserid());
					temp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					temp.setOpttype(CommonConst.OPER_TYPE_ADD);		
					temp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);		
					ret = sysUserRoleTmpRepository.insert(temp);
					if (ret != 1) {
						log.error("[e.sm.4022] Add user-role information into table 'SYS_USER_ROLE_TMP' failure, userId=[" 
								+ userId + "] roleId=[" + roleId + "]");
						messages.add("e.sm.4022");
						throw new BusinessException(messages);
					}
					
					log.info("Add user-role information into table 'SYS_USER_ROLE_TMP' successfully, userId=[" 
								+ userId + "] roleId=[" + roleId + "]");
				} else { // 2) failure
					log.error("[e.sm.4043] The user-role information is existed in temporary table 'SYS_USER_ROLE_TMP' and cannot be added again, userId=[" 
							+ userId + "] roleId=[" + roleId + "]");
					messages.add("e.sm.4043");
					throw new BusinessException(messages);
				}
			} else { // 1) failure
				log.error("[e.sm.4044] The user-role information is existed in table 'SYS_USER_ROLE' and cannot be added again, userId=[" 
						+ userId + "] roleId=[" + roleId + "]");
				messages.add("e.sm.4044");
				throw new BusinessException(messages);
			}
			
		}
	}

	
	/** ======================================= update and delete ======================================= */
	@Override
	@Transactional
	public void transUpdateByMode(UserInfForm form, String optType) {
		
		String roleMode = StringUtil.trim(form.getRoleMode());
		
		// add user information to temporary table 'SYS_USER_INF_TMP'
		updateUser(form, optType);
		
		// add user-orgnization information to temporary table 'SYS_USER_ORG_INF_TMP'
		updateUserOrg(form, optType);

		if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// add user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF_TMP'
			updateUserOrgRole(form, optType);
		} else if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// add user-role information to temporary table 'SYS_USER_ROLE_TMP'
			updateUserRole(form, optType);
		} else {
			// add user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF_TMP'
			updateUserOrgRole(form, optType);
			
			// add user-role information to temporary table 'SYS_USER_ROLE_TMP'
			updateUserRole(form, optType);
		}
	}

	/**
	 * yyw updated
	 * 
	 * 更新用户信息(为修改)
	 * @param form
	 * @param optType
	 */
	private void updateUser(UserInfForm form, String optType) {
		ResultMessages messages = ResultMessages.error();
		UserInf userInf = form.getUserInf();
		String userId = StringUtil.trim(userInf.getUserid());
		
		// 1) 判断主表中是否有记录
		UserInf  oldUserInf = userInfRepository.queryUser(userId);
		if (null != oldUserInf) {
			// 2) 判断临时表中是否存在相同待授权记录
			UserInfTmp userInfTmp = new UserInfTmp();
			userInfTmp.setUserid(userId);
			int ret = userInfTmpRepository.queryUserInfo(userInfTmp);
			if (ret == 0) {
				userInfTmp.setId(numberService.getSysIDSequence("0000",8));
				userInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOpttype(optType);
				userInfTmp.setOptstatus("01");
				// 删除，从原记录信息中直接获取值
				if (optType.equals("03")) {
					userInfTmp.setUserid(oldUserInf.getUserid());
					userInfTmp.setUsername(oldUserInf.getUsername());
					userInfTmp.setAuthamt(oldUserInf.getAuthamt());
					userInfTmp.setCreateorg(oldUserInf.getCreateorg());
					userInfTmp.setUserstatus(oldUserInf.getUserstatus());
					userInfTmp.setLoginorg(oldUserInf.getLoginorg());
				// 修改，从界面上获取值
				} else if(optType.equals("02")) {
					userInfTmp.setUserid(userInf.getUserid());
					userInfTmp.setUsername(form.getUsername());
					userInfTmp.setCreateorg(userInf.getCreateorg());
					userInfTmp.setUserstatus(form.getUserstatus());
					userInfTmp.setLoginorg(StringUtil.trim(form.getLoginorg()));
				// 维护类型处理异常处理
				}else{
					messages.add("e.sm.4011");
					throw new BusinessException(messages);
				}
				
				// 3) 插入临时表
				ret = userInfTmpRepository.insertUserInfTmp(userInfTmp);
				if (1 == ret) {
					// 4) 更新主表信息-状态信息
					UserInf userInfTemp = new UserInf();
					userInfTemp.setUserid(userId);
					userInfTemp.setOptstatus("01");
					ret = userInfRepository.updateUser(userInfTemp);
					if(ret == 1) {
						log.info("Add the user information in table 'SYS_USER_INF_TMP' successfully, userId=[" + userId + "]");
					} else { // failure
						log.error("[e.sm.4030] Failed to update user information, userId=[" + userId + "]");
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				} else { // 3) failure
					log.error("[e.sm.4029] Failed to add user information into temporary table, userId=[" + userId + "]");
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
			} else { // 2) failure
				log.error("[e.sm.4034] The user information is existed in temporary table, userId=[" + userId + "]");
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		} else { // 1) failure
			log.error("[e.sm.4033] The user information is not existed, userId=[" + userId + "]");
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
	}

	/**
	 * yyw updated
	 * 
	 * 更新用户机构信息(为修改)
	 * @param form
	 * @param optType
	 */
	private void updateUserOrg(UserInfForm form, String optType) {
		ResultMessages messages = ResultMessages.error();

		String userId = StringUtil.trim(form.getUserInf().getUserid());
		List<String> userOrgInfList = form.getUserOrgInfArr();
		
		for (int i = 0; i < userOrgInfList.size(); i++) {
			String orgId = StringUtil.trim(userOrgInfList.get(i));
			
			// 1) 查询用户机构临时表中是否存在相同待授权记录
			UserOrgInfTmp userOrgInfTmp = new UserOrgInfTmp();
			userOrgInfTmp.setUserid(userId);
			userOrgInfTmp.setOrgid(orgId);
			int ret = userOrgInfTmpRepository.queryUserOrgInfTmpCnt(userOrgInfTmp);
			if (ret == 0) {
				// 下面3)处需要根据此结果进行不同逻辑处理
				UserOrgInf userOrgInf = new UserOrgInf();
				userOrgInf.setUserid(userId);
				userOrgInf.setOrgid(orgId);
				UserOrgInf userOrgInfResult = userOrgInfRepository.queryUserOrgInf(userOrgInf);
				
				// 2) the different way accroding to the value of 'optType'
				// 2.1) optType = 03 is 删除
				if (CommonConst.OPER_TYPE_DELETE.equals(optType)) {			
					// 3) 查询用户机构表中是否存在相同记录
					if (null != userOrgInfResult) {
						// 为下面4)处插入准备数据
						userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
						userOrgInfTmp.setOrgname(userOrgInfResult.getOrgname());
						userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
						userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						userOrgInfTmp.setOpttype(CommonConst.OPER_TYPE_DELETE);				
						userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);			
					} else { // 3) failure
						messages.add("e.sm.4037");
						throw new BusinessException(messages);
					}
				
				// 2.2) optType = 03 is 修改
				} else if (CommonConst.OPER_TYPE_UPDATE.equals(optType)) {		
					// 3) 查询用户机构表中是否存在相同记录
					// 3.1) Not Existed - Add (原信息中不存在，现在存在则新增原数据)
					if (null == userOrgInfResult) {
						OrgInf orgInf = orgInfRepository.query(StringUtil.trim(userOrgInfTmp.getOrgid())); 	// 查询机构信息，为获得机构名称

						// 为下面4)处插入准备数据
						userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
						userOrgInfTmp.setOrgname(orgInf.getOrgname());
						userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
						userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						userOrgInfTmp.setOpttype(CommonConst.OPER_TYPE_ADD);				
						userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);		
					
					// 3.2) 原信息中存在，现在存在，则不做处理
					} else {
						// not do anything
						continue;
					}
				}

				// 4) 插入用户机构临时表
				ret = userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp);
				if(ret != 1){
					log.error("[e.sm.4037] Add user-org information into temporary table failure, userId=[" + userId + "] orgId=[" + userOrgInfTmp.getOrgid() + "]");
					messages.add("e.sm.4037");
					throw new BusinessException(messages);
				}
			} else { // 2) failure
				log.error("[e.sm.4038] The user-org information is existed in temporary table, userId=[" + userId + "] orgId=[" + userOrgInfTmp.getOrgid() + "]");
				messages.add("e.sm.4038");
				throw new BusinessException(messages);
			}
		}
		
		// 3.3) 当操作类型为修改时，原信息中存在，现在不存在，则删除
		if (CommonConst.OPER_TYPE_UPDATE.equals(optType)) {	
			List<UserOrgInf> list = userOrgInfRepository.queryNotInList(userOrgInfList, userId);
			if (null != list && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					UserOrgInf userOrg = list.get(i);
					UserOrgInfTmp userOrgInfTmp = new UserOrgInfTmp();
					userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
					userOrgInfTmp.setUserid(userId);
					userOrgInfTmp.setOrgid(StringUtil.trim(userOrg.getOrgid()));
					userOrgInfTmp.setOrgname(userOrgInfTmp.getOrgname());
					userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
					userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userOrgInfTmp.setOpttype(CommonConst.OPER_TYPE_ADD);
					userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);	

					int ret = userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp);
					if(ret != 1){
						messages.add("e.sm.4037");
						throw new BusinessException(messages);
					}
				}
			}
		}
	}

	/**
	 * yyw updated
	 * 
	 * 更新用户机构角色信息(为修改)
	 * @param form
	 * @param optType
	 */
	private void updateUserOrgRole(UserInfForm form, String optType) {
		ResultMessages messages = ResultMessages.error();
		String userId = StringUtil.trim(form.getUserInf().getUserid());
		
		// optType = 03 is 修改
		if(optType.equals(CommonConst.OPER_TYPE_UPDATE)){
			//修改操作
			String[] roleOrgArray = form.getRoleOrgArray();				// selected org-role information
			List<String> roleOrgList = new ArrayList<String>(); 
			if (roleOrgArray != null &&  roleOrgArray.length > 0) {
				for (int i = 0; i < roleOrgArray.length; i++) {
					String[] roleOrg = roleOrgArray[i].split("_");
					String orgId = roleOrg[0];
					String roleId = roleOrg[1];
					
					// 为下面3.3)查询数据准备List
					roleOrgList.add(roleOrgArray[i]);
					
					UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
					userRoleInfTmp.setUserid(form.getUserInf().getUserid());
					userRoleInfTmp.setOrgid(orgId);
					userRoleInfTmp.setRoleid(roleId);

					// 1) 查询用户机构角色临时表中是否存在相同待授权记录
					int ret = userRoleInfTmpRepository.queryUserRoleInfo(userRoleInfTmp);
					if (ret == 0) {
						// 下面3)处需要根据此结果进行不同逻辑处理
						UserRoleInf userOrgRole = new UserRoleInf();
						userOrgRole.setUserid(userId);
						userOrgRole.setOrgid(orgId);
						userOrgRole.setRoleid(roleId);
						UserRoleInf userOrgRoleResult = userRoleInfRepository.query(userOrgRole);
						
						// 2) the different way accroding to the value of 'optType'
						// 2.1) optType = 03 is 删除
						if (optType.equals(CommonConst.OPER_TYPE_DELETE)) {			
							// 3) 查询用户机构角色表中是否存在相同记录
							if (null != userOrgRoleResult) {
								// 为下面4)处插入准备数据
								userRoleInfTmp.setId(numberService.getSysIDSequence("0000",8));
								userRoleInfTmp.setOrgname(userOrgRoleResult.getOrgname());
								userRoleInfTmp.setRolename(userOrgRoleResult.getRolename());
								userRoleInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
								userRoleInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								userRoleInfTmp.setOpttype(CommonConst.OPER_TYPE_DELETE);				
								userRoleInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);			
							} else { // 3) failure
								messages.add("e.sm.4037");
								throw new BusinessException(messages);
							}
						
						// 2.2) optType = 03 is 修改
						} else if (optType.equals(CommonConst.OPER_TYPE_UPDATE)) {		
							// 3) 查询用户机构角色表中是否存在相同记录
							// 3.1) Not Existed - Add (原信息中不存在，现在存在则新增原数据)
							if (null == userOrgRoleResult) {
								OrgInf orgInf = orgInfRepository.query(StringUtil.trim(userRoleInfTmp.getOrgid())); 	// 查询机构信息，为获得机构名称
								RoleInf roleInf = roleInfRepository.query(StringUtil.trim(userRoleInfTmp.getRoleid())); // 查询角色信息，为获得角色名称
								UserInf userInf = userInfRepository.queryUser(StringUtil.trim(userRoleInfTmp.getUserid())); // 查询用户信息，为获得用户名称

								// 为下面4)处插入准备数据
								userRoleInfTmp.setId(numberService.getSysIDSequence("0000",8));
								userRoleInfTmp.setUsername(StringUtil.trim(userInf.getUsername()));
								userRoleInfTmp.setOrgname(StringUtil.trim(orgInf.getOrgname()));
								userRoleInfTmp.setRolename(StringUtil.trim(roleInf.getRolename()));
								userRoleInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
								userRoleInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								userRoleInfTmp.setOpttype(CommonConst.OPER_TYPE_ADD);				
								userRoleInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);		
							
							// 3.2) 原信息中存在，现在存在，则不做处理
							} else {
								// not do anything
								continue;
							}
						}

						// 4) 插入用户机构临时表
						ret = userRoleInfTmpRepository.insertUserRoleInfTmp(userRoleInfTmp);
						if(ret != 1){
							messages.add("e.sm.4037");
							throw new BusinessException(messages);
						}
					
					} else { // failure
						log.error("The user-org-role information is existed in temporay table, userId=[" + userId + "] orgId=[" 
								+ userRoleInfTmp.getOrgid() + "] roleId=[" + userRoleInfTmp.getRoleid() + "]");
						
					}
				}
			}

			// 3.3) 当操作类型为修改时，原信息中存在，现在不存在，则删除
			if (optType.equals(CommonConst.OPER_TYPE_UPDATE)) {
				List<UserRoleInf> list = userRoleInfRepository.queryNotInList(roleOrgList, userId);
				if (null != list && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						UserRoleInf userRole = list.get(i);
						UserRoleInfTmp userOrgRoleTmp = new UserRoleInfTmp();
						userOrgRoleTmp.setId(numberService.getSysIDSequence("0000",8));
						userOrgRoleTmp.setUserid(userId);
						userOrgRoleTmp.setOrgid(StringUtil.trim(userRole.getOrgid()));
						userOrgRoleTmp.setOrgname(userRole.getOrgname());
						userOrgRoleTmp.setRoleid(userRole.getRoleid());
						userOrgRoleTmp.setRolename(userRole.getRolename());
						userOrgRoleTmp.setCreater(ContextConst.getCurrentUser().getUserid());
						userOrgRoleTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						userOrgRoleTmp.setOpttype(CommonConst.OPER_TYPE_ADD);
						userOrgRoleTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);	

						int ret = userRoleInfTmpRepository.insertUserRoleInfTmp(userOrgRoleTmp);
						if(ret != 1){
							messages.add("e.sm.4037");
							throw new BusinessException(messages);
						}
					}
				}
			}
		}
	}

	/**
	 * yyw updated
	 * 
	 * 更新用户角色信息(为修改)
	 * @param form
	 * @param optType
	 */
	private void updateUserRole(UserInfForm form, String optType) {
		ResultMessages messages = ResultMessages.error();
		String userId = StringUtil.trim(form.getUserInf().getUserid());
		
		String[] roleArray = form.getRoleArray();		// selected role information
		if (roleArray != null &&  roleArray.length > 0) {
			for (int i = 0; i < roleArray.length; i++) {
				String roleId = roleArray[i];
				
				SysUserRoleTmp sysUserRoleTmp = new SysUserRoleTmp();
				sysUserRoleTmp.setUserid(StringUtil.trim(userId));
				sysUserRoleTmp.setRoleid(StringUtil.trim(roleId));

				// 1) 查询用户角色临时表中是否存在相同待授权记录
				int ret = sysUserRoleTmpRepository.queryCount(sysUserRoleTmp);
				if (ret == 0) {
					// 下面3)处需要根据此结果进行不同逻辑处理
					SysUserRole userRole = new SysUserRole();
					userRole.setUserid(StringUtil.trim(userId));
					userRole.setRoleid(StringUtil.trim(roleId));
					SysUserRole userRoleResult = sysUserRoleRepository.query(userRole);
					
					// 2) the different way accroding to the value of 'optType'
					// 2.1) optType = 03 is 删除
					if (CommonConst.OPER_TYPE_DELETE.equals(optType)) {			
						// 3) 查询用户角色表中是否存在相同记录
						if (null != userRoleResult) {
							// 为下面4)处插入准备数据
							sysUserRoleTmp.setId(numberService.getSysIDSequence("0000",8));
							sysUserRoleTmp.setRolename(userRoleResult.getRolename());
							sysUserRoleTmp.setCreater(ContextConst.getCurrentUser().getUserid());
							sysUserRoleTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							sysUserRoleTmp.setOpttype(CommonConst.OPER_TYPE_DELETE);				
							sysUserRoleTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);			
						} else { // 3) failure
							messages.add("e.sm.4037");
							throw new BusinessException(messages);
						}
					
					// 2.2) optType = 03 is 修改
					} else if (CommonConst.OPER_TYPE_UPDATE.equals(optType)) {		
						// 3) 查询用户角色表中是否存在相同记录
						// 3.1) Not Existed - Add (原信息中不存在，现在存在则新增原数据)
						if (null == userRoleResult) {
							RoleInf roleInf = roleInfRepository.query(StringUtil.trim(sysUserRoleTmp.getRoleid())); // 查询角色信息，为获得角色名称

							// 为下面4)处插入准备数据
							sysUserRoleTmp.setId(numberService.getSysIDSequence("0000",8));
							sysUserRoleTmp.setRolename(roleInf.getRolename());
							sysUserRoleTmp.setCreater(ContextConst.getCurrentUser().getUserid());
							sysUserRoleTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							sysUserRoleTmp.setOpttype(CommonConst.OPER_TYPE_ADD);				
							sysUserRoleTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);		
						
						// 3.2) 原信息中存在，现在存在，则不做处理
						} else {
							// not do anything
							continue;
						}
					}

					// 4) 插入用户机构临时表
					ret = sysUserRoleTmpRepository.insert(sysUserRoleTmp);
					if(ret != 1){
						messages.add("e.sm.4037");
						throw new BusinessException(messages);
					}
				
				} else { // failure
					log.error("The user-org-role information is existed in temporay table, userId=[" + userId + "] roleId=[" + sysUserRoleTmp.getRoleid() + "]");
					
				}
			}
		}

		// 3.3) 当操作类型为修改时，原信息中存在，现在不存在，则删除
		if (CommonConst.OPER_TYPE_UPDATE.equals(optType)) {
			List<SysUserRole> list = sysUserRoleRepository.queryNotInlist(roleArray, userId);
			if (null != list && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					SysUserRole userRole = list.get(i);
					SysUserRoleTmp userRoleTmp = new SysUserRoleTmp();
					userRoleTmp.setId(numberService.getSysIDSequence("0000",8));
					userRoleTmp.setUserid(userId);
					userRoleTmp.setRoleid(userRole.getRoleid());
					userRoleTmp.setRolename(userRole.getRolename());
					userRoleTmp.setCreater(ContextConst.getCurrentUser().getUserid());
					userRoleTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userRoleTmp.setOpttype(CommonConst.OPER_TYPE_ADD);
					userRoleTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);	

					int ret = sysUserRoleTmpRepository.insert(userRoleTmp);
					if(ret != 1){
						messages.add("e.sm.4037");
						throw new BusinessException(messages);
					}
				}
			}
		}
	}
	
	
	/** ======================================= auth ======================================= */
	@Override
	@Transactional
	public void transAuthByMode(UserInfTmpForm form) {
		UserInfTmp userInfTmp = form.getUserInfTmp();
		String userId = userInfTmp.getUserid();

		String roleMode = StringUtil.trim(form.getRoleMode());
		
		// auth user information to table 'SYS_USER_INF'
		authUser(userInfTmp);
		
		// auth user-orgnization information to table 'SYS_USER_ORG_INF'
		authUserOrg(userId);

		if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// auth user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF'
			authUserOrgRole(userId);
		} else if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// userId); user-role information to temporary table 'SYS_USER_ROLE'
			authUserRole(userId);
		} else {
			// add user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF'
			authUserOrgRole(userId);
			
			// userId); user-role information to temporary table 'SYS_USER_ROLE'
			authUserRole(userId);
		}
		
	}

	/**
	 * yyw updated
	 * 
	 * 授权用户信息(为审核)
	 * @param userInfTmp
	 */
	private void authUser(UserInfTmp userInfTmp) {
		ResultMessages messages = ResultMessages.error();
		String userId = userInfTmp.getUserid();
		
		userInfTmp = userInfTmpRepository.query(userInfTmp);
		
		// 1) 临时表中存在记录
		if (null != userInfTmp) {
			// 2) 录入操作员与授权/拒绝操作员是否为同一人判断
			if (CommonUtil.compareTlr(userInfTmp.getCreater()) == 1) {
				String optType = userInfTmp.getOpttype();
				
				// 3) data process by different way
				// 3.1) optType == 01 (Add)
				if (CommonConst.OPER_TYPE_ADD.equals(optType)) {
					UserInf userInf = setValueToUserInf(userInfTmp);
					userInf.setUserstatus(CommonConst.USER_STATUS_NORMAL);	// 01-正常
					userInf.setStatus(CommonConst.LOGIN_STATUS_NOT_LOGIN);	// 01-未登录
					userInf.setPassword(CommonConst.PWD);					// 设置用户默认密码
					userInf.setPwdchangeuser(ContextConst.getCurrentUser().getUserid());
					userInf.setPwdchangetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userInf.setCreater(StringUtil.trim(userInfTmp.getCreater()));
					userInf.setCreatetime(StringUtil.trim(userInfTmp.getCreatetime()));
					// 4) 插入主表信息
					int ret = userInfRepository.insertUserInf(userInf);
					if (ret != 1) {
						log.error("[e.sm.4028] Add new user failure, userId=[" + userInf.getUserid() + "]");
						messages.add("e.sm.4028");
						throw new BusinessException(messages);
					}

				// 3.2) optType == 02 (Update)
				} else if (CommonConst.OPER_TYPE_UPDATE.equals(optType)) {
					UserInf userInf = setValueToUserInf(userInfTmp);
					userInf.setLastoperator(userInfTmp.getCreater());
					userInf.setLastopttime(userInfTmp.getCreatetime());
					// 4) 更新主表信息
					if (userInfRepository.updateUser(userInf)!=1) {
						log.error("[e.sm.4030] Update user failure, userId=[" + userInf.getUserid() + "]");
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}

				// 3.3) optType == 03 (Delete)
				} else {
					// 4) 删除主表信息
					UserInf userInf = new UserInf();
					userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					if (userInfRepository.deleteUserInf(userInf) != 1) {
						log.error("[e.sm.4030] Delete user failure, userId=[" + userInf.getUserid() + "]");
						messages.add("e.sm.4032");
						throw new BusinessException(messages);
					}
				}
				
				// 5) 临时表更新处理
				userInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOptstatus("02");	// TODO
				int ret = userInfTmpRepository.updateUserInfTmp(userInfTmp);
				if (ret != 1) {
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
			} else { // 2) failure
				log.error("The creater is the same as author, creater=[" + userInfTmp.getCreater() + "]");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else { // 1) failure
			log.error("The user information is not existed, userId=[" + userId + "]");
			messages.add("e.sm.4036");
			throw new BusinessException(messages);
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 授权用户机构信息(为审核)
	 * @param userId
	 */
	private void authUserOrg(String userId) {
		ResultMessages messages = ResultMessages.error();

		// 1) 得到用户机构 - 合并后
		UserOrgInfTmp userOrgInfTmp = new UserOrgInfTmp();
		userOrgInfTmp.setUserid(StringUtil.trim(userId));
		userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
		List<UserOrgInfTmp> list = userOrgInfService.transQueryUserOrgInfMerge(userOrgInfTmp);
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				userOrgInfTmp = list.get(i);
				String optType = userOrgInfTmp.getOpttype();

				// 2) data process by different way
				// 2.1) optType == 01 (Add)
				if (CommonConst.OPER_TYPE_ADD.equals(optType)) {
					UserOrgInf userOrgInf = new UserOrgInf();
					userOrgInf.setUserid(userOrgInfTmp.getUserid());
					userOrgInf.setOrgid(userOrgInfTmp.getOrgid());
					userOrgInf.setOrgname(userOrgInfTmp.getOrgname());
					userOrgInf.setCreater(userOrgInfTmp.getCreater());
					userOrgInf.setCreatetime(userOrgInfTmp.getCreatetime());
					userOrgInf.setChecker(ContextConst.getCurrentUser().getUserid());
					userOrgInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					// 3) 插入
					if (userOrgInfRepository.insertUserOrgInf(userOrgInf) != 1) {
						messages.add("e.sm.4040");
						throw new BusinessException(messages);
					}
					
				// 2.2) optType == 03 (Delete)
				}else if(optType.equals("03")){
					UserOrgInf userOrgInf = new UserOrgInf();
					userOrgInf.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
					userOrgInf.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
					// 3) 删除
					if (userOrgInfRepository.deleteUserOrgInf(userOrgInf) != 1) {
						messages.add("e.sm.4041");
						throw new BusinessException(messages);
					}
					
				// 2.3) 维护类型错误
				}else{
					//messages.add("e.sm.4010");
					//throw new BusinessException(messages);
				}
				
				// 4) 更新用户机构临时表信息
				if (!CommonConst.OPER_TYPE_UPDATE.equals(optType)) {
					userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
					userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userOrgInfTmp.setOptstatus(CommonConst.OPTSTATUS_NORMAL);
					int ret = userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp);
					if (ret != 1) {
						messages.add("e.sm.4042");
						throw new BusinessException(messages);
					}
				}
			}
		}
	}

	/**
	 * yyw updated
	 * 
	 * 授权用户机构角色信息(为审核)
	 * @param userId
	 */
	private void authUserOrgRole(String userId) {
		ResultMessages messages = ResultMessages.error();
		
		// 1) 得到用户机构角色 - 合并后
		UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
		userRoleInfTmp.setUserid(StringUtil.trim(userId));
		userRoleInfTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
		List<UserRoleInfTmp> list = userRoleInfRepository.queryRoleListMerge(userRoleInfTmp);
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				UserRoleInfTmp temp = list.get(i);
				
				String optType = temp.getOpttype();

				// 2) data process by different way
				// 2.1) optType == 01 (Add)
				if (CommonConst.OPER_TYPE_ADD.equals(optType)) {
					UserRoleInf userRoleInf = setValueToUserRoleInf(temp);
					int ret = userRoleInfRepository.insertUserRoleInf(userRoleInf);
					if (ret != 1) {
						messages.add("e.sm.4021");
						throw new BusinessException(messages);
					}
					
				// 2.2) optType == 03 (Delete)
				} else if(CommonConst.OPER_TYPE_DELETE.equals(optType)){
					UserRoleInf userRoleInf = new UserRoleInf();
					userRoleInf.setUserid(StringUtil.trim(temp.getUserid()));
					userRoleInf.setOrgid(StringUtil.trim(temp.getOrgid()));
					userRoleInf.setRoleid(StringUtil.trim(temp.getRoleid()));
					int ret = userRoleInfRepository.deleteUserRoleInf(userRoleInf);
					if (ret != 1) {
						messages.add("e.sm.4025");
						throw new BusinessException(messages);
					}
				
				// 2.2) optType == 02 (Update)，修改主表中记录状态
				} else if(CommonConst.OPER_TYPE_UPDATE.equals(optType)){
					UserRoleInf userRoleInf = new UserRoleInf();
					userRoleInf.setUserid(StringUtil.trim(temp.getUserid()));
					userRoleInf.setOrgid(StringUtil.trim(temp.getOrgid()));
					userRoleInf.setRoleid(StringUtil.trim(temp.getRoleid()));
					userRoleInf.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
					int ret = userRoleInfRepository.updateUserRoleInf(userRoleInf);
					if (ret != 1) {
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				}
				
				// 3) 临时表更新处理
				if(!CommonConst.OPER_TYPE_UPDATE.equals(optType)) {
					temp.setChecker(ContextConst.getCurrentUser().getUserid());
					temp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					temp.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
					if(1!=userRoleInfTmpRepository.updateUserRoleInfTmp(temp)){
						messages.add("e.sm.4024");
						throw new BusinessException(messages);
					}
				}
			}
		}
	}

	/**
	 * yyw updated
	 * 
	 * 授权用户角色信息(为审核)
	 * @param userId
	 */
	private void authUserRole(String userId) {
		ResultMessages messages = ResultMessages.error();
		
		// 1) 得到用户角色 - 合并后
		SysUserRoleTmp sysUserRoleTmp = new SysUserRoleTmp();
		sysUserRoleTmp.setUserid(StringUtil.trim(userId));
		sysUserRoleTmp.setOptstatus(CommonConst.OPTSTATUS_WAITAUDIT);
		List<SysUserRoleTmp> list = sysUserRoleRepository.queryRoleListMerge(sysUserRoleTmp);
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				SysUserRoleTmp temp = list.get(i);
				
				String optType = temp.getOpttype();

				// 3) data process by different way
				// 3.1) optType == 01 (Add)
				if (CommonConst.OPER_TYPE_ADD.equals(optType)) {
					SysUserRole sysUserRole = setValueToSysUserRoleInf(temp);
					int ret = sysUserRoleRepository.insert(sysUserRole);
					if (ret != 1) {
						messages.add("e.sm.4021");
						throw new BusinessException(messages);
					}
					
				// 3.2) optType == 03 (Delete)
				} else if(CommonConst.OPER_TYPE_DELETE.equals(optType)){
					SysUserRole sysUserRole = new SysUserRole();
					sysUserRole.setUserid(StringUtil.trim(temp.getUserid()));
					sysUserRole.setRoleid(StringUtil.trim(temp.getRoleid()));
					int ret = sysUserRoleRepository.delete(sysUserRole);
					if (ret != 1) {
						messages.add("e.sm.4025");
						throw new BusinessException(messages);
					}
				
				// 3.2) optType == 02 (Update)，修改主表中记录状态
				} else if(CommonConst.OPER_TYPE_UPDATE.equals(optType)){
					SysUserRole sysUserRole = new SysUserRole();
					sysUserRole.setUserid(StringUtil.trim(temp.getUserid()));
					sysUserRole.setRoleid(StringUtil.trim(temp.getRoleid()));
					sysUserRole.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
					int ret = sysUserRoleRepository.update(sysUserRole);
					if (ret != 1) {
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				}
				
				// 4) 临时表更新处理
				temp.setChecker(ContextConst.getCurrentUser().getUserid());
				temp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				temp.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
				int ret = sysUserRoleTmpRepository.update(temp);
				if (ret != 1) {
					messages.add("e.sm.4024");
					throw new BusinessException(messages);
				}
			}
		}
	}
	
	
	/** ======================================= reject ======================================= */
	@Override
	@Transactional
	public void transRejectByMode(UserInfTmpForm form) {
		UserInfTmp userInfTmp = form.getUserInfTmp();
		String userId = userInfTmp.getUserid();

		String roleMode = StringUtil.trim(form.getRoleMode());
		
		// reject user information to table 'SYS_USER_INF'
		UserInfTmp userTmp = rejectUser(form);
		
		// reject user-orgnization information to table 'SYS_USER_ORG_INF'
		rejectUserOrg(userTmp);

		if (roleMode.equals(CommonConst.ROLE_MODE_BIND_ORG)) {
			// reject user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF'
			rejectUserOrgRole(userId);
		} else if (roleMode.equals(CommonConst.ROLE_MODE_UNBIND_ORG)) {
			// reject user-role information to temporary table 'SYS_USER_ROLE'
			rejectUserRole(userId);
		} else {
			// reject user-orgnization-role information to temporary table 'SYS_USER_ROLE_INF'
			rejectUserOrgRole(userId);
			
			// reject user-role information to temporary table 'SYS_USER_ROLE'
			rejectUserRole(userId);
		}
		
	}
	
	/**
	 * yyw updated
	 * 
	 * 拒绝用户信息(为审核)
	 * @param userInfTmp
	 */
	private UserInfTmp rejectUser(UserInfTmpForm form) {
		ResultMessages messages = ResultMessages.error();
		UserInfTmp userInfTmp = form.getUserInfTmp();
		
		// 1) Whether the user information is existed in temporary table
		UserInfTmp userInfTmpResult = userInfTmpRepository.query(userInfTmp);
		if (null != userInfTmpResult) {
			// 2) 录入操作员与授权/拒绝操作员是否为同一人判断
			if (CommonUtil.compareTlr(userInfTmpResult.getCreater()) == 1) {
				String optType = userInfTmpResult.getOpttype();
				
				// 3) 更新、删除时修改主表信息
				if(CommonConst.OPER_TYPE_UPDATE.equals(optType) || CommonConst.OPER_TYPE_DELETE.equals(optType)) {
					UserInf userInf = new UserInf();
					userInf.setUserid(StringUtil.trim(userInfTmpResult.getUserid()));
					userInf.setLastoperator(userInfTmpResult.getCreater());
					userInf.setLastopttime(userInfTmpResult.getCreatetime());
					userInf.setOptstatus(CommonConst.OPTSTATUS_NORMAL);
					int ret = userInfRepository.updateUser(userInf);
					if (ret != 1) {
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				}
				// 4) 临时表信息更新
				userInfTmpResult.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmpResult.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmpResult.setOptstatus("03");   // TODO
				int ret = userInfTmpRepository.updateUserInfTmp(userInfTmpResult);
				if (ret != 1) { 
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
				
				return userInfTmpResult;
			} else { // 2) failure
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else { // 1) failure
			messages.add("e.sm.4036");
			throw new BusinessException(messages);
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 拒绝用户机构信息(为审核)
	 * @param userId
	 */
	private void rejectUserOrg(UserInfTmp userInfTmp) {
		ResultMessages messages = ResultMessages.error();

		// 1) 根据用户Id得到机构列表
		UserOrgInfTmp userOrgInfTmp = new UserOrgInfTmp();
		userOrgInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
		List<UserOrgInfTmp> list = userOrgInfService.transQueryUserOrgInfTmpList(userOrgInfTmp);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				userOrgInfTmp = new UserOrgInfTmp();
				userOrgInfTmp = list.get(i);
				userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userOrgInfTmp.setOptstatus("03");		// TODO
				userOrgInfTmp.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
				userOrgInfTmp.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
				int ret = userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp);
				if(ret != 1){
					messages.add("e.sm.4042");
					throw new BusinessException(messages);
				}
			}
		}else{
			if(!CommonConst.OPER_TYPE_UPDATE.equals(userInfTmp.getOpttype())){
				messages.add("e.sm.4046");
				throw new BusinessException(messages);
			}
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 拒绝用户机构角色信息(为审核)
	 * @param userId
	 */
	private void rejectUserOrgRole(String userId) {
		ResultMessages messages = ResultMessages.error();

		// 1) 根据用户Id及机构ID查询对应的角色ID列表
		UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
		userRoleInfTmp.setUserid(StringUtil.trim(userId));
		List<UserRoleInfTmp> list = userRoleInfTmpRepository.querylist(userRoleInfTmp);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UserRoleInfTmp tmp = list.get(i);
				// 2) 录入操作员与授权/拒绝操作员是否为同一人判断
				if (CommonUtil.compareTlr(tmp.getCreater()) == 1) {
					String optType = tmp.getOpttype();
					
					// 3) 当记录为删除时，将主表中状态更新回-02-正常
					if (CommonConst.OPER_TYPE_DELETE.equals(optType)) {
						UserRoleInf userRoleInf = new UserRoleInf();
						userRoleInf.setUserid(StringUtil.trim(tmp.getUserid()));
						userRoleInf.setOrgid(StringUtil.trim(tmp.getOrgid()));
						userRoleInf.setRoleid(StringUtil.trim(tmp.getRoleid()));
						userRoleInf.setLastoperator(tmp.getCreater());
						userRoleInf.setLastopttime(tmp.getCreatetime());
						userRoleInf.setOptstatus(CommonConst.OPTSTATUS_NORMAL);
						int ret = userRoleInfRepository.updateUserRoleInf(userRoleInf);
						if (ret != 1) {
							messages.add("e.sm.4023");
							throw new BusinessException(messages);
						}
					}
					// 4) 更新临时表记录
					tmp.setUserid(StringUtil.trim(tmp.getUserid()));
					tmp.setOrgid(StringUtil.trim(tmp.getOrgid()));
					tmp.setRoleid(StringUtil.trim(tmp.getRoleid()));
					tmp.setChecker(ContextConst.getCurrentUser().getUserid());
					tmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					tmp.setOptstatus("03");				// TODO
					int ret = userRoleInfTmpRepository.updateUserRoleInfTmp(tmp);
					if (ret != 1) {
						messages.add("e.sm.4024");
						throw new BusinessException(messages);
					}
				}else{ // 2) failure
					messages.add("e.cm.2003");
					throw new BusinessException(messages);
				}
			}
		}
	}
	
	/**
	 * yyw updated
	 * 
	 * 拒绝用户角色信息(为审核)
	 * @param userId
	 */
	private void rejectUserRole(String userId) {
		ResultMessages messages = ResultMessages.error();

		// 1) 根据用户Id及机构ID查询对应的角色ID列表
		SysUserRoleTmp sysUserRoleTmp = new SysUserRoleTmp();
		sysUserRoleTmp.setUserid(StringUtil.trim(userId));
		List<SysUserRoleTmp> list = sysUserRoleTmpRepository.querylist(sysUserRoleTmp);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysUserRoleTmp tmp = list.get(i);
				// 2) 录入操作员与授权/拒绝操作员是否为同一人判断
				if (CommonUtil.compareTlr(tmp.getCreater()) == 1) {
					String optType = tmp.getOpttype();
					
					// 3) 当记录为删除时，将主表中状态更新回-02-正常
					if (CommonConst.OPER_TYPE_DELETE.equals(optType)) {
						SysUserRole sysUserRole = new SysUserRole();
						sysUserRole.setUserid(StringUtil.trim(tmp.getUserid()));
						sysUserRole.setRoleid(StringUtil.trim(tmp.getRoleid()));
						sysUserRole.setLastoperator(tmp.getCreater());
						sysUserRole.setLastopttime(tmp.getCreatetime());
						sysUserRole.setOptstatus(CommonConst.OPTSTATUS_NORMAL);
						int ret = sysUserRoleRepository.update(sysUserRole);
						if (ret != 1) {
							messages.add("e.sm.4023");
							throw new BusinessException(messages);
						}
					}
					// 4) 更新临时表记录
					tmp.setUserid(StringUtil.trim(tmp.getUserid()));
					tmp.setRoleid(StringUtil.trim(tmp.getRoleid()));
					tmp.setChecker(ContextConst.getCurrentUser().getUserid());
					tmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					tmp.setOptstatus("03");				// TODO
					int ret = sysUserRoleTmpRepository.update(tmp);
					if (ret != 1) {
						messages.add("e.sm.4024");
						throw new BusinessException(messages);
					}
				}else{ // 2) failure
					messages.add("e.cm.2003");
					throw new BusinessException(messages);
				}
			}
		}
	}

	
	/** ======================================= set value ======================================= */
	/**
	 * 将临时表对象中数据设置到正式表对象
	 * @param userInfTmp
	 * @return
	 */
	private UserInf setValueToUserInf(UserInfTmp userInfTmp) {
		UserInf userInf = new UserInf();
		userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
		userInf.setUsername(StringUtil.trim(userInfTmp.getUsername()));
		userInf.setCreateorg(userInfTmp.getCreateorg());
		userInf.setLoginorg(StringUtil.trim(userInfTmp.getLoginorg()));
		userInf.setChecker(ContextConst.getCurrentUser().getUserid());
		userInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		userInf.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
		return userInf;
	}

	/**
	 * 将临时表对象中数据设置到正式表对象
	 * @param userInfTmp
	 * @return
	 */
	private UserRoleInf setValueToUserRoleInf(UserRoleInfTmp userRoleInfTmp){
		UserRoleInf userRoleInf = new UserRoleInf();
		userRoleInf.setUserid(userRoleInfTmp.getUserid());
		userRoleInf.setUsername(userRoleInfTmp.getUsername());
		userRoleInf.setOrgid(userRoleInfTmp.getOrgid());
		userRoleInf.setOrgname(userRoleInfTmp.getOrgname());
		userRoleInf.setRoleid(userRoleInfTmp.getRoleid());
		userRoleInf.setRolename(userRoleInfTmp.getRolename());
		userRoleInf.setCreater(userRoleInfTmp.getCreater());
		userRoleInf.setCreatetime(userRoleInfTmp.getCreatetime());
		userRoleInf.setChecker(ContextConst.getCurrentUser().getUserid());
		userRoleInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		userRoleInf.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
		return userRoleInf;
	}

	/**
	 * 将临时表对象中数据设置到正式表对象
	 * @param userInfTmp
	 * @return
	 */
	private SysUserRole setValueToSysUserRoleInf(SysUserRoleTmp sysUserRoleTmp){
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserid(sysUserRoleTmp.getUserid());
		sysUserRole.setUsername(sysUserRoleTmp.getUsername());
		sysUserRole.setRoleid(sysUserRoleTmp.getRoleid());
		sysUserRole.setRolename(sysUserRoleTmp.getRolename());
		sysUserRole.setCreater(sysUserRoleTmp.getCreater());
		sysUserRole.setCreatetime(sysUserRoleTmp.getCreatetime());
		sysUserRole.setChecker(ContextConst.getCurrentUser().getUserid());
		sysUserRole.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		sysUserRole.setOptstatus(CommonConst.OPER_TYPE_UPDATE);
		return sysUserRole;
	}
	
	
	
	/*****************************************用户新增临时start******************************************/
	
	
	@Override
	@Transactional
	public void transAdd1(UserInfTmpForm form){
		//用户机构新增操作
		addUserOrg(form);
		
		//用户角色新增操作
		Map<String, List<String>> orgRoleListMap = new HashMap<String, List<String>>();
		String[] roleOrgArray = form.getRoleOrgArray();
		for(int i = 0 ; i < roleOrgArray.length;i++){
			String roleOrg = roleOrgArray[i];
			String orgId = roleOrg.split("_")[0];
			String roleId = roleOrg.split("_")[1];
			if(orgRoleListMap.get(orgId)==null){
				List<String> listTemp = new ArrayList<String>();
				listTemp.add(roleId);
				orgRoleListMap.put(orgId, listTemp);
			}else{
				orgRoleListMap.get(orgId).add(roleId);
			}
		}
		// 遍历 机构对应的角色列表
		Set<String> set = orgRoleListMap.keySet();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String userId = StringUtil.trim(form.getUserid());
			String orgId =  StringUtil.trim((String) iterator.next());
			List<String> roleIdList = orgRoleListMap.get(orgId);
			addUserRole(userId,orgId,roleIdList);
		}
		//日志写入数据库
		UserInfTmp userInfTmp=form.getUserInfTmp();
		String userId = form.getUserid();
		userInfTmp.setUserid(userId);
		userInfTmp.setUsername(form.getUsername());
		userInfTmp.setLoginorg(form.getLoginorg());
		TlrLogPrint.tlrSysLogPrint("SM_User_Qry", CommonConst.DATA_LOG_OPERTYPE_ADD, 
				"" , generateUserInfTmpString(userInfTmp));
	}
	//用户机构新增操作
	public void addUserOrg(UserInfTmpForm form){

		ResultMessages messages = ResultMessages.error();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		String userId = form.getUserid();
		userInfTmp.setUserid(userId);
		userInfTmp.setUsername(form.getUsername());
//		//金额特殊处理
//		String amt=form.getAuthamt().replace(",", "");
//		if(amt.contains(".")){
//			userInfTmp.setAuthamt(Long.valueOf(amt.substring(0, amt.length()-3)));
//		}else{
//			userInfTmp.setAuthamt(Long.valueOf(amt));
//		}
		userInfTmp.setLoginorg(StringUtil.trim(form.getLoginorg()));
		//查询用户表是否有相同记录
		if (null==userInfRepository.queryUser(userId)) {
			//查询用户临时表是否存在相同未授权记录
			if (userInfTmpRepository.queryUserInfo(userInfTmp)==0) {
				userInfTmp.setId(numberService.getSysIDSequence("0000",8));
				userInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOpttype("01");
				userInfTmp.setOptstatus("01");
				//插入用户临时表
				if (1 == userInfTmpRepository.insertUserInfTmp(userInfTmp)) {
					//从表单中获取机构列表信息
					UserOrgInf userOrgInf =new UserOrgInf();
					List<String> userOrgInfList=form.getUserOrgInfArr();
					for (int i = 0; i < userOrgInfList.size(); i++) {
						String userOrgId=StringUtil.trim(userOrgInfList.get(i));
						userOrgInf.setUserid(userId);
						userOrgInf.setOrgid(userOrgId);
						OrgInf orgInf=orgInfRepository.query(userOrgId);
						//查询用户机构表中是否存在相同记录信息
						if(null==userOrgInfRepository.queryUserOrgInf(userOrgInf)){
							UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp.setUserid(userId);
							userOrgInfTmp.setOrgid(userOrgId);
							//查询用户机构临时表中是否存在相同未授权记录
							if (userOrgInfTmpRepository.queryUserOrgInfTmpCnt(userOrgInfTmp)==0) {
								userOrgInfTmp.setOrgname(orgInf.getOrgname());
								userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
								userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
								userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								userOrgInfTmp.setOpttype("01");
								userOrgInfTmp.setOptstatus("01");
								//插入用户机构临时表
								if (1 != userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp)) {
									//插入错误，异常处理
									messages.add("e.sm.4037");
									throw new BusinessException(messages);
								}
							//用户机构临时表存在相同未授权记录
							}else{
								messages.add("e.sm.4038");
								throw new BusinessException(messages);
							}
						//用户机构表存在相同记录
						}else{
							messages.add("e.sm.4039");
							throw new BusinessException(messages);
						}
					}
				//插入用户临时表错误
				} else {
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
			//用户临时表存在相同未授权记录
			}else{
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		//用户表存在相同记录
		}else{
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
	};
	//用户角色新增操作
	public void addUserRole(String userId,String userOrgId,List<String> userroleidlist){

		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp=new UserRoleInfTmp();
		UserRoleInf userRoleInf=new UserRoleInf();
		OrgRoleInf orgRoleInf=new OrgRoleInf();
		OrgRoleInfTmp orgRoleInfTmp=new OrgRoleInfTmp();
		OrgInf orgInf=orgInfRepository.query(userOrgId);
		userRoleInf.setUserid(userId);
		userRoleInf.setOrgid(userOrgId);
		userRoleInfTmp.setUserid(userId);
		userRoleInfTmp.setOrgid(userOrgId);
		userRoleInfTmp.setOrgname(orgInf.getOrgname());
		orgRoleInf.setOrgid(userOrgId);
		orgRoleInfTmp.setOrgid(userOrgId);
		//得到用户可操作角色列表值
		for(int i=0;i<userroleidlist.size();i++){
			String userRoleId=StringUtil.trim(userroleidlist.get(i));
			RoleInf roleInf=roleInfRepository.query(userRoleId);
			userRoleInf.setRoleid(userRoleId);
			userRoleInfTmp.setRoleid(userRoleId);
			//查询主表中是否有记录，无记录可新增
			if (null==userRoleInfRepository.query(userRoleInf)) {
				//查询临时表中是否有待授权记录，无记录可新增
				if (userRoleInfTmpRepository.queryUserRoleInfo(userRoleInfTmp)==0) {
					userRoleInfTmp.setId(numberService.getSysIDSequence("0000",8));
					userRoleInfTmp.setRolename(roleInf.getRolename());
					userRoleInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
					userRoleInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userRoleInfTmp.setOpttype("01");
					userRoleInfTmp.setOptstatus("01");
					//插入新增信息到临时表中
					if (1 != userRoleInfTmpRepository.insertUserRoleInfTmp(userRoleInfTmp)) {
						//插入数据库错误异常处理
						messages.add("e.sm.4022");
						throw new BusinessException(messages);
					}
				}else{
					//临时表中存在相同记录，异常处理
					messages.add("e.sm.4043");
					throw new BusinessException(messages);
				}
			}else{
				//主表中存在相同记录，异常处理
				messages.add("e.sm.4044");
				throw new BusinessException(messages);
			}
		}
	};
	/*****************************************用户新增临时end******************************************/
	@Override
	public UserInf transQueryUserInf(UserInf userInf) {
		return userInfRepository.queryUser(StringUtil.trim(userInf.getUserid()));
	}
	
	@Override
	@Transactional
	public int transUpdate(UserInfForm form,String opttype) {
		ResultMessages messages = ResultMessages.error();
		//从前台界面获取值
		UserInf userInf=form.getUserInf();
		String userId = StringUtil.trim(userInf.getUserid());
		//赋值临时表主键值
		UserInfTmp userInfTmp = new UserInfTmp();
		userInfTmp.setUserid(userId);
		//将原来的值做一份保留
		UserInf oldUserInf=new UserInf();
		oldUserInf=userInfRepository.queryUser(userId);
		//判断主表中是否存在相同记录
		if (null!=oldUserInf) {
			//判断临时表中是否存在相同待授权记录
			if (userInfTmpRepository.queryUserInfo(userInfTmp)==0) {
				userInfTmp.setId(numberService.getSysIDSequence("0000",8));
				//删除，从原记录信息中直接获取值
				if(opttype.equals("03")){
					userInfTmp.setUserid(oldUserInf.getUserid());
					userInfTmp.setUsername(oldUserInf.getUsername());
					userInfTmp.setAuthamt(oldUserInf.getAuthamt());
					userInfTmp.setCreateorg(oldUserInf.getCreateorg());
					userInfTmp.setUserstatus(oldUserInf.getUserstatus());
					userInfTmp.setLoginorg(oldUserInf.getLoginorg());
				//修改，从界面上获取值
				}else if(opttype.equals("02")){
					userInfTmp.setUserid(userInf.getUserid());
					userInfTmp.setUsername(form.getUsername());
//					//金额特殊处理
//					String amt=form.getAuthamt().replace(",", "");
//					userInfTmp.setAuthamt(Long.valueOf(amt.substring(0, amt.length()-3)));
					userInfTmp.setCreateorg(userInf.getCreateorg());
					userInfTmp.setUserstatus(form.getUserstatus());
					userInfTmp.setLoginorg(StringUtil.trim(form.getLoginorg()));
				//维护类型处理异常处理
				}else{
					messages.add("e.sm.4011");
					throw new BusinessException(messages);
				}
				//设置其他信息
				userInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOpttype(opttype);
				userInfTmp.setOptstatus("01");
				//插入临时表
				if (1 == userInfTmpRepository.insertUserInfTmp(userInfTmp)) {
					//更新主表信息-状态信息
					UserInf userInfTemp = new UserInf();
					userInfTemp.setUserid(userId);
					userInfTemp.setOptstatus("01");
					if(1==userInfRepository.updateUser(userInfTemp)){
						//用户机构信息处理
						UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
						UserOrgInf olduserOrgInf=new UserOrgInf();
						//根据用户id查询用户机构信息表中信息
						List<String> oldorgidlist=new ArrayList<String>();
						olduserOrgInf.setUserid(StringUtil.trim(form.getUserInf().getUserid()));
						List<UserOrgInf> olduserOrgInfList=userOrgInfRepository.queryUserOrgInfList(olduserOrgInf);
						for (int i = 0; i < olduserOrgInfList.size(); i++) {
							oldorgidlist.add(StringUtil.trim(olduserOrgInfList.get(i).getOrgid()));
						}
						List<UserOrgInfTmp> tmp=new ArrayList<UserOrgInfTmp>();
						//删除，查询原有机构信息并删除
						if(opttype.equals("03")){
							for (int i = 0; i < olduserOrgInfList.size(); i++) {
								olduserOrgInf = olduserOrgInfList.get(i);
								userOrgInfTmp=new UserOrgInfTmp();
								userOrgInfTmp.setOrgid(StringUtil.trim(olduserOrgInf.getOrgid()));
								userOrgInfTmp.setOpttype("03");
								tmp.add(userOrgInfTmp);
							}
						//修改，原信息中存在，现在不存在则删除原数据，
							//原信息中不存在，现在存在则新增原数据，
							//否则不做处理
						}else if(opttype.equals("02")){
							List<String> userOrgInfList=form.getUserOrgInfArr();
							List<String> userOrgListTmp=new ArrayList<String>();
							for (int j = 0; j <userOrgInfList.size(); j++) {
								//相同不做处理
								String userorg=StringUtil.trim(userOrgInfList.get(j));
								userOrgListTmp.add(userorg);
								if(oldorgidlist.contains(userorg)){
									oldorgidlist.remove(userorg);
									userOrgListTmp.remove(userorg);
								}
							}
							//原信息中存在，现在不存在则删除原数据，
							if(oldorgidlist.size()>0){
								for (int i = 0; i < oldorgidlist.size(); i++) {
									userOrgInfTmp=new UserOrgInfTmp();
									userOrgInfTmp.setOrgid(StringUtil.trim(oldorgidlist.get(i)));
									userOrgInfTmp.setOpttype("03");
									tmp.add(userOrgInfTmp);
								}
							}
							//原信息中不存在，现在存在则新增原数据，
							if(userOrgListTmp.size()>0){
								for (int j = 0; j < userOrgListTmp.size(); j++) {
									userOrgInfTmp=new UserOrgInfTmp();
									userOrgInfTmp.setOrgid(StringUtil.trim(userOrgListTmp.get(j)));
									userOrgInfTmp.setOpttype("01");
									tmp.add(userOrgInfTmp);
								}
							}
						}else{
							messages.add("e.sm.4011");
							throw new BusinessException(messages);
						}
						if(tmp.size()>0){
							for(int i=0;i<tmp.size();i++){
								userOrgInfTmp=new UserOrgInfTmp();
								userOrgInfTmp=tmp.get(i);
								userOrgInfTmp.setUserid(userId);
								UserOrgInf userOrgInf=new UserOrgInf();
								userOrgInf.setUserid(userId);
								String orgId=StringUtil.trim(userOrgInfTmp.getOrgid());
								userOrgInf.setOrgid(orgId);
								OrgInf orgInf=orgInfRepository.query(orgId);
								userOrgInf=userOrgInfRepository.queryUserOrgInf(userOrgInf);
								String optype=userOrgInfTmp.getOpttype();
								if(optype.equals("01")){
									if(userOrgInf!=null){
										messages.add("e.sm.4039");
										throw new BusinessException(messages);
									}
								}else{
									if (userOrgInf==null) {
										messages.add("e.sm.4045");
										throw new BusinessException(messages);
									}
								}
								//查询用户机构临时表中是否存在相同待授权记录
								if (userOrgInfTmpRepository.queryUserOrgInfTmpCnt(userOrgInfTmp)==0) {
									userOrgInfTmp.setOrgname(orgInf.getOrgname());
									userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
									userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
									userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
									userOrgInfTmp.setOptstatus("01");
									//插入用户机构临时表
									if(1!=userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp)){
										//异常处理
										messages.add("e.sm.4037");
										throw new BusinessException(messages);
									}
								//用户机构临时表中存在相同待授权记录
								}else{
									messages.add("e.sm.4038");
									throw new BusinessException(messages);
								}
							}
						}
					//更新用户表错误
					}else{
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				//插入临时表错误
				} else {
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
			//临时表中存在相同记录
			}else{
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		//主表中存在相同记录
		}else{
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
		return 0;
	}

	/*****************************************用户修改或删除临时start******************************************/
	@Override
	@Transactional
	public int transUpdate1(UserInfForm form,String opttype) {
		//修改前的数据
		String userId = StringUtil.trim(form.getUserInf().getUserid());
		UserInf oldUserInf =userInfRepository.queryUser(userId);
		//用户角色操作
		if(opttype == "02"){
			//修改操作
			Map<String, List<String>> orgRoleListMap = new HashMap<String, List<String>>();
			if( form.getRoleOrgArray()!=null &&  form.getRoleOrgArray().length>0){
				String[] roleOrgArray = form.getRoleOrgArray();
				for(int i = 0 ; i < roleOrgArray.length;i++){
					String roleOrg = roleOrgArray[i];
					String orgId = roleOrg.split("_")[0];
					String roleId = roleOrg.split("_")[1];
					if(orgRoleListMap.get(orgId)==null){
						List<String> listTemp = new ArrayList<String>();
						listTemp.add(roleId);
						orgRoleListMap.put(orgId, listTemp);
					}else{
						orgRoleListMap.get(orgId).add(roleId);
					}
				}
			}
			// 遍历 机构对应的角色列表
			if(orgRoleListMap.keySet()!=null && orgRoleListMap.keySet().size()>0){
				Set<String> set = orgRoleListMap.keySet();
				for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
					String orgId = (String) iterator.next();
					List<String> roleIdList = orgRoleListMap.get(orgId);
					UserRoleInfForm userRoleForm = new UserRoleInfForm();
					UserRoleInf userRoleInf = new UserRoleInf();
					userRoleInf.setUserid(form.getUserInf().getUserid());
					userRoleInf.setOrgid(orgId);
					userRoleForm.setUserRoleInf(userRoleInf);
					userRoleForm.setUserRoleInfArr(roleIdList);
					if(form.getUserSelectRoles().contains(orgId)){
						form.getUserSelectRoles().remove(orgId);
					}
					updateUserRole(userRoleForm,opttype);
				}
			}
			if(form.getUserSelectRoles()!=null && form.getUserSelectRoles().size()>0){
				for(String orgId: form.getUserSelectRoles()){
					//处理删除机构下的所有角色
					List<String> roleIdList = new ArrayList<String>();
					UserRoleInfForm userRoleForm = new UserRoleInfForm();
					UserRoleInf userRoleInf = new UserRoleInf();
					userRoleInf.setUserid(form.getUserInf().getUserid());
					userRoleInf.setOrgid(orgId);
					userRoleForm.setUserRoleInf(userRoleInf);
					userRoleForm.setUserRoleInfArr(roleIdList);
					updateUserRole(userRoleForm,opttype);
				}
			}
			
			//修改后数据
			UserInfTmp userInfTmp= new UserInfTmp();
			userInfTmp.setUserid(userId);
			userInfTmp.setUsername(form.getUsername());
			userInfTmp.setLoginorg(form.getLoginorg());
			
			TlrLogPrint.tlrSysLogPrint("SM_User_Qry", CommonConst.DATA_LOG_OPERTYPE_MODIFY, 
					 generateUserInfString(oldUserInf) , generateUserInfTmpString(userInfTmp));
		}else if(opttype == "03"){
			//删除操作
			if(form.getUserSelectRoles().size()>0){
				for(String orgId:form.getUserSelectRoles()){
					UserRoleInfForm userRoleForm = new UserRoleInfForm();
					UserRoleInf userRoleInf=new UserRoleInf();
					userRoleInf.setUserid(StringUtil.trim(form.getUserInf().getUserid()));
					userRoleInf.setOrgid(StringUtil.trim(orgId));
					userRoleForm.setUserRoleInf(userRoleInf);
					updateUserRole(userRoleForm,opttype);
				}
			}
			//记录操作日志
			TlrLogPrint.tlrSysLogPrint("SM_User_Qry", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
					 generateUserInfString(oldUserInf) ,"");
		
		}
		
		//用户机构操作
		updateUserOrg(form, opttype);
		
		return 0;
	}
	
	//用户机构操作
	public void updateUserOrg2(UserInfForm form,String opttype){
		ResultMessages messages = ResultMessages.error();
		//从前台界面获取值
		UserInf userInf=form.getUserInf();
		String userId = StringUtil.trim(userInf.getUserid());
		//赋值临时表主键值
		UserInfTmp userInfTmp = new UserInfTmp();
		userInfTmp.setUserid(userId);
		//将原来的值做一份保留
		UserInf oldUserInf=new UserInf();
		oldUserInf=userInfRepository.queryUser(userId);
		//判断主表中是否存在相同记录
		if (null!=oldUserInf) {
			//判断临时表中是否存在相同待授权记录
			if (userInfTmpRepository.queryUserInfo(userInfTmp)==0) {
				userInfTmp.setId(numberService.getSysIDSequence("0000",8));
				//删除，从原记录信息中直接获取值
				if(opttype.equals("03")){
					userInfTmp.setUserid(oldUserInf.getUserid());
					userInfTmp.setUsername(oldUserInf.getUsername());
//					userInfTmp.setAuthamt(oldUserInf.getAuthamt());
					userInfTmp.setCreateorg(oldUserInf.getCreateorg());
					userInfTmp.setUserstatus(oldUserInf.getUserstatus());
					userInfTmp.setLoginorg(oldUserInf.getLoginorg());
				//修改，从界面上获取值
				}else if(opttype.equals("02")){
					userInfTmp.setUserid(userInf.getUserid());
					userInfTmp.setUsername(form.getUsername());
//					//金额特殊处理
//					String amt=form.getAuthamt().replace(",", "");
//					userInfTmp.setAuthamt(Long.valueOf(amt.substring(0, amt.length()-3)));
					userInfTmp.setCreateorg(userInf.getCreateorg());
					userInfTmp.setUserstatus(userInf.getUserstatus());
					userInfTmp.setLoginorg(StringUtil.trim(form.getLoginorg()));
				//维护类型处理异常处理
				}else{
					messages.add("e.sm.4011");
					throw new BusinessException(messages);
				}
				//设置其他信息
				userInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOpttype(opttype);
				userInfTmp.setOptstatus("01");
				//插入临时表
				if (1 == userInfTmpRepository.insertUserInfTmp(userInfTmp)) {
					//更新主表信息-状态信息
					UserInf userInfTemp = new UserInf();
					userInfTemp.setUserid(userId);
					userInfTemp.setOptstatus("01");
					if(1==userInfRepository.updateUser(userInfTemp)){
						//用户机构信息处理
						UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
						UserOrgInf olduserOrgInf=new UserOrgInf();
						//根据用户id查询用户机构信息表中信息
						List<String> oldorgidlist=new ArrayList<String>();
						olduserOrgInf.setUserid(StringUtil.trim(form.getUserInf().getUserid()));
						List<UserOrgInf> olduserOrgInfList=userOrgInfRepository.queryUserOrgInfList(olduserOrgInf);
						for (int i = 0; i < olduserOrgInfList.size(); i++) {
							oldorgidlist.add(StringUtil.trim(olduserOrgInfList.get(i).getOrgid()));
						}
						List<UserOrgInfTmp> tmp=new ArrayList<UserOrgInfTmp>();
						//删除，查询原有机构信息并删除
						if(opttype.equals("03")){
							for (int i = 0; i < olduserOrgInfList.size(); i++) {
								olduserOrgInf = olduserOrgInfList.get(i);
								userOrgInfTmp=new UserOrgInfTmp();
								userOrgInfTmp.setOrgid(StringUtil.trim(olduserOrgInf.getOrgid()));
								userOrgInfTmp.setOpttype("03");
								tmp.add(userOrgInfTmp);
							}
						//修改，原信息中存在，现在不存在则删除原数据，
							//原信息中不存在，现在存在则新增原数据，
							//原信息中存在，但机构下属角色被修改的数据，置该数据为修改状态
						}else if(opttype.equals("02")){
							List<String> userOrgInfList=form.getUserOrgInfArr();
							List<String> userOrgListTmp=new ArrayList<String>();
							List<String> userOrgListUpd=new ArrayList<String>();
							for (int j = 0; j <userOrgInfList.size(); j++) {
								//原信息中存在，但机构下属角色被修改的数据，置该数据为修改状态
								String userorg=StringUtil.trim(userOrgInfList.get(j));
								userOrgListTmp.add(userorg);
								if(oldorgidlist.contains(userorg)){
									oldorgidlist.remove(userorg);
									userOrgListTmp.remove(userorg);
									//查询用户角色零时表，有记录则该机构下下的角色被修改
									UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
									userRoleInfTmp.setUserid(userId);
									userRoleInfTmp.setOrgid(userorg);
									if(userRoleInfTmpRepository.querylist(userRoleInfTmp).size()>0){
										userOrgListUpd.add(userorg);
									}
								}
							}
							//原信息中存在，机构下角色被修改的，
							if(userOrgListUpd.size()>0){
								for (int i = 0; i < userOrgListUpd.size(); i++) {
									userOrgInfTmp=new UserOrgInfTmp();
									userOrgInfTmp.setOrgid(StringUtil.trim(userOrgListUpd.get(i)));
									userOrgInfTmp.setOpttype("02");
									tmp.add(userOrgInfTmp);
								}
							}
							//原信息中存在，现在不存在则删除原数据，
							if(oldorgidlist.size()>0){
								for (int i = 0; i < oldorgidlist.size(); i++) {
									userOrgInfTmp=new UserOrgInfTmp();
									userOrgInfTmp.setOrgid(StringUtil.trim(oldorgidlist.get(i)));
									userOrgInfTmp.setOpttype("03");
									tmp.add(userOrgInfTmp);
								}
							}
							//原信息中不存在，现在存在则新增原数据，
							if(userOrgListTmp.size()>0){
								for (int j = 0; j < userOrgListTmp.size(); j++) {
									userOrgInfTmp=new UserOrgInfTmp();
									userOrgInfTmp.setOrgid(StringUtil.trim(userOrgListTmp.get(j)));
									userOrgInfTmp.setOpttype("01");
									tmp.add(userOrgInfTmp);
								}
							}
						}else{
							messages.add("e.sm.4011");
							throw new BusinessException(messages);
						}
						if(tmp.size()>0){
							for(int i=0;i<tmp.size();i++){
								userOrgInfTmp=new UserOrgInfTmp();
								userOrgInfTmp=tmp.get(i);
								userOrgInfTmp.setUserid(userId);
								UserOrgInf userOrgInf=new UserOrgInf();
								userOrgInf.setUserid(userId);
								String orgId=StringUtil.trim(userOrgInfTmp.getOrgid());
								userOrgInf.setOrgid(orgId);
								OrgInf orgInf=orgInfRepository.query(orgId);
								userOrgInf=userOrgInfRepository.queryUserOrgInf(userOrgInf);
								String optype=userOrgInfTmp.getOpttype();
								if(optype.equals("01")){
									if(userOrgInf!=null){
										messages.add("e.sm.4039");
										throw new BusinessException(messages);
									}
								}else{
									if (userOrgInf==null) {
										messages.add("e.sm.4045");
										throw new BusinessException(messages);
									}
								}
								//查询用户机构临时表中是否存在相同待授权记录
								if (userOrgInfTmpRepository.queryUserOrgInfTmpCnt(userOrgInfTmp)==0) {
									userOrgInfTmp.setOrgname(orgInf.getOrgname());
									userOrgInfTmp.setId(numberService.getSysIDSequence("0000",8));
									userOrgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
									userOrgInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
									userOrgInfTmp.setOptstatus("01");
									//插入用户机构临时表
									if(1!=userOrgInfTmpRepository.insertUserOrgInfTmp(userOrgInfTmp)){
										//异常处理
										messages.add("e.sm.4037");
										throw new BusinessException(messages);
									}
								//用户机构临时表中存在相同待授权记录
								}else{
									messages.add("e.sm.4038");
									throw new BusinessException(messages);
								}
							}
						}
					//更新用户表错误
					}else{
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				//插入临时表错误
				} else {
					messages.add("e.sm.4029");
					throw new BusinessException(messages);
				}
			//临时表中存在相同记录
			}else{
				messages.add("e.sm.4034");
				throw new BusinessException(messages);
			}
		//主表中存在相同记录
		}else{
			messages.add("e.sm.4033");
			throw new BusinessException(messages);
		}
	}
	//用户角色操作
	public void updateUserRole(UserRoleInfForm form,String opttype){
		ResultMessages messages = ResultMessages.error();
		UserRoleInf userRoleInf=form.getUserRoleInf();
		UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
		String userId = StringUtil.trim(userRoleInf.getUserid());
		String orgId = StringUtil.trim(userRoleInf.getOrgid());
		userRoleInf.setUserid(userId);
		userRoleInf.setOrgid(orgId);
		List<UserRoleInfTmp> tmp=new ArrayList<UserRoleInfTmp>();
		//从数据库中获取原来角色信息列表
		List<UserRoleInf> oldUserRoleInflist=userRoleInfRepository.queryUserRoleIdList(userRoleInf);
		UserRoleInf oldUserRoleInf=new UserRoleInf();
		List<String> olduserrolelist=new ArrayList<String>();
		for(int i=0;i<oldUserRoleInflist.size();i++){
			//将查询出来的对象对应的角色值拼成一个List<String>列表
			olduserrolelist.add(StringUtil.trim(oldUserRoleInflist.get(i).getRoleid()));
		}
		//optype=03:删除，删除list oldUserRoleInf中的值
		if(opttype.equals("03")){
			//将list中的对象赋值给每一个新的对象
			for(int i=0;i<oldUserRoleInflist.size();i++){
				oldUserRoleInf=oldUserRoleInflist.get(i);
				userRoleInfTmp = new UserRoleInfTmp();
				userRoleInfTmp.setOpttype("03");
				userRoleInfTmp.setRoleid(StringUtil.trim(oldUserRoleInf.getRoleid()));
				tmp.add(userRoleInfTmp);
			}
		//optype=02:修改
		//如果olduserrolelist有，而userrolelist没有，设置optype=03;
		//如果userrolelist有，而olduserrolelist没有，设置optype=01;
		//相同则更新为修改状态：02到零时表
		}else if(opttype.equals("02")){
			//获取界面中的角色列表信息
			List<String> userrolelist=form.getUserRoleInfArr();
			List<String> userrolelistTmp=new ArrayList<String>();
			List<String> userrolelistUpd=new ArrayList<String>();
			for (int j = 0; j <userrolelist.size(); j++) {
				//相同则更新为修改状态：02到零时表
				String userrole=StringUtil.trim(userrolelist.get(j));
				userrolelistTmp.add(userrole);
				if(olduserrolelist.contains(userrole)){
					olduserrolelist.remove(userrole);
					userrolelistTmp.remove(userrole);
					userrolelistUpd.add(userrole);
				}
			}
			//olduserrolelist有，而userrolelist没有，设置optype=03;
			if(olduserrolelist.size()>0){
				for (int i = 0; i < olduserrolelist.size(); i++) {
					userRoleInfTmp=new UserRoleInfTmp();
					userRoleInfTmp.setOpttype("03");
					userRoleInfTmp.setRoleid(StringUtil.trim(olduserrolelist.get(i)));
					tmp.add(userRoleInfTmp);
				}
			}
			//如果userrolelist有，而olduserrolelist没有，设置optype=01;
			if(userrolelistTmp.size()>0){
				for (int j = 0; j < userrolelistTmp.size(); j++) {
					userRoleInfTmp = new UserRoleInfTmp();
					userRoleInfTmp.setOpttype("01");
					userRoleInfTmp.setRoleid(StringUtil.trim(userrolelistTmp.get(j)));
					tmp.add(userRoleInfTmp);
				}
			}
			//相同则更新到零时表，设置optype=02;
			if(userrolelistUpd.size()>0){
				for (int j = 0; j < userrolelistUpd.size(); j++) {
					userRoleInfTmp = new UserRoleInfTmp();
					userRoleInfTmp.setOpttype("02");
					userRoleInfTmp.setRoleid(StringUtil.trim(userrolelistUpd.get(j)));
					tmp.add(userRoleInfTmp);
				}
			}
		}else{
			messages.add("e.sm.4011");
			throw new BusinessException(messages);
		}
		if(tmp.size()>0){
			for(int i=0;i<tmp.size();i++){
				userRoleInfTmp=new UserRoleInfTmp();
				userRoleInfTmp=tmp.get(i);
				userRoleInfTmp.setUserid(userId);
				userRoleInfTmp.setOrgid(orgId);
				//查润主表中是否存在记录
				userRoleInf=new UserRoleInf();
				userRoleInf.setUserid(userId);
				userRoleInf.setOrgid(orgId);
				userRoleInf.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
				userRoleInf=userRoleInfRepository.query(userRoleInf);
				String optype=userRoleInfTmp.getOpttype();
				//新增时，主表无记录
				if(optype.equals("01")){
					if(userRoleInf!=null){
						messages.add("e.sm.4044");
						throw new BusinessException(messages);
					}
				//删除时，主表应存在记录
				}else if(optype.equals("03")){
					if (userRoleInf==null) {
						messages.add("e.sm.4026");
						throw new BusinessException(messages);
					}
				//修改时，主表应存在记录
				}else if(optype.equals("02")){
					if (userRoleInf==null) {
						messages.add("e.sm.4026");
						throw new BusinessException(messages);
					}
				}
				UserInf userInf=userInfRepository.queryUser(userId);
				OrgInf orgInf=orgInfRepository.query(orgId);
				RoleInf roleInf=roleInfRepository.query(StringUtil.trim(userRoleInfTmp.getRoleid()));
				//查询临时表中是否存在相同待授权记录
				if(userRoleInfTmpRepository.queryUserRoleInfo(userRoleInfTmp)==0){
					userRoleInfTmp.setId(numberService.getSysIDSequence("0000",8));
					userRoleInfTmp.setUsername(userInf.getUsername());
					userRoleInfTmp.setOrgname(orgInf.getOrgname());
					userRoleInfTmp.setRolename(roleInf.getRolename());
					userRoleInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
					userRoleInfTmp.setCreatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userRoleInfTmp.setOptstatus("01");
					//插入临时表
					if (1 == userRoleInfTmpRepository.insertUserRoleInfTmp(userRoleInfTmp)) {
						//更新主表状态-待授权
						userRoleInf=new UserRoleInf();
						userRoleInf.setOptstatus("01");
						userRoleInf.setUserid(userId);
						userRoleInf.setOrgid(orgId);
						if(optype.equals("03")||optype.equals("02")){
							userRoleInf.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
							if(1!=userRoleInfRepository.updateUserRoleInf(userRoleInf)){
								//更新主表，异常处理
								messages.add("e.sm.4023");
								throw new BusinessException(messages);
							}
						}
					}else{
						//插入临时表，异常处理
						messages.add("e.sm.4022");
						throw new BusinessException(messages);
					}
				}else{
					//临时表中存在相同记录，异常处理
					messages.add("e.sm.4043");
					throw new BusinessException(messages);
					}
				}
			}
		}
	/*****************************************用户修改或删除临时end******************************************/
	@Override
	@Transactional
	public int transAuth(UserInfTmpForm form) {
		ResultMessages messages = ResultMessages.error();
		UserInf userInf=new UserInf();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		userInfTmp=userInfTmpRepository.query(userInfTmp);
		//临时表中存在记录
		if(null!=userInfTmp){
			//录入操作员与授权/拒绝操作员是否为同一人判断
			if(CommonUtil.compareTlr(userInfTmp.getCreater())==1){
				String opttype=userInfTmp.getOpttype();
				//新增 处理
				if(opttype.equals("01")){
					userInf=setValueToUserInf(userInfTmp);
					//设置用户状态为01-正常
					userInf.setUserstatus("01");
					//设置登录状态为01-未登录
					userInf.setStatus("01");
					//设置用户默认密码为admin
					userInf.setPassword(CommonConst.PWD);
					userInf.setPwdchangeuser(ContextConst.getCurrentUser().getUserid());
					userInf.setPwdchangetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					
					userInf.setCreater(userInfTmp.getCreater());
					userInf.setCreatetime(userInfTmp.getCreatetime());
					//插入主表及插入异常处理
					if (userInfRepository.insertUserInf(userInf)!=1){
						messages.add("e.sm.4028");
						throw new BusinessException(messages);
					}
				//修改处理
				}else if(opttype.equals("02")){
					userInf=setValueToUserInf(userInfTmp);
					userInf.setLastoperator(userInfTmp.getCreater());
					userInf.setLastopttime(userInfTmp.getCreatetime());
					//更新主表及更新异常处理
					if (userInfRepository.updateUser(userInf)!=1){
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				//删除处理
				}else if(opttype.equals("03")){
					userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//删除主表信息及删除异常处理
					if (userInfRepository.deleteUserInf(userInf)!=1){
						messages.add("e.sm.4032");
						throw new BusinessException(messages);
					}
				//维护类型错误处理
				}else{
					messages.add("e.sm.4010");
					throw new BusinessException(messages);
				}
				//临时表更新处理
				userInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOptstatus("02");
				if(1==userInfTmpRepository.updateUserInfTmp(userInfTmp)){
					//用户机构信息处理
					UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
					userOrgInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//根据用户Id得到机构列表
					List<UserOrgInfTmp> list= userOrgInfService.transQueryUserOrgInfTmpList(userOrgInfTmp);
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp=list.get(i);
							UserOrgInf userOrgInf=new UserOrgInf();
							String opttype2=userOrgInfTmp.getOpttype();
							//新增
							if(opttype2.equals("01")){
								userOrgInf.setUserid(userOrgInfTmp.getUserid());
								userOrgInf.setOrgid(userOrgInfTmp.getOrgid());
								userOrgInf.setOrgname(userOrgInfTmp.getOrgname());
								userOrgInf.setCreater(userOrgInfTmp.getCreater());
								userOrgInf.setCreatetime(userOrgInfTmp.getCreatetime());
								userOrgInf.setChecker(ContextConst.getCurrentUser().getUserid());
								userOrgInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								//插入及插入异常处理
								if (userOrgInfRepository.insertUserOrgInf(userOrgInf)!=1){
									messages.add("e.sm.4040");
									throw new BusinessException(messages);
								}
							//删除
							}else if(opttype2.equals("03")){
								userOrgInf.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
								userOrgInf.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
								//删除及删除异常处理
								if (userOrgInfRepository.deleteUserOrgInf(userOrgInf)!=1){
									messages.add("e.sm.4041");
									throw new BusinessException(messages);
								}
							//维护类型错误
							}else{
								messages.add("e.sm.4010");
								throw new BusinessException(messages);
							}
							//更新用户机构临时表信息
							userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
							userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							userOrgInfTmp.setOptstatus("02");
							//更新错误处理
							if(1!=userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp)){
								messages.add("e.sm.4042");
								throw new BusinessException(messages);
							}
						}
					//用户机构临时表无记录
					}else{
						if(!opttype.equals("02")){
							messages.add("e.sm.4046");
							throw new BusinessException(messages);
						}
					}
				//更新临时表错误异常处理			
				}else{
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
			}else{
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		//临时表无记录异常处理
		}else{
			messages.add("e.sm.4036");
			throw new BusinessException(messages);
		}
		return 0;
	}
	/*****************************************用户授权临时start******************************************/
	@Override
	@Transactional
	public int transAuth1(UserInfTmpForm form) {
		//用户机构操作
		AuthUserOrg(form);
		
		//用户角色操作
		List<UserOrgInfTmp> userOrgInfTempList = form.getUserOrgInfTempList();
		for(UserOrgInfTmp userOrgInfTmp:userOrgInfTempList){
			AuthUserRole(StringUtil.trim(form.getUserInfTmp().getUserid()),userOrgInfTmp.getOrgid());
		}
		
		return 0;
	}
	//授权用户机构操作
	public void AuthUserOrg(UserInfTmpForm form){
		ResultMessages messages = ResultMessages.error();
		UserInf userInf=new UserInf();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		userInfTmp=userInfTmpRepository.query(userInfTmp);
		//临时表中存在记录
		if(null!=userInfTmp){
			//录入操作员与授权/拒绝操作员是否为同一人判断
			if(CommonUtil.compareTlr(userInfTmp.getCreater())==1){
				String userId = StringUtil.trim(userInfTmp.getUserid());
				UserInf oldUserInf =userInfRepository.queryUser(userId);
				
				String opttype=userInfTmp.getOpttype();
				
				//新增 处理
				if(opttype.equals("01")){
					userInf=setValueToUserInf(userInfTmp);
					//设置用户状态为01-正常
					userInf.setUserstatus("01");
					//设置登录状态为01-未登录
					userInf.setStatus("01");
					//设置用户默认密码为admin
					userInf.setPassword(CommonConst.PWD);
					userInf.setPwdchangeuser(ContextConst.getCurrentUser().getUserid());
					userInf.setPwdchangetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					
					userInf.setCreater(userInfTmp.getCreater());
					userInf.setCreatetime(userInfTmp.getCreatetime());
					//插入主表及插入异常处理
					if (userInfRepository.insertUserInf(userInf)!=1){
						messages.add("e.sm.4028");
						throw new BusinessException(messages);
					}
					//日志写入数据库					
					TlrLogPrint.tlrSysLogPrint("SM_Uchk_Qry", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 "" , generateUserInfString(userInf));
				//修改处理
				}else if(opttype.equals("02")){
					userInf=setValueToUserInf(userInfTmp);
					userInf.setLastoperator(userInfTmp.getCreater());
					userInf.setLastopttime(userInfTmp.getCreatetime());
					//更新主表及更新异常处理
					if (userInfRepository.updateUser(userInf)!=1){
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
					//日志写入数据库
					//修改前的数据
					TlrLogPrint.tlrSysLogPrint("SM_Uchk_Qry", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 generateUserInfString(oldUserInf) , generateUserInfTmpString(userInfTmp));
					
				//删除处理
				}else if(opttype.equals("03")){
					userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//删除主表信息及删除异常处理
					if (userInfRepository.deleteUserInf(userInf)!=1){
						messages.add("e.sm.4032");
						throw new BusinessException(messages);
					}
					TlrLogPrint.tlrSysLogPrint("SM_Uchk_Qry", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 generateUserInfString(oldUserInf) , "");
				//维护类型错误处理
				}else{
					messages.add("e.sm.4010");
					throw new BusinessException(messages);
				}
				//临时表更新处理
				userInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOptstatus("02");
				if(1==userInfTmpRepository.updateUserInfTmp(userInfTmp)){
					//用户机构信息处理
					UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
					userOrgInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//根据用户Id得到机构列表
					List<UserOrgInfTmp> list= userOrgInfService.transQueryUserOrgInfTmpList(userOrgInfTmp);
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp=list.get(i);
							UserOrgInf userOrgInf=new UserOrgInf();
							String opttype2=userOrgInfTmp.getOpttype();
							//新增
							if(opttype2.equals("01")){
								userOrgInf.setUserid(userOrgInfTmp.getUserid());
								userOrgInf.setOrgid(userOrgInfTmp.getOrgid());
								userOrgInf.setOrgname(userOrgInfTmp.getOrgname());
								userOrgInf.setCreater(userOrgInfTmp.getCreater());
								userOrgInf.setCreatetime(userOrgInfTmp.getCreatetime());
								userOrgInf.setChecker(ContextConst.getCurrentUser().getUserid());
								userOrgInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								//插入及插入异常处理
								if (userOrgInfRepository.insertUserOrgInf(userOrgInf)!=1){
									messages.add("e.sm.4040");
									throw new BusinessException(messages);
								}
							//删除
							}else if(opttype2.equals("03")){
								userOrgInf.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
								userOrgInf.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
								//删除及删除异常处理
								if (userOrgInfRepository.deleteUserOrgInf(userOrgInf)!=1){
									messages.add("e.sm.4041");
									throw new BusinessException(messages);
								}
							//修改
							}else if(opttype2.equals("02")){
								userOrgInf.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
								userOrgInf.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
								userOrgInf.setOrgname(userOrgInfTmp.getOrgname());
								userOrgInf.setCreater(userOrgInfTmp.getCreater());
								userOrgInf.setCreatetime(userOrgInfTmp.getCreatetime());
								userOrgInf.setChecker(ContextConst.getCurrentUser().getUserid());
								userOrgInf.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								//插入及插入异常处理
								if (userOrgInfRepository.updateUserOrgInf(userOrgInf)!=1){
									messages.add("ee.sm.4042");
									throw new BusinessException(messages);
								}
							//维护类型错误
							}else{
								messages.add("e.sm.4010");
								throw new BusinessException(messages);
							}
							//更新用户机构临时表信息
							userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
							userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							userOrgInfTmp.setOptstatus("02");
							//更新错误处理
							if(1!=userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp)){
								messages.add("e.sm.4042");
								throw new BusinessException(messages);
							}
						}
					//用户机构临时表无记录
					}else{
						if(!opttype.equals("02")){
							messages.add("e.sm.4046");
							throw new BusinessException(messages);
						}
					}
				//更新临时表错误异常处理			
				}else{
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
			}else{
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		//临时表无记录异常处理
		}else{
			messages.add("e.sm.4036");
			throw new BusinessException(messages);
		}
	}	
	//授权用户角色操作
	public void AuthUserRole(String userId,String orgId){
		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp= new UserRoleInfTmp();
		UserRoleInf userRoleInf=new UserRoleInf();
		userRoleInfTmp.setUserid(StringUtil.trim(userId));
		userRoleInfTmp.setOrgid(StringUtil.trim(orgId));
		//根据用户Id及机构ID查询对应的角色ID列表
		List<UserRoleInfTmp> list=userRoleInfTmpRepository.querylist(userRoleInfTmp);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				userRoleInfTmp=new UserRoleInfTmp();
				userRoleInfTmp=list.get(i);
				//录入操作员与授权/拒绝操作员是否为同一人判断
				if(CommonUtil.compareTlr(userRoleInfTmp.getCreater())==1){
					String opttype=userRoleInfTmp.getOpttype();
					//新增，主表中增加一条记录
					if(opttype.equals("01")){
						userRoleInf=setValueToUserRoleInf(userRoleInfTmp);
						//插入主表及插入异常处理
						if (userRoleInfRepository.insertUserRoleInf(userRoleInf)!=1){
							messages.add("e.sm.4021");
							throw new BusinessException(messages);
						}
					//删除，删除主表中记录
					}else if(opttype.equals("03")){
						userRoleInf.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
						userRoleInf.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
						userRoleInf.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
						//删除及删除异常处理
						if (userRoleInfRepository.deleteUserRoleInf(userRoleInf)!=1){
							messages.add("e.sm.4025");
							throw new BusinessException(messages);
						}
					//修改，修改主表中记录状态
					}else if(opttype.equals("02")){
						userRoleInf.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
						userRoleInf.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
						userRoleInf.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
						userRoleInf.setOptstatus("02");
						//修改及修改异常处理
						if (userRoleInfRepository.updateUserRoleInf(userRoleInf)!=1){
							messages.add("e.sm.4030");
							throw new BusinessException(messages);
						}
					//维护错误类型值错误处理
					}else{
						messages.add("e.sm.4010");
						throw new BusinessException(messages);
					}
					//临时表更新处理
					userRoleInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
					userRoleInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userRoleInfTmp.setOptstatus("02");
					//更新及更新异常处理
					if(1!=userRoleInfTmpRepository.updateUserRoleInfTmp(userRoleInfTmp)){
						messages.add("e.sm.4024");
						throw new BusinessException(messages);
					}
				}else{
					messages.add("e.cm.2003");
					throw new BusinessException(messages);
				}
			}
		//临时表查询错误
		}
//		else{
//			messages.add("e.sm.4027");
//			throw new BusinessException(messages);
//		}
	}
	
	/*****************************************用户授权临时end******************************************/

	@Override
	@Transactional
	public int transRejct(UserInfTmpForm form)  {
		ResultMessages messages = ResultMessages.error();
		UserInf userInf=new UserInf();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		userInfTmp=userInfTmpRepository.query(userInfTmp);
		//查询临时表信息
		if(null!=userInfTmp){
			//录入操作员与授权/拒绝操作员是否为同一人判断
			if(CommonUtil.compareTlr(userInfTmp.getCreater())==1){
				String opttype=userInfTmp.getOpttype();
				//更新、删除时修改主表信息
				if(opttype.equals("02")||opttype.equals("03")){
					userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					userInf.setLastoperator(userInfTmp.getCreater());
					userInf.setLastopttime(userInfTmp.getCreatetime());
					userInf.setOptstatus("02");
					if (userInfRepository.updateUser(userInf)!=1){
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				}
				//临时表信息更新
				userInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOptstatus("03");
				//更新处理
				if(1==userInfTmpRepository.updateUserInfTmp(userInfTmp)){
					UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
					userOrgInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//根据用户Id得到机构列表
					List<UserOrgInfTmp> list= userOrgInfService.transQueryUserOrgInfTmpList(userOrgInfTmp);
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp=list.get(i);
							userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
							userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							userOrgInfTmp.setOptstatus("03");
							userOrgInfTmp.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
							userOrgInfTmp.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
							//更新及更新异常处理
							if(1!=userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp)){
								messages.add("e.sm.4042");
								throw new BusinessException(messages);
							}
						}
					//用户机构临时表无记录
					}else{
						if(!opttype.equals("02")){
							messages.add("e.sm.4046");
							throw new BusinessException(messages);
						}
					}
				//更新用户临时表及更新异常处理
				}else{
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
			}else{
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		//用户临时表查询无记录处理
		}else{
			messages.add("e.sm.4036");
			throw new BusinessException(messages);
		}
		return 0;
	}
	/*****************************************用户拒绝临时start******************************************/
	@Override
	@Transactional
	public int transRejct1(UserInfTmpForm form)  {
		//用户机构操作
		RejctUserOrg(form);
		
		//用户角色操作
		List<UserOrgInfTmp> userOrgInfTempList = form.getUserOrgInfTempList();
		for(UserOrgInfTmp userOrgInfTmp:userOrgInfTempList){
			RejctUserRole(StringUtil.trim(form.getUserInfTmp().getUserid()),userOrgInfTmp.getOrgid());
		}
		return 0;
	}
	//用户机构操作
	public void RejctUserOrg(UserInfTmpForm form){
		ResultMessages messages = ResultMessages.error();
		UserInf userInf=new UserInf();
		UserInfTmp userInfTmp=form.getUserInfTmp();
		userInfTmp=userInfTmpRepository.query(userInfTmp);
		//查询临时表信息
		if(null!=userInfTmp){
			//录入操作员与授权/拒绝操作员是否为同一人判断
			if(CommonUtil.compareTlr(userInfTmp.getCreater())==1){
				String userId = StringUtil.trim(userInfTmp.getUserid());
				UserInf oldUserInf =userInfRepository.queryUser(userId);
				String opttype=userInfTmp.getOpttype();
				//更新、删除时修改主表信息
				if(opttype.equals("02")||opttype.equals("03")){
					userInf.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					userInf.setLastoperator(userInfTmp.getCreater());
					userInf.setLastopttime(userInfTmp.getCreatetime());
					userInf.setOptstatus("02");
					if (userInfRepository.updateUser(userInf)!=1){
						messages.add("e.sm.4030");
						throw new BusinessException(messages);
					}
				}
				//临时表信息更新
				userInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				userInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				userInfTmp.setOptstatus("03");
				//更新处理
				if(1==userInfTmpRepository.updateUserInfTmp(userInfTmp)){
					UserOrgInfTmp userOrgInfTmp=new UserOrgInfTmp();
					userOrgInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
					//根据用户Id得到机构列表
					List<UserOrgInfTmp> list= userOrgInfService.transQueryUserOrgInfTmpList(userOrgInfTmp);
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							userOrgInfTmp=new UserOrgInfTmp();
							userOrgInfTmp=list.get(i);
							userOrgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
							userOrgInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							userOrgInfTmp.setOptstatus("03");
							userOrgInfTmp.setUserid(StringUtil.trim(userOrgInfTmp.getUserid()));
							userOrgInfTmp.setOrgid(StringUtil.trim(userOrgInfTmp.getOrgid()));
							//更新及更新异常处理
							if(1!=userOrgInfTmpRepository.updateUserOrgInfTmp(userOrgInfTmp)){
								messages.add("e.sm.4042");
								throw new BusinessException(messages);
							}
						}
					//用户机构临时表无记录
					}else{
						if(!opttype.equals("02")){
							messages.add("e.sm.4046");
							throw new BusinessException(messages);
						}
					}
				//更新用户临时表及更新异常处理
				}else{
					messages.add("e.sm.4031");
					throw new BusinessException(messages);
				}
				//拒绝操作员日志写入数据库
				 if(opttype.equals("01")){
					 //拒绝新增
					TlrLogPrint.tlrSysLogPrint("操作员信息维护审核 ", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 "" , generateUserInfTmpString(userInfTmp));
				 }else if(opttype.equals("02")){
					 //拒绝修改
					TlrLogPrint.tlrSysLogPrint("SM_Uchk_Qry", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 generateUserInfString(oldUserInf) ,generateUserInfTmpString(userInfTmp));
				 }else if(opttype.equals("03")){
					 //拒绝删除
					TlrLogPrint.tlrSysLogPrint("SM_Uchk_Qry", CommonConst.DATA_LOG_OPERTYPE_CHECK, 
							 generateUserInfString(oldUserInf) , "");
				 }
			}else{
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		//用户临时表查询无记录处理
		}
//		else{
//			messages.add("e.sm.4036");
//			throw new BusinessException(messages);
//		}
	}		
	//用户角色操作
	public void RejctUserRole(String userId,String orgId){
		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp = new UserRoleInfTmp();
		userRoleInfTmp.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
		userRoleInfTmp.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
		//根据用户Id及机构ID查询对应的角色ID列表
		List<UserRoleInfTmp> list=userRoleInfTmpRepository.querylist(userRoleInfTmp);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				userRoleInfTmp=new UserRoleInfTmp();
				userRoleInfTmp=list.get(i);
				//录入操作员与授权/拒绝操作员是否为同一人判断
				if(CommonUtil.compareTlr(userRoleInfTmp.getCreater())==1){
					String optype=userRoleInfTmp.getOpttype();
					//当记录为删除时，将主表中状态更新回-02-正常
					if(optype.equals("03")){
						UserRoleInf userRoleInf=new UserRoleInf();
						userRoleInf.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
						userRoleInf.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
						userRoleInf.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
						userRoleInf.setLastoperator(userRoleInfTmp.getCreater());
						userRoleInf.setLastopttime(userRoleInfTmp.getCreatetime());
						userRoleInf.setOptstatus("02");
						if(1!=userRoleInfRepository.updateUserRoleInf(userRoleInf)){
							messages.add("e.sm.4023");
							throw new BusinessException(messages);
						}
					}
					//更新临时表记录
					userRoleInfTmp.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
					userRoleInfTmp.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
					userRoleInfTmp.setRoleid(StringUtil.trim(userRoleInfTmp.getRoleid()));
					userRoleInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
					userRoleInfTmp.setChecktime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					userRoleInfTmp.setOptstatus("03");
					//更新临时表及更新异常处理
					if(1!=userRoleInfTmpRepository.updateUserRoleInfTmp(userRoleInfTmp)){
						messages.add("e.sm.4024");
						throw new BusinessException(messages);
					}
				}else{
					messages.add("e.cm.2003");
					throw new BusinessException(messages);
				}
			}
		//临时表查询错误
		}
//		else{
//			messages.add("e.sm.4027");
//			throw new BusinessException(messages);
//		}
	}
	/*****************************************用户拒绝临时start******************************************/
	
	private String generateUserInfTmpString(UserInfTmp userInfTmp) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("index.label.sm.UserId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(userInfTmp.getUserid()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("index.label.sm.UserName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(userInfTmp.getUsername()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("index.label.sm.CreateOrg")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(userInfTmp.getCreateorg()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("index.label.sm.UserStatus")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(userInfTmp.getUserstatus()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("index.label.sm.LoginOrg")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(userInfTmp.getLoginorg()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}
	private String generateUserInfString(UserInf userInf) {
		StringBuffer sb = new StringBuffer();
		 	sb.append(message.getMessage("index.label.sm.UserId")).append(CommonConst.SEPARATE_KEY_VALUE);
			sb.append(userInf.getUserid()).append(CommonConst.SEPARATE_TWO_FIELD);
			sb.append(message.getMessage("index.label.sm.UserName")).append(CommonConst.SEPARATE_KEY_VALUE);
			sb.append(userInf.getUsername()).append(CommonConst.SEPARATE_TWO_FIELD);
			sb.append(message.getMessage("index.label.sm.CreateOrg")).append(CommonConst.SEPARATE_KEY_VALUE);
			sb.append(userInf.getCreateorg()).append(CommonConst.SEPARATE_TWO_FIELD);
			sb.append(message.getMessage("index.label.sm.UserStatus")).append(CommonConst.SEPARATE_KEY_VALUE);
			sb.append(userInf.getUserstatus()).append(CommonConst.SEPARATE_TWO_FIELD);
			sb.append(message.getMessage("index.label.sm.LoginOrg")).append(CommonConst.SEPARATE_KEY_VALUE);
			sb.append(userInf.getLoginorg()).append(CommonConst.SEPARATE_TWO_FIELD);;
		
		return sb.toString();
	}

	@Override
	@Transactional
	public void transSetStatusNormal(UserInfForm form, String status) {
		ResultMessages messages = ResultMessages.error();
		if(status!=null){
			String userId=StringUtil.trim(form.getUserid());
			UserInf userInf=new UserInf();
			userInf=userInfRepository.queryUser(userId);
			if(userInf!=null){
				userInf.setUserstatus("01");
				if(userInfRepository.updateUser(userInf)!=1){
					log.error("更新操作员表错误！");
					messages.add("e.sm.4030");
					throw new BusinessException(messages);
				}else{
				}
			}else{
				log.error("操作员表中没有该ID的记录！");
				messages.add("e.sm.4035");
				throw new BusinessException(messages);
			}
		}
	}

	@Override
	@Transactional
	public String transPasswordReset(UserInfForm form) {
		ResultMessages messages = ResultMessages.error();
		String userId=StringUtil.trim(form.getUserid());
		UserInf userInf=new UserInf();
		userInf=userInfRepository.queryUser(userId);
		if(userInf!=null){
			String pwd=GenRdmPwd.genRandmon(8);
			userInf.setPassword(SHAEncrypt.stringToSHA1(pwd));
			userInf.setPwdchangeuser(ContextConst.getCurrentUser().getUserid());
			userInf.setPwdchangetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
			if(userInfRepository.updateUser(userInf)==1){
				return pwd;
			}else{
				log.error("更新操作员表错误！");
				messages.add("e.sm.4030");
				throw new BusinessException(messages);
			}
		}else{
			log.error("操作员表中没有该ID的记录！");
			messages.add("e.sm.4035");
			throw new BusinessException(messages);
		}
	}
}
