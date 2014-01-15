package com.synesoft.ftzmis.app.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

/**
 * 6.3.1　代理发债（210301）
 * @author yyw
 * @date 2013-12-25
 */
public class FTZ210301Form implements Serializable {
 	
	private static final long serialVersionUID = 1L;

	public static interface FTZ210301FormMsgQry { }
	public static interface FTZ210301FormMsgDtl { }
	public static interface FTZ210301FormMsgUpt { }
	public static interface FTZ210301FormMsgSub { }
	
	public static interface FTZ210301FormTxnDtl { }
	public static interface FTZ210301FormTxnAddInit { }
	public static interface FTZ210301FormTxnAdd { }
	public static interface FTZ210301FormTxnUptInit { }
	public static interface FTZ210301FormTxnUpt { }
	
	public static interface FTZ210301FormAuthAll { }
	public static interface FTZ210301FormAuthTxnInit { }
	public static interface FTZ210301FormAuthTxn { }
	
	
	
	private String actionFlag;

	private String operFlag;
	
	private String msg;
	
	@Valid
	private FtzOffMsgCtlVO ftzOffMsgCtlVO;

	@Valid
	private FtzOffMsgCtl ftzOffMsgCtl;

	@Valid
	private FtzOffTxnDtl ftzOffTxnDtl;
	
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
	
}
