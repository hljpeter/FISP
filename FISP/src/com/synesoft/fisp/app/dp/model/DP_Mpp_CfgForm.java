package com.synesoft.fisp.app.dp.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;

public class DP_Mpp_CfgForm {

	@NotEmpty(message = "{e.dp.valid.001}")
	private String cdg_MppId = "";

	private DpMppCfg dpMppCfg = new DpMppCfg();

	private List<DpTableDtlVO> dpTableDtlVOs;

	public String getCdg_MppId() {
		return cdg_MppId;
	}

	public void setCdg_MppId(String cdg_MppId) {
		this.cdg_MppId = cdg_MppId;
	}

	public DpMppCfg getDpMppCfg() {
		return dpMppCfg;
	}

	public void setDpMppCfg(DpMppCfg dpMppCfg) {
		this.dpMppCfg = dpMppCfg;
	}

	public List<DpTableDtlVO> getDpTableDtlVOs() {
		return dpTableDtlVOs;
	}

	public void setDpTableDtlVOs(List<DpTableDtlVO> dpTableDtlVOs) {
		this.dpTableDtlVOs = dpTableDtlVOs;
	}
	
	

}
