package com.synesoft.sysrunner.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.sysrunner.app.BatchManage.model.BatchManageForm.BMG_DIM_Add;

public class BatDimInfo{
	
	 /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_DIM_INFO.DIMENSION_ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
	@NotEmpty(groups = { BMG_DIM_Add.class}, message = "{e.sysrunner.0024}")
    private String dimensionId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_DIM_INFO.DIM_TYPE_ID
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
	@NotEmpty(groups = { BMG_DIM_Add.class}, message = "{e.sysrunner.0025}")
    private String dimTypeId;
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column BAT_DIM_INFO.DIMENSION_NAME
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
	@NotEmpty(groups = { BMG_DIM_Add.class}, message = "{e.sysrunner.0026}")
    private String dimensionName;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column BAT_DIM_INFO.DIMENSION_NAME
     *
     * @return the value of BAT_DIM_INFO.DIMENSION_NAME
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public String getDimensionName() {
        return dimensionName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column BAT_DIM_INFO.DIMENSION_NAME
     *
     * @param dimensionName the value for BAT_DIM_INFO.DIMENSION_NAME
     *
     * @abatorgenerated Thu Dec 05 17:02:48 CST 2013
     */
    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

	public String getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}

	public String getDimTypeId() {
		return dimTypeId;
	}

	public void setDimTypeId(String dimTypeId) {
		this.dimTypeId = dimTypeId;
	}
    
}