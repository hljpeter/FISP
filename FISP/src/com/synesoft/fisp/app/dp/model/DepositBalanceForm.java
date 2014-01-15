package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.DmDepositBalance;


public class DepositBalanceForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private DmDepositBalance depositBalance = new DmDepositBalance();
	
    @Valid
	@NotEmpty(groups = {DP_02_06_Add.class}, message ="{e.dp.0015}")
	private String customerNo;
    
	private String workdate;

	@Valid
	@NotEmpty(groups = {DP_02_06_Add.class}, message ="{e.dp.0016}")
	private String depositAccCode;
	
	@Valid
	@NotEmpty(groups = {DP_02_06_Add.class}, message ="{e.dp.0017}")
	private String depositAgreementCode;
	
	@Valid
	@NotEmpty(groups = { DP_02_02_Modify.class,DP_02_06_Add.class}, message ="{e.dp.0011}")
	private String productType;
	
	@Valid
	@NotEmpty(groups = { DP_02_02_Modify.class}, message ="{e.dp.0012}")
	private String depositAgreementSdate;
	
	@Valid
	@NotEmpty(groups = { DP_02_02_Modify.class}, message ="{e.dp.0013}")
	private String depositAgreementEdate;
	
	@Valid
	@NotEmpty(groups = { DP_02_02_Modify.class,DP_02_06_Add.class}, message ="{e.dp.0008}")
	private String interestRateFix;
	
	@Valid
	@NotEmpty(groups = {DP_02_06_Add.class}, message ="{e.dp.0014}")
	private String depositBalanceDis;
	
	@Valid
	@NotEmpty(groups = { DP_02_02_Modify.class,DP_02_06_Add.class}, message ="{e.dp.0009}")
	private String interestRate;
	
	private String fcurrDollar;
	
	private String comment;
	
	public static interface DP_02_06_Add{
		
	}

	public static interface DP_02_02_Modify{
		
	}

	/**
	 * @return the workdate
	 */
	public String getWorkdate() {
		return workdate;
	}

	/**
	 * @param workdate the workdate to set
	 */
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the depositBalance
	 */
	public DmDepositBalance getDepositBalance() {
		return depositBalance;
	}

	/**
	 * @param depositBalance the depositBalance to set
	 */
	public void setDepositBalance(DmDepositBalance depositBalance) {
		this.depositBalance = depositBalance;
	}

	
	

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the depositAgreementSdate
	 */
	public String getDepositAgreementSdate() {
		return depositAgreementSdate;
	}

	/**
	 * @param depositAgreementSdate the depositAgreementSdate to set
	 */
	public void setDepositAgreementSdate(String depositAgreementSdate) {
		this.depositAgreementSdate = depositAgreementSdate;
	}

	/**
	 * @return the depositAgreementEdate
	 */
	public String getDepositAgreementEdate() {
		return depositAgreementEdate;
	}

	/**
	 * @param depositAgreementEdate the depositAgreementEdate to set
	 */
	public void setDepositAgreementEdate(String depositAgreementEdate) {
		this.depositAgreementEdate = depositAgreementEdate;
	}

	/**
	 * @return the interestRateFix
	 */
	public String getInterestRateFix() {
		return interestRateFix;
	}

	/**
	 * @param interestRateFix the interestRateFix to set
	 */
	public void setInterestRateFix(String interestRateFix) {
		this.interestRateFix = interestRateFix;
	}

	/**
	 * @return the interestRate
	 */
	public String getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the depositBalanceDis
	 */
	public String getDepositBalanceDis() {
		return depositBalanceDis;
	}

	/**
	 * @param depositBalanceDis the depositBalanceDis to set
	 */
	public void setDepositBalanceDis(String depositBalanceDis) {
		this.depositBalanceDis = depositBalanceDis;
	}

	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @return the depositAccCode
	 */
	public String getDepositAccCode() {
		return depositAccCode;
	}

	/**
	 * @param depositAccCode the depositAccCode to set
	 */
	public void setDepositAccCode(String depositAccCode) {
		this.depositAccCode = depositAccCode;
	}

	/**
	 * @return the depositAgreementCode
	 */
	public String getDepositAgreementCode() {
		return depositAgreementCode;
	}

	/**
	 * @param depositAgreementCode the depositAgreementCode to set
	 */
	public void setDepositAgreementCode(String depositAgreementCode) {
		this.depositAgreementCode = depositAgreementCode;
	}

	/**
	 * @return the fcurrDollar
	 */
	public String getFcurrDollar() {
		return fcurrDollar;
	}

	/**
	 * @param fcurrDollar the fcurrDollar to set
	 */
	public void setFcurrDollar(String fcurrDollar) {
		this.fcurrDollar = fcurrDollar;
	}

}
