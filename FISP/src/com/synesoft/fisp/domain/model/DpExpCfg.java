package com.synesoft.fisp.domain.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.app.dp.model.DP_Exp_AddForm.DP_Exp_Add;
import com.synesoft.fisp.app.dp.model.DP_Exp_UptForm.DP_Exp_Upt_Submit;

public class DpExpCfg {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.EXP_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String expId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.PROJ_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotEmpty(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.ifc.1002}")
    private String projId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.BRANCH_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotEmpty(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.de.2002}")
    private String branchId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.BATCH_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotNull(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.ifc.1004}")
    private Long batchNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.SEQ_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotNull(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.ifc.1006}")
    private Short seqNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.TABLE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String tableName;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.FILE_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String fileId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.FILE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotEmpty(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.dp.imp.0017}")
    private String fileName;
    
    private String fileType;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.FILE_PATH
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotEmpty(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.dp.imp.0019}")
    private String filePath;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.TABLE_FILTER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String tableFilter;
    
    private String tableFilter_key;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.FILE_TITLE
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String fileTitle;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.FIELD_TITLE_FLAG
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    @NotEmpty(groups = {DP_Exp_Add.class,DP_Exp_Upt_Submit.class}, message ="{e.dp.valid.006}")
    private String fieldTitleFlag;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.COMMENTS
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String comments;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.CREATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String createTime;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.CREATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String createUser;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.UPDATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String updateTime;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.UPDATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String updateUser;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.RSV1
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String rsv1;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.RSV2
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String rsv2;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.RSV3
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String rsv3;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.RSV4
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String rsv4;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column DP_EXP_CFG.RSV5
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    private String rsv5;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.EXP_ID
     *
     * @return the value of DP_EXP_CFG.EXP_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getExpId() {
        return expId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.EXP_ID
     *
     * @param expId the value for DP_EXP_CFG.EXP_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setExpId(String expId) {
        this.expId = expId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.PROJ_ID
     *
     * @return the value of DP_EXP_CFG.PROJ_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getProjId() {
        return projId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.PROJ_ID
     *
     * @param projId the value for DP_EXP_CFG.PROJ_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setProjId(String projId) {
        this.projId = projId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.BRANCH_ID
     *
     * @return the value of DP_EXP_CFG.BRANCH_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.BRANCH_ID
     *
     * @param branchId the value for DP_EXP_CFG.BRANCH_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.BATCH_NO
     *
     * @return the value of DP_EXP_CFG.BATCH_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public Long getBatchNo() {
        return batchNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.BATCH_NO
     *
     * @param batchNo the value for DP_EXP_CFG.BATCH_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.SEQ_NO
     *
     * @return the value of DP_EXP_CFG.SEQ_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public Short getSeqNo() {
        return seqNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.SEQ_NO
     *
     * @param seqNo the value for DP_EXP_CFG.SEQ_NO
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setSeqNo(Short seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.TABLE_NAME
     *
     * @return the value of DP_EXP_CFG.TABLE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.TABLE_NAME
     *
     * @param tableName the value for DP_EXP_CFG.TABLE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.FILE_ID
     *
     * @return the value of DP_EXP_CFG.FILE_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.FILE_ID
     *
     * @param fileId the value for DP_EXP_CFG.FILE_ID
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.FILE_NAME
     *
     * @return the value of DP_EXP_CFG.FILE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.FILE_NAME
     *
     * @param fileName the value for DP_EXP_CFG.FILE_NAME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.FILE_PATH
     *
     * @return the value of DP_EXP_CFG.FILE_PATH
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.FILE_PATH
     *
     * @param filePath the value for DP_EXP_CFG.FILE_PATH
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.TABLE_FILTER
     *
     * @return the value of DP_EXP_CFG.TABLE_FILTER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getTableFilter() {
        return tableFilter;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.TABLE_FILTER
     *
     * @param tableFilter the value for DP_EXP_CFG.TABLE_FILTER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setTableFilter(String tableFilter) {
        this.tableFilter = tableFilter;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.FILE_TITLE
     *
     * @return the value of DP_EXP_CFG.FILE_TITLE
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getFileTitle() {
        return fileTitle;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.FILE_TITLE
     *
     * @param fileTitle the value for DP_EXP_CFG.FILE_TITLE
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.FIELD_TITLE_FLAG
     *
     * @return the value of DP_EXP_CFG.FIELD_TITLE_FLAG
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getFieldTitleFlag() {
        return fieldTitleFlag;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.FIELD_TITLE_FLAG
     *
     * @param fieldTitleFlag the value for DP_EXP_CFG.FIELD_TITLE_FLAG
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setFieldTitleFlag(String fieldTitleFlag) {
        this.fieldTitleFlag = fieldTitleFlag;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.COMMENTS
     *
     * @return the value of DP_EXP_CFG.COMMENTS
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getComments() {
        return comments;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.COMMENTS
     *
     * @param comments the value for DP_EXP_CFG.COMMENTS
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.CREATE_TIME
     *
     * @return the value of DP_EXP_CFG.CREATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.CREATE_TIME
     *
     * @param createTime the value for DP_EXP_CFG.CREATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.CREATE_USER
     *
     * @return the value of DP_EXP_CFG.CREATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.CREATE_USER
     *
     * @param createUser the value for DP_EXP_CFG.CREATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.UPDATE_TIME
     *
     * @return the value of DP_EXP_CFG.UPDATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.UPDATE_TIME
     *
     * @param updateTime the value for DP_EXP_CFG.UPDATE_TIME
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.UPDATE_USER
     *
     * @return the value of DP_EXP_CFG.UPDATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.UPDATE_USER
     *
     * @param updateUser the value for DP_EXP_CFG.UPDATE_USER
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.RSV1
     *
     * @return the value of DP_EXP_CFG.RSV1
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getRsv1() {
        return rsv1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.RSV1
     *
     * @param rsv1 the value for DP_EXP_CFG.RSV1
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setRsv1(String rsv1) {
        this.rsv1 = rsv1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.RSV2
     *
     * @return the value of DP_EXP_CFG.RSV2
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getRsv2() {
        return rsv2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.RSV2
     *
     * @param rsv2 the value for DP_EXP_CFG.RSV2
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setRsv2(String rsv2) {
        this.rsv2 = rsv2;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.RSV3
     *
     * @return the value of DP_EXP_CFG.RSV3
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getRsv3() {
        return rsv3;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.RSV3
     *
     * @param rsv3 the value for DP_EXP_CFG.RSV3
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setRsv3(String rsv3) {
        this.rsv3 = rsv3;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.RSV4
     *
     * @return the value of DP_EXP_CFG.RSV4
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getRsv4() {
        return rsv4;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.RSV4
     *
     * @param rsv4 the value for DP_EXP_CFG.RSV4
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setRsv4(String rsv4) {
        this.rsv4 = rsv4;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column DP_EXP_CFG.RSV5
     *
     * @return the value of DP_EXP_CFG.RSV5
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public String getRsv5() {
        return rsv5;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column DP_EXP_CFG.RSV5
     *
     * @param rsv5 the value for DP_EXP_CFG.RSV5
     *
     * @abatorgenerated Tue Nov 12 10:03:53 CST 2013
     */
    public void setRsv5(String rsv5) {
        this.rsv5 = rsv5;
    }

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTableFilter_key() {
		return tableFilter_key;
	}

	public void setTableFilter_key(String tableFilter_key) {
		this.tableFilter_key = tableFilter_key;
	}
    
}