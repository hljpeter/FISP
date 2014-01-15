package com.synesoft.fisp.domain.repository.dp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;

@Repository("dP_Exp_Repository")
public class DP_Exp_RepositoryImpl implements DP_Exp_Repository {
	
	@Override
	public Page<DpExpCfg> queryDpMppCfgPage(Pageable pageable, DpExpCfg dpExpCfg) {
		return pageDpExpCfg.getPage(Table.DP_EXP_CFG, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, dpExpCfg, pageable);
	}
	
	@Override
	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		return queryDAO.executeForObject(Table.DP_EXP_CFG + "."
				+ SQLMap.SELECT_BYKEY, dpExpCfg, DpExpCfg.class);
	}
	
	@Override
	public int insertDpExpCfg(DpExpCfg dpExpCfg) {
		return updateDAO.execute(Table.DP_EXP_CFG + "." + SQLMap.INSERT, dpExpCfg);
	}
	
	@Override
	public int deleteDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		return updateDAO.execute(Table.DP_EXP_CFG + "." + SQLMap.DELETE_BYKEY, dpExpCfg);
	}
	
	@Override
	public int deleteDpExpCfgDtlByExpId(DpExpCfgDtl dpExpCfgDtl) {
		return updateDAO.execute(Table.DP_EXP_CFG_DTL + "." + SQLMap.DELETE_BY_EXP_ID, dpExpCfgDtl);
	}
	
	@Override
	public int queryDpExpCfgCountByBizKeys(DpExpCfg dpExpCfg) {
		return queryDAO.executeForObject(Table.DP_EXP_CFG + "."
				+ SQLMap.SELECT_COUNTS_BY_BIZKEYS, dpExpCfg, Integer.class);
	}
	
	@Override
	public int queryDpExpCfgCountByBizInfo(DpExpCfg dpExpCfg) {
		return queryDAO.executeForObject(Table.DP_EXP_CFG + "."
				+ SQLMap.SELECT_COUNTS_BY_BIZINFO, dpExpCfg, Integer.class);
	}
	
	@Override
	public int updateDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		return updateDAO.execute(Table.DP_EXP_CFG + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, dpExpCfg);
	}
	
	@Override
	public List<DpFileDtlVO> queryDpFileDtlsByFileName(DpFile dpFile) {
		List<DpFileDtlVO> dpFileDtlVOs = new ArrayList<DpFileDtlVO>();
		List<DpFileDtl> dpFileDtls = queryDAO.executeForObjectList(Table.DP_FILE_DTL + "." + SQLMap.SELECT_LIST_BY_FILENAME, dpFile);
		for(DpFileDtl dpFileDtl_tmp : dpFileDtls){
			DpFileDtlVO dpFileDtlVO = new DpFileDtlVO();
			dpFileDtlVO.setSeqNo(dpFileDtl_tmp.getFieldSeqNo());
			dpFileDtlVO.setFieldName(dpFileDtl_tmp.getFieldName());
			dpFileDtlVOs.add(dpFileDtlVO);
		}
		return dpFileDtlVOs;
	}
	
	@Override
	public List<DpExpCfgDtl> queryDpExpCfgDtlsByExpId(DpExpCfgDtl dpExpCfgDtl) {
		return  queryDAO.executeForObjectList(Table.DP_EXP_CFG_DTL + "." + SQLMap.SELECT_LIST, dpExpCfgDtl);
	}
	
	@Override
	public int insertDpExpCfgDtl(DpExpCfgDtl dpExpCfgDtl) {
		return updateDAO.execute(Table.DP_EXP_CFG_DTL + "." + SQLMap.INSERT, dpExpCfgDtl);
	}
	
	@Override
	public List<DpMapDict> queryDpMapDicts() {
		return  queryDAO.executeForObjectList(Table.DP_MAP_DICT + "." + SQLMap.SELECT_LIST, null);
	}
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpExpCfg> pageDpExpCfg;

}
