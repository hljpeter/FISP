package com.synesoft.fisp.app.common.trigger;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.SqlHolder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.DpMppCfgDtl;
import com.synesoft.fisp.domain.repository.DpExprParavalRepository;
import com.synesoft.fisp.domain.repository.dp.DP_Exp_Repository;
import com.synesoft.fisp.domain.repository.dp.DP_Mpp_Repository;

/**
 * @author zhongHubo
 * @date 2013年12月11日 20:01:34
 * @version 1.0
 * @Description 删除表达式参数值表(dp_expr_paraval)中无效的数据
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("triggerDpExprParaval")
public class TriggerDpExprParaval {
	
	private static final LogUtil log = new LogUtil(TriggerDpExprParaval.class);
	
	// 当前时间
	private String nowDate = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
	// 当前时间的上一天时间
	private String prevDate = DateUtil.getDatetimeByD(nowDate, -1, DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
	// 每次查询多少条数据
	private int size = 20;
	// update sql Id
	private String updateSqlId = Table.DP_EXPR_PARAVAL + CommonConst.POINT + SQLMap.UpdateByUseFlag;
	
	@Resource
	private DP_Mpp_Repository dP_Mpp_Repository;
	@Resource
	private DP_Exp_Repository dP_Exp_Repository;
	@Resource
	private DpExprParavalRepository dpExprParavalRepository;
	
	
	/**
	 * 入口方法
	 */
	@Transactional
	public void execute() {
		log.info("TriggerDpExprParaval execute method start.");
		
		try {
			// 处理DpMppCfg、DpMppCfgDtl表中的数据
			executeDpMppCfg(0);
			// 处理DpExpCfg、DpExpCfgDtl表中的数据
			executeDpExpCfg(0);
			
			// 删除无效数据
			DpExprParaval dpExprParaval = new DpExprParaval();
			dpExprParaval.setUseFlag(CommonConst.NO);
			dpExprParaval.setCreateTime(prevDate);
			// 删除之前将数据打印到日志文件做备份
			executeQueryDpExprParaval(0, dpExprParaval);
			// 执行删除
			dpExprParavalRepository.deleteByUseFlag(dpExprParaval);
			
			// 重置所有数据的useFlag
			dpExprParaval = new DpExprParaval();
			dpExprParaval.setUseFlag(CommonConst.NO);
			dpExprParavalRepository.updateByUseFlag(dpExprParaval);
			
		} catch (Exception e) {
			log.error("删除表达式参数值表(dp_expr_paraval)中无效的数据出现异常！", e);
		}
		
		log.info("TriggerDpExprParaval execute method end.");
	}
	
	/**
	 * 处理DpMppCfg、DpMppCfgDtl表中的数据
	 */
	public void executeDpMppCfg(int current_page) {
		// 初始化页码
		PageRequest pageRequest = new PageRequest(current_page, size);
		// 查询数据映射配置集合
		Page<DpMppCfg> page = dP_Mpp_Repository.queryDpMppCfgPage(pageRequest, null);
		List<DpMppCfg> list = page.getContent();
		current_page = page.getNumber();
		int next_page = current_page + 1;
		int totle_page = page.getTotalPages();
		
		if (list != null && list.size() > 0) {
			// 存放待执行的所有sql
			List<SqlHolder> list_sqlHolder = new ArrayList<SqlHolder>();
			for (DpMppCfg dpMppCfg : list) {
				if (dpMppCfg != null) {
					// 处理当前表中的参数值
					String seqNo_dpMppCfg = dpMppCfg.getProcCfg();
					if (StringUtil.isNotTrimEmpty(seqNo_dpMppCfg)) {
						List<DpExprParaval> list_dpExprParaval = CommonUtil.getAllDepBySeqNo(seqNo_dpMppCfg);
						if (list_dpExprParaval != null && list_dpExprParaval.size() > 0) {
							for (DpExprParaval dpExprParaval : list_dpExprParaval) {
								dpExprParaval.setUseFlag(CommonConst.YES);
								SqlHolder sqlHolder = new SqlHolder(updateSqlId, dpExprParaval);
								list_sqlHolder.add(sqlHolder);
							}
						}
					}
					
					// 处理Dtl表中的参数值
					String mppId = dpMppCfg.getMppId();
					DpMppCfgDtl dpMppCfgDtl = new DpMppCfgDtl();
					dpMppCfg.setMppId(mppId);
					List<DpMppCfgDtl> list_dpMppCfgDtl = dP_Mpp_Repository.queryDpMppCfgDtlsByMppId(dpMppCfgDtl);
					if (list_dpMppCfgDtl != null && list_dpMppCfgDtl.size() > 0) {
						for (DpMppCfgDtl v_dpMppCfgDtl : list_dpMppCfgDtl) {
							String seqNo_dpMppCfgDtl = v_dpMppCfgDtl.getColFormula();
							if (StringUtil.isNotTrimEmpty(seqNo_dpMppCfgDtl)) {
								List<DpExprParaval> list_dpExprParaval = CommonUtil.getAllDepBySeqNo(seqNo_dpMppCfgDtl);
								if (list_dpExprParaval != null && list_dpExprParaval.size() > 0) {
									for (DpExprParaval dpExprParaval : list_dpExprParaval) {
										dpExprParaval.setUseFlag(CommonConst.YES);
										SqlHolder sqlHolder = new SqlHolder(updateSqlId, dpExprParaval);
										list_sqlHolder.add(sqlHolder);
									}
								}
							}
						}
					}
				}
			}
			if (list_sqlHolder != null && list_sqlHolder.size() > 0) {
				dpExprParavalRepository.bathUpdate(list_sqlHolder);
			}
		}
		
		// 判断是否还有下一页数据，若有，则递归获取处理
		if (current_page < totle_page) {
			executeDpMppCfg(next_page);
		}
	}
	
	/**
	 * 处理DpExpCfg、DpExpCfgDtl表中的数据
	 */
	public void executeDpExpCfg(int current_page) {
		// 初始化页码
		PageRequest pageRequest = new PageRequest(current_page, size);
		// 查询数据映射配置集合
		Page<DpExpCfg> page = dP_Exp_Repository.queryDpMppCfgPage(pageRequest, null);
		List<DpExpCfg> list = page.getContent();
		current_page = page.getNumber();
		int next_page = current_page + 1;
		int totle_page = page.getTotalPages();
		
		if (list != null && list.size() > 0) {
			// 存放待执行的所有sql
			List<SqlHolder> list_sqlHolder = new ArrayList<SqlHolder>();
			for (DpExpCfg dpExpCfg : list) {
				if (dpExpCfg != null) {
					// 处理当前表中的参数值
					String seqNo_dpExpCfg = dpExpCfg.getTableFilter();
					if (StringUtil.isNotTrimEmpty(seqNo_dpExpCfg)) {
						List<DpExprParaval> list_dpExprParaval = CommonUtil.getAllDepBySeqNo(seqNo_dpExpCfg);
						if (list_dpExprParaval != null && list_dpExprParaval.size() > 0) {
							for (DpExprParaval dpExprParaval : list_dpExprParaval) {
								dpExprParaval.setUseFlag(CommonConst.YES);
								SqlHolder sqlHolder = new SqlHolder(updateSqlId, dpExprParaval);
								list_sqlHolder.add(sqlHolder);
							}
						}
					}
					
					// 处理Dtl表中的参数值
					String expId = dpExpCfg.getExpId();
					DpExpCfgDtl dpExpCfgDtl = new DpExpCfgDtl();
					dpExpCfgDtl.setExpId(expId);
					List<DpExpCfgDtl> list_dpExpCfgDtl = dP_Exp_Repository.queryDpExpCfgDtlsByExpId(dpExpCfgDtl);
					if (list_dpExpCfgDtl != null && list_dpExpCfgDtl.size() > 0) {
						for (DpExpCfgDtl v_dpExpCfgDtl : list_dpExpCfgDtl) {
							String seqNo_dpExpCfgDtl = v_dpExpCfgDtl.getFieldFormula();
							if (StringUtil.isNotTrimEmpty(seqNo_dpExpCfgDtl)) {
								List<DpExprParaval> list_dpExprParaval = CommonUtil.getAllDepBySeqNo(seqNo_dpExpCfgDtl);
								if (list_dpExprParaval != null && list_dpExprParaval.size() > 0) {
									for (DpExprParaval dpExprParaval : list_dpExprParaval) {
										dpExprParaval.setUseFlag(CommonConst.YES);
										SqlHolder sqlHolder = new SqlHolder(updateSqlId, dpExprParaval);
										list_sqlHolder.add(sqlHolder);
									}
								}
							}
						}
					}
				}
			}
			if (list_sqlHolder != null && list_sqlHolder.size() > 0) {
				dpExprParavalRepository.bathUpdate(list_sqlHolder);
			}
		}
		
		// 判断是否还有下一页数据，若有，则递归获取处理
		if (current_page < totle_page) {
			executeDpExpCfg(next_page);
		}
	}
	
	/**
	 * 查询DpExprParaval表中的无效数据，并打印成日志
	 */
	public void executeQueryDpExprParaval(int current_page, DpExprParaval dpExprParaval) {
		// 初始化页码
		PageRequest pageRequest = new PageRequest(current_page, size);
		// 查询数据映射配置集合
		Page<DpExprParaval> page = dpExprParavalRepository.queryDpExprParavalPage(pageRequest, dpExprParaval);
		List<DpExprParaval> list = page.getContent();
		current_page = page.getNumber();
		int next_page = current_page + 1;
		int totle_page = page.getTotalPages();
		
		if (list != null && list.size() > 0) {
			for (DpExprParaval dep : list) {
				StringBuffer sbf = new StringBuffer();
				sbf.append("wait del tempdate dp_expr_paraval (SEQ_NO, DP_CFG_ID, MAP_TYPE, BRANCH_ID, PARAM_VALUE, PARAM_TYPE, USE_FLAG, CREATE_TIME, CREATE_USER, RSV1, RSV2, RSV3, RSV4, RSV5) values (");
				sbf.append("'" + dep.getSeqNo() + "', ");
				sbf.append("'" + dep.getDpCfgId() + "', ");
				sbf.append("'" + dep.getMapType() + "', ");
				sbf.append("'" + dep.getBranchId() + "', ");
				sbf.append("'" + dep.getParamValue() + "', ");
				sbf.append("'" + dep.getParamType() + "', ");
				sbf.append("'" + dep.getUseFlag() + "', ");
				sbf.append("'" + dep.getCreateTime() + "', ");
				sbf.append("'" + dep.getCreateUser() + "', ");
				sbf.append("'" + dep.getRsv1() + "', ");
				sbf.append("'" + dep.getRsv2() + "', ");
				sbf.append("'" + dep.getRsv3() + "', ");
				sbf.append("'" + dep.getRsv4() + "', ");
				sbf.append("'" + dep.getRsv5() + "'");
				sbf.append(");");
				log.info(sbf.toString());
			}
		}
		
		// 判断是否还有下一页数据，若有，则递归获取处理
		if (current_page < totle_page) {
			executeQueryDpExprParaval(next_page, dpExprParaval);
		}
	}
	
}
