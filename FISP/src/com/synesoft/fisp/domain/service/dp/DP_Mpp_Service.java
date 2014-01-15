package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

public interface DP_Mpp_Service {

	public Page<DpMppCfg> queryDpMppCfgPage(Pageable pageable, DpMppCfg mppCfg);

	public DpMppCfg queryDpMppCfgByMppId(DpMppCfg mppCfg);

	public int delMppCfg(DpMppCfg mppCfg);

	public int insertDpMppCfgByMppId(DpMppCfg mppCfg);

	public int queryDpMppCfgCountByBizKeys(DpMppCfg mppCfg);

	public int queryDpMppCfgCountByBizInfo(DpMppCfg mppCfg);

	public int updateDpMppCfgByMppId(DpMppCfg mppCfg);

	public List<DpTableDtlVO> queryDpTableDtlsByTableName(DpTable dpTable);

	public List<DpMppCfgDtl> queryDpMppCfgDtlsByMppId(DpMppCfgDtl dpMppCfgDtl);

	public int insertDpMppCfgDtl(List<DpMppCfgDtl> dpMppCfgDtls);

	public Page<OrgInf> queryOrgInfPage(Pageable pageable, OrgInf orgInf);

	public Page<DpTable> queryDpTablePage(Pageable pageable, DpTable dpTable);

	public List<DpExprMethod> queryDpExprMethod(DpExprMethod dpExprMethod);

	public List<DpExprMethodDtl> queryDpExprMethodDtlByMId(
			DpExprMethodDtl dpExprMethodDtl);

	public List<DpExprTmp> queryDpExprTmpByUserId(DpExprTmp dpExprTmp);
	
	public List<DpTableDtl> queryExprDpTableDtlsByTableName(DpTable dpTable);
	
	public DpExprMethodDtl queryDpExprMethodRetuenType(DpExprMethod dpExprMethod);
	
	public int insertDpExprTmp(DpExprTmp dpExprTmp);
	
	public DpExprTmp queryDpExprTmpByKey(DpExprTmp dpExprTmp);
	
	public int deleteDpExprTmp(DpExprTmp dpExprTmp);
	
	public int deleteDpMppCfgDtlByMppId(DpMppCfgDtl mppCfgDtl);
	
	public List<DpMapDict> query0001DpMapDicts();
	
	public DpExprParaval queryDpExprParavalBySeqNo(DpExprParaval dpExprParaval);
	
	public int insertDpExprParaval(DpExprParaval dpExprParaval);
	
	public int updateDpExprParaval(DpExprParaval dpExprParaval);

}
