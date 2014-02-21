package com.synesoft.ftzmis.app.model;

import java.util.List;

import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;


/**
 * 报文生成
 * @author yyw
 * @date 2014-01-07
 */
public class FTZMsgGenerateForm {
	
	private List<FtzMsgGenerateParam> list;
	private String[] selecteds;

	/**
	 * @return the selecteds
	 */
	public String[] getSelecteds() {
		return selecteds;
	}

	/**
	 * @param selecteds the selecteds to set
	 */
	public void setSelecteds(String[] selecteds) {
		this.selecteds = selecteds;
	}

	/**
	 * @return the list
	 */
	public List<FtzMsgGenerateParam> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<FtzMsgGenerateParam> list) {
		this.list = list;
	}

}
