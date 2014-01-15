package com.synesoft.fisp.app.dp.model;

import java.util.List;

import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;

public class DP_Mpp_DtlForm {
	
	private String dtl_MppId ="";
	
	private DpMppCfg dpMppCfg = new DpMppCfg();
	
	private List<DpTableDtlVO> dpTableDtlVOs;

	public DpMppCfg getDpMppCfg() {
		return dpMppCfg;
	}

	public void setDpMppCfg(DpMppCfg dpMppCfg) {
		this.dpMppCfg = dpMppCfg;
	}

	public String getDtl_MppId() {
		return dtl_MppId;
	}

	public void setDtl_MppId(String dtl_MppId) {
		this.dtl_MppId = dtl_MppId;
	}

	public List<DpTableDtlVO> getDpTableDtlVOs() {
		return dpTableDtlVOs;
	}

	public void setDpTableDtlVOs(List<DpTableDtlVO> dpTableDtlVOs) {
		this.dpTableDtlVOs = dpTableDtlVOs;
	}
	
}
