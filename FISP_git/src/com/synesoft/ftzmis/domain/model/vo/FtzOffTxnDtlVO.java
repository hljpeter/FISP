package com.synesoft.ftzmis.domain.model.vo;

import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author yyw
 * @date 2014-01-06
 */
public class FtzOffTxnDtlVO extends FtzOffTxnDtl {

	private String oldMakDatetime;

	/**
	 * @return the oldMakDatetime
	 */
	public String getOldMakDatetime() {
		return oldMakDatetime;
	}

	/**
	 * @param oldMakDatetime the oldMakDatetime to set
	 */
	public void setOldMakDatetime(String oldMakDatetime) {
		this.oldMakDatetime = oldMakDatetime;
	}
	
}
