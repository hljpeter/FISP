package com.synesoft.sysrunner.domain.model;

import java.math.BigDecimal;

public class BatCtl {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_CTL.ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    private BigDecimal id;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_CTL.BAT_DATE
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    private String batDate;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_CTL.RUNTIME_ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    private BigDecimal runtimeId;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column BAT_CTL.ID
     *
     * @return the value of BAT_CTL.ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column BAT_CTL.ID
     *
     * @param id the value for BAT_CTL.ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column BAT_CTL.BAT_DATE
     *
     * @return the value of BAT_CTL.BAT_DATE
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public String getBatDate() {
        return batDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column BAT_CTL.BAT_DATE
     *
     * @param batDate the value for BAT_CTL.BAT_DATE
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public void setBatDate(String batDate) {
        this.batDate = batDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column BAT_CTL.RUNTIME_ID
     *
     * @return the value of BAT_CTL.RUNTIME_ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public BigDecimal getRuntimeId() {
        return runtimeId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column BAT_CTL.RUNTIME_ID
     *
     * @param runtimeId the value for BAT_CTL.RUNTIME_ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public void setRuntimeId(BigDecimal runtimeId) {
        this.runtimeId = runtimeId;
    }
}