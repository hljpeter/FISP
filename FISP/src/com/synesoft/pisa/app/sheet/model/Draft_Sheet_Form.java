package com.synesoft.pisa.app.sheet.model; 

import java.math.BigDecimal;

import javax.validation.Valid;

import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-17
 */
public class Draft_Sheet_Form{
	
	
	public static interface Draft_Sheet_Form_Update{};
	
	private ExpSheetInfo sheetDraftList = new ExpSheetInfo();
	
	@Valid
	private ExpSheetDtl  expSheetDtl;
	
	private String sheetNo;  //表单代码
	
	private BigDecimal  seqNo; // 序号
	
	private String subNo;    //期数
	
	private String areaType;   //地区类型
	
	private String areaCode;  //地区代码
		
	private Short batNo;		//批次
		
	private String itemName;  //指标名称
	
	private String itemNo;    //指标代码
	

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Short getBatNo() {
		return batNo;
	}

	public void setBatNo(Short batNo) {
		this.batNo = batNo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
	}

	public ExpSheetInfo getSheetDraftList() {
		return sheetDraftList;
	}

	public void setSheetDraftList(ExpSheetInfo sheetDraftList) {
		this.sheetDraftList = sheetDraftList;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return the seqNo
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the expSheetDtl
	 */
	public ExpSheetDtl getExpSheetDtl() {
		return expSheetDtl;
	}

	/**
	 * @param expSheetDtl the expSheetDtl to set
	 */
	public void setExpSheetDtl(ExpSheetDtl expSheetDtl) {
		this.expSheetDtl = expSheetDtl;
	}

	

}
 