package com.synesoft.fisp.domain.service.dp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
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
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;
import com.synesoft.fisp.domain.repository.dp.DP_Mpp_Repository;

@Service("dP_Mpp_Service")
public class DP_Mpp_ServiceImpl implements DP_Mpp_Service {

	/**
	 * 
	 * dp_Mpp_Qry
	 * 
	 */

	@Override
	public Page<DpMppCfg> queryDpMppCfgPage(Pageable pageable, DpMppCfg mppCfg) {
		return dp_Mpp_Repository.queryDpMppCfgPage(pageable, mppCfg);
	}

	@Override
	public DpMppCfg queryDpMppCfgByMppId(DpMppCfg mppCfg) {
		return dp_Mpp_Repository.queryDpMppCfgByMppId(mppCfg);
	}

	@Override
	@Transactional
	public int delMppCfg(DpMppCfg mppCfg) {
		DpMppCfgDtl dpMppCfgDtl = new DpMppCfgDtl();
		dpMppCfgDtl.setMppId(mppCfg.getMppId());
		dp_Mpp_Repository.deleteDpMppCfgDtlByMppId(dpMppCfgDtl);
		/* 记录日志 */
		List<DpMppCfgDtl> dpMppCfgDtls = dp_Mpp_Repository
				.queryDpMppCfgDtlsByMppId(dpMppCfgDtl);

		for (DpMppCfgDtl mppCfgDtl : dpMppCfgDtls) {
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNow("yyyyMMddHHmmss");
			StringBuffer beforeData = new StringBuffer();
			beforeData.append("删除了映射明细ID为：");
			beforeData.append(mppCfgDtl.getMppDtlId());
			beforeData.append(",映射ID为：");
			beforeData.append(mppCfgDtl.getMppId());
			beforeData.append(",目标表列名为：");
			beforeData.append(mppCfgDtl.getDestColName());
			beforeData.append(",列表达式为：");
			beforeData.append(mppCfgDtl.getColFormula());
			beforeData.append(",唯一键标志为：");
			beforeData.append(mppCfgDtl.getUkFlag());
			beforeData.append(",重复处理类型为：");
			beforeData.append(mppCfgDtl.getDupProcType());
			beforeData.append(",备注为：");
			beforeData.append(mppCfgDtl.getComments() + "的元素");
			TlrLogPrint.tlrSysLogPrint("DP_Mpp_Qry", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "D",
					time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
					"");
		}
		/* 记录日志 */
		DpMppCfg dpMppCfg = dp_Mpp_Repository.queryDpMppCfgByMppId(mppCfg);
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了映射ID为：");
		beforeData.append(dpMppCfg.getMppId());
		beforeData.append(",工程号为：");
		beforeData.append(dpMppCfg.getProjId());
		beforeData.append(",机构号为：");
		beforeData.append(dpMppCfg.getBranchId());
		beforeData.append(",批次号为：");
		beforeData.append(dpMppCfg.getBatchNo());
		beforeData.append(",序号为：");
		beforeData.append(dpMppCfg.getSeqNo());
		beforeData.append(",目标表名为：");
		beforeData.append(dpMppCfg.getDestTable());
		beforeData.append(",源表名为：");
		beforeData.append(dpMppCfg.getSrcTable());
		
		beforeData.append(",处理类型为：");
		beforeData.append(dpMppCfg.getProcType());
		beforeData.append(",SP名称/SQL语句/源表过滤公式为：");
		beforeData.append(dpMppCfg.getProcCfg());
		beforeData.append(",重复处理类型为：");
		beforeData.append(dpMppCfg.getDupProcType());
		beforeData.append(",错误处理类型为：");
		beforeData.append(dpMppCfg.getErrProcType());
		beforeData.append(",映射名称为：");
		beforeData.append(dpMppCfg.getMppName());
		beforeData.append(",备注为：");
		beforeData.append(dpMppCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Mpp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
				"");
		dp_Mpp_Repository.deleteDpMppCfgByMppId(mppCfg);
		
		

		return 1;
	}

	/**
	 * 
	 * dp_Mpp_Add
	 * 
	 * 
	 */

	@Override
	@Transactional
	public int insertDpMppCfgByMppId(DpMppCfg mppCfg) {
		/* 记录日志 */
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer afterData = new StringBuffer();
		afterData.append("插入了映射ID为：");
		afterData.append(mppCfg.getMppId());
		afterData.append(",工程号为：");
		afterData.append(mppCfg.getProjId());
		afterData.append(",机构号为：");
		afterData.append(mppCfg.getBranchId());
		afterData.append(",批次号为：");
		afterData.append(mppCfg.getBatchNo());
		afterData.append(",序号为：");
		afterData.append(mppCfg.getSeqNo());
		afterData.append(",目标表名为：");
		afterData.append(mppCfg.getDestTable());
		afterData.append(",源表名为：");
		afterData.append(mppCfg.getSrcTable());
		afterData.append(",处理类型为：");
		afterData.append(mppCfg.getProcType());
		afterData.append(",SP名称/SQL语句/源表过滤公式为：");
		afterData.append(mppCfg.getProcCfg());
		afterData.append(",重复处理类型为：");
		afterData.append(mppCfg.getDupProcType());
		afterData.append(",错误处理类型为：");
		afterData.append(mppCfg.getErrProcType());
		afterData.append(",映射名称为：");
		afterData.append(mppCfg.getMppName());
		afterData.append(",备注为：");
		afterData.append(mppCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Mpp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());
		
		return dp_Mpp_Repository.insertDpMppCfgByMppId(mppCfg);
	}

