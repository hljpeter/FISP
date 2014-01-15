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
import com.synesoft.fisp.domain.model.DpExprMethod;
import com.synesoft.fisp.domain.model.DpExprMethodDtl;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.DpMppCfgDtl;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;

@Repository("dP_Mpp_Repository")
public class DP_Mpp_RepositoryImpl implements DP_Mpp_Repository {
	
	
	@Override
	public Page<com.synesoft.fisp.domain.model.DpMppCfg> queryDpMppCfgPage(
			Pageable pageable, com.synesoft.fisp.domain.model.DpMppCfg mppCfg) {
		return pageDpMppCfg.getPage(Table.DP_MPP_CFG, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, mppCfg, pageable);
	}
	
	@Override
	public DpMppCfg queryDpMppCfgByMppId(DpMppCfg mppCfg) {
		return queryDAO.executeForObject(Table.DP_MPP_CFG + "."
				+ SQLMap.SELECT_BYKEY, mppCfg, DpMppCfg.class);
	}
	
	@Override
	public int insertDpMppCfgByMppId(DpMppCfg mppCfg) {
		return updateDAO.execute(Table.DP_MPP_CFG + "." + SQLMap.INSERT, mppCfg);
	}
	
	@Override
	public int deleteDpMppCfgByMppId(DpMppCfg mppCfg) {
		return updateDAO.execute(Table.DP_MPP_CFG + "." + SQLMap.DELETE_BYKEY, mppCfg);
	}
	
	@Override
	public int deleteDpMppCfgDtlByMppId(DpMppCfgDtl mppCfgDtl) {
		return updateDAO.execute(Table.DP_MPP_CFG_DTL + "." + SQLMap.DELETE_BY_MPP_ID, mppCfgDtl);
	}
	
	@Override
	public int queryDpMppCfgCountByBizInfo(DpMppCfg mppCfg) {
		return queryDAO.executeForObject(Table.DP_MPP_CFG + "."
				+ SQLMap.SELECT_COUNTS_BY_BIZINFO, mppCfg, Integer.class);
	}
	
	@Override
	public int queryDpMppCfgCountByBizKeys(DpMppCfg mppCfg) {
		return queryDAO.executeForObject(Table.DP_MPP_CFG + "."
				+ SQLMap.SELECT_COUNTS_BY_BIZKEYS, mppCfg, Integer.class);
	}
	
	@Override
	public int updateDpMppCfgByMppId(DpMppCfg mppCfg) {
		return updateDAO.execute(Table.DP_MPP_CFG + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, mppCfg);
	}
	
