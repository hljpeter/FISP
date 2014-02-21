/**
 * 
 */
package com.synesoft.ftzmis.app.model;

import java.math.BigDecimal;

import com.synesoft.ftzmis.domain.model.FtzActMstr;

/**
 * @author Peter
 * @date 2014-1-26 下午5:47:39
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

public class FtzInCommonForm {
	
	private String check_SubmitDate =""; 
	
	private String check_AccountNo =""; 
	
	private String check_SubAccountNo ="";
	
	private FtzActMstr ftzActMstr;
	
	private BigDecimal balance_expend;
	
	private BigDecimal balance_enter;
	
	private BigDecimal balance_expendflushes;
	
	private BigDecimal balance_enterflushes;
	
	private BigDecimal balance_current;
	
	private String balance_result;
	
	private String query_branchId = "";
	
	private String query_submitDate_start = "";
	
	private String query_accountNo = "";

	private String query_subAccountNo = "";
	
	private String query_currency = "";
	
	private String selected_msgId = "";

	private String selected_seqNo;
	
	private String selected_actNo = "";
	
	private String selected_subActNo = "";

	public String getSelected_actNo() {
		return selected_actNo;
	}

	public void setSelected_actNo(String selected_actNo) {
		this.selected_actNo = selected_actNo;
	}

	public String getSelected_subActNo() {
		return selected_subActNo;
	}

	public void setSelected_subActNo(String selected_subActNo) {
		this.selected_subActNo = selected_subActNo;
	}

	public String getSelected_msgId() {
		return selected_msgId;
	}

	public void setSelected_msgId(String selected_msgId) {
		this.selected_msgId = selected_msgId;
	}

	public String getSelected_seqNo() {
		return selected_seqNo;
	}

	public void setSelected_seqNo(String selected_seqNo) {
		this.selected_seqNo = selected_seqNo;
	}

	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_submitDate_start() {
		return query_submitDate_start;
	}

	public void setQuery_submitDate_start(String query_submitDate_start) {
		this.query_submitDate_start = query_submitDate_start;
	}

	public String getQuery_accountNo() {
		return query_accountNo;
	}

	public void setQuery_accountNo(String query_accountNo) {
		this.query_accountNo = query_accountNo;
	}

	public String getQuery_subAccountNo() {
		return query_subAccountNo;
	}

	public void setQuery_subAccountNo(String query_subAccountNo) {
		this.query_subAccountNo = query_subAccountNo;
	}

	public String getQuery_currency() {
		return query_currency;
	}

	public void setQuery_currency(String query_currency) {
		this.query_currency = query_currency;
	}

	public String getCheck_SubmitDate() {
		return check_SubmitDate;
	}

	public void setCheck_SubmitDate(String check_SubmitDate) {
		this.check_SubmitDate = check_SubmitDate;
	}

	public String getCheck_AccountNo() {
		return check_AccountNo;
	}

	public void setCheck_AccountNo(String check_AccountNo) {
		this.check_AccountNo = check_AccountNo;
	}

	public String getCheck_SubAccountNo() {
		return check_SubAccountNo;
	}

	public void setCheck_SubAccountNo(String check_SubAccountNo) {
		this.check_SubAccountNo = check_SubAccountNo;
	}

	public FtzActMstr getFtzActMstr() {
		return ftzActMstr;
	}

	public void setFtzActMstr(FtzActMstr ftzActMstr) {
		this.ftzActMstr = ftzActMstr;
	}

	public BigDecimal getBalance_expend() {
		return balance_expend;
	}

	public void setBalance_expend(BigDecimal balance_expend) {
		this.balance_expend = balance_expend;
	}

	public BigDecimal getBalance_enter() {
		return balance_enter;
	}

	public void setBalance_enter(BigDecimal balance_enter) {
		this.balance_enter = balance_enter;
	}

	public BigDecimal getBalance_expendflushes() {
		return balance_expendflushes;
	}

	public void setBalance_expendflushes(BigDecimal balance_expendflushes) {
		this.balance_expendflushes = balance_expendflushes;
	}

	public BigDecimal getBalance_enterflushes() {
		return balance_enterflushes;
	}

	public void setBalance_enterflushes(BigDecimal balance_enterflushes) {
		this.balance_enterflushes = balance_enterflushes;
	}

	public BigDecimal getBalance_current() {
		return balance_current;
	}

	public void setBalance_current(BigDecimal balance_current) {
		this.balance_current = balance_current;
	}

	public String getBalance_result() {
		return balance_result;
	}

	public void setBalance_result(String balance_result) {
		this.balance_result = balance_result;
	}


}
