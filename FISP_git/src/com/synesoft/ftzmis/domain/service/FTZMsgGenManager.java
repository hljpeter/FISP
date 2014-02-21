package com.synesoft.ftzmis.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.fisp.app.common.context.CommonResourceHelper;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgHead;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.domain.model.FtzMsgBatch;
import com.synesoft.ftzmis.domain.model.FtzMsgGenerateParam;
import com.synesoft.ftzmis.domain.model.FtzMsgProc;
import com.synesoft.ftzmis.domain.repository.FTZMsgGenerateRepository;

/**
 * 报文生成管理类<br>
 * 使用说明：<br>
 * 1) AppCtxUtil.getCtx().getBean("ftzMsgGenManager")或者Spring注解的方式（未测试可行）获得该类的实例<br>
 * 2) 调用setParam(FtzMsgGenerateParam)设置参数，需要传入BranchList（机构Id）和MsgNoList<br>
 * 3) 调用run()方法执行<br>
 * <br>
 * run()方法执行：<br>
 * 1) init()初始化基本参数msgConfig(存放报文参数的内部类，参数设置在SYS_PARAM中)和errorList生成失败的报文数量<br>
 * 2) getList()，根据设置的param获取指定机构和报文编号下的批量数量（不同状态）<br>
 * 3) start() 开始执行<br>
 * <br>
 * start() 方法执行：<br>
 * 1) 根据getList()返回的结果，查询出每个机构，每个报文编号下需要报送的工作日期<br>
 * 2) 查询出每个批量，每个工作日期下是否有要报送新增，修改，删除的明细<br>
 * 3) 生成报文<br>
 * <br>
 * 调用getErrorList()方法获得生成报文失败的List（仅机构号，报文编号和数量）
 * @author yyw
 * @date 2014-01-07
 */
@Service("ftzMsgGenManager")
public class FTZMsgGenManager {

	private static LogUtil log = new LogUtil(FTZMsgGenManager.class);

	private MsgConfigParam msgConfig;
	private List<FtzMsgGenerateParam> errorList;
	private FtzMsgGenerateParam param;
	
	/**
	 * 初始化参数
	 */
	public synchronized void init() {
		msgConfig = new MsgConfigParam();
		errorList = new ArrayList<FtzMsgGenerateParam>();
	}
	
	/**
	 * @param param the param to set
	 */
	public void setParam(FtzMsgGenerateParam param) {
		this.param = param;
	}

	/**
	 * 生成报文入口<br>
	 * 步骤：<br>
	 * 1) init()
	 * 2) getList()
	 * 3) start()
	 */
	@Transactional
	public synchronized void run() {
		init();
		List<FtzMsgGenerateParam> list = null;
		try {
			list = getList();
		} catch(BusinessException e) {
			throw e;
		}
		start(list);
	}
	
