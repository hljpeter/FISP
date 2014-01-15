package com.synesoft.fisp.domain.service.sm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.UserRoleInfForm;
import com.synesoft.fisp.app.sm.model.UserRoleInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.OrgRoleInf;
import com.synesoft.fisp.domain.model.OrgRoleInfTmp;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
import com.synesoft.fisp.domain.repository.sm.OrgInfRepository;
import com.synesoft.fisp.domain.repository.sm.OrgRoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.OrgRoleInfTmpRepository;
import com.synesoft.fisp.domain.repository.sm.RoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfRepository;
import com.synesoft.fisp.domain.repository.sm.UserRoleInfTmpRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Service("userRoleInfMaintenanceService")
public class UserRoleInfMaintenanceServiceImpl implements UserRoleInfMaintenanceService {

	@Override
	public Page<UserRoleInf> transQueryUserRoleInfList(Pageable pageable, UserRoleInf userRoleInf) {
		Page<UserRoleInf> page = userRoleInfRepository.queryList(pageable, userRoleInf);
		return page;
	}
	
	@Override
	public UserRoleInf transQueryUserRoleInf(UserRoleInf userRoleInf) {
		userRoleInf.setUserid(StringUtil.trim(userRoleInf.getUserid()));
		userRoleInf.setOrgid(StringUtil.trim(userRoleInf.getOrgid()));
		userRoleInf=userRoleInfRepository.query(userRoleInf);
		return userRoleInf;
	}
	@Override
	public List<UserRoleInf> transQueryUserRoleInfList(UserRoleInf userRoleInf) {
		List<UserRoleInf> list=null;
		userRoleInf.setUserid(StringUtil.trim(userRoleInf.getUserid()));
		userRoleInf.setOrgid(StringUtil.trim(userRoleInf.getOrgid()));
		list=userRoleInfRepository.queryUserRoleIdList(userRoleInf);
		return list;
	}
	@Override
	public Page<UserRoleInfTmp> transQueryUserRoleInfTmpList(Pageable pageable,
			UserRoleInfTmp userRoleInfTmp) {
		Page<UserRoleInfTmp> page = userRoleInfTmpRepository.queryList(pageable,
				userRoleInfTmp);
		return page;
	}
	@Override
	public UserRoleInfTmp transQueryUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp) {
		userRoleInfTmp=userRoleInfTmpRepository.query(userRoleInfTmp);
		return userRoleInfTmp;
	}
	@Override
	public List<UserRoleInfTmp> transQueryUserRoleInfTmpList(UserRoleInfTmp userRoleInfTmp) {
		List<UserRoleInfTmp> list=null;
		userRoleInfTmp.setUserid(StringUtil.trim(userRoleInfTmp.getUserid()));
		userRoleInfTmp.setOrgid(StringUtil.trim(userRoleInfTmp.getOrgid()));
		list=userRoleInfTmpRepository.querylist(userRoleInfTmp);
		return list;
	}
	@Override
	public List<UserRoleInfTmp> transQueryRoleListMerge(UserRoleInfTmp userRoleInfTmp) {
		return userRoleInfRepository.queryRoleListMerge(userRoleInfTmp);
	}
	@Override
	@Transactional
	public int transAdd(UserRoleInfTmpForm form){
		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp=form.getUserRoleInfTmp();
		UserRoleInf userRoleInf=new UserRoleInf();
		OrgRoleInf orgRoleInf=new OrgRoleInf();
		OrgRoleInfTmp orgRoleInfTmp=new OrgRoleInfTmp();
		String userId = StringUtil.trim(form.getUserid());
		String userOrgId =  StringUtil.trim(form.getUserorgid());
		OrgInf orgInf=orgInfRepository.query(userOrgId);
		userRoleInf.setUserid(userId);
		userRoleInf.setOrgid(userOrgId);
		userRoleInfTmp.setUserid(userId);
		userRoleInfTmp.setOrgid(userOrgId);
		userRoleInfTmp.setOrgname(orgInf.getOrgname());
		orgRoleInf.setOrgid(userOrgId);
		orgRoleInfTmp.setOrgid(userOrgId);
		//得到用户可操作角色列表值
		List<String> userroleidlist=form.getUserRoleInfArr();
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
		return 0;
	}

	@Override
	@Transactional
	public int transUpdate(UserRoleInfForm form,String opttype) {
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
		//相同则不做任何处理
		}else if(opttype.equals("02")){
			//获取界面中的角色列表信息
			List<String> userrolelist=form.getUserRoleInfArr();
			List<String> userrolelistTmp=new ArrayList<String>();
			for (int j = 0; j <userrolelist.size(); j++) {
				//相同则不做任何处理
				String userrole=StringUtil.trim(userrolelist.get(j));
				userrolelistTmp.add(userrole);
				if(olduserrolelist.contains(userrole)){
					olduserrolelist.remove(userrole);
					userrolelistTmp.remove(userrole);
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
				}else{
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
						if(optype.equals("03")){
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
		return 0;
	}

	@Override
	@Transactional
	public int transAuth(UserRoleInfTmpForm form) {
		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp=form.getUserRoleInfTmp();
		UserRoleInf userRoleInf=new UserRoleInf();
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
		}else{
			messages.add("e.sm.4027");
			throw new BusinessException(messages);
		}
		return 0;
	}

	@Override
	@Transactional
	public int transRejct(UserRoleInfTmpForm form) {
		ResultMessages messages = ResultMessages.error();
		UserRoleInfTmp userRoleInfTmp=form.getUserRoleInfTmp();
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
		}else{
			messages.add("e.sm.4027");
			throw new BusinessException(messages);
		}
		return 0;
	}
	
	public UserRoleInf setValueToUserRoleInf(UserRoleInfTmp userRoleInfTmp){
		UserRoleInf userRoleInf=new UserRoleInf();
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
		userRoleInf.setOptstatus("02");
		return userRoleInf;
	}
	@Override
	public List<String> transQueryRoleIdListFromTmp(UserRoleInfTmp userRoleInfTmp) {
		List<UserRoleInfTmp> resultList = transQueryUserRoleInfTmpList(userRoleInfTmp);
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < resultList.size(); i++) {
			UserRoleInfTmp tmp = resultList.get(i);
			idList.add(tmp.getRoleid());
		}
		return idList;
	}

	@Override
	public List<String> transQueryUserRoleIdList(UserRoleInf userRoleInf) {
		List<UserRoleInf> resultList = transQueryUserRoleInfList(userRoleInf);
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < resultList.size(); i++) {
			UserRoleInf tmp = resultList.get(i);
			idList.add(tmp.getRoleid());
		}
		return idList;
	}

	@Autowired
	protected UserRoleInfRepository userRoleInfRepository;

	@Autowired
	protected UserRoleInfTmpRepository userRoleInfTmpRepository;
	
	@Autowired
	protected OrgRoleInfRepository orgRoleInfRepository;

	@Autowired
	protected OrgRoleInfTmpRepository orgRoleInfTmpRepository;
	@Autowired
	protected OrgInfRepository orgInfRepository;
	@Autowired
	protected RoleInfRepository roleInfRepository;
	@Autowired
	protected UserInfRepository userInfRepository;
	
	@Autowired
	protected NumberService numberService;

}
