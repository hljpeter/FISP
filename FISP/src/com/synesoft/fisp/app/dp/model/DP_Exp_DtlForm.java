package com.synesoft.fisp.app.dp.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;

public class DP_Exp_DtlForm {
	
	@NotEmpty(message = "{e.dp.valid.005}")
	private String dtl_ExpId = "";

	private DpExpCfg dpExpCfg = new DpExpCfg();

	private List<DpFileDtlVO> dpFileDtlVOs;

	public String getDtl_ExpId() {
		return dtl_ExpId;
	}

	public void setDtl_ExpId(String dtl_ExpId) {
		this.dtl_ExpId = dtl_ExpId;
	}

	public DpExpCfg getDpExpCfg() {
		return dpExpCfg;
	}

	public void setDpExpCfg(DpExpCfg dpExpCfg) {
		this.dpExpCfg = dpExpCfg;
	}

	public List<DpFileDtlVO> getDpFileDtlVOs() {
		return dpFileDtlVOs;
	}

	public void setDpFileDtlVOs(List<DpFileDtlVO> dpFileDtlVOs) {
		this.dpFileDtlVOs = dpFileDtlVOs;
	}
	
}
