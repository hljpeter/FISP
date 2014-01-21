package com.synesoft.ftzmis.app.common.msgproc;

import com.synesoft.ftzmis.app.common.util.DateUtil;

public class FtzMsgHead {

	public String VER; // 版本号
	public String SRC; // 发起节点代码
	public String DES; // 接收节点代码
	public String APP; // 应用名称
	public String MsgNo; // 报文编号
	public String MsgID; // 报文标识号
	public String MsgRef; // 报文参考号
	public String WorkDate; // 工作日期
	public String EditFlag; // 操作标识
	public String Reserve; // 预留字段

	public String getVER() {
		return VER;
	}

	public void setVER(String vER) {
		VER = vER;
	}

	public String getSRC() {
		return SRC;
	}

	public void setSRC(String sRC) {
		SRC = sRC;
	}

	public String getDES() {
		return DES;
	}

	public void setDES(String dES) {
		DES = dES;
	}

	public String getAPP() {
		return APP;
	}

	public void setAPP(String aPP) {
		APP = aPP;
	}

	public String getMsgNo() {
		return MsgNo;
	}

	public void setMsgNo(String msgNo) {
		MsgNo = msgNo;
	}

	public String getMsgID() {
		return MsgID;
	}

	public void setMsgID(String msgID) {
		MsgID = msgID;
	}

	public String getMsgRef() {
		return MsgRef;
	}

	public void setMsgRef(String msgRef) {
		MsgRef = msgRef;
	}

	public String getWorkDate() {
		return WorkDate;
	}

	public void setWorkDate(String workDate) {
		WorkDate = workDate;
	}

	public String getEditFlag() {
		return EditFlag;
	}

	public void setEditFlag(String editFlag) {
		EditFlag = editFlag;
	}

	public String getReserve() {
		return Reserve;
	}

	public void setReserve(String reserve) {
		Reserve = reserve;
	}

	public static FtzMsgHead getMsgHead() {
		FtzMsgHead mh = new FtzMsgHead();
		mh.setVER("1.0");
		mh.setSRC("671100000013");
		mh.setDES("100000000000");
		mh.setAPP("FTZMIS");
		mh.setWorkDate(DateUtil.getNowInputDate());
		mh.setEditFlag("1");
		mh.setReserve("");

		return mh;
	}
}
