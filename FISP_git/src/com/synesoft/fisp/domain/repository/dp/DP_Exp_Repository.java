package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;

public interface DP_Exp_Repository {

	public Page<DpExpCfg> queryDpMppCfgPage(Pageable pageable, DpExpCfg dpExpCfg);

	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg);

	public int insertDpExpCfg(DpExpCfg dpExpCfg);

	public int deleteDpExpCfgByExpId(DpExpCfg dpExpCfg);

	public int deleteDpExpCfgDtlByExpId(DpExpCfgDtl dpExpCfgDtl);

	public int queryDpExpCfgCountByBizKeys(DpExpCfg dpExpCfg);

	public int queryDpExpCfgCountByBizInfo(DpExpCfg dpExpCfg);

	public int updateDpExpCfgByExpId(DpExpCfg dpExpCfg);

	public List<DpFileDtlVO> queryDpFileDtlsByFileName(DpFile dpFile);

	public List<DpExpCfgDtl> queryDpExpCfgDtlsByExpId(DpExpCfgDtl dpExpCfgDtl);

	public int insertDpExpCfgDtl(DpExpCfgDtl dpExpCfgDtl);
	
	public List<DpMapDict> queryDpMapDicts();

}
