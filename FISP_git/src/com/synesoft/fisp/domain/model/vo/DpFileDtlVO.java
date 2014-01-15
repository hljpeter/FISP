package com.synesoft.fisp.domain.model.vo;

public class DpFileDtlVO {

	private Short seqNo;

	private String fieldName;

	private String fieldFormula;

	private String fieldFormula_key;

	private String fieldFormula_flag;

	private String comments;

	public Short getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Short seqNo) {
		this.seqNo = seqNo;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldFormula() {
		return fieldFormula;
	}

	public void setFieldFormula(String fieldFormula) {
		this.fieldFormula = fieldFormula;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFieldFormula_key() {
		return fieldFormula_key;
	}

	public void setFieldFormula_key(String fieldFormula_key) {
		this.fieldFormula_key = fieldFormula_key;
	}

	public String getFieldFormula_flag() {
		return fieldFormula_flag;
	}

	public void setFieldFormula_flag(String fieldFormula_flag) {
		this.fieldFormula_flag = fieldFormula_flag;
	}

}
