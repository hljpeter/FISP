package com.synesoft.ftzmis.app.common.xmlproc;

import com.synesoft.ftzmis.app.common.util.DateUtil;

public class MsgHead {

	public String VER; //版本号
	public String SRC; //发起节点代码
	public String DES; //接收节点代码
	public String APP; //应用名称
	public String WorkDate; //工作日期
	public String EditFlag; //操作标识
	public String Reserve; //预留字段
	
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

	public static MsgHead getMsgHead() {
		MsgHead mh = new MsgHead();
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
