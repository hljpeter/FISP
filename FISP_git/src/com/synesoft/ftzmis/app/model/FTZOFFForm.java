package com.synesoft.ftzmis.app.model;


import javax.validation.Valid;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

public class FTZOFFForm {

	public static interface FTZOFFFormMsgId { }
	public static interface FTZOFFFormMsQry { }
	
	@Valid
	private FtzOffMsgCtlVO ftzOffMsgCtlVO;

	@Valid
	private FtzOffMsgCtl ftzOffMsgCtl;

	@Valid
	private FtzOffTxnDtl ftzOffTxnDtl;

	private String actionFlag;

	private String operFlag;
	
	private String msg;
	
	
	private String query_branchId ="";
	
	private String query_workDate_start ="";
	
	private String query_workDate_end ="";
	
	private String query_msgId ="";
	
	private String query_msgStatus ="";
	
	private String query_msgNo = "";
	
	private String selected_msgId ="";
	
	private String selected_msgNo ="";

	/**
	 * @return the actionFlag
	 */
	public String getActionFlag() {
		return actionFlag;
	}

	/**
	 * @param actionFlag the actionFlag to set
	 */
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	/**
	 * @return the operFlag
	 */
	public String getOperFlag() {
		return operFlag;
	}

	/**
	 * @param operFlag the operFlag to set
	 */
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the ftzOffMsgCtlVO
	 */
	public FtzOffMsgCtlVO getFtzOffMsgCtlVO() {
		return ftzOffMsgCtlVO;
	}

	/**
	 * @param ftzOffMsgCtlVO the ftzOffMsgCtlVO to set
	 */
	public void setFtzOffMsgCtlVO(FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		this.ftzOffMsgCtlVO = ftzOffMsgCtlVO;
	}

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
	 * @return the selected_msgNo
	 */
	public String getSelected_msgNo() {
		return selected_msgNo;
	}

	/**
	 * @param selected_msgNo the selected_msgNo to set
	 */
	public void setSelected_msgNo(String selected_msgNo) {
		this.selected_msgNo = selected_msgNo;
	}

	/**
	 * @return the query_msgNo
	 */
	public String getQuery_msgNo() {
		return query_msgNo;
	}

	/**
	 * @param query_msgNo the query_msgNo to set
	 */
	public void setQuery_msgNo(String query_msgNo) {
		this.query_msgNo = query_msgNo;
	}

	
	
}
