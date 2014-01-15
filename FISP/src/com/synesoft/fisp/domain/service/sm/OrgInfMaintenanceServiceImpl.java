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
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.app.sm.model.OrgInfForm;
import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.OrgInfTmp;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.OrgInfTmpRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserOrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Service("orgInfMaintenanceService")
public class OrgInfMaintenanceServiceImpl implements OrgInfMaintenanceService {

	private static LogUtil log = new LogUtil(OrgInfMaintenanceServiceImpl.class);

	@Override
	public Page<OrgInf> transQueryOrgInfList(Pageable pageable, OrgInf orgInf) {
		Page<OrgInf> page = orgInfRepository.queryList(pageable, orgInf);
		return page;
	}

	@Override
	public List<OrgInf> transQueryOrgInfList() {
		OrgInf orgInf = new OrgInf();
		return orgInfRepository.queryOrgInfList(orgInf);
	}

	@Override
	public List<OrgInf> transQueryOrgInfList(List<UserOrgInf> list) {
		List<OrgInf> list_OrgInf = this.transQueryOrgInfList();
		// list_OrgInf排除list中所有的机构
		removeSpecialOrg(list, list_OrgInf);
		return list_OrgInf;
	}

	@Override
	public List<OrgInf> transQueryOrgInfListArr(List<String> list) {
		List<OrgInf> list_OrgInf = this.transQueryOrgInfList();
		// list_OrgInf排除list中所有的机构
		removeSpecialOrgArr(list, list_OrgInf);
		return list_OrgInf;
	}

	@Override
	public List<OrgInf> transQueryOrgInfListTmp(List<UserOrgInfTmp> list) {
		List<OrgInf> list_OrgInf = this.transQueryOrgInfList();
		// list_OrgInf排除list中所有的机构
		removeSpecialOrgTmp(list, list_OrgInf);
		return list_OrgInf;
	}

	@Override
	public List<OrgInf> transQueryOrgInfList(String orgIdList) {
		return orgInfRepository.queryOrgInfList(orgIdList);
	}

	@Override
	public OrgInf transQueryOrgInf(OrgInf orgInf) {
		orgInf = orgInfRepository.query(StringUtil.trim(orgInf.getOrgid()));
		return orgInf;
	}

	@Override
	public Page<OrgInfTmp> transQueryOrgInfTmpList(Pageable pageable,
			OrgInfTmp orgInfTmp) {
		Page<OrgInfTmp> page = orgInfTmpRepository.queryList(pageable,
				orgInfTmp);
		return page;
	}

	@Override
	public OrgInfTmp transQueryOrgInfTmp(OrgInfTmp orgInfTmp) {
		orgInfTmp = orgInfTmpRepository.query(orgInfTmp);
		return orgInfTmp;
	}