	@Override
	public int queryDpMppCfgCountByBizInfo(DpMppCfg mppCfg) {
		return dp_Mpp_Repository.queryDpMppCfgCountByBizInfo(mppCfg);
	}

	@Override
	public int queryDpMppCfgCountByBizKeys(DpMppCfg mppCfg) {
		return dp_Mpp_Repository.queryDpMppCfgCountByBizKeys(mppCfg);
	}

	/**
	 * 
	 * dp_Mpp_Upt
	 * 
	 * 
	 */
	@Override
	@Transactional
	public int updateDpMppCfgByMppId(DpMppCfg mppCfg) {
		/* 记录日志 */
		DpMppCfg dpMppCfg = dp_Mpp_Repository.queryDpMppCfgByMppId(mppCfg);
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer beforeData = new StringBuffer();
		StringBuffer afterData = new StringBuffer();
		beforeData.append("映射ID为：");
		beforeData.append(dpMppCfg.getMppId());
		beforeData.append(",工程号为：");
		beforeData.append(dpMppCfg.getProjId());
		beforeData.append(",机构号为：");
		beforeData.append(dpMppCfg.getBranchId());
		beforeData.append(",批次号为：");
		beforeData.append(dpMppCfg.getBatchNo());
		beforeData.append(",序号为：");
		beforeData.append(dpMppCfg.getSeqNo());
		beforeData.append(",目标表名为：");
		beforeData.append(dpMppCfg.getDestTable());
		beforeData.append(",源表名为：");
		beforeData.append(dpMppCfg.getSrcTable());
		beforeData.append(",处理类型为：");
		beforeData.append(dpMppCfg.getProcType());
		beforeData.append(",SP名称/SQL语句/源表过滤公式为：");
		beforeData.append(dpMppCfg.getProcCfg());
		beforeData.append(",重复处理类型为：");
		beforeData.append(dpMppCfg.getDupProcType());
		beforeData.append(",错误处理类型为：");
		beforeData.append(dpMppCfg.getErrProcType());
		beforeData.append(",映射名称为：");
		beforeData.append(dpMppCfg.getMppName());
		beforeData.append(",备注为：");
		beforeData.append(dpMppCfg.getComments() + "的元素");
		
		afterData.append("更新了映射ID为：");
		afterData.append(mppCfg.getMppId());
		afterData.append(",工程号为：");
		afterData.append(mppCfg.getProjId());
		afterData.append(",机构号为：");
		afterData.append(mppCfg.getBranchId());
		afterData.append(",批次号为：");
		afterData.append(mppCfg.getBatchNo());
		afterData.append(",序号为：");
		afterData.append(mppCfg.getSeqNo());
		afterData.append(",目标表名为：");
		afterData.append(mppCfg.getDestTable());
		afterData.append(",源表名为：");
		afterData.append(mppCfg.getSrcTable());
		afterData.append(",处理类型为：");
		afterData.append(mppCfg.getProcType());
		afterData.append(",SP名称/SQL语句/源表过滤公式为：");
		afterData.append(mppCfg.getProcCfg());
		afterData.append(",重复处理类型为：");
		afterData.append(mppCfg.getDupProcType());
		afterData.append(",错误处理类型为：");
		afterData.append(mppCfg.getErrProcType());
		afterData.append(",映射名称为：");
		afterData.append(mppCfg.getMppName());
		afterData.append(",备注为：");
		afterData.append(mppCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Mpp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "M",
				time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
				afterData.toString());
		
		
		return dp_Mpp_Repository.updateDpMppCfgByMppId(mppCfg);
	}
	
	@Override
	public List<DpMapDict> query0001DpMapDicts() {
		return dp_Mpp_Repository.query0001DpMapDicts();
	}

	/**
	 * 
	 * dp_Mpp_Dtl
	 * 
	 * 
	 */
	@Override
	public List<DpTableDtlVO> queryDpTableDtlsByTableName(DpTable dpTable) {
		return dp_Mpp_Repository.queryDpTableDtlsByTableName(dpTable);
	}

	@Override
	public List<DpMppCfgDtl> queryDpMppCfgDtlsByMppId(DpMppCfgDtl dpMppCfgDtl) {
		return dp_Mpp_Repository.queryDpMppCfgDtlsByMppId(dpMppCfgDtl);
	}

