package com.synesoft.pisa.domain.model;

import java.math.BigDecimal;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.pisa.app.sheet.model.Draft_Sheet_Form.Draft_Sheet_Form_Update;

public class ExpSheetDtl{
   
	/**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.SEQ_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private BigDecimal seqNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.SHEET_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String sheetNo;
	/**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.SUB_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String subCode;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.FREQ_FLAG
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String freqFlag;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.SUB_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String subNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.BAT_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private Short batNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.MSG_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String msgType;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.BANK_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String bankCode;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.AREA_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String areaCode;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.AREA_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String areaType;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.ITEM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String itemNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DIM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String dimNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DIM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String dimName;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.ITEM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String itemName;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DATA_TYPE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String dataType1;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DATA_TYPE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String dataType2;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DATA_VALUE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    @NotEmpty(groups = {Draft_Sheet_Form_Update.class}, message = "{e.pisa.1004}")
    @Pattern(regexp="^[0-9]+\\.{0,1}[0-9]{0,2}$",groups= {Draft_Sheet_Form_Update.class},message="{e.pisa.1006}")
    private String dataValue1;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.DATA_VALUE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    @NotEmpty(groups = {Draft_Sheet_Form_Update.class}, message = "{e.pisa.1005}")
    @Pattern(regexp="^[0-9]+\\.{0,1}[0-9]{0,2}$",groups= {Draft_Sheet_Form_Update.class},message="{e.pisa.1007}")
    private String dataValue2;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.RSV1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String rsv1;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.RSV2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String rsv2;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.RSV3
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String rsv3;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.RSV4
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String rsv4;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.RSV5
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    private String rsv5;
    
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.STATUS
     *
     * @abatorgenerated Fri Jan 03 15:36:45 CST 2014
     */
    private String status;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.MAK_USER_ID
     *
     * @abatorgenerated Fri Jan 03 15:36:45 CST 2014
     */
    private String makUserId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.MAK_DATETIME
     *
     * @abatorgenerated Fri Jan 03 15:36:45 CST 2014
     */
    private String makDatetime;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.CHK_USER_ID
     *
     * @abatorgenerated Fri Jan 03 15:36:45 CST 2014
     */
    private String chkUserId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column EXP_SHEET_DTL.CHK_DATETIME
     *
     * @abatorgenerated Fri Jan 03 15:36:45 CST 2014
     */
    private String chkDatetime;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.SUB_CODE
     *
     * @return the value of EXP_SHEET_DTL.SUB_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getSubCode() {
        return subCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.SUB_CODE
     *
     * @param subCode the value for EXP_SHEET_DTL.SUB_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.FREQ_FLAG
     *
     * @return the value of EXP_SHEET_DTL.FREQ_FLAG
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getFreqFlag() {
        return freqFlag;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.FREQ_FLAG
     *
     * @param freqFlag the value for EXP_SHEET_DTL.FREQ_FLAG
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setFreqFlag(String freqFlag) {
        this.freqFlag = freqFlag;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.SUB_NO
     *
     * @return the value of EXP_SHEET_DTL.SUB_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getSubNo() {
        return subNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.SUB_NO
     *
     * @param subNo the value for EXP_SHEET_DTL.SUB_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setSubNo(String subNo) {
        this.subNo = subNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.BAT_NO
     *
     * @return the value of EXP_SHEET_DTL.BAT_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public Short getBatNo() {
        return batNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.BAT_NO
     *
     * @param batNo the value for EXP_SHEET_DTL.BAT_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setBatNo(Short batNo) {
        this.batNo = batNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.MSG_TYPE
     *
     * @return the value of EXP_SHEET_DTL.MSG_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.MSG_TYPE
     *
     * @param msgType the value for EXP_SHEET_DTL.MSG_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.BANK_CODE
     *
     * @return the value of EXP_SHEET_DTL.BANK_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.BANK_CODE
     *
     * @param bankCode the value for EXP_SHEET_DTL.BANK_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.AREA_CODE
     *
     * @return the value of EXP_SHEET_DTL.AREA_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.AREA_CODE
     *
     * @param areaCode the value for EXP_SHEET_DTL.AREA_CODE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.AREA_TYPE
     *
     * @return the value of EXP_SHEET_DTL.AREA_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.AREA_TYPE
     *
     * @param areaType the value for EXP_SHEET_DTL.AREA_TYPE
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.ITEM_NO
     *
     * @return the value of EXP_SHEET_DTL.ITEM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.ITEM_NO
     *
     * @param itemNo the value for EXP_SHEET_DTL.ITEM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DIM_NO
     *
     * @return the value of EXP_SHEET_DTL.DIM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDimNo() {
        return dimNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DIM_NO
     *
     * @param dimNo the value for EXP_SHEET_DTL.DIM_NO
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDimNo(String dimNo) {
        this.dimNo = dimNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DIM_NAME
     *
     * @return the value of EXP_SHEET_DTL.DIM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDimName() {
        return dimName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DIM_NAME
     *
     * @param dimName the value for EXP_SHEET_DTL.DIM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.ITEM_NAME
     *
     * @return the value of EXP_SHEET_DTL.ITEM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.ITEM_NAME
     *
     * @param itemName the value for EXP_SHEET_DTL.ITEM_NAME
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DATA_TYPE1
     *
     * @return the value of EXP_SHEET_DTL.DATA_TYPE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDataType1() {
        return dataType1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DATA_TYPE1
     *
     * @param dataType1 the value for EXP_SHEET_DTL.DATA_TYPE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDataType1(String dataType1) {
        this.dataType1 = dataType1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DATA_TYPE2
     *
     * @return the value of EXP_SHEET_DTL.DATA_TYPE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDataType2() {
        return dataType2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DATA_TYPE2
     *
     * @param dataType2 the value for EXP_SHEET_DTL.DATA_TYPE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDataType2(String dataType2) {
        this.dataType2 = dataType2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DATA_VALUE1
     *
     * @return the value of EXP_SHEET_DTL.DATA_VALUE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDataValue1() {
        return dataValue1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DATA_VALUE1
     *
     * @param dataValue1 the value for EXP_SHEET_DTL.DATA_VALUE1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDataValue1(String dataValue1) {
        this.dataValue1 = dataValue1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.DATA_VALUE2
     *
     * @return the value of EXP_SHEET_DTL.DATA_VALUE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getDataValue2() {
        return dataValue2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.DATA_VALUE2
     *
     * @param dataValue2 the value for EXP_SHEET_DTL.DATA_VALUE2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setDataValue2(String dataValue2) {
        this.dataValue2 = dataValue2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.RSV1
     *
     * @return the value of EXP_SHEET_DTL.RSV1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getRsv1() {
        return rsv1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.RSV1
     *
     * @param rsv1 the value for EXP_SHEET_DTL.RSV1
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setRsv1(String rsv1) {
        this.rsv1 = rsv1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.RSV2
     *
     * @return the value of EXP_SHEET_DTL.RSV2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getRsv2() {
        return rsv2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.RSV2
     *
     * @param rsv2 the value for EXP_SHEET_DTL.RSV2
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setRsv2(String rsv2) {
        this.rsv2 = rsv2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.RSV3
     *
     * @return the value of EXP_SHEET_DTL.RSV3
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getRsv3() {
        return rsv3;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.RSV3
     *
     * @param rsv3 the value for EXP_SHEET_DTL.RSV3
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setRsv3(String rsv3) {
        this.rsv3 = rsv3;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.RSV4
     *
     * @return the value of EXP_SHEET_DTL.RSV4
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getRsv4() {
        return rsv4;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.RSV4
     *
     * @param rsv4 the value for EXP_SHEET_DTL.RSV4
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setRsv4(String rsv4) {
        this.rsv4 = rsv4;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column EXP_SHEET_DTL.RSV5
     *
     * @return the value of EXP_SHEET_DTL.RSV5
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public String getRsv5() {
        return rsv5;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column EXP_SHEET_DTL.RSV5
     *
     * @param rsv5 the value for EXP_SHEET_DTL.RSV5
     *
     * @abatorgenerated Wed Dec 18 11:27:19 CST 2013
     */
    public void setRsv5(String rsv5) {
        this.rsv5 = rsv5;
    }

	public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public String getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
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
	 * @return the makUserId
	 */
	public String getMakUserId() {
		return makUserId;
	}

	/**
	 * @param makUserId the makUserId to set
	 */
	public void setMakUserId(String makUserId) {
		this.makUserId = makUserId;
	}

	/**
	 * @return the makDatetime
	 */
	public String getMakDatetime() {
		return makDatetime;
	}

	/**
	 * @param makDatetime the makDatetime to set
	 */
	public void setMakDatetime(String makDatetime) {
		this.makDatetime = makDatetime;
	}

	/**
	 * @return the chkUserId
	 */
	public String getChkUserId() {
		return chkUserId;
	}

	/**
	 * @param chkUserId the chkUserId to set
	 */
	public void setChkUserId(String chkUserId) {
		this.chkUserId = chkUserId;
	}

	/**
	 * @return the chkDatetime
	 */
	public String getChkDatetime() {
		return chkDatetime;
	}

	/**
	 * @param chkDatetime the chkDatetime to set
	 */
	public void setChkDatetime(String chkDatetime) {
		this.chkDatetime = chkDatetime;
	}
}