	/**
	 * 统计机构，报文编号下，正在录入，录入完成，审核通过，审核失败四个状态的批量数量
	 * @param branchList - 机构List
	 * @return
	 * 		List<FtzMsgGenerateParam> - 结果List
	 */
	public synchronized List<FtzMsgGenerateParam> getList() {
		log.debug("getList() start ...");

		ResultMessages messages = ResultMessages.error();
		
		List<FtzMsgGenerateParam> list = repository.queryList(param);
		if (null == list || list.isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return list;
	}

	/**
	 * 开始执行
	 * @param list - 所有批量的List
	 */
	@Transactional
	public synchronized void start(List<FtzMsgGenerateParam> list) {
		log.debug("run() start ...");
		
		// search submit date
		if (null != list && !list.isEmpty()) {
			for (FtzMsgGenerateParam param: list) {
				int errorNum = 0;
				
				// 根据BRANCH_ID, MSG_NO, MSG_STATUS ='03'找出需要报送批量的工作日期
				List<String> dateList = repository.querySubmitDateList(param);
				if (null != dateList && !dateList.isEmpty()) {
					for (String workDate: dateList) {
						param.setWorkDate(workDate);
						param.setBlankFlag(CommonConst.MSG_BLANK_FLAG_NORMAL);
						
						// 根据MSG_STATUS ='03',BRANCH_ID,MSG_NO,WORK_DATE找出每个批量报送新增的明细数量
						param.setSendFlag("A");
						List<FtzMsgGenerateParam> txnList_A = repository.queryTxnList(param);
						errorNum += generateMsg(txnList_A, CommonConst.FTZ_MSG_EDIT_FLAG_ADD);
						
						// 根据MSG_STATUS ='03',BRANCH_ID, MSG_NO,WORK_DATE找出每个批量报送修改的明细数量
						param.setSendFlag("C");
						List<FtzMsgGenerateParam> txnList_C = repository.queryTxnList(param);
						errorNum += generateMsg(txnList_C, CommonConst.FTZ_MSG_EDIT_FLAG_UPDATE);
						
						// 根据MSG_STATUS ='03',BRANCH_ID, MSG_NO,WORK_DATE找出每个批量报送删除的明细数量
						param.setSendFlag("D");
						List<FtzMsgGenerateParam> txnList_D = repository.queryTxnList(param);
						errorNum += generateMsg(txnList_D, CommonConst.FTZ_MSG_EDIT_FLAG_DELETE);
					}
				}
				
				if (errorNum != 0) {
					FtzMsgGenerateParam errorParam = new FtzMsgGenerateParam();
					errorParam.setBranchId(param.getBranchId());
					errorParam.setMsgNo(param.getMsgNo());
					errorParam.setCnt(errorNum);
					errorList.add(errorParam);
				}
			}
		}
	}

	/**
	 * 产生报文<br>
	 * 逻辑：<br>
	 * 1) 获得一个批量的参数，计算出这个批量的大小
	 * 2) 将当前批量加入到报文中，判断报文是否达到要求上限，
	 * 3) 如果以上两步都符合，则插入数据库；如果不符合，则继续增加
	 * @param txnList - 交易明细List
	 * @param editFlag - 操作标志
	 * @return
	 * 		int - 报文生成失败数量（一个机构，一个报文编号，一个操作类型）
	 */
	@Transactional
	public synchronized int generateMsg(List<FtzMsgGenerateParam> txnList, String editFlag) {
		int errorNum = 0;
		if (null != txnList && !txnList.isEmpty()) {
			int i = 0;
			List<FtzMsgGenerateParam> msgList = new ArrayList<FtzMsgGenerateParam>();
			FtzMsgGenerateParam param = null;
			double msgSize = 0.00;			// 当前报文大小(初始为0)
			while (i < txnList.size()) {
				// init
				param = txnList.get(i);
				msgSize = calcSize(param);
				
				try {
					checkBatch(param);
				} catch(BusinessException e) {
					log.error(e.getMessage());
					continue;
				}
				
				// 如果返回FALSE，产生一个报文（将List中数据入库后清空）
				Boolean flag = checkMsg(param, msgSize, msgList.size());
				if (!flag) {
					// insert database
					try {
//						msgList.add(param);
						insertDB(msgList, editFlag);
					} catch(Exception e) {
						log.error(e.getMessage());
						errorNum++;
					}
					
					msgList.clear();
					msgSize = 0;
				} else {
					msgList.add(param); // 批量插入到待生产报文List中
					i++;
				}
			}
			// insert database
			try {
//				msgList.add(param);
				insertDB(msgList, editFlag);
			} catch(Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				errorNum++;
			}
		}
		return errorNum;
	}
	
	/**
	 * 插入数据库（一个事务）<br>
	 * 逻辑：<br>
	 * 1) 插入报文表<br>
	 * 2) 插入报文批量映射表<br>
	 * @param msgList - 一个报文下的所有批量
	 * @param editFlag - 操作标志
	 */
	@Transactional
	public synchronized void insertDB(List<FtzMsgGenerateParam> msgList, String editFlag) {
		ResultMessages message = ResultMessages.error();
		
		FtzMsgProc proc = new FtzMsgProc();
		proc.setId(repository.getMsgProcIdSeq());
		proc.setVer(FtzMsgHead.getMsgHead().getVER());
		proc.setDes(FtzMsgHead.getMsgHead().getDES());
		proc.setSrc(FtzMsgHead.getMsgHead().getSRC());
		proc.setMsgRef(proc.getSrc());
		proc.setApp(FtzMsgHead.getMsgHead().getAPP());
		proc.setWorkDate(procCommonService.queryWorkDate());
		proc.setEditFlag(editFlag);
		proc.setMsgId(proc.getSrc() + proc.getWorkDate() + numberService.getSysIDSequence(12));
		proc.setMsgNo(msgList.get(0).getMsgNo());
		proc.setMsgDirection(CommonConst.MSG_PROC_DIRECT_SEND);
		proc.setMsgStatus(CommonConst.MSG_PROC_STATUS_WAITING_SEND);
		int ret = repository.insertMsgProc(proc);
		if (ret != 1) {
			log.error("[e.ftzmis.msg.generate.0003] Insert FTZ_MSG_PROC failure!");
			message.add("e.ftzmis.msg.generate.0003");
			throw new BusinessException(message);
		}
		
		for (FtzMsgGenerateParam param: msgList) {
			FtzMsgBatch fmb = new FtzMsgBatch();
			fmb.setId(proc.getId());		// 报文表的ID
			fmb.setMsgId(param.getMsgId());	// 批量头的MSG_ID
			fmb.setMsgNo(param.getMsgNo());	
			ret = repository.insertMsgBatch(fmb);
			if (ret != 1) {
				log.error("[e.ftzmis.msg.generate.0004] Insert FTZ_MSG_BATCH failure!");
				message.add("e.ftzmis.msg.generate.0004");
				throw new BusinessException(message);
			}
		}
	}

	/**
	 * 根据账号，子账号，申报日期，产生表内空批量
	 * @param accountNo
	 * @param subAccountNo
	 * @param submitDate
	 */
	public void generateBlankBatch(String accountNo, String subAccountNo, String submitDate) {
		FtzMsgGenerateParam param = new FtzMsgGenerateParam();
		param.setAccountNo(accountNo);
		param.setSubAccountNo(subAccountNo);
		param.setSubmitDate(submitDate);
		param.setWorkDate(procCommonService.queryWorkDate());
		repository.callBlankBatchProc(param);
	}
	
	/**
	 * 计算批量大小
	 * 逻辑：批量头 + 交易明细
	 * @param param
	 * @return
	 * 		批量的大小，单位KB
	 */
	private double calcSize(FtzMsgGenerateParam param) {
		double size = 0.00;
		if (FtzMsgProcService.MsgType.FTZ_OUT_MSG.equals(FtzMsgProcService.getMsgType(param.getMsgNo()))) { // 表外需要验交易明细数量
			size = msgConfig.getOFF_BATCH_SIZE() + msgConfig.getOFF_DETAIL_SIZE() * param.getCnt(); 
		} else {
			size = msgConfig.getIN_BATCH_SIZE() + msgConfig.getIN_DETAIL_SIZE() * param.getCnt(); 
		}
		return size;
	}
	
	/**
	 * 检查批量是否符合要求<br>
	 * 逻辑：<br>
	 * 1) 如果是表外批量，明细数量超过1000，则跳过<br>
	 * 2) 如果已经产生报文，则跳过
	 * @param param - 批量参数对象
	 */
	private void checkBatch(FtzMsgGenerateParam param) {
		ResultMessages messages = ResultMessages.error();
		
		// 判断明细数量
		if (FtzMsgProcService.MsgType.FTZ_OUT_MSG.equals(FtzMsgProcService.getMsgType(param.getMsgNo()))) {
			if (param.getCnt() > (msgConfig.getDETAIL_MAX_NUM())) {
				log.error("[e.ftzmis.msg.generate.0001] The number of detail is over maximun '" + (msgConfig.getDETAIL_MAX_NUM()) + "'!");
				messages.add("e.ftzmis.msg.generate.0001");
				throw new BusinessException(messages);
			}
		}

		// 查询是否已经产生报文
		int msgCount = repository.queryMsg(param);
		if (msgCount != 0) {
			log.error("[e.ftzmis.msg.generate.0002] The batch has been generated!");
			messages.add("e.ftzmis.msg.generate.0002");
			throw new BusinessException(messages);
		}
	}

	/**
	 * 检查加上一个批量后，报文是否符合要求，事实报文List中还没有加<br>
	 * 逻辑：<br>
	 * 1) 当前报文大小 + 当前批量大小 是否大于 报文最大值 * 临界参数<br>
	 * 2) 同时，如果是表内，当前报文下批量数量 + 1（当前批量） 是否大于批量数量最大值 * 临界参数
	 * @param param - 批量参数对象
	 * @param msgSize - 报文大小
	 * @param msgNum - 报文下批量数量
	 * @return
	 * 		TRUE - 新报文符合要求，可以继续增加
	 * 		FALSE - 新报文符合不要求，当前批量不可以加入此报文
	 */
	private Boolean checkMsg(FtzMsgGenerateParam param, double msgSize, int batchNum) {
		double size = calcSize(param); // 计算批量大小
		msgSize += size;
		batchNum++;
		
		// 一个报文中批量不超过200，或者大小不超过2M
		if (msgSize > (msgConfig.getMSG_SIZE() * msgConfig.get_n())) { 
			return false;
		} else {
			if (FtzMsgProcService.MsgType.FTZ_IN_MSG.equals(FtzMsgProcService.getMsgType(param.getMsgNo())) && batchNum > (msgConfig.getBATCH_MAX_NUM() * msgConfig.get_n()))
				return false;
			else
				return true;
		}
	}

	/**
	 * @return the errorList
	 */
	public List<FtzMsgGenerateParam> getErrorList() {
		return errorList;
	}

	@Autowired
	private NumberService numberService;
	@Autowired
	private ProcCommonService procCommonService;
	@Autowired
	private FTZMsgGenerateRepository repository;

	private class MsgConfigParam {
		private Integer DETAIL_MAX_NUM;
		private Integer N;
		private Integer MSG_SIZE;
		private Integer BATCH_MAX_NUM;
		private Integer OFF_BATCH_SIZE;
		private Integer OFF_DETAIL_SIZE;
		private Integer IN_BATCH_SIZE;
		private Integer IN_DETAIL_SIZE;
		private double _n;
		
		public MsgConfigParam() {
			SysParam sysParam = CommonResourceHelper.getSysParam("FTZ", "DETAIL_MAX");
			DETAIL_MAX_NUM = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "N");
			N = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "MSG_SIZE");
			MSG_SIZE = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "BATCH_MAX");
			BATCH_MAX_NUM = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "OFF_BATCH_SIZE");
			OFF_BATCH_SIZE = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "OFF_DETAIL_SIZE");
			OFF_DETAIL_SIZE = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "IN_BATCH_SIZE");
			IN_BATCH_SIZE = Integer.valueOf(sysParam.getParamVal());
			sysParam = CommonResourceHelper.getSysParam("FTZ", "IN_DETAIL_SIZE");
			IN_DETAIL_SIZE = Integer.valueOf(sysParam.getParamVal());
			_n = Double.valueOf(N) / 100;
		}

		/**
		 * @return the dETAIL_MAX_NUM
		 */
		public Integer getDETAIL_MAX_NUM() {
			return DETAIL_MAX_NUM;
		}

		/**
		 * @return the mSG_SIZE
		 */
		public Integer getMSG_SIZE() {
			return MSG_SIZE;
		}

		/**
		 * @return the bATCH_MAX_NUM
		 */
		public Integer getBATCH_MAX_NUM() {
			return BATCH_MAX_NUM;
		}

		/**
		 * @return the oFF_BATCH_SIZE
		 */
		public Integer getOFF_BATCH_SIZE() {
			return OFF_BATCH_SIZE;
		}

		/**
		 * @return the oFF_DETAIL_SIZE
		 */
		public Integer getOFF_DETAIL_SIZE() {
			return OFF_DETAIL_SIZE;
		}

		/**
		 * @return the iN_BATCH_SIZE
		 */
		public Integer getIN_BATCH_SIZE() {
			return IN_BATCH_SIZE;
		}

		/**
		 * @return the iN_DETAIL_SIZE
		 */
		public Integer getIN_DETAIL_SIZE() {
			return IN_DETAIL_SIZE;
		}

		/**
		 * @return the _n
		 */
		public double get_n() {
			return _n;
		}
		
	}
	
}
