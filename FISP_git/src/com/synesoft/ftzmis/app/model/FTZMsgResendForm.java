package com.synesoft.ftzmis.app.model;

import javax.validation.Valid;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS(资金来源再发送)
 * @date 2013-12-31下午04:14:23
 */
/**
 * @author hb_huang
 *
 */
public class FTZMsgResendForm {
	
	public static interface FTZ210101FormAddDtl { }
	
	public static interface FTZ210101FormAddDtlDtl { }

	@Valid
	private FtzInMsgCtl ftzInMsgCtl;
	
	@Valid
	private FtzInTxnDtl ftzInTxnDtl;
	
	private String query_branchId ="";
	
	private String query_submitDate_start ="";
	
	private String query_submitDate_end ="";
	
	private String query_msgId ="";
	
	private String query_msgStatus ="";
	
	private String query_accountNo ="";
	
	private String query_subAccountNo ="";
	
	private String query_accountName ="";
	
	private String query_msgNo ="";
	
	private String selected_msgId ="";
	
	private String selected_seqNo;

	public String getSelected_seqNo() {
		return selected_seqNo;
	}

	public void setSelected_seqNo(String selected_seqNo) {
		this.selected_seqNo = selected_seqNo;
	}

	public FtzInMsgCtl getFtzInMsgCtl() {
		return ftzInMsgCtl;
	}

	public void setFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		this.ftzInMsgCtl = ftzInMsgCtl;
	}

	public FtzInTxnDtl getFtzInTxnDtl() {
		return ftzInTxnDtl;
	}

	public void setFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		this.ftzInTxnDtl = ftzInTxnDtl;
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

	public String getQuery_submitDate_end() {
		return query_submitDate_end;
	}

	public void setQuery_submitDate_end(String query_submitDate_end) {
		this.query_submitDate_end = query_submitDate_end;
	}

	public String getQuery_msgId() {
		return query_msgId;
	}

	public void setQuery_msgId(String query_msgId) {
		this.query_msgId = query_msgId;
	}

	public String getQuery_msgStatus() {
		return query_msgStatus;
	}

	public void setQuery_msgStatus(String query_msgStatus) {
		this.query_msgStatus = query_msgStatus;
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

	public String getQuery_accountName() {
		return query_accountName;
	}

	public void setQuery_accountName(String query_accountName) {
		this.query_accountName = query_accountName;
	}

	public String getQuery_msgNo() {
		return query_msgNo;
	}

	public void setQuery_msgNo(String query_msgNo) {
		this.query_msgNo = query_msgNo;
	}

	public String getSelected_msgId() {
		return selected_msgId;
	}

	public void setSelected_msgId(String selected_msgId) {
		this.selected_msgId = selected_msgId;
	}
}
