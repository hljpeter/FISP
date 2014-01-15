package com.synesoft.ftzmis.app.model;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author hb_huang
 * @system FTZMIS(远期结售汇Form)
 * @date 2014-1-3上午10:03:47
 */
public class FTZ210310Form {
	
	private FtzOffMsgCtl ftzOffMsgCtl;
	
	private FtzOffTxnDtl ftzOffTxnDtl;
	
	private String query_branchId ="";
	
	private String query_workDate_start ="";
	
	private String query_workDate_end ="";
	
	private String query_msgId ="";
	
	private String query_msgStatus ="";
	
	private String selected_msgId ="";
	
	private String selected_seqNo ="";
	
	private String selected_msgNo = "";
	
	private String unAuthFlag ="";
	
	private String authStat ="";
	
	private String authFinishFlag ="";
	
	private String actionFlag;
	
	private String father_makTime;
	
	private String father_chkTime;
	
	public FtzOffMsgCtl getFtzOffMsgCtl() {
		return ftzOffMsgCtl;
	}

	public void setFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		this.ftzOffMsgCtl = ftzOffMsgCtl;
	}

	public FtzOffTxnDtl getFtzOffTxnDtl() {
		return ftzOffTxnDtl;
	}

	public void setFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		this.ftzOffTxnDtl = ftzOffTxnDtl;
	}

	public String getQuery_branchId() {
		return query_branchId;
	}

	public void setQuery_branchId(String query_branchId) {
		this.query_branchId = query_branchId;
	}

	public String getQuery_workDate_start() {
		return query_workDate_start;
	}

	public void setQuery_workDate_start(String query_workDate_start) {
		this.query_workDate_start = query_workDate_start;
	}

	public String getQuery_workDate_end() {
		return query_workDate_end;
	}

	public void setQuery_workDate_end(String query_workDate_end) {
		this.query_workDate_end = query_workDate_end;
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

	public String getSelected_msgNo() {
		return selected_msgNo;
	}

	public void setSelected_msgNo(String selected_msgNo) {
		this.selected_msgNo = selected_msgNo;
	}

	public String getUnAuthFlag() {
		return unAuthFlag;
	}

	public void setUnAuthFlag(String unAuthFlag) {
		this.unAuthFlag = unAuthFlag;
	}

	public String getAuthStat() {
		return authStat;
	}

	public void setAuthStat(String authStat) {
		this.authStat = authStat;
	}

	public String getAuthFinishFlag() {
		return authFinishFlag;
	}

	public void setAuthFinishFlag(String authFinishFlag) {
		this.authFinishFlag = authFinishFlag;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getFather_makTime() {
		return father_makTime;
	}

	public void setFather_makTime(String father_makTime) {
		this.father_makTime = father_makTime;
	}

	public String getFather_chkTime() {
		return father_chkTime;
	}

	public void setFather_chkTime(String father_chkTime) {
		this.father_chkTime = father_chkTime;
	}
}
