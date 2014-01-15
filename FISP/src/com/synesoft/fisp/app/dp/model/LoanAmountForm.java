package com.synesoft.fisp.app.dp.model;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.DmLoanAmount;


public class LoanAmountForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private DmLoanAmount loanAmount = new DmLoanAmount();
   
    @Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1101}")
    private String workdate;
    
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1102}")
	private String customerType;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1103}")
	private String loanIndustryType;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1104}")
	private String loanIouCode;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1105}")
	private String productType;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1106}")
	private String loanActualInvest;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1107}")
	private String loanOriginationDate;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1108}")
	private String loanMaturityDate;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1109}")
	private String currency;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1110}")
	private String loanIouAmount;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1111}")
	private String interestRateFix;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1112}")
	private String interestRate;

	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1113}")
	private String loanGuaranteeKind;

	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1114}")
	private String status;
	
	@Valid
	@NotEmpty(groups = { DP_01_02_Modify.class,DP_01_06_Add.class}, message ="{e.dp.1115}")
	private String loanRecoverySign;
	
	private String endDate;
	
	private String comment;
	
	public static interface DP_01_02_Modify{
		
	}
	public static interface DP_01_06_Add{
		
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
	 * @return the loanAmount
	 */
	public DmLoanAmount getLoanAmount() {
		return loanAmount;
	}
	/**
	 * @param loanAmount the loanAmount to set
	 */
	public void setLoanAmount(DmLoanAmount loanAmount) {
		this.loanAmount = loanAmount;
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
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the loanIndustryType
	 */
	public String getLoanIndustryType() {
		return loanIndustryType;
	}
	/**
	 * @param loanIndustryType the loanIndustryType to set
	 */
	public void setLoanIndustryType(String loanIndustryType) {
		this.loanIndustryType = loanIndustryType;
	}
	/**
	 * @return the loanIouCode
	 */
	public String getLoanIouCode() {
		return loanIouCode;
	}
	/**
	 * @param loanIouCode the loanIouCode to set
	 */
	public void setLoanIouCode(String loanIouCode) {
		this.loanIouCode = loanIouCode;
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
	 * @return the loanActualInvest
	 */
	public String getLoanActualInvest() {
		return loanActualInvest;
	}
	/**
	 * @param loanActualInvest the loanActualInvest to set
	 */
	public void setLoanActualInvest(String loanActualInvest) {
		this.loanActualInvest = loanActualInvest;
	}
	/**
	 * @return the loanOriginationDate
	 */
	public String getLoanOriginationDate() {
		return loanOriginationDate;
	}
	/**
	 * @param loanOriginationDate the loanOriginationDate to set
	 */
	public void setLoanOriginationDate(String loanOriginationDate) {
		this.loanOriginationDate = loanOriginationDate;
	}
	/**
	 * @return the loanMaturityDate
	 */
	public String getLoanMaturityDate() {
		return loanMaturityDate;
	}
	/**
	 * @param loanMaturityDate the loanMaturityDate to set
	 */
	public void setLoanMaturityDate(String loanMaturityDate) {
		this.loanMaturityDate = loanMaturityDate;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the loanIouAmount
	 */
	public String getLoanIouAmount() {
		return loanIouAmount;
	}
	/**
	 * @param loanIouAmount the loanIouAmount to set
	 */
	public void setLoanIouAmount(String loanIouAmount) {
		this.loanIouAmount = loanIouAmount;
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
	 * @return the loanGuaranteeKind
	 */
	public String getLoanGuaranteeKind() {
		return loanGuaranteeKind;
	}
	/**
	 * @param loanGuaranteeKind the loanGuaranteeKind to set
	 */
	public void setLoanGuaranteeKind(String loanGuaranteeKind) {
		this.loanGuaranteeKind = loanGuaranteeKind;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the loanRecoverySign
	 */
	public String getLoanRecoverySign() {
		return loanRecoverySign;
	}
	/**
	 * @param loanRecoverySign the loanRecoverySign to set
	 */
	public void setLoanRecoverySign(String loanRecoverySign) {
		this.loanRecoverySign = loanRecoverySign;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