	/**
	 * 
	 * dp_Mpp_Cfg
	 * 
	 * 
	 */
	@Override
	@Transactional
	public int insertDpMppCfgDtl(List<DpMppCfgDtl> dpMppCfgDtls) {
		int count = 0;
		for (DpMppCfgDtl dpMppCfgDtl : dpMppCfgDtls) {
			/* 记录日志 */
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNow("yyyyMMddHHmmss");
			StringBuffer afterData = new StringBuffer();
			afterData.append("插入了映射明细ID为：");
			afterData.append(dpMppCfgDtl.getMppDtlId());
			afterData.append(",映射ID为：");
			afterData.append(dpMppCfgDtl.getMppId());
			afterData.append(",目标表列名为：");
			afterData.append(dpMppCfgDtl.getDestColName());
			afterData.append(",列表达式为：");
			afterData.append(dpMppCfgDtl.getColFormula());
			afterData.append(",唯一键标志为：");
			afterData.append(dpMppCfgDtl.getUkFlag());
			afterData.append(",重复处理类型为：");
			afterData.append(dpMppCfgDtl.getDupProcType());
			afterData.append(",备注为：");
			afterData.append(dpMppCfgDtl.getComments() + "的元素");
			TlrLogPrint.tlrSysLogPrint("DP_Mpp_Qry", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "A",
					time.substring(0, 8), time.substring(8, 14), "",
					afterData.toString());
			
			
			dp_Mpp_Repository.insertDpMppCfgDtl(dpMppCfgDtl);
			count += 1;
		}
		if (count == dpMppCfgDtls.size()) {
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * 
	 * DP_Mpp_OrgQry
	 * 
	 * 
	 */
	@Override
	public Page<OrgInf> queryOrgInfPage(Pageable pageable, OrgInf orgInf) {
		return dp_Mpp_Repository.queryOrgInfPage(pageable, orgInf);
	}

	/**
	 * 
	 * DP_Mpp_TableQry
	 * 
	 * 
	 */
	@Override
	public Page<DpTable> queryDpTablePage(Pageable pageable, DpTable dpTable) {
		return dp_Mpp_Repository.queryDpTablePage(pageable, dpTable);
	}

	/**
	 * 
	 * DP_Expr
	 * 
	 * 
	 */
	public List<DpExprMethod> queryDpExprMethod(DpExprMethod dpExprMethod) {
		return dp_Mpp_Repository.queryDpExprMethod(dpExprMethod);
	}

	@Override
	public List<DpExprMethodDtl> queryDpExprMethodDtlByMId(
			DpExprMethodDtl dpExprMethodDtl) {
		return dp_Mpp_Repository.queryDpExprMethodDtlByMId(dpExprMethodDtl);
	}

	@Override
	public List<DpExprTmp> queryDpExprTmpByUserId(DpExprTmp dpExprTmp) {
		return dp_Mpp_Repository.queryDpExprTmpByUserId(dpExprTmp);
	}

	@Override
	public List<DpTableDtl> queryExprDpTableDtlsByTableName(DpTable dpTable) {
		return dp_Mpp_Repository.queryExprDpTableDtlsByTableName(dpTable);
	}

	@Override
	public DpExprMethodDtl queryDpExprMethodRetuenType(DpExprMethod dpExprMethod) {
		return dp_Mpp_Repository.queryDpExprMethodRetuenType(dpExprMethod);
	}

	@Override
	@Transactional
	public int insertDpExprTmp(DpExprTmp dpExprTmp) {
		return dp_Mpp_Repository.insertDpExprTmp(dpExprTmp);
	}

	@Override
	public DpExprTmp queryDpExprTmpByKey(DpExprTmp dpExprTmp) {
		return dp_Mpp_Repository.queryDpExprTmpByKey(dpExprTmp);
	}

	@Override
	@Transactional
	public int deleteDpExprTmp(DpExprTmp dpExprTmp) {
		return dp_Mpp_Repository.deleteDpExprTmp(dpExprTmp);
	}
	
	@Override
	@Transactional
	public int deleteDpMppCfgDtlByMppId(DpMppCfgDtl mppCfgDtl){
		return dp_Mpp_Repository.deleteDpMppCfgDtlByMppId(mppCfgDtl);
	}
	
	/**
	 * 
	 * DP_Expr_Paravla
	 * 
	 * 
	 */
	
	@Override
	public DpExprParaval queryDpExprParavalBySeqNo(DpExprParaval dpExprParaval) {
		return dp_Mpp_Repository.queryDpExprParavalBySeqNo(dpExprParaval);
	}
	
	@Override
	@Transactional
	public int insertDpExprParaval(DpExprParaval dpExprParaval) {
		return dp_Mpp_Repository.insertDpExprParaval(dpExprParaval);
	}
	
	@Override
	@Transactional
	public int updateDpExprParaval(DpExprParaval dpExprParaval) {
		return dp_Mpp_Repository.updateDpExprParaval(dpExprParaval);
	}

	@Resource
	protected DP_Mpp_Repository dp_Mpp_Repository;
}