	/**
	 * 机构信息新增
	 */
	@Override
	@Transactional
	public void transAdd(OrgInfTmpForm form) {
		log.info("机构信息维护新增开始！");
		ResultMessages messages = ResultMessages.error();
		OrgInfTmp orgInfTmp = form.getOrgInfTmp();
		String orgId = form.getOrgid();
		orgInfTmp.setOrgid(orgId);
		orgInfTmp.setSuprorgid(form.getSuprorgid());
		// 批量报送平台不需要银行代码信息
		// orgInfTmp.setBankid(form.getBankid());
		 orgInfTmp.setOrgname(form.getOrgname());
		// 1、查询机构信息表中是否存在相同机构Id的记录
		if (null == orgInfRepository.query(orgId)) {
			// 2、机构信息表中不存在该记录，查询机构信息维护临时表中是否存在相同机构ID且操作状态处于01-待审核的记录
			if (orgInfTmpRepository.queryOrgInfo(orgInfTmp) == 0) {
				// 3、将表单中的信息插入机构信息维护临时表
				orgInfTmp.setId(numberService.getSysIDSequence("0000", 8));
				orgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				orgInfTmp.setCreatetime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				orgInfTmp.setOpttype("01");
				orgInfTmp.setOptstatus("01");
				if (orgInfTmpRepository.insertOrgInfTmp(orgInfTmp) != 1) {
					// 插入机构信息维护临时表异常处理
					log.error("插入机构信息维护临时表错误！");
					messages.add("e.sm.4002");
					throw new BusinessException(messages);
				}
				// 4、记录操作日志
				TlrLogPrint.tlrSysLogPrint("SM01_01",
						CommonConst.DATA_LOG_OPERTYPE_ADD, "",
						generateOrgInfTmpString(orgInfTmp));
				log.info("机构信息维护新增结束！");
			} else {
				// 机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录
				log.error("机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录！");
				messages.add("e.sm.4007");
				throw new BusinessException(messages);
			}
		} else {
			// 机构信息表中存在相同机构Id的记录
			log.error("机构信息表中存在相同机构Id的记录！");
			messages.add("e.sm.4006");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 机构信息修改
	 */
	@Override
	@Transactional
	public void transUpdate(OrgInfForm form) {
		log.info("机构信息维护修改开始！");
		ResultMessages messages = ResultMessages.error();
		OrgInf orgInf = form.getOrgInf();
		String orgId = StringUtil.trim(orgInf.getOrgid());
		orgInf.setOrgid(orgId);

		OrgInfTmp orgInfTmp = new OrgInfTmp();
		orgInfTmp.setOrgid(orgId);
		// 保留原来机构信息
		OrgInf oldOrgInf = new OrgInf();
		oldOrgInf = orgInfRepository.query(orgId);
		// 1、查询机构信息表中是否存在相同机构Id的记录
		if (null != oldOrgInf) {
			// 2、机构信息表中存在该记录，查询机构信息维护临时表中是否存在相同机构ID且操作状态处于01-待审核的记录
			if (orgInfTmpRepository.queryOrgInfo(orgInfTmp) == 0) {
				// 3.1、将表单中的信息插入机构信息维护临时表
				orgInfTmp.setId(numberService.getSysIDSequence("0000", 8));
				orgInfTmp.setOrgid(orgInf.getOrgid());
				orgInfTmp.setOrgname(form.getOrgname());
				orgInfTmp.setSuprorgid(form.getSuprorgid());
				orgInfTmp.setSuprorgname(orgInf.getSuprorgname());
				// 批量报送平台不需要银行代码信息
				// orgInfTmp.setBankid(form.getBankid());
				// orgInfTmp.setBankname(orgInf.getBankname());
				// orgInfTmp.setNodecode(orgInf.getNodecode());
				orgInfTmp.setCreater(ContextConst.getCurrentUser().getUserid());
				orgInfTmp.setCreatetime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				orgInfTmp.setOpttype("02");
				orgInfTmp.setOptstatus("01");
				if (orgInfTmpRepository.insertOrgInfTmp(orgInfTmp) != 1) {
					// 插入机构信息维护临时表异常处理
					log.error("插入机构信息维护临时表错误！");
					messages.add("e.sm.4002");
					throw new BusinessException(messages);
				}
				// 3.2、更新机构信息表
				oldOrgInf.setOptstatus("01");
				if (orgInfRepository.updateOrgInf(oldOrgInf) != 1) {
					// 更新机构信息表异常处理
					log.error("更新机构信息表错误！");
					messages.add("e.sm.4003");
					throw new BusinessException(messages);
				}
				// 4、记录操作日志
				TlrLogPrint.tlrSysLogPrint("SM01_01",
						CommonConst.DATA_LOG_OPERTYPE_MODIFY,
						generateOrgInfString(oldOrgInf),
						generateOrgInfTmpString(orgInfTmp));
				log.info("机构信息维护修改结束！");
			} else {
				// 机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录
				log.error("机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录！");
				messages.add("e.sm.4007");
				throw new BusinessException(messages);
			}
		} else {
			// 机构信息表中不存在该机构Id的记录
			log.error("机构信息表中不存在该机构Id的记录！");
			messages.add("e.sm.4008");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 机构信息删除
	 */
	@Override
	@Transactional
	public void transDel(OrgInfForm form) {
		log.info("机构信息维护删除开始！");
		ResultMessages messages = ResultMessages.error();
		String orgId = StringUtil.trim(form.getOrgInf().getOrgid());

		OrgInfTmp orgInfTmp = new OrgInfTmp();
		orgInfTmp.setOrgid(orgId);

		OrgInf oldOrgInf = new OrgInf();
		oldOrgInf = orgInfRepository.query(orgId);
		// 1、查询机构信息表中是否存在相同机构Id的记录
		if (null != oldOrgInf) {
			//add by wy 2013-12-06,增加判断是否有该机构是否有下级机构
			if(orgInfRepository.queryOrgInfBySuprOrgId(orgId)==0){
				// 2、机构信息表存在记录，判断该机构下是否有创建角色信息
				if (roleInfRepository.queryRoleInfCnt(orgId) == 0) {
					// 3、该机构下没有创建角色信息，判断该机构下是否创建操作员信息
					if (userInfRepository.queryUserInfCnt(orgId) == 0) {
						// 4、该机构下没有操作员，判断该机构是否可让操作员进行操作
						if (userOrgInfRepository.queryUserOrgInfCnt(orgId) == 0) {
							// 5、操作员不能操作该机构，判断机构信息维护临时表中是否存在相同机构ID且操作状态为01-待审核的记录
							if (orgInfTmpRepository.queryOrgInfo(orgInfTmp) == 0) {
								// 6.1、插入机构信息维护临时表
								orgInfTmp.setId(numberService.getSysIDSequence(
										"0000", 8));
								orgInfTmp.setOrgid(oldOrgInf.getOrgid());
								orgInfTmp.setOrgname(oldOrgInf.getOrgname());
								orgInfTmp.setSuprorgid(oldOrgInf.getSuprorgid());
								orgInfTmp
										.setSuprorgname(oldOrgInf.getSuprorgname());
								// 批量报送平台不需要银行代码信息
								// orgInfTmp.setBankid(oldOrgInf.getBankid());
								// orgInfTmp.setBankname(oldOrgInf.getBankname());
								// orgInfTmp.setNodecode(oldOrgInf.getNodecode());
								orgInfTmp.setCreater(ContextConst.getCurrentUser()
										.getUserid());
								orgInfTmp
										.setCreatetime(DateUtil
												.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
								orgInfTmp.setOpttype("03");
								orgInfTmp.setOptstatus("01");
								if (orgInfTmpRepository.insertOrgInfTmp(orgInfTmp) != 1) {
									// 插入机构信息维护临时表异常处理
									log.error("插入机构信息维护临时表错误！");
									messages.add("e.sm.4002");
									throw new BusinessException(messages);
								}
								// 6.2、更新机构信息表
								oldOrgInf.setOptstatus("01");
								if (orgInfRepository.updateOrgInf(oldOrgInf) != 1) {
									// 更新机构信息表异常处理
									log.error("更新机构信息表错误！");
									messages.add("e.sm.4003");
									throw new BusinessException(messages);
								}
								// 7、记录操作日志
								TlrLogPrint.tlrSysLogPrint("SM01_01",
										CommonConst.DATA_LOG_OPERTYPE_DELETE,
										generateOrgInfString(oldOrgInf), "");
								log.info("机构信息维护删除结束！");
							} else {
								// 机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录
								log.error("机构信息维护临时表中存在相同机构ID且操作状态处于01-待审核的记录！");
								messages.add("e.sm.4007");
								throw new BusinessException(messages);
							}
						} else {
							// 该机构ID已分配用户操作
							log.error("该机构已分配用户操作");
							messages.add("e.sm.4049");
							throw new BusinessException(messages);
						}
					} else {
						// 该机构ID下有创建用户
						log.error("该机构下有创建用户！");
						messages.add("e.sm.4048");
						throw new BusinessException(messages);
					}
				} else {
					// 该机构ID下有创建角色
					log.error("该机构下有创建角色！");
					messages.add("e.sm.4047");
					throw new BusinessException(messages);
				}
			}else{
				// 该机构下有创建下级机构，不能删除
				log.error("该机构下有创建下级机构，不能删除！");
				messages.add("e.sm.4055");
				throw new BusinessException(messages);
			}
		} else {
			// 机构信息表中不存在该机构Id的记录
			log.error("机构信息表中不存在该机构Id的记录！");
			messages.add("e.sm.4008");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 机构信息维护审核
	 */
	@Override
	@Transactional
	public void transAuth(OrgInfTmp orgInfTmp) {
		log.info("机构信息维护审核开始！");
		ResultMessages messages = ResultMessages.error();
		OrgInf orgInf = new OrgInf();
		// 原始数据
		String orgId = StringUtil.trim(orgInfTmp.getOrgid());
		OrgInf oldOrgInf = orgInfRepository.query(orgId);

		orgInfTmp = orgInfTmpRepository.query(orgInfTmp);
		// 1、判断机构信息维护临时表中是否存在该机构Id的记录
		if (null != orgInfTmp) {
			// 2、机构信息维护临时表存在该记录，判断录入操作员与审核操作员是否为同一人
			if (CommonUtil.compareTlr(orgInfTmp.getCreater()) == 1) {
				// 3、录入操作员、审核操作员不为同一人，判断维护类型
				String opttype = orgInfTmp.getOpttype();
				log.info("维护类型为：" + opttype);
				// 4.1、维护类型01-新增
				if (opttype.equals("01")) {
					// 4.1.1、将机构信息维护临时表中的数据插入机构信息表
					orgInf = setValueToOrgInf(orgInfTmp);
					orgInf.setCreater(orgInfTmp.getCreater());
					orgInf.setCreatetime(orgInfTmp.getCreatetime());
					if (orgInfRepository.insertOrgInf(orgInf) != 1) {
						// 插入机构信息表异常处理
						log.error("插入机构信息表错误！");
						messages.add("e.sm.4001");
						throw new BusinessException(messages);
					}
					// 4.1.2、记录操作日志
					TlrLogPrint.tlrSysLogPrint("SM01_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK, "",
							generateOrgInfTmpString(orgInfTmp));
					// 4.2、维护类型02-修改
				} else if (opttype.equals("02")) {
					// 4.2.1、将机构信息维护临时表中数据更新到机构信息表中
					orgInf = setValueToOrgInf(orgInfTmp);
					orgInf.setLastoperator(orgInfTmp.getCreater());
					orgInf.setLastopttime(orgInfTmp.getCreatetime());
					if (orgInfRepository.updateOrgInf(orgInf) != 1) {
						// 更新机构信息表异常处理
						log.error("更新机构信息表错误！");
						messages.add("e.sm.4003");
						throw new BusinessException(messages);
					}
					// 4.2.2、记录操作日志
					TlrLogPrint.tlrSysLogPrint("SM01_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK,
							generateOrgInfString(oldOrgInf),
							generateOrgInfTmpString(orgInfTmp));
					// 4.3、维护类型03-删除
				} else if (opttype.equals("03")) {
					// 4.3.1、删除机构信息表中的原始数据
					if (orgInfRepository.deleteOrgInf(oldOrgInf) != 1) {
						// 删除机构信息表异常处理
						log.error("删除机构信息表错误！");
						messages.add("e.sm.4005");
						throw new BusinessException(messages);
					}
					// 4.3.2、记录操作日志
					TlrLogPrint.tlrSysLogPrint("SM01_02",
							CommonConst.DATA_LOG_OPERTYPE_CHECK,
							generateOrgInfString(oldOrgInf), "");
				} else {
					// 维护类型不存在
					log.error("维护类型不存在!");
					messages.add("e.sm.4010");
					throw new BusinessException(messages);
				}
				// 5、更新机构信息维护临时表
				orgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				orgInfTmp.setChecktime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				orgInfTmp.setOptstatus("02");
				if (orgInfTmpRepository.updateOrgInfTmp(orgInfTmp) != 1) {
					// 更新机构信息维护临时表异常处理
					log.error("更新机构信息维护临时表错误！");
					messages.add("e.sm.4004");
					throw new BusinessException(messages);
				}
				log.info("机构信息维护审核结束！");
			} else {
				// 录入操作员、审核操作员为同一人
				log.error("录入操作员、审核操作员为同一人!");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			// 机构信息维护临时表中不存在该记录
			log.error("机构信息维护临时表中不存在该记录！");
			messages.add("e.sm.4009");
			throw new BusinessException(messages);
		}
	}

	@Override
	@Transactional
	public void transRejct(OrgInfTmp orgInfTmp) {
		log.info("机构信息维护拒绝开始！");
		ResultMessages messages = ResultMessages.error();
		// 原始数据
		String orgId = StringUtil.trim(orgInfTmp.getOrgid());
		OrgInf oldOrgInf = orgInfRepository.query(orgId);

		orgInfTmp = orgInfTmpRepository.query(orgInfTmp);
		// 1、判断机构信息维护临时表中是否存在该机构Id的记录
		if (null != orgInfTmp) {
			// 2、机构信息维护临时表存在该记录，判断录入操作员与审核操作员是否为同一人
			if (CommonUtil.compareTlr(orgInfTmp.getCreater()) == 1) {
				// 3、判断维护类型
				String opttype = orgInfTmp.getOpttype();
				// 3.1、维护类型为02-修改或03-删除
				if (opttype.equals("02") || opttype.equals("03")) {
					// 3.1.1、更新机构信息表
					oldOrgInf.setLastoperator(orgInfTmp.getCreater());
					oldOrgInf.setLastopttime(orgInfTmp.getCreatetime());
					oldOrgInf.setOptstatus("02");
					if (orgInfRepository.updateOrgInf(oldOrgInf) != 1) {
						// 更新机构信息表异常处理
						log.error("更新机构信息表错误！");
						messages.add("e.sm.4003");
						throw new BusinessException(messages);
					}
					// 3.1.2、记录操作日志
					// 维护类型03-删除
					if (opttype.equals("03")) {
						TlrLogPrint.tlrSysLogPrint("SM01_02",
								CommonConst.DATA_LOG_OPERTYPE_REJECT, "",
								generateOrgInfString(oldOrgInf));
						// 维护类型02-修改
					} else if (opttype.equals("02")) {
						TlrLogPrint.tlrSysLogPrint("SM01_02",
								CommonConst.DATA_LOG_OPERTYPE_REJECT,
								generateOrgInfString(oldOrgInf),
								generateOrgInfTmpString(orgInfTmp));
					}
					// 3.2、维护类型为01-新增
				} else if (opttype.equals("01")) {
					// 3.2.1、记录操作日志
					TlrLogPrint.tlrSysLogPrint("SM01_02",
							CommonConst.DATA_LOG_OPERTYPE_REJECT, "",
							generateOrgInfTmpString(orgInfTmp));
				}
				// 4、更新机构信息临时表
				orgInfTmp.setChecker(ContextConst.getCurrentUser().getUserid());
				orgInfTmp.setChecktime(DateUtil
						.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
				orgInfTmp.setOptstatus("03");
				if (orgInfTmpRepository.updateOrgInfTmp(orgInfTmp) != 1) {
					// 更新机构信息维护临时表异常处理
					log.error("更新机构信息维护临时表错误！");
					messages.add("e.sm.4004");
					throw new BusinessException(messages);
				}
				log.info("机构信息维护拒绝结束！");
			} else {
				// 录入操作员、审核操作员为同一人
				log.error("录入操作员、审核操作员为同一人!");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			// 机构信息维护临时表中不存在该记录
			log.error("机构信息维护临时表中不存在该记录！");
			messages.add("e.sm.4009");
			throw new BusinessException(messages);
		}
	}

	public OrgInf setValueToOrgInf(OrgInfTmp orgInfTmp) {
		OrgInf orgInf = new OrgInf();
		orgInf.setOrgid(StringUtil.trim((orgInfTmp.getOrgid())));
		orgInf.setOrgname(orgInfTmp.getOrgname());
		orgInf.setSuprorgid(orgInfTmp.getSuprorgid());
		orgInf.setSuprorgname(orgInfTmp.getSuprorgname());
		// 批量报送平台不需要银行代码信息
		// orgInf.setBankid(orgInfTmp.getBankid());
		// orgInf.setBankname(orgInfTmp.getBankname());
		// orgInf.setNodecode(orgInfTmp.getNodecode());
		orgInf.setChecker(ContextConst.getCurrentUser().getUserid());
		orgInf.setChecktime(DateUtil
				.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		orgInf.setOptstatus("02");
		return orgInf;
	}

	/**
	 * list_OrgInf排除list中所有的机构
	 * 
	 * @param list
	 * @param list_OrgInf
	 */
	private void removeSpecialOrgTmp(List<UserOrgInfTmp> list,
			List<OrgInf> list_OrgInf) {

		// list_OrgInf排除list中所有的机构
		if (list_OrgInf != null && list_OrgInf.size() > 0) {

			// 用来存储所有需要排除的机构Id
			Map<String, String> map = new HashMap<String, String>();

			for (UserOrgInfTmp userOrgInfTmp : list) {
				if (userOrgInfTmp != null && userOrgInfTmp.getOrgid() != null) {
					map.put(userOrgInfTmp.getOrgid().trim(), "");
				}
			}
			List<OrgInf> listTmp = new ArrayList<OrgInf>();
			for (OrgInf orgInf : list_OrgInf) {
				if (orgInf != null && orgInf.getOrgid() != null) {
					if (map.containsKey(orgInf.getOrgid().trim())) {
						listTmp.add(orgInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_OrgInf.removeAll(listTmp);
			}
		}
	}

	/**
	 * list_OrgInf排除list中所有的机构
	 * 
	 * @param list
	 * @param list_OrgInf
	 */
	private void removeSpecialOrg(List<UserOrgInf> list,
			List<OrgInf> list_OrgInf) {

		// list_OrgInf排除list中所有的机构
		if (list_OrgInf != null && list_OrgInf.size() > 0) {

			// 用来存储所有需要排除的机构Id
			Map<String, String> map = new HashMap<String, String>();

			for (UserOrgInf userOrgInf : list) {
				if (userOrgInf != null && userOrgInf.getOrgid() != null) {
					map.put(userOrgInf.getOrgid().trim(), "");
				}
			}
			List<OrgInf> listTmp = new ArrayList<OrgInf>();
			for (OrgInf orgInf : list_OrgInf) {
				if (orgInf != null && orgInf.getOrgid() != null) {
					if (map.containsKey(orgInf.getOrgid().trim())) {
						listTmp.add(orgInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_OrgInf.removeAll(listTmp);
			}
		}
	}

	/**
	 * list_OrgInf排除list中所有的机构
	 * 
	 * @param list
	 * @param list_OrgInf
	 */
	private void removeSpecialOrgArr(List<String> list, List<OrgInf> list_OrgInf) {

		// list_OrgInf排除list中所有的机构
		if (list_OrgInf != null && list_OrgInf.size() > 0) {

			// 用来存储所有需要排除的机构Id
			Map<String, String> map = new HashMap<String, String>();

			for (String orgId : list) {
				if (orgId != null) {
					map.put(orgId.trim(), "");
				}
			}
			List<OrgInf> listTmp = new ArrayList<OrgInf>();
			for (OrgInf orgInf : list_OrgInf) {
				if (orgInf != null && orgInf.getOrgid() != null) {
					if (map.containsKey(orgInf.getOrgid().trim())) {
						listTmp.add(orgInf);
					}
				}
			}
			if (listTmp.size() > 0) {
				list_OrgInf.removeAll(listTmp);
			}
		}
	}

	private String generateOrgInfTmpString(OrgInfTmp orgInfTmp) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("index.label.sm.OrganizationId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInfTmp.getOrgid())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.OrganizationName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInfTmp.getOrgname())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message
						.getMessage("index.label.sm.SuperiorOrganizationId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(StringUtil.trim(orgInfTmp.getSuprorgid()))
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message
						.getMessage("index.label.sm.SuperiorOrganizationName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInfTmp.getSuprorgname())
				.append(CommonConst.SEPARATE_TWO_FIELD);
//				.append(message.getMessage("index.label.sm.BankId"))
//				.append(CommonConst.SEPARATE_KEY_VALUE)
//				.append(orgInfTmp.getBankid())
//				.append(CommonConst.SEPARATE_TWO_FIELD)
//				.append(message.getMessage("index.label.sm.BankName"))
//				.append(CommonConst.SEPARATE_KEY_VALUE)
//				.append(orgInfTmp.getBankname())
//				.append(CommonConst.SEPARATE_TWO_FIELD);

		return sb.toString();
	}

	private String generateOrgInfString(OrgInf orgInf) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("index.label.sm.OrganizationId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInf.getOrgid())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message.getMessage("index.label.sm.OrganizationName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInf.getOrgname())
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message
						.getMessage("index.label.sm.SuperiorOrganizationId"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(StringUtil.trim(orgInf.getSuprorgid()))
				.append(CommonConst.SEPARATE_TWO_FIELD)
				.append(message
						.getMessage("index.label.sm.SuperiorOrganizationName"))
				.append(CommonConst.SEPARATE_KEY_VALUE)
				.append(orgInf.getSuprorgname())
				.append(CommonConst.SEPARATE_TWO_FIELD);
//				.append(message.getMessage("index.label.sm.BankId"))
//				.append(CommonConst.SEPARATE_KEY_VALUE)
//				.append(orgInf.getBankid())
//				.append(CommonConst.SEPARATE_TWO_FIELD)
//				.append(message.getMessage("index.label.sm.BankName"))
//				.append(CommonConst.SEPARATE_KEY_VALUE)
//				.append(orgInf.getBankname())
//				.append(CommonConst.SEPARATE_TWO_FIELD);

		return sb.toString();
	}

	/**
	 * 根据选择的机构IDList，查询机构信息，共两部分
	 * 1) index = 0， 选择的Id，查询得到的机构信息List
	 * 2) index = 1，未被选择的机构信息List
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList(List<String> selectedList) {
		log.debug("transQueryAvailabledAndSelectedList start");
		
		List<OrgInf> availableOrgList = orgInfRepository.queryNotInOrgIdList((ArrayList<String>) selectedList);
		List<OrgInf> selectedOrgList = orgInfRepository.queryInOrgIdList((ArrayList<String>) selectedList);
		
		List<List<OrgInf>> resultList = new ArrayList<List<OrgInf>>();
		resultList.add(selectedOrgList);
		resultList.add(availableOrgList);
		
		return resultList;
	}

	/**
	 * 根据选择的机构信息List，查询机构信息，共两部分
	 * 1) index = 0， 选择的Id，查询得到的机构信息List
	 * 2) index = 1，未被选择的机构信息List
	 * 使用transQueryAvailabledAndSelectedList(List<String>)的结果
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList2(List<UserOrgInf> selectedList) {
		log.debug("transQueryAvailabledAndSelectedList2 start");
		
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < selectedList.size(); i++) {
			UserOrgInf tmp = selectedList.get(i);
			idList.add(tmp.getOrgid());
		}
		
		return transQueryAvailabledAndSelectedList(idList);
	}

	/**
	 * 根据选择的机构信息List，查询机构信息，共两部分
	 * 1) index = 0， 选择的Id，查询得到的机构信息List
	 * 2) index = 1，未被选择的机构信息List
	 * 使用transQueryAvailabledAndSelectedList(List<String>)的结果
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList3(List<UserOrgInfTmp> selectedList) {
		log.debug("transQueryAvailabledAndSelectedList3 start");
		
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < selectedList.size(); i++) {
			UserOrgInfTmp tmp = selectedList.get(i);
			idList.add(tmp.getOrgid());
		}
		
		return transQueryAvailabledAndSelectedList(idList);
	}
	
	@Autowired
	protected OrgInfRepository orgInfRepository;

	@Autowired
	protected RoleInfRepository roleInfRepository;

	@Autowired
	protected UserInfRepository userInfRepository;

	@Autowired
	protected UserOrgInfRepository userOrgInfRepository;

	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;

	@Autowired
	protected OrgInfTmpRepository orgInfTmpRepository;

	@Autowired
	protected NumberService numberService;

	private MessagesUtil message = MessagesUtil.getInstance();

}