	@Override
	public List<DpTableDtlVO> queryDpTableDtlsByTableName(DpTable dpTable) {
		List<DpTableDtlVO> dpTableDtlVOs = new ArrayList<DpTableDtlVO>();
		List<DpTableDtl> dpTableDtls = queryDAO.executeForObjectList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_LIST_BY_TABLEID, dpTable);
		for(DpTableDtl dpTableDtl : dpTableDtls){
			DpTableDtlVO dpTableDtlVO = new DpTableDtlVO();
			dpTableDtlVO.setDestColName(dpTableDtl.getColName());
			dpTableDtlVOs.add(dpTableDtlVO);
		}
		return dpTableDtlVOs;
	}
	
	@Override
	public List<DpMppCfgDtl> queryDpMppCfgDtlsByMppId(DpMppCfgDtl dpMppCfgDtl) {
		return  queryDAO.executeForObjectList(Table.DP_MPP_CFG_DTL + "." + SQLMap.SELECT_LIST, dpMppCfgDtl);
	}
	
	@Override
	public int insertDpMppCfgDtl(DpMppCfgDtl dpMppCfgDtl) {
		return updateDAO.execute(Table.DP_MPP_CFG_DTL + "." + SQLMap.INSERT, dpMppCfgDtl);
	}
	
	@Override
	public Page<OrgInf> queryOrgInfPage(
			Pageable pageable, OrgInf orgInf) {
		return pageOrgInf.getPage(Table.ORGINF, SQLMap.SELECT_AUTHORITY_COUNTS,
				SQLMap.SELECT_AUTHORITY_LIST, orgInf, pageable);
	}
	
	@Override
	public Page<DpTable> queryDpTablePage(Pageable pageable, DpTable dpTable) {
		return pageDpTable.getPage(Table.DP_TABLE, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, dpTable, pageable);
	}
	
	@Override
	public List<DpExprMethod> queryDpExprMethod(DpExprMethod dpExprMethod) {
		return  queryDAO.executeForObjectList(Table.DP_EXPR_METHOD + "." + SQLMap.SELECT_LIST, dpExprMethod);
	}
	
	@Override
	public List<DpExprMethodDtl> queryDpExprMethodDtlByMId(
			DpExprMethodDtl dpExprMethodDtl) {
		return  queryDAO.executeForObjectList(Table.DP_EXPR_METHOD_DTL + "." + SQLMap.SELECT_LIST, dpExprMethodDtl);
	}
	
	@Override
	public List<DpExprTmp> queryDpExprTmpByUserId(DpExprTmp dpExprTmp) {
		return  queryDAO.executeForObjectList(Table.DP_EXPR_TMP + "." + SQLMap.SELECT_LIST, dpExprTmp);
	}
	
	@Override
	public List<DpTableDtl> queryExprDpTableDtlsByTableName(DpTable dpTable) {
		return  queryDAO.executeForObjectList(Table.DP_TABLE_DTL + "." + SQLMap.SELECT_LIST_BY_TABLEID, dpTable);
	}
	
	@Override
	public DpExprMethodDtl queryDpExprMethodRetuenType(DpExprMethod dpExprMethod) {
		return queryDAO.executeForObject(Table.DP_EXPR_METHOD_DTL + "."
				+ SQLMap.SELECT_MOTHEDRETURNTYPE, dpExprMethod, DpExprMethodDtl.class);
	}
	
	@Override
	public int insertDpExprTmp(DpExprTmp dpExprTmp) {
		return updateDAO.execute(Table.DP_EXPR_TMP + "." + SQLMap.INSERT, dpExprTmp);
	}
	
	@Override
	public DpExprTmp queryDpExprTmpByKey(DpExprTmp dpExprTmp) {
		return queryDAO.executeForObject(Table.DP_EXPR_TMP + "."
				+ SQLMap.SELECT_BYKEY, dpExprTmp, DpExprTmp.class);
	}
	
	@Override
	public int deleteDpExprTmp(DpExprTmp dpExprTmp) {
		return updateDAO.execute(Table.DP_EXPR_TMP + "." + SQLMap.DELETE_BYKEY, dpExprTmp);
	}
	
	@Override
	public List<DpMapDict> query0001DpMapDicts() {
		return  queryDAO.executeForObjectList(Table.DP_MAP_DICT + "." + SQLMap.SELECT_0001_LIST, null);
	}
	
	@Override
	public DpExprParaval queryDpExprParavalBySeqNo(DpExprParaval dpExprParaval) {
		return queryDAO.executeForObject(Table.DP_EXPR_PARAVAL + "."
				+ SQLMap.SELECT_BYKEY, dpExprParaval, DpExprParaval.class);
	}
	
	@Override
	public int insertDpExprParaval(DpExprParaval dpExprParaval) {
		return updateDAO.execute(Table.DP_EXPR_PARAVAL + "." + SQLMap.INSERT, dpExprParaval);
	}
	
	@Override
	public int updateDpExprParaval(DpExprParaval dpExprParaval) {
		return updateDAO.execute(Table.DP_EXPR_PARAVAL + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, dpExprParaval);
	}
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpMppCfg> pageDpMppCfg;
	
	@Resource
	private PageHandler<OrgInf> pageOrgInf;
	
	@Resource
	private PageHandler<DpTable> pageDpTable;
}
