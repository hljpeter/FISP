package com.synesoft.ftzmis.app.common.msgproc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.dataproc.common.util.CommonUtil;
import com.synesoft.dataproc.common.util.FileUtil;
import com.synesoft.dataproc.common.util.StringUtil;
import com.synesoft.dataproc.exp.util.XMLUtil;
import com.synesoft.dataproc.model.DpExpCfg;
import com.synesoft.dataproc.repository.DpExpCfgRepository;
import com.synesoft.dataproc.service.DpExpCfgService;
import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.fisp.app.common.context.MemoryContextHolder;
import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.ftzmis.app.common.msgproc.ibmMQ.MQConstant;
import com.synesoft.ftzmis.app.common.msgproc.ibmMQ.MQMsgProc;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzMsgProc;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository;
import com.synesoft.ftzmis.domain.repository.FtzMsgProcRepository;
import com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository;

@Service("ftzMsgProcService")
public class FtzMsgProcService{
	@Autowired
	private ProcCommonService procCommonService;
	
	@Resource
	private DpExpCfgRepository dpExpCfgRepository;
	
	@Resource
	private FtzInMsgCtlRepository ftzInMsgCtlRepository;
	
	@Resource
	private FtzOffMsgCtlRepository ftzOffMsgCtlRepository;
	
	@Autowired
	private DpExpCfgService dpExpCfgService;
	
	@Resource
	private FtzMsgProcRepository ftzMsgProcRepository;
	
	private static final LogUtil log = new LogUtil(FtzMsgProcService.class);
	
	private static final String charsetName = "GBK";
	
	private static final String endTag = "</CFX>";
	
	// 报文编号分类
	private static enum MsgType {
		FTZ_IN_MSG, // 表内报文(发送)
		FTZ_OUT_MSG, // 表外报文(发送)
		FTZ_ACT_SEND, // 账户信息报送
		FTZ_ACT_RECEIVE, // 客户信息下发
		FTZ_COMM_REPLY, // 报文接收应答
		FTZ_PROC_REPLY // 报文处理失败应答
	}
	
	private static final Map<String, MsgType> mMsgType = new HashMap<String, MsgType>() {

		private static final long serialVersionUID = 1L;

		{
			put("210101", MsgType.FTZ_IN_MSG); // 单位存款
			put("210102", MsgType.FTZ_IN_MSG); // FTU内部活动-金融债券
			put("210103", MsgType.FTZ_IN_MSG); // FTU内部活动-中长期借款
			put("210104", MsgType.FTZ_IN_MSG); // 应付及暂收款
			put("210105", MsgType.FTZ_IN_MSG); // 卖出回购资产
			put("210106", MsgType.FTZ_IN_MSG); // 向中央银行借款
			put("210107", MsgType.FTZ_IN_MSG); // 同业往来
			put("210108", MsgType.FTZ_IN_MSG); // 系统内资金往来
			put("210109", MsgType.FTZ_IN_MSG); // 外汇买卖
			put("210110", MsgType.FTZ_IN_MSG); // FTU委托存款及委托投资基金
			put("210111", MsgType.FTZ_IN_MSG); // 代理金融机构委托贷款基金
			put("210112", MsgType.FTZ_IN_MSG); // 各项准备
			put("210201", MsgType.FTZ_IN_MSG); // 各项贷款
			put("210202", MsgType.FTZ_IN_MSG); // 有价证券
			put("210203", MsgType.FTZ_IN_MSG); // 股权及其他投资
			put("210204", MsgType.FTZ_IN_MSG); // 应收及预付款
			put("210205", MsgType.FTZ_IN_MSG); // 买入返售资产
			put("210206", MsgType.FTZ_IN_MSG); // 存放中央准备金存款
			put("210207", MsgType.FTZ_IN_MSG); // 存放中央银行特种存款
			put("210208", MsgType.FTZ_IN_MSG); // 缴存中央银行财政性存款
			put("210209", MsgType.FTZ_IN_MSG); // 同业往来（运用方）
			put("210210", MsgType.FTZ_IN_MSG); // 系统内资金往来
			put("210211", MsgType.FTZ_IN_MSG); // 库存现金
			put("210212", MsgType.FTZ_IN_MSG); // 外汇买卖
			put("210401", MsgType.FTZ_IN_MSG); // 代理发债
			put("210301", MsgType.FTZ_OUT_MSG); // 应付信用证（进口开证）
			put("210302", MsgType.FTZ_OUT_MSG); // 应付保函/备用证（保函/备用证开立）
			put("210303", MsgType.FTZ_OUT_MSG); // 信用证保兑（进口应付信用证加保）
			put("210304", MsgType.FTZ_OUT_MSG); // 应付银行承兑汇票
			put("210305", MsgType.FTZ_OUT_MSG); // 应收信用证（出口交单）
			put("210306", MsgType.FTZ_OUT_MSG); // 应收保函/备用证（保函通知，收到境外保函）
			put("210307", MsgType.FTZ_OUT_MSG); // 信用证保兑（出口应收信用证加保）
			put("210308", MsgType.FTZ_OUT_MSG); // 应收银行承兑汇票
			put("210309", MsgType.FTZ_OUT_MSG); // 远期结售汇
			put("210310", MsgType.FTZ_OUT_MSG); // 汇率掉期业务（远期未交割部分）
			put("210311", MsgType.FTZ_OUT_MSG); // 表外理财
			put("210501", MsgType.FTZ_ACT_SEND); // FTE、FTN、FTU 账户信息报送（新增、修改、删除）
			put("310101", MsgType.FTZ_ACT_RECEIVE); // 客户信息下发
			put("910101", MsgType.FTZ_COMM_REPLY); // 通用应答
			put("910102", MsgType.FTZ_PROC_REPLY); // 报文失败应答

		}
	};
	
