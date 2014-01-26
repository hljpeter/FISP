package com.synesoft.ftzmis.domain.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrTmp;
import com.synesoft.ftzmis.domain.repository.FTZ210501Repository;

/**
 * @author hb_huang
 *
 * 2013-12-27 下午01:02:28
 *
 */
@Service
public class FTZ210501ServiceImpl implements FTZ210501Service {
	
	protected static String funcId = "210501";

	private static final Logger log = LoggerFactory.getLogger(FTZ210501ServiceImpl.class);
	
	@Resource
	protected FTZ210501Repository ftz210501repository;
	
	@Override
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable,
			FtzActMstr ftzActMstr) {
		return ftz210501repository.queryFtzActMstrPage(pageable, ftzActMstr);
	}
	
	@Override
	public Page<FtzActMstrTmp> queryFtzActMstrTmpPage(Pageable pageable,
			FtzActMstrTmp ftzActMstrTmp) {
		return ftz210501repository.queryFtzActMstrTmpPage(pageable, ftzActMstrTmp);
	}

	@Override
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr) {
		FtzActMstr new_FtzActMstr = ftz210501repository.queryFtzActMstr(ftzActMstr);
		if (new_FtzActMstr != null) {
			if(new_FtzActMstr.getDeptType() != null && !"".equals(new_FtzActMstr.getDeptType())) {
				new_FtzActMstr.setDeptType(new_FtzActMstr.getDeptType().trim());
			}
			if (new_FtzActMstr.getBalanceCode() != null && !"".equals(new_FtzActMstr.getBalanceCode())) {
				new_FtzActMstr.setBalanceCode(new_FtzActMstr.getBalanceCode().trim());
			}
		}
		return new_FtzActMstr;
	}
	
	@Override
	public FtzActMstrTmp queryFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp) {
		FtzActMstrTmp new_FtzActMstrTmp = ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp);
		if (new_FtzActMstrTmp != null) {
			if(null != new_FtzActMstrTmp.getDeptType() && !"".equals(new_FtzActMstrTmp.getDeptType())) {
				new_FtzActMstrTmp.setDeptType(new_FtzActMstrTmp.getDeptType().trim());
			}
			if (null != new_FtzActMstrTmp.getBalanceCode() && !"".equals(new_FtzActMstrTmp.getBalanceCode())) {
				new_FtzActMstrTmp.setBalanceCode(new_FtzActMstrTmp.getBalanceCode().trim());
			}
		}
		return new_FtzActMstrTmp;
	}
	
	@Override
	public List<FtzActMstr> queryFtzActMstrList(FtzActMstr ftzActMstr) {
		return ftz210501repository.queryFtzActMstrList(ftzActMstr);
	}
	
	/**
	 * 账户信息新增
	 */
	@Override
	@Transactional
	public void insertFtzActMstr(FtzActMstrTmp ftzActMstrTmp) {
		log.info("账户信息新增开始！");
		ResultMessages messages = ResultMessages.error();
		FtzActMstr ftzActMstr = new FtzActMstr();
		ftzActMstr.setAccountNo(ftzActMstrTmp.getAccountNo());
		ftzActMstr.setSubAccountNo(ftzActMstrTmp.getSubAccountNo());
		FtzActMstr result_FtzActMstr = ftz210501repository.queryFtzActMstr(ftzActMstr);
		if (null == result_FtzActMstr) {
			if(null == ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp)){
				ftzActMstrTmp.setAccStatus("DS01");
				ftzActMstrTmp.setMakUserId(ContextConst.getCurrentUser().getUserid());
				ftzActMstrTmp.setMakDatetime(DateUtil.getNowInputDateTime());
				ftzActMstrTmp.setOpType("A");
				if(ftz210501repository.insertFtzActMstrTmp(ftzActMstrTmp) != 1) {
					//插入账户信息临时表异常处理
					log.error("插入账户信息临时表错误！");
					messages.add("e.ftzmis.210501.0012");
					throw new BusinessException(messages);
				}
				//记录操作日志
				BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzActMstrTmp.toString());
			} else {
				//账户信息临时表中存在相同的账户和子账户
				log.error("账户信息临时表中存在相同的账号和子账号");
				messages.add("e.ftzmis.210501.0015");
				throw new BusinessException(messages);
			}
		//如果有，状态为撤销，为重开户
		} else if (result_FtzActMstr.getAccStatus().equals("DS05")) {
			if (null == ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp)) {
				ftzActMstrTmp.setAccStatus("DS01");
				ftzActMstrTmp.setMakUserId(ContextConst.getCurrentUser().getUserid());
				ftzActMstrTmp.setMakDatetime(DateUtil.getNowInputDateTime());
				ftzActMstrTmp.setOpType("M");
				if(ftz210501repository.insertFtzActMstrTmp(ftzActMstrTmp) != 1) {
					//插入账户信息临时表异常处理
					log.error("插入账户信息临时表错误！");
					messages.add("e.ftzmis.210501.0012");
					throw new BusinessException(messages);
				}
				//记录操作日志
				BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzActMstrTmp.toString());
			} else {
				//账户信息临时表中存在相同的账户和子账户
				log.error("账户信息临时表中存在相同的账号和子账号");
				messages.add("e.ftzmis.210501.0015");
				throw new BusinessException(messages);
			}
		} else {
			//账户信息表中存在相同的账户和子账户
			log.error("账户信息表中存在相同的账号和子账号");
			messages.add("e.ftzmis.210501.0015");
			throw new BusinessException(messages);
		}
		log.info("账户信息新增结束！");
	}
	
	/**
	 * 账户修改
	 */
	@Override
	@Transactional
	public void updateFtzActMstr(FtzActMstr ftzActMstr) {
		log.info("账户信息维护修改开始！");
		ResultMessages messages = ResultMessages.error();
		//用于记录日志
		FtzActMstr query_ftzActMstr = new FtzActMstr();
		query_ftzActMstr.setAccountNo(ftzActMstr.getAccountNo());
		query_ftzActMstr.setSubAccountNo(ftzActMstr.getSubAccountNo());
		FtzActMstr ftzActMstr_tmp = this.queryFtzActMstr(query_ftzActMstr);
		//
		FtzActMstrTmp ftzActMstrTmp = new FtzActMstrTmp();
		setFtzActMstrTmp(ftzActMstr, ftzActMstrTmp);
		if (null == ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp)) {
			ftzActMstrTmp.setAccStatus("DS01");
			ftzActMstrTmp.setOpType("M");
			if (ftz210501repository.insertFtzActMstrTmp(ftzActMstrTmp) != 1) {
				//插入临时表异常处理
				log.error("插入账户信息临时表错误！");
				messages.add("e.ftzmis.210501.0012");
				throw new BusinessException(messages);
			}
			//记录操作日志
			BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, ftzActMstr_tmp.toString(), ftzActMstr.toString());
		} else {
			//账户信息临时表中存在相同的账户和子账户
			log.error("账户信息临时表中存在相同的账号和子账号");
			messages.add("e.ftzmis.210501.0013");
			throw new BusinessException(messages);
		}
		log.info("账户信息维护修改结束！");
	}
	
	/**
	 * 账户删除
	 * @param ftzActMstr
	 */
	@Override
	@Transactional
	public void deleteFtzActMstr(FtzActMstr ftzActMstr) {
		log.info("账户信息删除开始");
		ResultMessages messages = ResultMessages.error();
		FtzActMstrTmp ftzActMstrTmp = new FtzActMstrTmp();
		setFtzActMstrTmp(ftzActMstr, ftzActMstrTmp);
		if (null == ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp)) {
				ftzActMstrTmp.setAccStatus("DS05");
				ftzActMstrTmp.setOpType("D");
				if (ftz210501repository.insertFtzActMstrTmp(ftzActMstrTmp) != 1) {
					log.error("插入账户信息临时表错误！");
					messages.add("e.ftzmis.210501.0012");
					throw new BusinessException(messages);
				}
				//记录操作日志
				BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE, ftzActMstr.toString(), "");
		} else {
			//账户临时表中有数据待审核
			log.error("账户信息临时表中有数据待审核");
			messages.add("e.ftzmis.210501.0014");
			throw new BusinessException(messages);
		}
		log.info("账户信息删除结束");
	}
	
	/**
	 * 账户复核成功
	 */
	@Override
	@Transactional
	public void authFtzActMstr(FtzActMstrTmp ftzActMstrTmp) {
		log.info("账户审核成功开始");
		ResultMessages messages = ResultMessages.error();
		//用于记录日志
		FtzActMstr query_ftzActMstr = new FtzActMstr();
		query_ftzActMstr.setAccountNo(ftzActMstrTmp.getAccountNo());
		query_ftzActMstr.setSubAccountNo(ftzActMstrTmp.getSubAccountNo());
		FtzActMstr ftzActMstr_tmp = this.queryFtzActMstr(query_ftzActMstr);
		//
		FtzActMstr ftzActMstr = new FtzActMstr();
		FtzActMstrTmp old_ftzActMstrTmp = ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp);
		//判断临时表中是否存在该账户
		if (null != old_ftzActMstrTmp) {
			//判断审核操作员和录入操作员是否为同一个人
			if (CommonUtil.compareTlr(old_ftzActMstrTmp.getMakUserId()) == 1) {
				String opType = old_ftzActMstrTmp.getOpType();
				log.info("维护类型为："+opType);
				//维护类型为新增
				if (opType.equals("A")) {
					setFtzActMstr(old_ftzActMstrTmp, ftzActMstr);
					ftzActMstr.setChkUserId(ContextConst.getCurrentUser().getUserid());
					ftzActMstr.setChkDatetime(DateUtil.getNowInputDateTime());
					ftzActMstr.setChkAddWord(ftzActMstrTmp.getChkAddWord());
					if (ftz210501repository.insertFtzActMstr(ftzActMstr) != 1) {
						log.error("插入账户信息表错误！");
						messages.add("e.ftzmis.210501.0017");
						throw new BusinessException(messages);
					}
					//记录操作日志
					BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzActMstr.toString());
					//删除临时表中的数据
					ftz210501repository.deleteFtzActMstrTmp(ftzActMstrTmp);
					//维护类型为修改
				} else if (opType.equals("M")) {
					setFtzActMstr(old_ftzActMstrTmp, ftzActMstr);
					ftzActMstr.setChkUserId(ContextConst.getCurrentUser().getUserid());
					ftzActMstr.setChkDatetime(DateUtil.getNowInputDateTime());
					ftzActMstr.setChkAddWord(ftzActMstrTmp.getChkAddWord());
					if (ftz210501repository.updateFtzActMstr(ftzActMstr) != 1) {
						log.error("更新账户信息表错误！");
						messages.add("e.ftzmis.210501.0018");
						throw new BusinessException(messages);
					}
					//记录操作日志
					BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK, ftzActMstr_tmp.toString(), ftzActMstr.toString());
					//删除临时表中的数据
					ftz210501repository.deleteFtzActMstrTmp(ftzActMstrTmp);
					//维护类型为删除
				} else if (opType.equals("D")) {
					setFtzActMstr(old_ftzActMstrTmp, ftzActMstr);
					ftzActMstr.setAccStatus("DS05");
					ftzActMstr.setAccountNo(old_ftzActMstrTmp.getAccountNo());
					ftzActMstr.setSubAccountNo(old_ftzActMstrTmp.getSubAccountNo());
					ftzActMstr.setChkUserId(ContextConst.getCurrentUser().getUserid());
					ftzActMstr.setChkDatetime(DateUtil.getNowInputDateTime());
					ftzActMstr.setChkAddWord(ftzActMstrTmp.getChkAddWord());
					if(ftz210501repository.updateFtzActMstr(ftzActMstr) !=1){
						log.error("更新账户信息表错误！");
						messages.add("e.ftzmis.210501.0018");
						throw new BusinessException(messages);
					}
					//记录操作日志
					BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK, ftzActMstr_tmp.toString(), ftzActMstr.toString());
					//删除临时表中的数据
					ftz210501repository.deleteFtzActMstrTmp(ftzActMstrTmp);
				} else {
					log.error("维护类型不存在！");
					messages.add("e.sm.4010");
					throw new BusinessException(messages);
				}
			} else {
				//录入和审核为同一人
				log.error("录入操作员和审核操作员为同一人");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			//临时表中不存在该记录
			log.error("账户信息临时表中不存在该记录！");
			messages.add("e.ftzmis.210501.0016");
			throw new BusinessException(messages);
		}
		
		log.info("账户审核成功结束");
	}
	
	/**
	 * 审核拒绝
	 */
	@Override
	@Transactional
	public void refuseFtzActMstr(FtzActMstrTmp ftzActMstrTmp) {
		log.info("账户审核拒绝开始");
		ResultMessages messages = ResultMessages.error();
		//用于记录日志
		FtzActMstr query_ftzActMstr = new FtzActMstr();
		query_ftzActMstr.setAccountNo(ftzActMstrTmp.getAccountNo());
		query_ftzActMstr.setSubAccountNo(ftzActMstrTmp.getSubAccountNo());
		FtzActMstr ftzActMstr_tmp = this.queryFtzActMstr(query_ftzActMstr);
		FtzActMstrTmp old_ftzActMstrTmp = ftz210501repository.queryFtzActMstrTmp(ftzActMstrTmp);
		//判断临时表中是否存在该账户
		if (null != old_ftzActMstrTmp) {
			if (CommonUtil.compareTlr(old_ftzActMstrTmp.getMakUserId()) == 1) {
				//删除临时表中的数据
				ftz210501repository.deleteFtzActMstrTmp(ftzActMstrTmp);
				//记录操作日志
				BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT, ftzActMstr_tmp.toString(), ftzActMstrTmp.toString());
			} else {
				//录入和审核为同一人
				log.error("录入操作员和审核操作员为同一人");
				messages.add("e.cm.2003");
				throw new BusinessException(messages);
			}
		} else {
			//临时表中不存在该记录
			log.error("账户信息临时表中不存在该记录！");
			messages.add("e.ftzmis.210501.0016");
			throw new BusinessException(messages);
		}
		log.info("账户审核拒绝结束");
	}
	
	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}
	
	private void setFtzActMstrTmp(FtzActMstr ftzActMstr, FtzActMstrTmp ftzActMstrTmp) {
		ftzActMstrTmp.setAccountNo(ftzActMstr.getAccountNo());
		ftzActMstrTmp.setSubAccountNo(ftzActMstr.getSubAccountNo());
		ftzActMstrTmp.setBranchId(ftzActMstr.getBranchId());
		ftzActMstrTmp.setCurrency(ftzActMstr.getCurrency());
		ftzActMstrTmp.setDeptType(ftzActMstr.getDeptType());
		ftzActMstrTmp.setBalanceCode(ftzActMstr.getBalanceCode());
		ftzActMstrTmp.setAccOrgCode(ftzActMstr.getAccOrgCode());
		ftzActMstrTmp.setAccountName(ftzActMstr.getAccountName());
		ftzActMstrTmp.setAccType(ftzActMstr.getAccType());
		ftzActMstrTmp.setDepositRate(ftzActMstr.getDepositRate());
		ftzActMstrTmp.setCustomType(ftzActMstr.getCustomType());
		ftzActMstrTmp.setDocumentType(ftzActMstr.getDocumentType());
		ftzActMstrTmp.setDocumentNo(ftzActMstr.getDocumentNo());
		ftzActMstrTmp.setAccStatus(ftzActMstr.getAccStatus());
		ftzActMstrTmp.setLastBalance(ftzActMstr.getLastBalance());
		ftzActMstrTmp.setDebitCount(ftzActMstr.getDebitCount());
		ftzActMstrTmp.setDebitAmount(ftzActMstr.getDebitAmount());
		ftzActMstrTmp.setCreditCount(ftzActMstr.getCreditCount());
		ftzActMstrTmp.setCreditAmount(ftzActMstr.getCreditAmount());
		ftzActMstrTmp.setBalance(ftzActMstr.getBalance());
		ftzActMstrTmp.setMakUserId(ftzActMstr.getMakUserId());
		ftzActMstrTmp.setMakDatetime(ftzActMstr.getMakDatetime());
		ftzActMstrTmp.setChkUserId(ftzActMstr.getChkUserId());
		ftzActMstrTmp.setChkDatetime(ftzActMstr.getChkDatetime());
		ftzActMstrTmp.setChkAddWord(ftzActMstr.getChkAddWord());
	}
	
	private void setFtzActMstr(FtzActMstrTmp ftzActMstrTmp, FtzActMstr ftzActMstr) {
		ftzActMstr.setAccountNo(ftzActMstrTmp.getAccountNo());
		ftzActMstr.setSubAccountNo(ftzActMstrTmp.getSubAccountNo());
		ftzActMstr.setBranchId(ftzActMstrTmp.getBranchId());
		ftzActMstr.setCurrency(ftzActMstrTmp.getCurrency());
		ftzActMstr.setDeptType(ftzActMstrTmp.getDeptType());
		ftzActMstr.setBalanceCode(ftzActMstrTmp.getBalanceCode());
		ftzActMstr.setAccOrgCode(ftzActMstrTmp.getAccOrgCode());
		ftzActMstr.setAccountName(ftzActMstrTmp.getAccountName());
		ftzActMstr.setAccType(ftzActMstrTmp.getAccType());
		ftzActMstr.setDepositRate(ftzActMstrTmp.getDepositRate());
		ftzActMstr.setCustomType(ftzActMstrTmp.getCustomType());
		ftzActMstr.setDocumentType(ftzActMstrTmp.getDocumentType());
		ftzActMstr.setDocumentNo(ftzActMstrTmp.getDocumentNo());
		ftzActMstr.setAccStatus(ftzActMstrTmp.getAccStatus());
		ftzActMstr.setLastBalance(ftzActMstrTmp.getLastBalance());
		ftzActMstr.setDebitCount(ftzActMstrTmp.getDebitCount());
		ftzActMstr.setDebitAmount(ftzActMstrTmp.getDebitAmount());
		ftzActMstr.setCreditCount(ftzActMstrTmp.getCreditCount());
		ftzActMstr.setCreditAmount(ftzActMstrTmp.getCreditAmount());
		ftzActMstr.setBalance(ftzActMstrTmp.getBalance());
		ftzActMstr.setMakUserId(ftzActMstrTmp.getMakUserId());
		ftzActMstr.setMakDatetime(ftzActMstrTmp.getMakDatetime());
		ftzActMstr.setChkUserId(ftzActMstrTmp.getChkUserId());
		ftzActMstr.setChkDatetime(ftzActMstrTmp.getChkDatetime());
		ftzActMstr.setChkAddWord(ftzActMstrTmp.getChkAddWord());
	}

}