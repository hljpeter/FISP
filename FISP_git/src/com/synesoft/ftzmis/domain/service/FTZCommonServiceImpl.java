/**
 * 
 */
package com.synesoft.ftzmis.domain.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrHistory;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.repository.FtzActMstrHistoryRepository;
import com.synesoft.ftzmis.domain.repository.FtzActMstrRepository;
import com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository;

/**
 * @author Peter
 * @date 2014-1-27 上午10:02:34
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

@Service
public class FTZCommonServiceImpl implements FTZCommonService {

	/**
	 * 校验某日期下某账号余额是否连续
	 * 
	 * 调用后请先判断check_result是否为true，若为false则调用失败，可能参数为空，可能该账户该日期无账号数据
	 * 
	 * @param submitDate
	 *            申报日期
	 * @param accountNo
	 *            账号
	 * @param subAccountNo
	 *            子账号-若无子账号，则填写账号的内容
	 * @return Map check_result - boolean 调用方法结果 balance_result - boolean 余额校验结果
	 *         balance_current - BigDecimal 计算余额 balance_beginning - BigDecimal
	 *         日初余额 balance_end - BigDecimal 日终余额 balance_expend - BigDecimal
	 *         出账发生额 balance_enter - BigDecimal 入账发生额 balance_expendflushes -
	 *         BigDecimal 出账冲正发生额 balance_enterflushes - BigDecimal 入账冲正发生额
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map BalanceCheck(String submitDate, String accountNo,
			String subAccountNo) {
		Map balanceResult = new HashMap<String, Object>();
		boolean check_result = false;
		boolean balance_result = false;
		// 若参数为空，返回调用方法结果 false
		if (null == submitDate || null == accountNo || null == subAccountNo) {
			balanceResult.put("check_result", check_result);
			return balanceResult;
		}
		// 查询当前工作日期，与传入的申报日期对比
		String workDate = procCommonService.queryWorkDate();
		submitDate = submitDate.replace("-", "");
		// 相等 - 查询账号主表
		BigDecimal beginningBal = new BigDecimal(0);
		BigDecimal endBal = new BigDecimal(0);
		if (workDate.equals(submitDate)) {
			FtzActMstr query_ActMstr = new FtzActMstr();
			query_ActMstr.setAccountNo(accountNo);
			query_ActMstr.setSubAccountNo(subAccountNo);
			FtzActMstr result_ActMstr = ftzActMstrRepos
					.queryFtzActMstrByKey(query_ActMstr);
			if (null == result_ActMstr) {
				balanceResult.put("check_result", check_result);
				return balanceResult;
			}
			beginningBal = result_ActMstr.getLastBalance();
			endBal = result_ActMstr.getBalance();
		}
		// 否则查询账号历史表 若无记录 ，返回调用方法结果 false
		else {
			FtzActMstrHistory query_FtzActMstrHistory = new FtzActMstrHistory();
			query_FtzActMstrHistory.setAccountNo(accountNo);
			query_FtzActMstrHistory.setSubAccountNo(subAccountNo);
			query_FtzActMstrHistory.setSubmitDate(submitDate);
			FtzActMstrHistory result_FtzActMstrHistory = ftzActMstrHistoryRepos
					.queryFtzActMstrHistoryByKey(query_FtzActMstrHistory);
			if (null == result_FtzActMstrHistory) {
				balanceResult.put("check_result", check_result);
				return balanceResult;
			}
			beginningBal = result_FtzActMstrHistory.getLastBalance();
			endBal = result_FtzActMstrHistory.getBalance();
		}

		// 查询不同发生额
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setRsv1(submitDate);
		ftzInTxnDtl.setRsv2(accountNo);
		ftzInTxnDtl.setRsv3(subAccountNo);
		List<FtzInTxnDtl> ftzInTxnDtls_count = ftzInTxnDtlRepos
				.queryDtlCountByCDFlag(ftzInTxnDtl);
		List<FtzInTxnDtl> ftzInTxnDtls_sum = ftzInTxnDtlRepos
				.queryAmountSumByCDFlag(ftzInTxnDtl);
		int expendCount = 0;
		int enterCount = 0;
		int expendflushesCount = 0;
		int enterflushesCount = 0;
		if (null != ftzInTxnDtls_count) {
			for (FtzInTxnDtl dtl : ftzInTxnDtls_count) {
				if (CommonConst.CD_FLAG_EXPEND.equals(dtl.getCdFlag())) {
					expendCount = dtl.getCdCount();
				} else if (CommonConst.CD_FLAG_ENTER.equals(dtl.getCdFlag())) {
					enterCount = dtl.getCdCount();
				} else if (CommonConst.CD_FLAG_EXPEND_FLUSHES.equals(dtl
						.getCdFlag())) {
					expendflushesCount = dtl.getCdCount();
				} else if (CommonConst.CD_FLAG_ENTER_FLUSHES.equals(dtl
						.getCdFlag())) {
					enterflushesCount = dtl.getCdCount();
				}
			}
		}
		BigDecimal expendBal = new BigDecimal(0);
		BigDecimal enterBal = new BigDecimal(0);
		BigDecimal expendflushesBal = new BigDecimal(0);
		BigDecimal enterflushesBal = new BigDecimal(0);
		if (null != ftzInTxnDtls_sum) {
			for (FtzInTxnDtl dtl : ftzInTxnDtls_sum) {
				if (CommonConst.CD_FLAG_EXPEND.equals(dtl.getCdFlag())) {
					expendBal = dtl.getCdSum();
				} else if (CommonConst.CD_FLAG_ENTER.equals(dtl.getCdFlag())) {
					enterBal = dtl.getCdSum();
				} else if (CommonConst.CD_FLAG_EXPEND_FLUSHES.equals(dtl
						.getCdFlag())) {
					expendflushesBal = dtl.getCdSum();
				} else if (CommonConst.CD_FLAG_ENTER_FLUSHES.equals(dtl
						.getCdFlag())) {
					enterflushesBal = dtl.getCdSum();
				}
			}
		}

		// 计算当前余额
		BigDecimal currentBal = beginningBal.add(enterBal)
				.add(expendflushesBal).subtract(expendBal)
				.subtract(enterflushesBal);

		check_result = true;

		FtzActMstr update_ActMstr = new FtzActMstr();
		FtzActMstrHistory update_FtzActMstrHistory = new FtzActMstrHistory();
		if (currentBal.equals(endBal)) {
			balance_result = true;
			update_ActMstr.setCheckStatus(CommonConst.BALANCE_CHECK_STATUS_PASS);
			update_FtzActMstrHistory.setCheckStatus(CommonConst.BALANCE_CHECK_STATUS_PASS);
		} else {
			balance_result = false;
			update_ActMstr.setCheckStatus(CommonConst.BALANCE_CHECK_STATUS_NOT_PASS);
			update_FtzActMstrHistory.setCheckStatus(CommonConst.BALANCE_CHECK_STATUS_NOT_PASS);
		}

		// 将校验结果更新至账号表
		if (workDate.equals(submitDate)) {
			update_ActMstr.setAccountNo(accountNo);
			update_ActMstr.setSubAccountNo(subAccountNo);
			update_ActMstr.setDebitAmount(enterBal);
			update_ActMstr.setDebitCount(enterCount);
			update_ActMstr.setCreditAmount(expendBal);
			update_ActMstr.setCreditCount(expendCount);
			update_ActMstr.setReversalDebitAmount(enterflushesBal);
			update_ActMstr.setReversalDebitCount(enterflushesCount);
			update_ActMstr.setReversalCreditAmount(expendflushesBal);
			update_ActMstr.setReversalCreditCount(expendflushesCount);
			update_ActMstr.setCheckDatetime(DateUtil.getNowInputDateTime());
			ftzActMstrRepos.updateFtzActMstrByKey(update_ActMstr);
		}
		// 否则查询账号历史表 若无记录 ，返回调用方法结果 false
		else {
			update_FtzActMstrHistory.setAccountNo(accountNo);
			update_FtzActMstrHistory.setSubAccountNo(subAccountNo);
			update_FtzActMstrHistory.setSubmitDate(submitDate);
			update_FtzActMstrHistory.setDebitAmount(enterBal);
			update_FtzActMstrHistory.setDebitCount(enterCount);
			update_FtzActMstrHistory.setCreditAmount(expendBal);
			update_FtzActMstrHistory.setCreditCount(expendCount);
			update_FtzActMstrHistory.setReversalDebitAmount(enterflushesBal);
			update_FtzActMstrHistory.setReversalDebitCount(enterflushesCount);
			update_FtzActMstrHistory.setReversalCreditAmount(expendflushesBal);
			update_FtzActMstrHistory.setReversalCreditCount(expendflushesCount);
			update_FtzActMstrHistory.setCheckDatetime(DateUtil.getNowInputDateTime());
			ftzActMstrHistoryRepos.updateFtzActMstrHistoryByKey(update_FtzActMstrHistory);
		}

		balanceResult.put("check_result", check_result);
		balanceResult.put("balance_result", balance_result);
		balanceResult.put("balance_current", currentBal);
		balanceResult.put("balance_beginning", beginningBal);
		balanceResult.put("balance_end", endBal);
		balanceResult.put("balance_expend", expendBal);
		balanceResult.put("balance_enter", enterBal);
		balanceResult.put("balance_expendflushes", expendflushesBal);
		balanceResult.put("balance_enterflushes", enterflushesBal);

		return balanceResult;
	}
	//查询根据主键查询历史表
	@Override
	public FtzActMstrHistory queryFtzActMstrHistory(FtzActMstrHistory ftzActMstrHistory) {
		FtzActMstrHistory new_ftzActMstrHistory = ftzActMstrHistoryRepos.queryFtzActMstrHistoryByKey(ftzActMstrHistory);
		if (new_ftzActMstrHistory != null) {
			if(null != new_ftzActMstrHistory.getDeptType() && !"".equals(new_ftzActMstrHistory.getDeptType())) {
				new_ftzActMstrHistory.setDeptType(new_ftzActMstrHistory.getDeptType().trim());
			}
			if (null != new_ftzActMstrHistory.getBalanceCode() && !"".equals(new_ftzActMstrHistory.getBalanceCode())) {
				new_ftzActMstrHistory.setBalanceCode(new_ftzActMstrHistory.getBalanceCode().trim());
			}
		}
		return new_ftzActMstrHistory;
	}
	

	@Resource
	private ProcCommonService procCommonService;

	@Resource
	private FTZCMAccountQryService ftzcmactQryServ;

	@Resource
	private FtzInTxnDtlRepository ftzInTxnDtlRepos;

	@Resource
	private FtzActMstrRepository ftzActMstrRepos;

	@Resource
	private FtzActMstrHistoryRepository ftzActMstrHistoryRepos;

}
