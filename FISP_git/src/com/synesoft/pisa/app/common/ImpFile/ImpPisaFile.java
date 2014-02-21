package com.synesoft.pisa.app.common.ImpFile;

import java.util.List;

import org.springframework.util.StringUtils;

import cn.com.synesoft.jsynframe.batch.batch.BatchParameters;
import cn.com.synesoft.jsynframe.batch.batch.BatchServerBase;
import cn.com.synesoft.jsynframe.util.ExceptionBase;

import com.synesoft.dataproc.common.constants.DataProcConst;
import com.synesoft.dataproc.common.constants.ImpFileConfig;
import com.synesoft.dataproc.common.util.AppCtxUtil;
import com.synesoft.dataproc.common.util.DateUtil;
import com.synesoft.dataproc.common.util.GenRdmGUID;
import com.synesoft.dataproc.common.util.LogUtil;
import com.synesoft.dataproc.imp.ImpHelper;
import com.synesoft.dataproc.imp.fileutil.CsvUtil;
import com.synesoft.dataproc.imp.fileutil.ExcelUtil;
import com.synesoft.dataproc.imp.fileutil.TxtUtil;
import com.synesoft.dataproc.model.DpImpLog;
import com.synesoft.dataproc.service.ProcCommonService;

public class ImpPisaFile extends BatchServerBase {

	private static final LogUtil log = new LogUtil(ImpPisaFile.class);// log日志

	private ImpFileConfig impFileConfig;// 初始化参数集

	private boolean initConfig;// 初始化参数是否正确

	private DpImpLog dpImpLog = new DpImpLog(); // 导入日志

	private ProcCommonService procCommonService = (ProcCommonService) AppCtxUtil
			.getCtx().getBean("procCommonService");// 数据处理公用处理类
	
