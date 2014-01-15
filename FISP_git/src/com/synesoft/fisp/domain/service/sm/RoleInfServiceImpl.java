package com.synesoft.fisp.domain.service.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.RoleConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.app.sm.model.RoleInfForm;
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.RoleInfTmp;
import com.synesoft.fisp.domain.model.SysRoleFunc;
import com.synesoft.fisp.domain.model.SysRoleFuncTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfTmpRepository;
import com.synesoft.fisp.domain.repository.sm.SysFuncInfoRepository;
import com.synesoft.fisp.domain.repository.sm.SysRoleFuncReponsitory;
import com.synesoft.fisp.domain.repository.sm.SysRoleFuncTmpReponsitory;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * @author zhongHubo
 * @date 2013年7月10日 17:09:22
 * @version 1.0
 * @Description 角色操作ServiceImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("roleInfService")
public class RoleInfServiceImpl implements RoleInfService {
	private static final LogUtil log = new LogUtil(RoleInfServiceImpl.class);

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找。 并排除list中所有的角色
	 * 
	 * @param createorg
	 *            创建机构
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRecTmp(String createorg,
			List<UserRoleInfTmp> list) {

		List<RoleInf> list_RoleInf = this.queryRolesByOrgRec(createorg);

		// list_RoleInf排除list中所有的角色
		removeSpecialRoleTmp(list, list_RoleInf);

		return list_RoleInf;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找 并排除list中所有的角色
	 * 
	 * @param roleInf
	 *            角色对象
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRecTmp(RoleInf roleInf,
			List<UserRoleInfTmp> list) {

		List<RoleInf> list_RoleInf = this.queryRolesByOrgRec(roleInf);

		// list_RoleInf排除list中所有的角色
		removeSpecialRoleTmp(list, list_RoleInf);

		return list_RoleInf;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找 并排除list中所有的角色
	 * 
	 * @param roleInf
	 *            角色对象
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> transQueryRoleInfListArr(String createorg,
			List<String> list) {

		List<RoleInf> list_RoleInf = this.queryRolesByOrgRec(createorg);

		// list_RoleInf排除list中所有的角色
		removeSpecialRoleArr(list, list_RoleInf);

		return list_RoleInf;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找。 并排除list中所有的角色
	 * 
	 * @param createorg
	 *            创建机构
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRec(String createorg,
			List<UserRoleInf> list) {

		List<RoleInf> list_RoleInf = this.queryRolesByOrgRec(createorg);

		// list_RoleInf排除list中所有的角色
		removeSpecialRole(list, list_RoleInf);

		return list_RoleInf;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找 并排除list中所有的角色
	 * 
	 * @param roleInf
	 *            角色对象
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRec(RoleInf roleInf,
			List<UserRoleInf> list) {

		List<RoleInf> list_RoleInf = this.queryRolesByOrgRec(roleInf);

		// list_RoleInf排除list中所有的角色
		removeSpecialRole(list, list_RoleInf);

		return list_RoleInf;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * 
	 * @param createorg
	 *            创建机构
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRec(String createorg) {

		// 组装查询条件
		RoleInf roleInf = new RoleInf();
		roleInf.setCreateorg(createorg);

		return this.queryRolesByOrgRec(roleInf);
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * 
	 * @param roleInf
	 *            角色对象
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrgRec(RoleInf roleInf) {
		// 待返回的结果集
		List<RoleInf> list = new ArrayList<RoleInf>();

		// 查询当前参数中机构对应的所有角色
		List<RoleInf> list_tmp = this.queryRolesByOrg(roleInf);
		if (list_tmp != null && list_tmp.size() > 0) {
			list.addAll(list_tmp);
		}

		boolean hasNext = true;
		// 递归获取上级机构的所有角色
		while (hasNext) {

			// 获取当前机构
			String currentOrg = roleInf.getCreateorg();
			// 获取机构信息
			OrgInf orgInf = orgInfRepository.query(currentOrg);

			// 判断当前机构是否有上级机构
			if (orgInf == null
					|| "".equals(orgInf.getSuprorgid())
					|| StringUtil.trim(orgInf.getSuprorgid())
							.equals(currentOrg)) {
				// 不存在上级机构，跳出递归
				hasNext = false;
				break;

			} else {
				// 存在上级机构，查询上级机构所拥有的所有可供下级机构使用的角色

				// 获取上级机构
				String createorg = StringUtil.trim(orgInf.getSuprorgid());
				roleInf = new RoleInf();
				roleInf.setCreateorg(createorg);
				roleInf.setInfruseflag(RoleConst.DOWNUSE_USABLE);

				// 将上级机构赋值给当前机构，供下次循环使用
				currentOrg = createorg;

				// 查询上级机构所拥有的所有可供下级机构使用的角色
				list_tmp = this.queryRolesByOrg(roleInf);
				if (list_tmp != null && list_tmp.size() > 0) {
					list.addAll(list_tmp);
				}

				// 继续递归获取上级机构所拥有的所有可供下级机构使用的角色
				continue;
			}
		}

		// 将list中的角色去重
		list = this.remvReRoleInf(list);

		return list;
	}

	/**
	 * 根据机构Id查询该机构所拥有的所有角色
	 * 
	 * @param orgInf
	 *            机构
	 * @return 角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrg(RoleInf roleInf) {

		return roleInfRepository.queryRolesByOrg(roleInf);
	}

	/**
	 * 将list中的角色去重
	 * 
	 * @param list
	 *            待去重的角色列表
	 * @return 已去重的角色列表
	 */
	private List<RoleInf> remvReRoleInf(List<RoleInf> list) {
		List<RoleInf> list_new = new ArrayList<RoleInf>();

		// 申明Map存放<roleid, roleInfo>去重角色信息
		Map<String, RoleInf> map = new HashMap<String, RoleInf>();
		if (list != null && list.size() > 0) {
			for (RoleInf roleInf : list) {
				map.put(roleInf.getRoleid().trim(), roleInf);
			}

			list_new.addAll(map.values());
		}

		return list_new;
	}

	@Override
	public Page<RoleInf> transQueryRoleInfList(Pageable pageable,
			RoleInf roleInf) {
		Page<RoleInf> page = roleInfRepository.queryList(pageable, roleInf);
		return page;
	}

	@Override
	public RoleInf transQueryRoleInf(RoleInf roleInf) {
		roleInf = roleInfRepository.query(StringUtil.trim(roleInf.getRoleid()));
		roleInf.setMenulist(CommonUtil.increaseMenu(roleInf.getMenulist()));
		return roleInf;
	}

	@Override
	public Page<RoleInfTmp> transQueryRoleInfTmpList(Pageable pageable,
			RoleInfTmp roleInfTmp) {
		Page<RoleInfTmp> page = roleInfTmpRepository.queryList(pageable,
				roleInfTmp);
		return page;
	}

	@Override
	public RoleInfTmp transQueryRoleInfTmp(RoleInfTmp roleInfTmp) {
		roleInfTmp = roleInfTmpRepository.query(roleInfTmp);
		roleInfTmp
				.setMenulist(CommonUtil.increaseMenu(roleInfTmp.getMenulist()));
		return roleInfTmp;
	}

	/**
	 * 角色新增
	 */
	@Override
	@Transactional
	public void transAdd(RoleInfTmpForm form) {
		log.info("角色信息维护新增开始！");
		ResultMessages messages = ResultMessages.error();
		RoleInfTmp roleInfTmp = form.getRoleInfTmp();
		String roleId = form.getRoleid();
		String roleName = form.getRolename();
		roleInfTmp.setRoleid(roleId);
		roleInfTmp.setRolename(roleName);
		// 获取所有功能ID，新增角色功能表
		String menuStr = StringUtil.trim(form.getMeunlist());
		String[] menus = menuStr.split(CommonConst.UNSEPERATOR);
		// 1、判断角色信息表中是否存在相同角色ID的记录
		if (null == roleInfRepository.query(roleId)) {
			// 2、角色信息表中不存在相同角色ID的记录，判断角色信息维护临时表中是否存在相同角色ID且状态为01-待审核的记录
			if (roleInfTmpRepository.queryRoleInfo(roleInfTmp) == 0) {
				// 角色信息维护临时表中不存在相同角色ID且状态为01-待审核的记录
				// 3.1、将表单中的信息插入角色信息维护临时表中
				roleInfTmp.setId(numberService.getSysIDSequence("0000", 8));
				roleInfTmp
						.setCreater(ContextConst.getCurrentUser().getUserid());
				roleInfTmp.setCreatetime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				if (roleInfTmp.getInfruseflag() == null
						|| roleInfTmp.getInfruseflag().equals("")) {
					roleInfTmp.setInfruseflag("02");
				} else {
					roleInfTmp.setInfruseflag(roleInfTmp.getInfruseflag());
				}
				roleInfTmp.setOpttype("01");
				roleInfTmp.setOptstatus("01");
				if (roleInfTmpRepository.insertRoleInfTmp(roleInfTmp) != 1) {
					// 插入角色信息维护临时表异常处理
					log.error("插入角色信息维护临时表错误！");
					messages.add("e.sm.4013");
					throw new BusinessException(messages);
				}
				// 3.2、将表单中的角色功能信息插入到系统角色功能维护临时表中
				for (String funcId : menus) {
					SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
					sysRoleFuncTmp.setId(numberService.getSysIDSequence("0000",
							8));
					sysRoleFuncTmp.setFuncId(StringUtil.trim(funcId));
					sysRoleFuncTmp.setRoleid(StringUtil.trim(roleId));
					sysRoleFuncTmp.setCreater(ContextConst.getCurrentUser()
							.getUserid());
					sysRoleFuncTmp.setCreatetime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					sysRoleFuncTmp.setOpttype("01");
					sysRoleFuncTmp.setOptstatus("01");
					if (sysRoleFuncTmpReponsitory
							.insertSysRoleFuncTmp(sysRoleFuncTmp) != 1) {
						// 插入系统角色功能维护临时表异常处理
						log.error("插入系统角色功能维护临时表错误！");
						messages.add("e.sm.4053");
						throw new BusinessException(messages);
					}
				}
				// 4、记录操作日志
				roleInfTmp.setMenulist(funcIDToFuncName(menus));
				TlrLogPrint.tlrSysLogPrint("SM02_01",
						CommonConst.DATA_LOG_OPERTYPE_ADD, "",
						generateRoleInfTmpString(roleInfTmp));
				log.info("角色信息维护新增结束！");
			} else {
				// 角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录
				log.error("角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录！");
				messages.add("e.sm.4018");
				throw new BusinessException(messages);
			}
		} else {
			// 存在相同角色ID记录处理
			log.error("角色信息表中已经存在相同角色ID的记录！");
			messages.add("e.sm.4017");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 角色信息修改
	 */
	@Override
	@Transactional
	public void transUpdate(RoleInfForm form) {
		log.info("角色信息维护修改开始！");
		ResultMessages messages = ResultMessages.error();
		RoleInf roleInf = form.getRoleInf();
		roleInf.setRolename(StringUtil.trim(form.getRolename()));
		String roleId = StringUtil.trim(roleInf.getRoleid());

		RoleInfTmp roleInfTmp = new RoleInfTmp();
		roleInfTmp.setRoleid(roleId);
		// 查询原来的角色信息
		RoleInf oldRoleInf = new RoleInf();
		oldRoleInf = roleInfRepository.query(roleId);
		// 查询原来的系统角色功能信息
		SysRoleFunc sysRoleFunc = new SysRoleFunc();
		sysRoleFunc.setRoleId(roleId);
		List<SysRoleFunc> list = sysRoleFuncReponsitory
				.querySysRoleFunc(sysRoleFunc);
		// 获取所有功能ID
		String menuStr = StringUtil.trim(form.getMeunlist());
		String[] menus = menuStr.split(CommonConst.UNSEPERATOR);
		// 1、判断角色信息表中是否存在相同角色ID的记录
		if (null != oldRoleInf) {
			// 2、角色信息表中不存在相同角色ID的记录，判断角色信息维护临时表中是否存在相同角色ID且状态为01-待审核的记录
			if (roleInfTmpRepository.queryRoleInfo(roleInfTmp) == 0) {
				// 3.1、插入角色信息维护临时表
				roleInfTmp.setId(numberService.getSysIDSequence("0000", 8));
				roleInfTmp.setRoleid(roleInf.getRoleid());
				roleInfTmp.setRolename(roleInf.getRolename());
				roleInfTmp.setRoledesc(roleInf.getRoledesc());
				roleInfTmp.setCreateorg(roleInf.getCreateorg());
				if (roleInf.getInfruseflag() == null
						|| roleInf.getInfruseflag().equals("")) {
					roleInfTmp.setInfruseflag("02");
				} else {
					roleInfTmp.setInfruseflag(roleInf.getInfruseflag());
				}
				roleInfTmp
						.setCreater(ContextConst.getCurrentUser().getUserid());
				roleInfTmp.setCreatetime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				roleInfTmp.setOpttype("02");
				roleInfTmp.setOptstatus("01");
				if (roleInfTmpRepository.insertRoleInfTmp(roleInfTmp) != 1) {
					// 插入角色信息维护临时表异常
					log.error("插入角色信息维护临时表错误！");
					messages.add("e.sm.4013");
					throw new BusinessException(messages);
				}
				// 3.2、更新角色信息表状态
				oldRoleInf.setOptstatus("01");
				if (roleInfRepository.updateRoleInf(oldRoleInf) != 1) {
					// 更新角色信息表异常处理
					log.error("更新角色信息表错误！");
					messages.add("e.sm.4014");
					throw new BusinessException(messages);
				}
				// 3.3、插入系统角色功能维护临时表
				for (String funcId : menus) {
					SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
					sysRoleFuncTmp.setId(numberService.getSysIDSequence("0000",
							8));
					sysRoleFuncTmp.setFuncId(StringUtil.trim(funcId));
					sysRoleFuncTmp.setRoleid(StringUtil.trim(roleId));
					sysRoleFuncTmp.setCreater(ContextConst.getCurrentUser()
							.getUserid());
					sysRoleFuncTmp.setCreatetime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					sysRoleFuncTmp.setOpttype("02");
					sysRoleFuncTmp.setOptstatus("01");
					if (sysRoleFuncTmpReponsitory
							.insertSysRoleFuncTmp(sysRoleFuncTmp) != 1) {
						// 插入系统角色功能维护临时表异常处理
						log.error("插入系统角色功能维护临时表错误！");
						messages.add("e.sm.4053");
						throw new BusinessException(messages);
					}
				}
				// 3.4、更新系统角色功能表状态
				if (list.size() > 0) {
					// 将角色功能主表中记录设置为待审核
					sysRoleFunc.setRoleId(StringUtil.trim(sysRoleFunc
							.getRoleId()));
					sysRoleFunc.setOptstatus("01");
					sysRoleFunc.setUpdateTime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					sysRoleFunc.setUpdateUser(ContextConst.getCurrentUser()
							.getUserid());
					if (sysRoleFuncReponsitory.updateSysRoleFunc(sysRoleFunc) != list
							.size()) {
						// 更新系统角色功能表异常处理
						log.error("更新系统角色功能表错误！");
						messages.add("e.sm.4054");
						throw new BusinessException(messages);
					}
				} else {
					// 不错任何处理
					log.info("系统角色功能表中无该角色的功能描述！");
				}
				// 日志写入数据库
				roleInfTmp.setMenulist(funcIDToFuncName(menus));
				if (list.size() > 0) {
					oldRoleInf.setMenulist(funcIDToFuncName(list));
				} else {
					log.info("系统角色功能表中无该角色的功能描述！");
				}
				TlrLogPrint.tlrSysLogPrint("SM02_01",
						CommonConst.DATA_LOG_OPERTYPE_MODIFY,
						generateRoleInfString(oldRoleInf),
						generateRoleInfTmpString(roleInfTmp));
				log.info("角色信息维护修改结束！");
			} else {
				// 角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录
				log.error("角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录！");
				messages.add("e.sm.4018");
				throw new BusinessException(messages);
			}
		} else {
			// 不存在相同角色ID记录处理
			log.error("角色信息表中不存在该角色ID的记录！");
			messages.add("e.sm.4019");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 角色信息删除
	 */
	@Override
	@Transactional
	public void transDel(RoleInfForm form) {
		log.info("角色信息维护删除开始！");
		ResultMessages messages = ResultMessages.error();
		String roleId = StringUtil.trim(form.getRoleInf().getRoleid());

		RoleInfTmp roleInfTmp = new RoleInfTmp();
		roleInfTmp.setRoleid(roleId);

		RoleInf oldRoleInf = new RoleInf();
		oldRoleInf = roleInfRepository.query(roleId);

		//根据角色ID查询系统角色功能表中对应角色的功能信息
		SysRoleFunc sysRoleFunc = new SysRoleFunc();
		sysRoleFunc.setRoleId(roleId);
		List<SysRoleFunc> list = sysRoleFuncReponsitory
				.querySysRoleFunc(sysRoleFunc);
		// 1、判断角色信息表中是否存在相同角色ID的记录
		if (null != oldRoleInf) {
			// 2、判断该角色是否已经分配给用户使用
			if (userRoleInfRepository.queryUserRoleInfCnt(roleId) == 0) {
				// 3、角色信息表中不存在相同角色ID的记录，判断角色信息维护临时表中是否存在相同角色ID且状态为01-待审核的记录
				if (roleInfTmpRepository.queryRoleInfo(roleInfTmp) == 0) {
					// 4.1、插入角色信息维护临时表
					roleInfTmp.setId(numberService.getSysIDSequence("0000", 8));
					roleInfTmp.setRoleid(oldRoleInf.getRoleid());
					roleInfTmp.setRolename(oldRoleInf.getRolename());
					roleInfTmp.setRoledesc(oldRoleInf.getRoledesc());
					roleInfTmp.setCreateorg(oldRoleInf.getCreateorg());
					roleInfTmp.setInfruseflag(oldRoleInf.getInfruseflag());
					roleInfTmp.setCreater(ContextConst.getCurrentUser()
							.getUserid());
					roleInfTmp.setCreatetime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					roleInfTmp.setOpttype("03");
					roleInfTmp.setOptstatus("01");
					if (roleInfTmpRepository.insertRoleInfTmp(roleInfTmp) != 1) {
						// 插入角色信息维护临时表异常
						log.error("插入角色信息维护临时表错误！");
						messages.add("e.sm.4013");
						throw new BusinessException(messages);
					}
					// 4.2、更新角色信息表状态
					oldRoleInf.setOptstatus("01");
					if (roleInfRepository.updateRoleInf(oldRoleInf) != 1) {
						// 更新角色信息表异常处理
						log.error("更新角色信息表错误！");
						messages.add("e.sm.4014");
						throw new BusinessException(messages);
					}
					// 4.3、插入系统角色功能维护临时表及更新系统角色功能表
					if (list.size() > 0) {
						for (SysRoleFunc tmp : list) {
							// 4.3.1、插入系统角色功能维护临时表
							SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
							sysRoleFuncTmp.setId(numberService
									.getSysIDSequence("0000", 8));
							sysRoleFuncTmp.setFuncId(StringUtil.trim(tmp
									.getFuncId()));
							sysRoleFuncTmp.setRoleid(roleId);
							sysRoleFuncTmp.setCreater(ContextConst
									.getCurrentUser().getUserid());
							sysRoleFuncTmp
									.setCreatetime(DateUtil
											.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							sysRoleFuncTmp.setOpttype("03");
							sysRoleFuncTmp.setOptstatus("01");
							if (sysRoleFuncTmpReponsitory
									.insertSysRoleFuncTmp(sysRoleFuncTmp) != 1) {
								// 插入系统角色功能维护临时表异常处理
								log.error("插入系统角色功能维护临时表错误！");
								messages.add("e.sm.4053");
								throw new BusinessException(messages);
							}
						}
						// 4.3.2、更新系统角色功能表
						sysRoleFunc.setRoleId(StringUtil.trim(sysRoleFunc
								.getRoleId()));
						sysRoleFunc.setOptstatus("01");
						sysRoleFunc.setUpdateTime(DateUtil
								.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						sysRoleFunc.setUpdateUser(ContextConst.getCurrentUser()
								.getUserid());
						if (sysRoleFuncReponsitory
								.updateSysRoleFunc(sysRoleFunc) != list.size()) {
							// 更新系统角色功能表异常处理
							log.error("更新系统角色功能表错误！");
							messages.add("e.sm.4054");
							throw new BusinessException(messages);
						}
					} else {
						// 不错任何处理
						log.info("系统角色功能表中无该角色的功能描述！");
					}
					// 5、记录操作员日志
					if (list.size() > 0) {
						roleInfTmp.setMenulist(funcIDToFuncName(list));
					} else {
						log.info("系统角色功能表中无该角色的功能描述！");
					}
					TlrLogPrint.tlrSysLogPrint("SM02_01",
							CommonConst.DATA_LOG_OPERTYPE_DELETE,
							generateRoleInfTmpString(roleInfTmp), "");
					log.info("角色信息维护删除结束！");
				} else {
					// 角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录
					log.error("角色信息维护临时表中存在相同角色ID且状态为01-待审核的记录！");
					messages.add("e.sm.4018");
					throw new BusinessException(messages);
				}
			} else {
				// 角色ID已分配给用户使用
				log.error("角色ID已分配给用户使用！");
				messages.add("e.sm.4050");
				throw new BusinessException(messages);
			}
		} else {
			// 不存在相同角色ID记录处理
			log.error("角色信息表中不存在该角色ID的记录！");
			messages.add("e.sm.4019");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 角色信息审核
	 */
	@Override
	@Transactional
	public void transAuth(RoleInfTmp roleInfTmp) {
		log.info("角色信息维护审核开始！");
		ResultMessages messages = ResultMessages.error();
		RoleInf roleInf = new RoleInf();
		// 根据角色信息维护临时表中的ID查询出对应的记录
		roleInfTmp = roleInfTmpRepository.query(roleInfTmp);
		// 1、判断角色信息维护临时表中是否存在该信息
		if (null != roleInfTmp) {
			// 2、角色信息维护了临时表中存在该记录，根据角色ID查询出系统角色功能维护临时表中的待审核记录（及操作状态为：01-待审核）
			SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
			sysRoleFuncTmp.setRoleid(StringUtil.trim(roleInfTmp.getRoleid()));
			sysRoleFuncTmp.setOptstatus("01");
			List<SysRoleFuncTmp> tmplist = sysRoleFuncTmpReponsitory
					.querySysRoleFuncTmp(sysRoleFuncTmp);
			// 根据角色ID查询出系统角色功能表中该角色的所有记录
			SysRoleFunc sysRoleFunc = new SysRoleFunc();
			sysRoleFunc.setRoleId(StringUtil.trim(roleInfTmp.getRoleid()));
			List<SysRoleFunc> list = sysRoleFuncReponsitory
					.querySysRoleFunc(sysRoleFunc);
			// 2、判断录入操作员和审核操作员是否为同一人
			if (CommonUtil.compareTlr(roleInfTmp.getCreater()) == 1) {
				// 不为同一人时处理
				// 3、判断维护类型
				String opttype = roleInfTmp.getOpttype();
				// 维护类型为01-新增
				if (opttype.equals("01")) {
					// 3.1.1、将角色信息维护临时表中的数据插入到角色信息表中
					roleInf = setValueToRoleInf(roleInfTmp);
					roleInf.setCreater(roleInfTmp.getCreater());
					roleInf.setCreatetime(roleInfTmp.getCreatetime());
					if (roleInfRepository.insertRoleInf(roleInf) != 1) {
						// 插入角色信息表异常处理
						log.error("插入角色信息表错误！");
						messages.add("e.sm.4012");
						throw new BusinessException(messages);
					}
					// 3.1.2、判断是否需要对系统角色功能维护临时表进行操作
					if (tmplist.size() > 0) {
						// 需对系统角色功能维护临时表的中数据进行相应操作
						for (SysRoleFuncTmp tmp : tmplist) {
							// 3.1.2.1、将信息插入到系统角色功能表中
							SysRoleFunc func = new SysRoleFunc();
							func = setValueToSysRoleFunc(tmp);
							func.setOptstatus("02");
							if (sysRoleFuncReponsitory.insertSysRoleFunc(func) != 1) {
								// 插入系统角色功能表异常处理
								log.error("插入系统角色功能表错误！");
								messages.add("e.sm.4012");
								throw new BusinessException(messages);
							}
						}
					} else {
						// 系统角色功能维护临时表中不存在相应记录更新时，直接进行一下步操作
						log.info("系统角色功能维护临时表中不存在需要处理的记录，无法进行新增操作！");
					}
					// 3.1.3记录操作日志
					log.info("角色新增审核-记录操作日志！");
					if (tmplist.size() > 0) {
						// 获取功能信息
						log.info("获取新增时的系统角色功能维护临时表中信息！");
						roleInfTmp.setMenulist(funcIDToFuncNameTmp(tmplist));
					} else {
						// 系统角色功能维护临时表中不存在相应记录更新时，直接进行一下步操作
						log.info("系统角色功能维护临时表中不存在需要处理的记录，无法获取功能信息！");
					}
					TlrLogPrint.tlrSysLogPrint("SM02_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK, "",
							generateRoleInfTmpString(roleInfTmp));
					// 维护类型为02-修改
				} else if (opttype.equals("02")) {
					// 3.2.1、将角色信息维护临时表中的数据插入到角色信息表中
					roleInf = setValueToRoleInf(roleInfTmp);
					roleInf.setLastoperator(roleInfTmp.getCreater());
					roleInf.setLastopttime(roleInfTmp.getCreatetime());
					if (roleInfRepository.updateRoleInf(roleInf) != 1) {
						messages.add("e.sm.4014");
						throw new BusinessException(messages);
					}
					// 3.2.2、对系统角色功能表的操作
					// 3.2.2.2、判断是否需要对系统角色功能表进行操作
					if (list.size() > 0) {
						// 3.2.2.2.1、删除系统角色功能表中的该角色的所有功能记录
						sysRoleFunc.setRoleId(StringUtil.trim(sysRoleFunc
								.getRoleId()));
						if (sysRoleFuncReponsitory
								.deleteSysRoleFunc(sysRoleFunc) != list.size()) {
							// 删除系统角色功能表异常处理
							log.error("删除系统角色功能表错误！");
							messages.add("e.sm.4051");
							throw new BusinessException(messages);
						}
					} else {
						// 不需要对系统角色功能表进行操作
						log.info("系统角色表中无该角色的相关功能能信息，无法进行删除操作！");
					}
					// 3.2.2.3、判断是否需要对系统角色功能维护临时表进行操作
					if (tmplist.size() > 0) {
						// 3.2.2.3.1、将系统角色功能维护临时表中的数据插入到系统角色功能表中
						for (SysRoleFuncTmp tmp3 : tmplist) {
							SysRoleFunc func2 = new SysRoleFunc();
							func2 = setValueToSysRoleFunc(tmp3);
							func2.setOptstatus("02");
							if (sysRoleFuncReponsitory.insertSysRoleFunc(func2) != 1) {
								// 插入系统角色功能表异常处理
								log.error("插入系统角色功能表错误！");
								messages.add("e.sm.4012");
								throw new BusinessException(messages);
							}
						}
					} else {
						// 不需要对系统角色功能维护临时表进行操作
						log.info("系统角色功能维护临时表中不存在需要处理的记录，无法进行更新操作");
					}
					// 3.2.3、记录操作日志
					log.info("角色修改审核-记录操作日志！");
					if (list.size() > 0) {
						log.info("获取修改时系统角色功能表中信息！");
						roleInf.setMenulist(funcIDToFuncName(list));
					} else {
						// 不需要对系统角色功能表进行操作
						log.info("系统角色功能表中无该角色的相关功能能信息，无法获取功能信息！");
					}
					if (tmplist.size() > 0) {
						log.info("获取修改时员系统角色功能维护临时表中信息！");
						roleInfTmp.setMenulist(funcIDToFuncNameTmp(tmplist));
					} else {
						// 不需要对系统角色功能维护临时表进行操作
						log.info("系统角色功能维护临时表中无该角色的相关功能能信息，无法获取功能信息！！");
					}
					TlrLogPrint.tlrSysLogPrint("SM02_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK,
							generateRoleInfString(roleInf),
							generateRoleInfTmpString(roleInfTmp));
					// 维护类型为03-删除
				} else if (opttype.equals("03")) {
					// 3.3.3、删除角色功能正式表中的数据
					// 3.3.3.1、删除系统角色功能表中的数据
					if (list.size() > 0) {
						// 3.3.3.1.1、删除系统角色功能表中的该角色的所有功能记录
						sysRoleFunc.setRoleId(StringUtil.trim(sysRoleFunc
								.getRoleId()));
						if (sysRoleFuncReponsitory
								.deleteSysRoleFunc(sysRoleFunc) != list.size()) {
							// 删除系统角色功能表异常处理
							log.error("删除系统角色功能表错误！");
							messages.add("e.sm.4051");
							throw new BusinessException(messages);
						}
					} else {
						// 不需要对系统角色功能表进行操作
						log.info("系统角色表中无该角色的相关功能能信息，无法进行删除操作！");
					}
					// 3.3.3.2、删除角色表中数据
					String roleId = StringUtil.trim(roleInfTmp.getRoleid());
					roleInf = roleInfRepository.query(roleId);
					if (roleInfRepository.deleteRoleInf(roleInf) != 1) {
						// 删除角色信息表异常处理
						messages.add("e.sm.4016");
						throw new BusinessException(messages);
					}
					// 3.3.3.3、记录操作日志
					log.info("角色删除审核-记录操作日志！");
					if (list.size() > 0) {
						log.info("获取删除时系统角色功能表中信息！");
						roleInf.setMenulist(funcIDToFuncName(list));
					} else {
						// 不需要对系统角色功能表进行操作
						log.info("系统角色功能表中无该角色的相关功能能信息，无法获取功能信息！");
					}
					TlrLogPrint.tlrSysLogPrint("SM02_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK,
							generateRoleInfString(roleInf), "");
				} else {
					// 维护类型不存在
					log.error("维护类型不能为空！");
					messages.add("e.sm.4010");
					throw new BusinessException(messages);
				}
				// 4.1、更新角色信息维护临时表状态
				roleInfTmp
						.setChecker(ContextConst.getCurrentUser().getUserid());
				roleInfTmp.setChecktime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				roleInfTmp.setOptstatus("02");
				if (roleInfTmpRepository.updateRoleInfTmp(roleInfTmp) != 1) {
					// 更新角色信息维护临时表异常处理
					log.error("更新角色信息维护临时表错误！");
					messages.add("e.sm.4013");
					throw new BusinessException(messages);
				}
				// 4.2、更新系统角色功能维护临时表状态
				if (tmplist.size() > 0) {
					log.info("更新系统角色功能维护临时表信息！");
					sysRoleFuncTmp.setRoleid(StringUtil.trim(sysRoleFuncTmp
							.getRoleid()));
					sysRoleFuncTmp.setChecker(StringUtil.trim(ContextConst
							.getCurrentUser().getUserid()));
					sysRoleFuncTmp.setChecktime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					sysRoleFuncTmp.setOptstatus("02");
					if (sysRoleFuncTmpReponsitory
							.updateSysRoleFuncTmp(sysRoleFuncTmp) != tmplist
							.size()) {
						// 更新系统角色功能维护临时表异常处理
						log.error("更新系统角色功能维护临时表错误！");
						messages.add("e.sm.4052");
						throw new BusinessException(messages);
					}
				} else {
					// 不需要对系统角色功能维护临时表进行操作
					log.info("系统角色功能维护临时表中无该角色的相关功能能信息！");
				}
				log.info("角色信息维护审核结束！");
			} else {
				// 为同一人时处理
				log.error("录入操作员与审核操作员为同一人！");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			// 角色信息维护临时表中不存在该信息时处理
			log.error("角色信息维护临时表中不存在该记录！");
			messages.add("e.sm.4020");
			throw new BusinessException(messages);
		}

	}

	/**
	 * 角色维护拒绝
	 */
	@Override
	@Transactional
	public void transRejct(RoleInfTmp roleInfTmp) {
		log.info("角色信息维护审核拒绝开始！");
		ResultMessages messages = ResultMessages.error();
		RoleInf roleInf = new RoleInf();
		roleInf=roleInfRepository.query(StringUtil.trim(roleInfTmp.getRoleid()));
		// 根据角色信息维护临时表中的ID查询出对应的记录
		roleInfTmp = roleInfTmpRepository.query(roleInfTmp);
		// 1、判断角色信息维护临时表中是否存在该信息
		if (null != roleInfTmp) {
			// 根据角色ID查询出系统角色功能维护临时表中的待审核记录（及操作状态为：01-待审核）
			SysRoleFuncTmp sysRoleFuncTmp = new SysRoleFuncTmp();
			sysRoleFuncTmp.setRoleid(StringUtil.trim(roleInfTmp.getRoleid()));
			sysRoleFuncTmp.setOptstatus("01");
			List<SysRoleFuncTmp> tmplist = sysRoleFuncTmpReponsitory
					.querySysRoleFuncTmp(sysRoleFuncTmp);
			// 根据角色ID查询出系统角色功能表中该角色的所有记录
			SysRoleFunc sysRoleFunc = new SysRoleFunc();
			sysRoleFunc.setRoleId(StringUtil.trim(roleInfTmp.getRoleid()));
			List<SysRoleFunc> list = sysRoleFuncReponsitory
					.querySysRoleFunc(sysRoleFunc);

			// 2、判断录入操作员和审核操作员是否为同一人
			if (CommonUtil.compareTlr(roleInfTmp.getCreater()) == 1) {
				// 不为同一人时处理
				// 3、判断维护类型
				String opttype = roleInfTmp.getOpttype();
				log.info("维护类型为：" + opttype);
				// 3.1、维护类型为02-修改或03-删除
				if (opttype.equals("02") || opttype.equals("03")) {
					// 3.1.1、更新角色信息表信息，更新操作状态及最后操作员、最后操作时间
					roleInf.setLastoperator(roleInfTmp.getCreater());
					roleInf.setLastopttime(roleInfTmp.getCreatetime());
					roleInf.setOptstatus("02");
					if (roleInfRepository.updateRoleInf(roleInf) != 1) {
						// 更新角色信息表异常处理
						log.error("更新角色信息表错误！");
						messages.add("e.sm.4014");
						throw new BusinessException(messages);
					}
					// 3.1.2、更新系统角色功能表，更新操作状态
					if (list.size() > 0) {
						sysRoleFunc.setRoleId(StringUtil.trim(sysRoleFunc
								.getRoleId()));
						sysRoleFunc.setOptstatus("02");
						sysRoleFunc.setUpdateTime(DateUtil
								.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						sysRoleFunc.setUpdateUser(ContextConst.getCurrentUser()
								.getUserid());
						if (sysRoleFuncReponsitory
								.updateSysRoleFunc(sysRoleFunc) != list.size()) {
							// 更新系统角色功能表异常处理
							log.error("更新系统角色功能表错误！");
							messages.add("e.sm.4054");
							throw new BusinessException(messages);
						}
					}
					// 3.1.3、记录操作员日志
					// 维护类型为02-修改
					if (opttype.equals("02")) {
						// 3.1.3.1、记录维护类型为修改时的操作日志
						log.info("角色修改拒绝-记录操作日志！");
						if (list.size() > 0) {
							log.info("获取修改时系统角色功能表中信息！");
							roleInf.setMenulist(funcIDToFuncName(list));
						} else {
							// 不需要对系统角色功能表进行操作
							log.info("系统角色功能表中无该角色的相关功能能信息，无法获取功能信息！");
						}
						if (tmplist.size() > 0) {
							log.info("获取修改时员系统角色功能维护临时表中信息！");
							roleInfTmp
									.setMenulist(funcIDToFuncNameTmp(tmplist));
						} else {
							// 不需要对系统角色功能维护临时表进行操作
							log.info("系统角色功能维护临时表中无该角色的相关功能能信息，无法获取功能信息！！");
						}
						TlrLogPrint.tlrSysLogPrint("SM02_02",
								CommonConst.DATA_LOG_OPERTYPE_REJECT,
								generateRoleInfString(roleInf),
								generateRoleInfTmpString(roleInfTmp));
						// 维护类型为03-删除
					} else if (opttype.equals("03")) {
						// 日志写入数据库
						log.info("角色删除拒绝-记录操作日志！");
						if (list.size() > 0) {
							log.info("获取修改时系统角色功能表中信息！");
							roleInf.setMenulist(funcIDToFuncName(list));
						} else {
							// 不需要对系统角色功能表进行操作
							log.info("系统角色功能表中无该角色的相关功能能信息，无法获取功能信息！");
						}
						TlrLogPrint.tlrSysLogPrint("SM02_02",
								CommonConst.DATA_LOG_OPERTYPE_REJECT,
								generateRoleInfString(roleInf), "");
					}
					// 3.2、维护类型为01-新增
				} else if (opttype.equals("01")) {
					// 3.2.1、记录操作员日志
					log.info("角色新增拒绝-记录操作日志！");
					if (tmplist.size() > 0) {
						log.info("获取修改时员系统角色功能维护临时表中信息！");
						roleInfTmp.setMenulist(funcIDToFuncNameTmp(tmplist));
					} else {
						// 不需要对系统角色功能维护临时表进行操作
						log.info("系统角色功能维护临时表中无该角色的相关功能能信息，无法获取功能信息！！");
					}
					TlrLogPrint.tlrSysLogPrint("SM02_02",
							CommonConst.DATA_LOG_OPERTYPE_REJECT,
							generateRoleInfTmpString(roleInfTmp), "");
				} else {
					// 维护类型不存在
					log.error("维护类型不能为空！");
					messages.add("e.sm.4010");
					throw new BusinessException(messages);

				}
				// 3.3、更新系统角色功能维护临时表及角色信息维护临时表信息
				// 3.3.1、更新系统角色功能维护临时表
				if (tmplist.size() > 0) {
					log.info("更新系统角色功能维护临时表信息！");
					sysRoleFuncTmp.setRoleid(StringUtil.trim(sysRoleFuncTmp
							.getRoleid()));
					sysRoleFuncTmp.setChecker(StringUtil.trim(ContextConst
							.getCurrentUser().getUserid()));
					sysRoleFuncTmp.setChecktime(DateUtil
							.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
					sysRoleFuncTmp.setOptstatus("03");
					if (sysRoleFuncTmpReponsitory
							.updateSysRoleFuncTmp(sysRoleFuncTmp) != tmplist
							.size()) {
						// 更新系统角色功能维护临时表异常处理
						log.error("更新系统角色功能维护临时表错误！");
						messages.add("e.sm.4052");
						throw new BusinessException(messages);
					}
				} else {
					// 不需要对系统角色功能维护临时表进行操作
					log.info("系统角色功能维护临时表中无该角色的相关功能能信息！");
				}
				// 3.3.2、角色信息维护临时表信息
				roleInfTmp
						.setChecker(ContextConst.getCurrentUser().getUserid());
				roleInfTmp.setChecktime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				roleInfTmp.setOptstatus("03");
				if (roleInfTmpRepository.updateRoleInfTmp(roleInfTmp) != 1) {
					// 更新角色信息维护临时表异常处理
					log.error("更新角色信息维护临时表错误！");
					messages.add("e.sm.4013");
					throw new BusinessException(messages);
				}
				log.info("角色信息维护拒绝结束！");
			} else {
				// 录入操作员与审核操作员为同一人
				log.error("录入操作员与审核操作员为同一人！");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			// 角色信息维护临时表中不存在该信息时处理
			log.error("角色信息维护临时表中不存在该记录！");
			messages.add("e.sm.4020");
			throw new BusinessException(messages);
		}
	}

	public RoleInf setValueToRoleInf(RoleInfTmp roleInfTmp) {
		RoleInf roleInf = new RoleInf();
		roleInf.setRoleid(StringUtil.trim((roleInfTmp.getRoleid())));
		roleInf.setRolename(roleInfTmp.getRolename());
		roleInf.setRoledesc(roleInfTmp.getRoledesc());
		roleInf.setCreateorg(roleInfTmp.getCreateorg());
		String flag = roleInfTmp.getInfruseflag();
		if (flag == null || flag.equals("")) {
			flag = "02";
		}
		roleInf.setInfruseflag(flag);
		roleInf.setMenulist(roleInfTmp.getMenulist());
		roleInf.setChecker(ContextConst.getCurrentUser().getUserid());
		roleInf.setChecktime(DateUtil
				.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		roleInf.setOptstatus("02");
		return roleInf;
	}

	public SysRoleFunc setValueToSysRoleFunc(SysRoleFuncTmp sysRoleFuncTmp) {
		SysRoleFunc sysRoleFunc = new SysRoleFunc();
		sysRoleFunc.setRoleId(sysRoleFuncTmp.getRoleid());
		sysRoleFunc.setFuncId(sysRoleFuncTmp.getFuncId());
		sysRoleFunc.setCreateUser(sysRoleFuncTmp.getCreater());
		sysRoleFunc.setCreateTime(sysRoleFuncTmp.getCreatetime());

		return sysRoleFunc;
	}

	/**
	 * list_RoleInf排除list中所有的角色
	 * 
	 * @param list
	 * @param list_RoleInf
	 */
	private void removeSpecialRoleTmp(List<UserRoleInfTmp> list,
			List<RoleInf> list_RoleInf) {

		// list_RoleInf排除list中所有的角色
		if (list_RoleInf != null && list_RoleInf.size() > 0) {

			// 用来存储所有需要排除的角色Id
			Map<String, String> map = new HashMap<String, String>();

			for (UserRoleInfTmp userRoleInfTmp : list) {
				if (userRoleInfTmp != null
						&& userRoleInfTmp.getRoleid() != null) {
					map.put(userRoleInfTmp.getRoleid().trim(), "");
				}
			}
			List<RoleInf> listTmp = new ArrayList<RoleInf>();
			for (RoleInf roleInf : list_RoleInf) {
				if (roleInf != null && roleInf.getRoleid() != null) {
					if (map.containsKey(roleInf.getRoleid().trim())) {
						listTmp.add(roleInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_RoleInf.removeAll(listTmp);
			}
		}
	}

	/**
	 * list_RoleInf排除list中所有的角色
	 * 
	 * @param list
	 * @param list_RoleInf
	 */
	private void removeSpecialRoleArr(List<String> list,
			List<RoleInf> list_RoleInf) {

		// list_RoleInf排除list中所有的角色
		if (list_RoleInf != null && list_RoleInf.size() > 0) {

			// 用来存储所有需要排除的角色Id
			Map<String, String> map = new HashMap<String, String>();

			for (String roleId : list) {
				if (roleId != null && roleId != null) {
					map.put(roleId.trim(), "");
				}
			}
			List<RoleInf> listTmp = new ArrayList<RoleInf>();
			for (RoleInf roleInf : list_RoleInf) {
				if (roleInf != null && roleInf.getRoleid() != null) {
					if (map.containsKey(roleInf.getRoleid().trim())) {
						listTmp.add(roleInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_RoleInf.removeAll(listTmp);
			}
		}
	}

	/**
	 * list_RoleInf排除list中所有的角色
	 * 
	 * @param list
	 * @param list_RoleInf
	 */
	private void removeSpecialRole(List<UserRoleInf> list,
			List<RoleInf> list_RoleInf) {

		// list_RoleInf排除list中所有的角色
		if (list_RoleInf != null && list_RoleInf.size() > 0) {

			// 用来存储所有需要排除的角色Id
			Map<String, String> map = new HashMap<String, String>();

			for (UserRoleInf userRoleInf : list) {
				if (userRoleInf != null && userRoleInf.getRoleid() != null) {
					map.put(userRoleInf.getRoleid().trim(), "");
				}
			}
			List<RoleInf> listTmp = new ArrayList<RoleInf>();
			for (RoleInf roleInf : list_RoleInf) {
				if (roleInf != null && roleInf.getRoleid() != null) {
					if (map.containsKey(roleInf.getRoleid().trim())) {
						listTmp.add(roleInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_RoleInf.removeAll(listTmp);
			}
		}
	}

	private String generateRoleInfTmpString(RoleInfTmp roleInfTmp) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("index.label.sm.RoleId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getRoleid())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.RoleName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getRolename())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.RoleDesc"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getRoledesc())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.MenuList"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getMenulist())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.CreateOrg"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getCreateorg())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.InfrUseFlag"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInfTmp.getInfruseflag())
				.append(CommonConst.SEPARATE_TWO_FIELD);

		return sb.toString();
	}

	private String generateRoleInfString(RoleInf roleInf) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("index.label.sm.RoleId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getRoleid())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.RoleName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getRolename())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.RoleDesc"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getRoledesc())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.MenuList"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getMenulist())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.CreateOrg"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getCreateorg())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.InfrUseFlag"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(roleInf.getInfruseflag())
				.append(CommonConst.SEPARATE_TWO_FIELD);

		return sb.toString();
	}

	/** 根据功能ID获取功能描述 **/
	public String funcIDToFuncName(String[] menus) {
		// 角色可用功能
		String funcNames = "";
		// 获取功能ID对应功能名称的Map
		Map<String, String> funcNameMap = sysFuncInfoRepository
				.queryAllSysFuncInfo();
		for (String funcId : menus) {
			if (funcNameMap.get(funcId)!=null && funcNameMap.get(funcId).length() > 0) {
				funcNames = funcNames + funcNameMap.get(funcId);
			}
		}
		return funcNames;
	}

	/** 根据功能ID获取功能描述 **/
	public String funcIDToFuncName(List<SysRoleFunc> sysRoleFuncList) {
		// 角色可用功能
		String funcNames = "";
		// 获取功能ID对应功能名称的Map
		Map<String, String> funcNameMap = sysFuncInfoRepository
				.queryAllSysFuncInfo();
		for (SysRoleFunc sysRoleFunc : sysRoleFuncList) {
			String funcId = StringUtil.trim(sysRoleFunc.getFuncId());
			if (funcNameMap.get(funcId)!=null && funcNameMap.get(funcId).length() > 0) {
				funcNames = funcNames + funcNameMap.get(funcId) + ",";
			}
		}
		return funcNames;
	}

	/** 根据功能ID获取功能描述 **/
	public String funcIDToFuncNameTmp(List<SysRoleFuncTmp> sysRoleFuncTmpList) {
		// 角色可用功能
		String funcNames = "";
		// 获取功能ID对应功能名称的Map
		Map<String, String> funcNameMap = sysFuncInfoRepository
				.queryAllSysFuncInfo();
		for (SysRoleFuncTmp sysRoleFuncTmp : sysRoleFuncTmpList) {
			String funcId = StringUtil.trim(sysRoleFuncTmp.getFuncId());
			if (funcNameMap.get(funcId)!=null && funcNameMap.get(funcId).length() > 0) {
				funcNames = funcNames + funcNameMap.get(funcId) + ",";
			}
		}
		return funcNames;
	}

	@Autowired
	protected RoleInfRepository roleInfRepository;

	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;

	@Autowired
	protected RoleInfTmpRepository roleInfTmpRepository;

	@Autowired
	protected OrgInfRepository orgInfRepository;

	@Autowired
	protected NumberService numberService;

	@Autowired
	protected SysRoleFuncTmpReponsitory sysRoleFuncTmpReponsitory;

	@Autowired
	protected SysRoleFuncReponsitory sysRoleFuncReponsitory;

	@Autowired
	protected SysFuncInfoRepository sysFuncInfoRepository;

	private MessagesUtil message = MessagesUtil.getInstance();
}
