package com.synesoft.ftzmis.app.model; 
/** 
 *
 *银行代码查询
 * description:
 * @author wjl 
 * @version 2013-12-31
 */
public class FTZCMBankQryForm {

	private String bankCode;
	
	private String bankName;
	
	private String selected_bankName;

	private String selected_bankCode;
	
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSelected_bankName() {
		return selected_bankName;
	}

	public void setSelected_bankName(String selected_bankName) {
		this.selected_bankName = selected_bankName;
	}

	public String getSelected_bankCode() {
		return selected_bankCode;
	}

	public void setSelected_bankCode(String selected_bankCode) {
		this.selected_bankCode = selected_bankCode;
	}
	
	
}
 