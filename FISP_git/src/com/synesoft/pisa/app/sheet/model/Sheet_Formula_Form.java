package com.synesoft.pisa.app.sheet.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.pisa.domain.model.ExpSheetFormula;

public class Sheet_Formula_Form implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExpSheetFormula expSheetFormula=new ExpSheetFormula();
	
	@Valid
	@NotEmpty(groups = { Sheet_Formula_Add.class,Sheet_Formula_Modify.class}, message ="{e.pisa.1001}")
	private String sheetNo;
	
	@Valid
	@NotEmpty(groups = { Sheet_Formula_Add.class,Sheet_Formula_Modify.class}, message ="{e.pisa.1003}")
	private String formulaName;
	
	private String itemNo;
	
	private String dimNo;
	
	private String itemName;
	
	@Valid
	@NotEmpty(groups = { Sheet_Formula_Add.class,Sheet_Formula_Modify.class}, message ="{e.pisa.1002}")
	private String formulaArea;

	public static interface Sheet_Formula_Add {
	}
	public static interface Sheet_Formula_Modify{
	}
	/**
	 * @return the expSheetFormula
	 */
	public ExpSheetFormula getExpSheetFormula() {
		return expSheetFormula;
	}

	/**
	 * @param expSheetFormula the expSheetFormula to set
	 */
	public void setExpSheetFormula(ExpSheetFormula expSheetFormula) {
		this.expSheetFormula = expSheetFormula;
	}

	/**
	 * @return the sheetNo
	 */
	public String getSheetNo() {
		return sheetNo;
	}

	/**
	 * @param sheetNo the sheetNo to set
	 */
	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
	}

	/**
	 * @return the formulaName
	 */
	public String getFormulaName() {
		return formulaName;
	}

	/**
	 * @param formulaName the formulaName to set
	 */
	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	/**
	 * @return the itemNo
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * @param itemNo the itemNo to set
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return the dimNo
	 */
	public String getDimNo() {
		return dimNo;
	}

	/**
	 * @param dimNo the dimNo to set
	 */
	public void setDimNo(String dimNo) {
		this.dimNo = dimNo;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the formulaArea
	 */
	public String getFormulaArea() {
		return formulaArea;
	}

	/**
	 * @param formulaArea the formulaArea to set
	 */
	public void setFormulaArea(String formulaArea) {
		this.formulaArea = formulaArea;
	}

}
