package com.synesoft.ftzmis.app.model;

import javax.validation.Valid;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

public class FTZ210302Form {
	
	public static interface FTZ210302FormAddDtl { }
	
	public static interface FTZ210302FormAddDtlDtl { }
	
	@Valid
	private FtzOffMsgCtl ftzOffMsgCtl;
	
	@Valid
	private FtzOffTxnDtl ftzOffTxnDtl;
	
	private String query_branchId ="";
	
	private String query_workDate_start ="";
	
	private String query_workDate_end ="";
	
	private String query_msgId ="";
	
	private String query_msgStatus ="";
	
	private String selected_msgId ="";
	
	private String selected_seqNo = "";
	
	private String unAuthFlag ="";
	
	private String authStat ="";
	
	private String authFinishFlag ="";

	/**
	 * @return the ftzOffMsgCtl
	 */
	public FtzOffMsgCtl getFtzOffMsgCtl() {
		return ftzOffMsgCtl;
	}

	/**
	 * @param ftzOffMsgCtl the ftzOffMsgCtl to set
	 */
	public void setFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		this.ftzOffMsgCtl = ftzOffMsgCtl;
	}

	/**
	 * @return the ftzOffTxnDtl
	 */
	public FtzOffTxnDtl getFtzOffTxnDtl() {
		return ftzOffTxnDtl;
	}

	/**
	 * @param ftzOffTxnDtl the ftzOffTxnDtl to set
	 */
	public void setFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		this.ftzOffTxnDtl = ftzOffTxnDtl;
	}

	/**
	 * @return the query_branchId
	 */
	public String getQuery_branchId() {
		return query_branchId;
	}

	/**
	 * @param query_branchId the query_branchId to set
	 */
	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	/**
	 * @return the query_workDate_start
	 */
	public String getQuery_workDate_start() {
		return query_workDate_start;
	}

	/**
	 * @param query_workDate_start the query_workDate_start to set
	 */
	public void setQuery_workDate_start(String query_workDate_start) {
		this.query_workDate_start = query_workDate_start;
	}

	/**
	 * @return the query_workDate_end
	 */
	public String getQuery_workDate_end() {
		return query_workDate_end;
	}

	/**
	 * @param query_workDate_end the query_workDate_end to set
	 */
	public void setQuery_workDate_end(String query_workDate_end) {
		this.query_workDate_end = query_workDate_end;
	}

	/**
	 * @return the query_msgId
	 */
	public String getQuery_msgId() {
		return query_msgId;
	}

	/**
	 * @param query_msgId the query_msgId to set
	 */
	public void setQuery_msgId(String query_msgId) {
		this.query_msgId = query_msgId;
	}

	/**
	 * @return the query_msgStatus
	 */
	public String getQuery_msgStatus() {
		return query_msgStatus;
	}

	/**
	 * @param query_msgStatus the query_msgStatus to set
	 */
	public void setQuery_msgStatus(String query_msgStatus) {
		this.query_msgStatus = query_msgStatus;
	}

	/**
	 * @return the selected_msgId
	 */
	public String getSelected_msgId() {
		return selected_msgId;
	}

	/**
	 * @param selected_msgId the selected_msgId to set
	 */
	public void setSelected_msgId(String selected_msgId) {
		this.selected_msgId = selected_msgId;
	}

	/**
	 * @return the selected_seqNo
	 */
	public String getSelected_seqNo() {
		return selected_seqNo;
	}

	/**
	 * @param selected_seqNo the selected_seqNo to set
	 */
	public void setSelected_seqNo(String selected_seqNo) {
		this.selected_seqNo = selected_seqNo;
	}

	/**
	 * @return the unAuthFlag
	 */
	public String getUnAuthFlag() {
		return unAuthFlag;
	}

	/**
	 * @param unAuthFlag the unAuthFlag to set
	 */
	public void setUnAuthFlag(String unAuthFlag) {
		this.unAuthFlag = unAuthFlag;
	}

	/**
	 * @return the authStat
	 */
	public String getAuthStat() {
		return authStat;
	}

	/**
	 * @param authStat the authStat to set
	 */
	public void setAuthStat(String authStat) {
		this.authStat = authStat;
	}

	/**
	 * @return the authFinishFlag
	 */
	public String getAuthFinishFlag() {
		return authFinishFlag;
	}

	/**
	 * @param authFinishFlag the authFinishFlag to set
	 */
	public void setAuthFinishFlag(String authFinishFlag) {
		this.authFinishFlag = authFinishFlag;
	}

}