	public ImpPisaFile(BatchParameters batchParameters) throws ExceptionBase {
		super(batchParameters);

		dpImpLog.setBeginTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));// 任务开始时间
		dpImpLog.setRsv1(String.valueOf(batchParameters.getRuntimeId())); //RuntimeId
		dpImpLog.setRsv2(String.valueOf(batchParameters.getStepId())); //RuntimeId

		// 初始化导入配置信息
		try {
			impFileConfig = ImpHelper.initImpFileConfig(batchParameters.getTaskId(), batchParameters.getStepId());
			setInitConfig(true);
		} catch (Exception e) {
			setInitConfig(false);
			log.error("初始化导入文件参数失败,失败原因:" + e.getMessage());
		}
	}

	@Override
	protected boolean execute() throws ExceptionBase {

		// 获取参数成功
		if (isInitConfig() && (impFileConfig != null)) {

			try {
				// 固定长度TXT或分隔符TXT文件
				if (DataProcConst.FILE_TYPE_FIXED_TXT.equals(impFileConfig
						.getDpFile().getFileType())
						|| DataProcConst.FILE_TYPE_TXT.equals(impFileConfig
								.getDpFile().getFileType())) {
					TxtUtil txtUtil = new TxtUtil(impFileConfig);
					txtUtil.readFile(); // 读文件并入库
					
					dpImpLog.setImpResult(String.valueOf(txtUtil.isReadRight())); // 读取文件是否正确
					dpImpLog.setTotalRows(Long.valueOf(txtUtil.getTotal_count())); // 总行数
					dpImpLog.setErrRows(Long.valueOf(txtUtil.getFailed_count())); // 总行数
					dpImpLog.setErrInfo(txtUtil.getErrMsg()); // 错误信息
					
					writeImpLog(); // 写操作日志
				}
				// CSV文件
				else if (DataProcConst.FILE_TYPE_CSV.equals(impFileConfig
						.getDpFile().getFileType())) {
					CsvUtil csvUtil = new CsvUtil(impFileConfig);
					csvUtil.readFile(); // 读文件并入库
					
					dpImpLog.setImpResult(String.valueOf(csvUtil.isReadRight())); // 读取文件是否正确
					dpImpLog.setTotalRows(Long.valueOf(csvUtil.getTotal_count())); // 总行数
					dpImpLog.setErrRows(Long.valueOf(csvUtil.getFailed_count())); // 总行数
					dpImpLog.setErrInfo(csvUtil.getErrMsg()); // 错误信息
					
					writeImpLog(); // 写操作日志
				}
				// EXCEL文件
				if (DataProcConst.FILE_TYPE_EXCEL.equals(impFileConfig.getDpFile().getFileType())) {
					//目录可能有变量，文件名可能有变量
					
					
					//文件列表
					List<String> fileList = null;
					
					//遍历文件列表
					for (int i =0; i< fileList.size(); i++){
						//替换文件名
						
						
						//调用导入
						ExcelUtil excelUtil = new ExcelUtil(impFileConfig);
						excelUtil.readFile(); // 读文件并入库
						
						dpImpLog.setImpResult(String.valueOf(excelUtil.isReadRight())); // 读取文件是否正确
						dpImpLog.setTotalRows(Long.valueOf(excelUtil.getTotal_count())); // 总行数
						dpImpLog.setErrRows(Long.valueOf(excelUtil.getFailed_count())); // 总行数
						dpImpLog.setErrInfo(excelUtil.getErrMsg()); // 错误信息
						
						writeImpLog(); // 写操作日志
					}
				}
				// 暂不支持的文件类型
				else {
					log.error("不支持的文件类型,"+impFileConfig.getDpFile().getFileType());
					return false;
				}
			} catch (Exception e){
				log.error("导入文件出错,出错信息:"+e.getMessage());
				return false;
			}

			return true;
		}
		// 初始化导入文件参数失败
		else {
			return false;
		}
	}

	/**
	 * 记录导入日志
	 */
	private void writeImpLog() {
		dpImpLog.setImpLogId(GenRdmGUID.getGUID());// 主键
		dpImpLog.setImpId(impFileConfig.getDpImpCfg().getImpId());// 导入配置ID
		dpImpLog.setWorkDate(procCommonService.queryWorkDate());// 工作日期
		dpImpLog.setProjId(impFileConfig.getDpImpCfg().getProjId());// 工程号
		dpImpLog.setBranchId(impFileConfig.getDpImpCfg().getBranchId());// 机构号
		dpImpLog.setBatchNo(impFileConfig.getDpImpCfg().getBatchNo());// 批次号
		dpImpLog.setSeqNo(impFileConfig.getDpImpCfg().getSeqNo());// 序号
		dpImpLog.setFileName(impFileConfig.getDpImpCfg().getFileName());// 文件名
		dpImpLog.setTableName(impFileConfig.getDpImpCfg().getTableName());// 表名
		// 导入结果
		if (!isInitConfig() || "FALSE".equals(dpImpLog.getImpResult().trim().toUpperCase())) {
			dpImpLog.setImpResult(DataProcConst.IMP_RESULT_FALSE);
		} else {
			dpImpLog.setImpResult(DataProcConst.IMP_RESULT_TRUE);
		}
		
		dpImpLog.setEndTime(DateUtil
				.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));// 结束时间

		StringBuffer sb = new StringBuffer();
		sb.append(
				"INSERT INTO DP_IMP_LOG (IMP_LOG_ID, IMP_ID, WORK_DATE, PROJ_ID, BRANCH_ID, BATCH_NO, SEQ_NO, ")
				.append("FILE_NAME, TABLE_NAME, IMP_RESULT, TOTAL_ROWS, ERR_ROWS,ERR_INFO, BEGIN_TIME, END_TIME, RSV1, RSV2) VALUES ( ")
				.append(StringUtils.quote(dpImpLog.getImpLogId())).append(",")
				.append(StringUtils.quote(dpImpLog.getImpId())).append(",")
				.append(StringUtils.quote(dpImpLog.getWorkDate())).append(",")
				.append(StringUtils.quote(dpImpLog.getProjId())).append(",")
				.append(StringUtils.quote(dpImpLog.getBranchId())).append(",")
				.append(dpImpLog.getBatchNo()).append(",")
				.append(dpImpLog.getSeqNo()).append(",")
				.append(StringUtils.quote(dpImpLog.getFileName())).append(",")
				.append(StringUtils.quote(dpImpLog.getTableName())).append(",")
				.append(StringUtils.quote(dpImpLog.getImpResult())).append(",")
				.append(dpImpLog.getTotalRows()).append(",")
				.append(dpImpLog.getErrRows()).append(",")
				.append(StringUtils.quote(dpImpLog.getErrInfo())).append(",")
				.append(StringUtils.quote(dpImpLog.getBeginTime())).append(",")
				.append(StringUtils.quote(dpImpLog.getEndTime())).append(",")
				.append(StringUtils.quote(dpImpLog.getRsv1())).append(",")
				.append(StringUtils.quote(dpImpLog.getRsv2())).append(")");

		procCommonService.update(sb.toString());
	}

	public ImpFileConfig getImpFileConfig() {
		return impFileConfig;
	}

	public void setImpFileConfig(ImpFileConfig impFileConfig) {
		this.impFileConfig = impFileConfig;
	}

	public boolean isInitConfig() {
		return initConfig;
	}

	public void setInitConfig(boolean initConfig) {
		this.initConfig = initConfig;
	}

	public static void main(String[] args) {

	}

}