	/**
	 * 提交报文信息
	 * @param msg_no 报文编号
	 * @param msg_id 报文标识号
	 */
	@Transactional
	public void submitMsg(String msg_no, String msg_id) {
		//将报文信息设置为待发送
		setSendMsgAwait(msg_no, msg_id, false);
		//写报文信息到文件
		writeToXml(msg_no, msg_id);
	}
	
	/**
	 * 设置报文信息为 待处理
	 * @param msg_no 报文编号
	 * @param msg_id 报文标识号
	 * @param err_correct_flag 纠错标志(人行反馈错误,修改后重发 为True，否则是false)
	 * @param errInfo 错误信息
	 */
	@Transactional
	public void setSendMsgAwait(String msg_no, String msg_id, boolean err_correct_flag) {
		FtzMsgProc ftzMsgProc = new FtzMsgProc();
		
		ftzMsgProc.setMsgId(msg_id); // 报文标识号
		ftzMsgProc.setMsgNo(msg_no); // 报文编号
		ftzMsgProc.setWorkDate(procCommonService.queryWorkDate()); // 工作日期
		ftzMsgProc.setMsgDirection("S"); // S-发送,R-接收
		// 10-待发送;11-发送成功;12-发送失败;13-接收应答成功;14-接收应答失败;15-接收应答超时;16-失败处理应答;21-待处理;22-处理成功;23-处理失败;
		ftzMsgProc.setMsgStatus("10"); 
		
		log.info("将报文信息设置为10-待发送,工作日期:"+ftzMsgProc.getWorkDate()+",报文编号:"+msg_no+"报文标识号:"+msg_id+"是否纠错报文:"+err_correct_flag);
		
		ftzMsgProcRepository.insertFtzMsgProc(ftzMsgProc);
	}
	
