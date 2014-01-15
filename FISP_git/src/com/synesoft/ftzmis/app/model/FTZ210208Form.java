package com.synesoft.ftzmis.app.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;

public class FTZ210208Form implements Serializable {
 	
	private static final long serialVersionUID = 1L;

	public static interface FTZInFormMsgKey { }
	public static interface FTZInFormTxnKey { }
	
	public static interface FTZInFormMsgQry { }
	

	public static interface FTZInFormTxnAdd { }
	public static interface FTZ210206FormTxn { }
	public static interface FTZ210206FormTxnUpt { }
	
	public static interface FTZ210206FormAuthAll { }
	public static interface FTZ210206FormAuthTxnInit { }
	public static interface FTZ210206FormAuthTxn { }
	
	
	private String actionFlag;

	private String operFlag;
	
	private String msg;
	
	@Valid
	private FtzInMsgCtlVO ftzInMsgCtlVO;

	@Valid
	private FtzInMsgCtl ftzInMsgCtl;

	@Valid
	private FtzInTxnDtl ftzInTxnDtl;
	
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
	 * @return the ftzInTxnDtl
	 */
	public FtzInTxnDtl getFtzInTxnDtl() {
		return ftzInTxnDtl;
	}

	/**
	 * @param ftzInTxnDtl the ftzInTxnDtl to set
	 */
	public void setFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		this.ftzInTxnDtl = ftzInTxnDtl;
	}

	/**
	 * @return the ftzInMsgCtl
	 */
	public FtzInMsgCtl getFtzInMsgCtl() {
		return ftzInMsgCtl;
	}

	/**
	 * @param ftzInMsgCtl the ftzInMsgCtl to set
	 */
	public void setFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		this.ftzInMsgCtl = ftzInMsgCtl;
	}

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
	 * @return the ftzInMsgCtlVO
	 */
	public FtzInMsgCtlVO getFtzInMsgCtlVO() {
		return ftzInMsgCtlVO;
	}

	/**
	 * @param ftzInMsgCtlVO the ftzInMsgCtlVO to set
	 */
	public void setFtzInMsgCtlVO(FtzInMsgCtlVO ftzInMsgCtlVO) {
		this.ftzInMsgCtlVO = ftzInMsgCtlVO;
	}
	
}
