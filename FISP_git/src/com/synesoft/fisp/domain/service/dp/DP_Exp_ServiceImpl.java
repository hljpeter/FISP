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
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;
import com.synesoft.fisp.domain.repository.dp.DP_Exp_Repository;

@Service
public class DP_Exp_ServiceImpl implements DP_Exp_Service {
	
	/**
	 * 
	 * DP_Exp_Qry
	 * 
	 */
	
	@Override
	public Page<DpExpCfg> queryDpExpCfgPage(Pageable pageable, DpExpCfg dpExpCfg) {
		return dP_Exp_Repository.queryDpMppCfgPage(pageable, dpExpCfg);
	}
	
	@Override
	public DpExpCfg queryDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		return dP_Exp_Repository.queryDpExpCfgByExpId(dpExpCfg);
	}
	
	@Override
	@Transactional
	public int delDpExpCfg(DpExpCfg dpExpCfg) {
		DpExpCfgDtl dpExpCfgDtl = new DpExpCfgDtl();
		dpExpCfgDtl.setExpId(dpExpCfg.getExpId());
		dP_Exp_Repository.deleteDpExpCfgDtlByExpId(dpExpCfgDtl);
		/* 记录日志 */
		List<DpExpCfgDtl> dpExpCfgDtls = dP_Exp_Repository.queryDpExpCfgDtlsByExpId(dpExpCfgDtl);

		for (DpExpCfgDtl expCfgDtl : dpExpCfgDtls) {
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNow("yyyyMMddHHmmss");
			StringBuffer beforeData = new StringBuffer();
			beforeData.append("删除了导出配置明细ID为：");
			beforeData.append(expCfgDtl.getExpDtlId());
			beforeData.append(",导出配置ID为：");
			beforeData.append(expCfgDtl.getExpId());
			beforeData.append(",序号为：");
			beforeData.append(expCfgDtl.getSeqNo());
			beforeData.append(",文件字段名为：");
			beforeData.append(expCfgDtl.getFieldName());
			beforeData.append(",字段表达式为：");
			beforeData.append(expCfgDtl.getFieldFormula());
			beforeData.append(",备注为：");
			beforeData.append(expCfgDtl.getComments() + "的元素");
			TlrLogPrint.tlrSysLogPrint("DP_Exp_Qry", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "D",
					time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
					"");
		}
		
		/* 记录日志 */
		DpExpCfg expCfg = dP_Exp_Repository.queryDpExpCfgByExpId(dpExpCfg);
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer beforeData = new StringBuffer();
		beforeData.append("删除了导出配置ID为：");
		beforeData.append(expCfg.getExpId());
		beforeData.append(",工程号为：");
		beforeData.append(expCfg.getProjId());
		beforeData.append(",机构号为：");
		beforeData.append(expCfg.getBranchId());
		beforeData.append(",批次号为：");
		beforeData.append(expCfg.getBatchNo());
		beforeData.append(",序号为：");
		beforeData.append(expCfg.getSeqNo());
		beforeData.append(",表名为：");
		beforeData.append(expCfg.getTableName());
		beforeData.append(",文件ID为：");
		beforeData.append(expCfg.getFileId());
		beforeData.append(",文件名为：");
		beforeData.append(expCfg.getFileName());
		beforeData.append(",文件路径为：");
		beforeData.append(expCfg.getFilePath());
		beforeData.append(",表数据过滤公式为：");
		beforeData.append(expCfg.getTableFilter());
		beforeData.append(",文件抬头为：");
		beforeData.append(expCfg.getFileTitle());
		beforeData.append(",字段标题标志为：");
		beforeData.append(expCfg.getFieldTitleFlag());
		beforeData.append(",备注为：");
		beforeData.append(expCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Exp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "D",
				time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
				"");
		
		
		dP_Exp_Repository.deleteDpExpCfgByExpId(dpExpCfg);
		
		return 1;
	}
	
	/**
	 * 
	 * DP_Exp_Add
	 * 
	 */
	
	@Override
	@Transactional
	public int insertDpExpCfg(DpExpCfg dpExpCfg) {
		
		/* 记录日志 */
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer afterData = new StringBuffer();
		afterData.append("删除了导出配置ID为：");
		afterData.append(dpExpCfg.getExpId());
		afterData.append(",工程号为：");
		afterData.append(dpExpCfg.getProjId());
		afterData.append(",机构号为：");
		afterData.append(dpExpCfg.getBranchId());
		afterData.append(",批次号为：");
		afterData.append(dpExpCfg.getBatchNo());
		afterData.append(",序号为：");
		afterData.append(dpExpCfg.getSeqNo());
		afterData.append(",表名为：");
		afterData.append(dpExpCfg.getTableName());
		afterData.append(",文件ID为：");
		afterData.append(dpExpCfg.getFileId());
		afterData.append(",文件名为：");
		afterData.append(dpExpCfg.getFileName());
		afterData.append(",文件路径为：");
		afterData.append(dpExpCfg.getFilePath());
		afterData.append(",表数据过滤公式为：");
		afterData.append(dpExpCfg.getTableFilter());
		afterData.append(",文件抬头为：");
		afterData.append(dpExpCfg.getFileTitle());
		afterData.append(",字段标题标志为：");
		afterData.append(dpExpCfg.getFieldTitleFlag());
		afterData.append(",备注为：");
		afterData.append(dpExpCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Exp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "A",
				time.substring(0, 8), time.substring(8, 14), "",
				afterData.toString());
		return dP_Exp_Repository.insertDpExpCfg(dpExpCfg);
	}
	
	@Override
	public int queryDpExpCfgCountByBizKeys(DpExpCfg dpExpCfg) {
		return dP_Exp_Repository.queryDpExpCfgCountByBizKeys(dpExpCfg);
	}
	
	@Override
	public int queryDpExpCfgCountByBizInfo(DpExpCfg dpExpCfg) {
		return dP_Exp_Repository.queryDpExpCfgCountByBizInfo(dpExpCfg);
	}
	
	
	/**
	 * 
	 * DP_Exp_Upt
	 * 
	 */
	@Override
	@Transactional
	public int updateDpExpCfgByExpId(DpExpCfg dpExpCfg) {
		
		/* 记录日志 */
		DpExpCfg expCfg = dP_Exp_Repository.queryDpExpCfgByExpId(dpExpCfg);
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		String time = DateUtil.getNow("yyyyMMddHHmmss");
		StringBuffer beforeData = new StringBuffer();
		StringBuffer afterData = new StringBuffer();
		beforeData.append("导出配置ID为：");
		beforeData.append(expCfg.getExpId());
		beforeData.append(",工程号为：");
		beforeData.append(expCfg.getProjId());
		beforeData.append(",机构号为：");
		beforeData.append(expCfg.getBranchId());
		beforeData.append(",批次号为：");
		beforeData.append(expCfg.getBatchNo());
		beforeData.append(",序号为：");
		beforeData.append(expCfg.getSeqNo());
		beforeData.append(",表名为：");
		beforeData.append(expCfg.getTableName());
		beforeData.append(",文件ID为：");
		beforeData.append(expCfg.getFileId());
		beforeData.append(",文件名为：");
		beforeData.append(expCfg.getFileName());
		beforeData.append(",文件路径为：");
		beforeData.append(expCfg.getFilePath());
		beforeData.append(",表数据过滤公式为：");
		beforeData.append(expCfg.getTableFilter());
		beforeData.append(",文件抬头为：");
		beforeData.append(expCfg.getFileTitle());
		beforeData.append(",字段标题标志为：");
		beforeData.append(expCfg.getFieldTitleFlag());
		beforeData.append(",备注为：");
		beforeData.append(expCfg.getComments() + "的元素");
		
		afterData.append("更新了导出配置ID为：");
		afterData.append(dpExpCfg.getExpId());
		afterData.append(",工程号为：");
		afterData.append(dpExpCfg.getProjId());
		afterData.append(",机构号为：");
		afterData.append(dpExpCfg.getBranchId());
		afterData.append(",批次号为：");
		afterData.append(dpExpCfg.getBatchNo());
		afterData.append(",序号为：");
		afterData.append(dpExpCfg.getSeqNo());
		afterData.append(",表名为：");
		afterData.append(dpExpCfg.getTableName());
		afterData.append(",文件ID为：");
		afterData.append(dpExpCfg.getFileId());
		afterData.append(",文件名为：");
		afterData.append(dpExpCfg.getFileName());
		afterData.append(",文件路径为：");
		afterData.append(dpExpCfg.getFilePath());
		afterData.append(",表数据过滤公式为：");
		afterData.append(dpExpCfg.getTableFilter());
		afterData.append(",文件抬头为：");
		afterData.append(dpExpCfg.getFileTitle());
		afterData.append(",字段标题标志为：");
		afterData.append(dpExpCfg.getFieldTitleFlag());
		afterData.append(",备注为：");
		afterData.append(dpExpCfg.getComments() + "的元素");
		TlrLogPrint.tlrSysLogPrint("DP_Exp_Qry", orgInfo.getOrgid(),
				userInfo.getUserid(), userInfo.getUsername(), "M",
				time.substring(0, 8), time.substring(8, 14), beforeData.toString(),
				afterData.toString());
		
		return dP_Exp_Repository.updateDpExpCfgByExpId(dpExpCfg);
	}
	
	/**
	 * 
	 * DP_Exp_Cfg
	 * 
	 */
	public List<DpFileDtlVO> queryDpFileDtlsByFileName(DpFile dpFile) {
		return dP_Exp_Repository.queryDpFileDtlsByFileName(dpFile);
	}
	
	@Override
	public List<DpExpCfgDtl> queryDpExpCfgDtlsByExpId(DpExpCfgDtl dpExpCfgDtl) {
		return dP_Exp_Repository.queryDpExpCfgDtlsByExpId(dpExpCfgDtl);
	}
	
	@Override
	@Transactional
	public int insertDpExpCfgDtl(List<DpExpCfgDtl> dpExpCfgDtls) {
		int count =0;
		for(DpExpCfgDtl dpExpCfgDtl : dpExpCfgDtls){
			/* 记录日志 */
			OrgInf orgInfo = ContextConst.getOrgInfByUser();
			UserInf userInfo = ContextConst.getCurrentUser();
			String time = DateUtil.getNow("yyyyMMddHHmmss");
			StringBuffer afterData = new StringBuffer();
			afterData.append("插入了导出配置明细ID为：");
			afterData.append(dpExpCfgDtl.getExpDtlId());
			afterData.append(",导出配置ID为：");
			afterData.append(dpExpCfgDtl.getExpId());
			afterData.append(",序号为：");
			afterData.append(dpExpCfgDtl.getSeqNo());
			afterData.append(",文件字段名为：");
			afterData.append(dpExpCfgDtl.getFieldName());
			afterData.append(",字段表达式为：");
			afterData.append(dpExpCfgDtl.getFieldFormula());
			afterData.append(",备注为：");
			afterData.append(dpExpCfgDtl.getComments() + "的元素");
			TlrLogPrint.tlrSysLogPrint("DP_Exp_Qry", orgInfo.getOrgid(),
					userInfo.getUserid(), userInfo.getUsername(), "A",
					time.substring(0, 8), time.substring(8, 14), "",
					afterData.toString());
			
			
			dP_Exp_Repository.insertDpExpCfgDtl(dpExpCfgDtl);
			 count+=1;
		}
		if(count == dpExpCfgDtls.size()){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	@Override
	@Transactional
	public int deleteDpExpCfgDtlByExpId(DpExpCfgDtl dpExpCfgDtl) {
		return dP_Exp_Repository.deleteDpExpCfgDtlByExpId(dpExpCfgDtl);
	}
	
	
	
	@Resource
	protected DP_Exp_Repository dP_Exp_Repository;

}