	/**
	 * 保存报文信息到xml文件
	 * @param msg_no 报文编号
	 * @param msg_id 报文标识号
	 */
	public void writeToXml(String msg_no, String msg_id) {
		StringBuffer errInfo = new StringBuffer();
		StringBuffer xml = new StringBuffer();
		
		DpExpCfg dpExpCfg = dpExpCfgRepository.queryDpExpCfg("FTZ"+msg_no);
		
		Map<String, Object> msgParam = new HashMap<String, Object>();
		msgParam.put("%MSG_NO%", msg_no);
		msgParam.put("%MSG_ID%", msg_id);
		
		dpExpCfg.setFilePath(dpExpCfg.getFilePath().replace("%WORK_DATE%", procCommonService.queryWorkDate()));
		dpExpCfg.setFileName(dpExpCfg.getFileName().replace("%MSG_NO%", msg_no).replace("%MSG_ID%", msg_id));
		
		// 获取文件全路径
		String fullFileName = CommonUtil.getFullFileName(dpExpCfg);
		
		XMLUtil xmlUtil = new XMLUtil(errInfo, dpExpCfgService, procCommonService);
		
		FtzMsgProc ftzMsgProc = new FtzMsgProc();
		ftzMsgProc.setMsgId(msg_id); // 报文标识号
		ftzMsgProc.setMsgNo(msg_no); // 报文编号
		ftzMsgProc.setWorkDate(procCommonService.queryWorkDate());
		ftzMsgProc.setFileName(fullFileName);
		
		if (xmlUtil.xmlHandle(dpExpCfg, xml, msgParam)){
			try {
				// 创建文件
				if (!CommonUtil.createFile(errInfo, dpExpCfg)){
					log.error("写报文信息到文件出错,报文编号:"+msg_no+",报文标识号:"+msg_id+",错误信息:"+errInfo.toString());
				}
				
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fullFileName), charsetName);
				BufferedWriter writer = new BufferedWriter(write);
				writer.write(xml.toString());
				writer.close(); 
				log.info("写报文信息成功,报文编号:"+msg_no+",报文标识号:"+msg_id+",文件名:"+fullFileName);
			} catch (Exception e) {
				log.error("写报文信息到文件出错,报文编号:"+msg_no+",报文标识号:"+msg_id+",错误信息:"+errInfo.toString());
				ftzMsgProc.setFailReason(getErrInfo("写报文信息到文件出错,报文编号:"+msg_no+",报文标识号:"+msg_id+",错误信息:"+errInfo.toString()));
			} 
		} else {
			//更新错误信息到
			log.error("写报文信息到文件出错,报文编号:"+msg_no+",报文标识号:"+msg_id+",错误信息:"+xmlUtil.getSbf().toString());
			ftzMsgProc.setFailReason(getErrInfo("写报文信息到文件出错,报文编号:"+msg_no+",报文标识号:"+msg_id+",错误信息:"+xmlUtil.getSbf().toString()));
		}
		
		//更新信息到信息处理队列
		ftzMsgProcRepository.updateFtzMsgProc(ftzMsgProc);
	}
	
	/**
	 * 处理报文信息
	 */
	@Transactional
	public void pendingMsgProc(){
		// 获取待处理信息列表
		List<FtzMsgProc> lFtzMsgProc = ftzMsgProcRepository.queryPendingMsg(MQConstant.resendCount);
		log.debug("本次需处理信息队列记录数:" + lFtzMsgProc.size());

		if (lFtzMsgProc!= null && !lFtzMsgProc.isEmpty()) {
			for (FtzMsgProc ftzMsgProc : lFtzMsgProc) {
				
				//发送报文信息处理
				if ("S".equals(ftzMsgProc.getMsgDirection())) {
					log.info("开始处理发送报文,ID:"+ftzMsgProc.getId()+",工作日期:"+ftzMsgProc.getWorkDate()
							+",报文编号:"+ftzMsgProc.getMsgNo()+",报文标识号:"+ftzMsgProc.getMsgId());
					
					String fail_reason = "";
					String msg_status = "12";  //10-待发送;11-发送成功;12-发送失败;13-接收应答成功;15-接收应答超时;21-待处理;22-处理成功;23-处理失败;
					try {
						// 从文件获取报文信息
						String msg_info = readToBuffer(ftzMsgProc.getFileName()).toString();
						
						// 发送到MQ
						if (MQMsgProc.sendMessage(msg_info, fail_reason)) {
							msg_status = "11";  //10-待发送;11-发送成功;12-发送失败;13-接收应答成功;14-接收应答失败;15-接收应答超时;16-失败处理应答;21-待处理;22-处理成功;23-处理失败;
							log.info("发送报文处理成功,工作日期:"+ftzMsgProc.getWorkDate()+",报文编号:"+ftzMsgProc.getMsgNo()+",报文标识号:"+ftzMsgProc.getMsgId());
						} else {
							fail_reason = "发送报文出错:"+ fail_reason;
						}
					} catch (IOException e) {
						fail_reason = "读取文件出错:" + e.getMessage();
						log.error("读取文件出错:" + e.getMessage());
					} finally {
						//更新发送结果
						FtzMsgProc resMsgProc = new FtzMsgProc();
						resMsgProc.setId(ftzMsgProc.getId());
						resMsgProc.setMsgStatus(msg_status);
						resMsgProc.setAppProcCount((short) (ftzMsgProc.getAppProcCount()+1));
						resMsgProc.setSendTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						resMsgProc.setFailReason(getErrInfo(fail_reason));
						
						//更新发送队列表状态
						ftzMsgProcRepository.updateFtzMsgProc(resMsgProc);
						
						// 更新交易状态
						if (MsgType.FTZ_IN_MSG.equals(mMsgType.get(ftzMsgProc.getMsgNo()))) { //表内报文(发送)
							FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
							ftzInMsgCtl.setMsgId(ftzMsgProc.getMsgId());
							ftzInMsgCtl.setMsgStatus(msg_status);
							ftzInMsgCtl.setSndDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							
							ftzInMsgCtlRepository.updateFtzInMsgCtl(ftzInMsgCtl);
						} else if (MsgType.FTZ_OUT_MSG.equals(mMsgType.get(ftzMsgProc.getMsgNo()))){ //表外报文(发送)
							FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
							
							ftzOffMsgCtl.setMsgId(ftzMsgProc.getMsgId());
							ftzOffMsgCtl.setMsgStatus(msg_status);
							ftzOffMsgCtl.setSndDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							
							ftzOffMsgCtlRepository.updateFtzOffMsgCtl(ftzOffMsgCtl);
						} else if (MsgType.FTZ_ACT_SEND.equals(mMsgType.get(ftzMsgProc.getMsgNo()))) { //账户信息报送210501
							//账户表暂时无发送状态信息
						}
					}
				} else
				// 接收报文信息处理
				if ("R".equals(ftzMsgProc.getMsgDirection())) {
					log.info("开始处理接收报文,ID:"+ftzMsgProc.getId()+",工作日期:"+ftzMsgProc.getWorkDate()
							+",报文编号:"+ftzMsgProc.getMsgNo()+",报文标识号:"+ftzMsgProc.getMsgId()+",报文参考号:"+ftzMsgProc.getMsgRef());
					
					String fail_reason = ""; // 失败原因
					String msg_status = "";  //10-待发送;11-发送成功;12-发送失败;;;15-接收应答超时;;21-待处理;22-处理成功;23-处理失败;
					String ref_msg_no = ""; //参考报文编号
					String Result = ""; // 处理结果
					String AddWord = ""; // 附言
					String ACK_DATETIME = ""; // 接收应答日期时间
					String ERR_DATETIME = ""; // 处理应答日期时间
					try {
						// 从文件获取报文信息
						Element element = readXml(ftzMsgProc.getFileName());
						
						if (MsgType.FTZ_COMM_REPLY.equals(mMsgType.get(ftzMsgProc.getMsgNo()))) { // 报文接收应答910101
							ref_msg_no = element.selectSingleNode("MSG/RETURN/MsgNo").getText();
							Result = element.selectSingleNode("MSG/RETURN/Result").getText();
							AddWord = element.selectSingleNode("MSG/RETURN/AddWord").getText();
							ACK_DATETIME = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
							
							if ("90000".equals(Result)){
								msg_status = "13"; // 13-接收应答成功
							} else {
								msg_status = "14"; // 14-接收应答失败
								log.info("该报文被人行拒绝,原因:"+element.selectSingleNode("MSG/RETURN/Information").getText());
							}
						} else if (MsgType.FTZ_PROC_REPLY.equals(mMsgType.get(ftzMsgProc.getMsgNo()))) { // 报文处理失败应答910102
							ref_msg_no = element.selectSingleNode("MSG/ERROR/MsgNo").getText();
							Result = element.selectSingleNode("MSG/ERROR/Result").getText();
							AddWord = element.selectSingleNode("MSG/ERROR/AddWord").getText();
							ERR_DATETIME = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
							
							if ("90000".equals(Result)){
								msg_status = "17"; // 17-成功处理应答
							} else {
								msg_status = "16"; // 16-失败处理应答
								log.info("该报文被人行拒绝,原因:"+element.selectSingleNode("MSG/RETURN/Information").getText());
							}
						} else if (MsgType.FTZ_ACT_RECEIVE.equals(mMsgType.get(ftzMsgProc.getMsgNo()))) { // 客户信息下发
							//暂时未处理
						} else {
							log.error("无法识别的报文编号:"+ftzMsgProc.getMsgNo());
						}
						
						
						// 更新交易状态
						if (MsgType.FTZ_IN_MSG.equals(mMsgType.get(ref_msg_no))) { // 表内报文应答
							FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
							ftzInMsgCtl.setMsgId(ftzMsgProc.getMsgRef());
							ftzInMsgCtl.setMsgStatus(msg_status);
							if (!"".equals(ACK_DATETIME)){
								ftzInMsgCtl.setAckDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							if (!"".equals(ERR_DATETIME)){
								ftzInMsgCtl.setErrDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							ftzInMsgCtl.setResult(Result);
							ftzInMsgCtl.setAddWord(AddWord);
							
							ftzInMsgCtlRepository.updateFtzInMsgCtl(ftzInMsgCtl);
						} else if (MsgType.FTZ_OUT_MSG.equals(mMsgType.get(ref_msg_no))){ // 表外报文应答
							FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
							ftzOffMsgCtl.setMsgId(ftzMsgProc.getMsgRef());
							ftzOffMsgCtl.setMsgStatus(msg_status);
							ftzOffMsgCtl.setSndDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							if (!"".equals(ACK_DATETIME)){
								ftzOffMsgCtl.setAckDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							if (!"".equals(ERR_DATETIME)){
								ftzOffMsgCtl.setErrDatetime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							ftzOffMsgCtl.setResult(Result);
							ftzOffMsgCtl.setAddWord(AddWord);
							
							ftzOffMsgCtlRepository.updateFtzOffMsgCtl(ftzOffMsgCtl);
						} else if (MsgType.FTZ_ACT_SEND.equals(mMsgType.get(ref_msg_no))) { //账户信息报送210501
							//账户表暂时无发送状态信息
						}
					} catch (DocumentException e) {
						fail_reason = "读取文件出错:" + e.getMessage();
						msg_status = "22"; // 22-处理失败;
					} finally {
						//更新处理结果
						FtzMsgProc msgProc = new FtzMsgProc();
						msgProc.setId(ftzMsgProc.getId());
						msgProc.setMsgStatus(msg_status);
						msgProc.setAppProcCount((short) (ftzMsgProc.getAppProcCount()+1));
						msgProc.setAppProcTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
						msgProc.setFailReason(getErrInfo(fail_reason));
						ftzMsgProcRepository.updateFtzMsgProc(msgProc);
						
						//更新原报文状态
						FtzMsgProc refMsgProc = new FtzMsgProc();
						refMsgProc.setMsgId(ftzMsgProc.getMsgRef());
						refMsgProc.setMsgStatus(msg_status);
						refMsgProc.setWorkDate(procCommonService.queryWorkDate());
						//更新接收队列表状态
						ftzMsgProcRepository.updateFtzMsgProc(refMsgProc);
					}
				}
			}
		}
	}
	
	/**
	 * 处理从MQ接收的报文
	 * @param msg
	 */
	@Transactional
	public void RcvMsgFromMqProc(String msg){
		String xml = "";
		int endIndex = msg.indexOf(endTag);
		if (endIndex != -1) {
			xml = msg.substring(0, endIndex + endTag.length());
		} else {
			log.error("报文格式错误,找不到结束节点:" + endTag + "报文信息:" + msg);
			return;
		}
		
		FtzMsgProc ftzMsgProc = new FtzMsgProc();
		
		//将接收报文信息写入文件
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding(charsetName);
        Document document = null;
        try {
			document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
		} catch (DocumentException e) {
			log.error("读取接收报文失败," + "报文信息:" + msg + ",原因:" + e.getMessage());
			return;
		}
        Element rootElement = document.getRootElement();
        ftzMsgProc.setMsgId(rootElement.selectSingleNode("HEAD/MsgID").getText());
        ftzMsgProc.setMsgNo(rootElement.selectSingleNode("HEAD/MsgNo").getText());
        ftzMsgProc.setMsgRef(rootElement.selectSingleNode("HEAD/MsgRef").getText());
        ftzMsgProc.setWorkDate(rootElement.selectSingleNode("HEAD/WorkDate").getText());
        ftzMsgProc.setMsgDirection("R");
        ftzMsgProc.setMsgStatus("21"); // 21-待处理
        SysParam sysParam = (SysParam) MemoryContextHolder.getMemoryResourceByFilter(MemoryResourceType.SYS_PARAM, "001", "FTZ");
        String file_path = "";
        String file_name = "FTZ_"+ftzMsgProc.getMsgNo()+"_"+ftzMsgProc.getMsgId()+".xml";
        if (sysParam!=null && sysParam.getParamVal()!=null){
        	file_path = sysParam.getParamVal().replace("%WORK_DATE%", procCommonService.queryWorkDate());
        	String lastStr = file_path.substring(file_path.length()-1);
			if (!"/".equals(lastStr) && !"\\".equals(lastStr)) {
				file_path = file_path + "/";
			} 
			ftzMsgProc.setFileName(file_path+file_name);
        }else{
        	log.error("接收报文路径配置获取失败,请检查系统参数表的设置。报文信息:" + msg);
        	return;
        }
        
        ftzMsgProc.setReceiveTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        
		// 创建文件
		try {
			if (!FileUtil.creatFile(file_path, file_name)){
				log.error("创建文件失败,报文信息:" + msg);
				return;
			}
		} catch (IOException e) {
			log.error("创建文件失败,报文信息:" + msg);
			return;
		}
		
		OutputStreamWriter write = null;
		try {
			write = new OutputStreamWriter(new FileOutputStream(file_path + file_name), charsetName);
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(xml.toString());
			writer.close();
		} catch (IOException e) {
			log.error("写报文信息到文件失败," + "报文信息:" + msg);
			return;
		} 
			
		log.info("写报文信息成功,报文编号:"+ftzMsgProc.getMsgNo()+",报文标识号:"+ftzMsgProc.getMsgId()+",文件名:"+file_path + file_name);
        
        //设置接收报文信息为 待处理
		ftzMsgProcRepository.insertFtzMsgProc(ftzMsgProc);
	}
	
	/**
	 * 从文件读取报文信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public StringBuffer readToBuffer(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            sb.append(line); // 将读到的内容添加到 buffer 中
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        
        return sb; 
    }
	
	// 截取错误信息
	public static String getErrInfo(String errInfo) {
		int len_sbf = StringUtil.getStrLength(errInfo);
		if (len_sbf <= 2000) {
			return errInfo;
		} else {
			return errInfo.substring(0, 1999);
		}
	}
	
	/**
	 * 读取指定XML,返回根节点
	 * @param file_name
	 * @return
	 * @throws DocumentException 
	 * @throws Exception
	 */
	public Element readXml(String file_name) throws DocumentException{  
	      
        //创建文件对象     
        File file=new File(file_name);     
        //创建一个读取XML文件的对象     
        SAXReader reader=new SAXReader(); 
        reader.setEncoding(charsetName);
        //创建一个文档对象     
        Document document=reader.read(file);     
        //获取文件的根节点     
        Element rootElement=document.getRootElement();  
        
        return rootElement;
    }
	
}
