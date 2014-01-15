package com.synesoft.ftzmis.app.model;

import java.io.Serializable;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

/**
 * 6.3.5　应付银行承兑汇票（210305）
 * @author yyw
 * @date 2014-01-07
 */
public class FTZ210305Form implements Serializable {
 	
	private static final long serialVersionUID = 1L;

	private String actionFlag;

	private String operFlag;
	
	private String msg;

	private FtzOffMsgCtlVO ftzOffMsgCtlVO;

	private FtzOffMsgCtl ftzOffMsgCtl;

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
