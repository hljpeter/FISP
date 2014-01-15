package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;

public interface DP_Exp_Service {

	public Page<DpExpCfg> queryDpExpCfgPage(Pageable pageable, DpExpCfg dpExpCfg);

	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg);

	public int delDpExpCfg(DpExpCfg dpExpCfg);

	public int insertDpExpCfg(DpExpCfg dpExpCfg);

	public int queryDpExpCfgCountByBizKeys(DpExpCfg dpExpCfg);

	public int queryDpExpCfgCountByBizInfo(DpExpCfg dpExpCfg);

	public int updateDpExpCfgByExpId(DpExpCfg dpExpCfg);

	public List<DpFileDtlVO> queryDpFileDtlsByFileName(DpFile dpFile);

	public List<DpExpCfgDtl> queryDpExpCfgDtlsByExpId(DpExpCfgDtl dpExpCfgDtl);

	public int insertDpExpCfgDtl(List<DpExpCfgDtl> dpExpCfgDtls);
	
	public int deleteDpExpCfgDtlByExpId(DpExpCfgDtl dpExpCfgDtl);

}
