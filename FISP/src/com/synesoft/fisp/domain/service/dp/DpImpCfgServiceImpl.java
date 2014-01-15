package com.synesoft.fisp.domain.service.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.MessagesUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpImpCfgDtl;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;
import com.synesoft.fisp.domain.model.vo.DpImpCfgVO;
import com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository;
import com.synesoft.fisp.domain.repository.dp.DpFileRepository;
import com.synesoft.fisp.domain.repository.dp.DpImpCfgDtlRepository;
import com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository;
import com.synesoft.fisp.domain.repository.dp.DpMapDictRepository;
import com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository;
import com.synesoft.fisp.domain.repository.dp.DpTableRepository;
import com.synesoft.fisp.domain.service.NumberService;

@Service("dpImpCfgService")
public class DpImpCfgServiceImpl implements DpImpCfgService {

	private static final LogUtil log = new LogUtil(DpImpCfgServiceImpl.class);
	
	@Autowired
	protected DpImpCfgRepository dpImpCfgRepository;
	@Autowired
	protected DpImpCfgDtlRepository dpImpCfgDtlRepository;
	@Autowired
	protected DpFileRepository dpFileRepository;
	@Autowired
	protected DpFileDtlRepository dpFileDtlRepository;
	@Autowired
	protected DpTableRepository dpTableRepository;
	@Autowired
	protected DpTableDtlRepository dpTableDtlRepository;
	@Autowired
	protected DpMapDictRepository dpMapDictRepository;
	
	@Autowired
	protected NumberService numberService;

	private MessagesUtil message = MessagesUtil.getInstance();
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transQueryDpImpCfg(java.lang.String)
	 */
	@Override
	public DpImpCfg transQueryDpImpCfg(String id) {
		log.info("[transQueryDpImpCfg] - start");
		ResultMessages messages = ResultMessages.error();
		
		log.info("searching record, param[id=" + id + "]");
		DpImpCfg dpImpCfg = dpImpCfgRepository.query(id.trim());
		if (null == dpImpCfg) {
			log.error("[e.dp.imp.0008] Failed to delete record, param[id=" + id + "]");
			messages.add("e.dp.imp.0008", id);
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpImpCfg] - end");
		return dpImpCfg;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transQueryDpImpCfgLogicKey(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	public DpImpCfg transQueryDpImpCfgLogicKey(DpImpCfg dpImpCfg) {
		log.info("[transQueryDpImpCfgLogicKey] - start");

		ResultMessages messages = ResultMessages.error();
		
		log.info("Searching record by logic key");
		DpImpCfg result = dpImpCfgRepository.query(dpImpCfg);
		if (null != result) {
			log.error("[e.dp.imp.0009] Searching by logic key, the record is existed, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBatchNo() + ", seqno=" + dpImpCfg.getSeqNo() + "]");
			messages.add("e.dp.imp.0009", dpImpCfg.getBranchId(), dpImpCfg.getProjId(), 
					dpImpCfg.getBatchNo(), dpImpCfg.getSeqNo());
			throw new BusinessException(messages);
		}

		log.info("[transQueryDpImpCfgLogicKey] - end");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transQueryDpImpCfgPage(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public Page<DpImpCfg> transQueryDpImpCfgPage(Pageable pageable, DpImpCfg dpImpCfg) {
		log.info("[transQueryDpImpCfgPage] - start");

		ResultMessages messages = ResultMessages.error();
		
		log.info("searching record, param[branchid=" + (dpImpCfg == null? "":dpImpCfg.getBranchId())  
				+ ", fileid=" + (dpImpCfg == null? "":dpImpCfg.getFileId()) 
				+ ", tablename=" + (dpImpCfg == null? "":dpImpCfg.getTableName()) 
				+ ", pagenum=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");
		
		// remove space
		DpImpCfg cond = new DpImpCfg();
		if (null != dpImpCfg) {
			cond.setBranchId(StringUtil.trim(dpImpCfg.getBranchId()));
			cond.setFileName(StringUtil.trim(dpImpCfg.getFileName()));
			cond.setTableName(StringUtil.trim(dpImpCfg.getTableName()));
		}
		
		Page<DpImpCfg> page = dpImpCfgRepository.queryList(pageable, cond);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] Failed to search records");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpImpCfgPage] - end");
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transQueryDpImpCfgList(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	public List<DpImpCfg> transQueryDpImpCfgList(DpImpCfg dpImpCfg) {
		log.info("[transQueryDpImpCfgList] - start");

		ResultMessages messages = ResultMessages.error();
		
		log.info("searching record, param[branchid=" + (dpImpCfg == null? "":dpImpCfg.getBranchId())  
				+ ", filename=" + (dpImpCfg == null? "":dpImpCfg.getFileName()) 
				+ ", tablename=" + (dpImpCfg == null? "":dpImpCfg.getTableName()) + "]");
		
		// remove space
		DpImpCfg cond = new DpImpCfg();
		if (null != dpImpCfg) {
			cond.setBranchId(StringUtil.trim(dpImpCfg.getBranchId()));
			cond.setFileName(StringUtil.trim(dpImpCfg.getFileName()));
			cond.setTableName(StringUtil.trim(dpImpCfg.getTableName()));
			cond.setFileId(StringUtil.trim(dpImpCfg.getFileId()));
		}
		
		List<DpImpCfg> list = dpImpCfgRepository.queryList(cond);
		if (null == list || list.isEmpty()) {
			log.error("[w.dp.0001] Failed to search records");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpImpCfgList] - end");
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transDel(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	@Transactional
	public void transDel(DpImpCfg dpImpCfg) {
		log.info("[transDel] - start");

		ResultMessages messages = ResultMessages.error();
		
		// remove space
		DpImpCfg cond = new DpImpCfg();
		if (null != dpImpCfg) {
			cond.setImpId(StringUtil.trim(dpImpCfg.getImpId()));
			cond.setUpdateTime(StringUtil.trim(dpImpCfg.getUpdateTime()));
			cond.setUpdateUser(StringUtil.trim(dpImpCfg.getUpdateUser()));
		}
		
		// Whether or not the configuration information is existed
		DpImpCfg result = dpImpCfgRepository.query(cond.getImpId());
		if (null == result) {
			log.error("[e.dp.imp.0008] ImpId is wrong, cannot find any information. param[id=" 
					+ dpImpCfg.getImpId() + ", updatetime=" + dpImpCfg.getUpdateTime() 
					+ ", updateuser=" + dpImpCfg.getUpdateUser() + "]");
			messages.add("e.dp.imp.0008", dpImpCfg.getImpId());
			throw new BusinessException(messages);
		}
		
		// first, deleting the detail information
		int re = dpImpCfgDtlRepository.delete(cond.getImpId());
		log.info("Deleting the detail information success, param[impid=" 
				+ dpImpCfg.getImpId() + ", success deleted=" + re + "]");

		// second, deleting the configuration information
		re = dpImpCfgRepository.delete(cond);
		if (1 != re) {
			log.error("[e.dp.imp.0002] Failed to delete the import configuration, param[id=" 
					+ dpImpCfg.getImpId() + ", updatetime=" + dpImpCfg.getUpdateTime() 
					+ ", updateuser=" + dpImpCfg.getUpdateUser() + "]");
			messages.add("e.dp.imp.0002", dpImpCfg.getImpId());
			throw new BusinessException(messages);
		}

		log.info("Deleting the configuration information success, param[id=" + dpImpCfg.getImpId() 
				+ ", updatetime=" + dpImpCfg.getUpdateTime() 
				+ ", updateuser=" + dpImpCfg.getUpdateUser() + "]");
		log.info("[transDel] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transAdd(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	@Transactional
	public void transAdd(DpImpCfg dpImpCfg) {
		log.info("[transAdd] - start");

		ResultMessages messages = ResultMessages.error();
		
		// remove space
		DpImpCfg cond = new DpImpCfg();
		if (null != dpImpCfg) {
			cond.setProjId(StringUtil.trim(dpImpCfg.getProjId()));
			cond.setBranchId(StringUtil.trim(dpImpCfg.getBranchId()));
			cond.setBatchNo(dpImpCfg.getBatchNo());
			cond.setSeqNo(dpImpCfg.getSeqNo());
		}
		
		// search filename is existed or not
		DpFile dpFile = dpFileRepository.queryForLogicKey(dpImpCfg.getFileName().trim());
		if (null == dpFile) {
			log.error("[e.dp.imp.0023] file name was not existed, param[filename=" 
					+ dpImpCfg.getFileName() + "]");
			messages.add("e.dp.imp.0023", dpImpCfg.getFileName());
			throw new BusinessException(messages);
		}

		// search tablename is existed or not
		DpTable dpTable = dpTableRepository.queryForLogicKey(dpImpCfg.getTableName().trim());
		if (null == dpTable) {
			log.error("[e.dp.imp.0024] table name was not existed, param[tablename=" 
					+ dpImpCfg.getTableName() + "]");
			messages.add("e.dp.imp.0024", dpImpCfg.getTableName());
			throw new BusinessException(messages);
		}
		
		// search projId + branchId + batchNo + seqNo is existed or not.
		transQueryDpImpCfgLogicKey(cond);
		
		// search projId + branchId + batchNo + filename + tablename is existed or not
		cond.setSeqNo(null);
		cond.setFileName(StringUtil.trim(dpImpCfg.getFileName()));
		cond.setTableName(StringUtil.trim(dpImpCfg.getTableName()));
		List<DpImpCfg> list = dpImpCfgRepository.queryList(cond);
		if (null != list && !list.isEmpty()) {
			log.error("[e.dp.imp.0010] table name and file name was used, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBranchId() + ", tablename=" + dpImpCfg.getTableName() + ", filename=" 
					+ dpImpCfg.getFileName() + "]");
			messages.add("e.dp.imp.0010", dpImpCfg.getBranchId(), dpImpCfg.getProjId(), 
					dpImpCfg.getBatchNo(), dpImpCfg.getTableName(), dpImpCfg.getFileName());
			throw new BusinessException(messages);
		}
		
		// add a record
		cond.setSeqNo(dpImpCfg.getSeqNo());
		cond.setCreateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		cond.setCreateUser(ContextConst.getCurrentUser().getUserid());
		cond.setComments(StringUtil.trim(dpImpCfg.getComments()));
		cond.setMandFlag(StringUtil.trim(dpImpCfg.getMandFlag()));
		cond.setFilePath(StringUtil.trim(dpImpCfg.getFilePath()));
		cond.setImpId(numberService.getSysIDSequence(32));
		cond.setFileId(dpFile.getFileId());
		
		int re = dpImpCfgRepository.insert(cond);
		if (re < 1) {
			log.error("[e.dp.imp.0011] Failed to add the configuration information, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBatchNo() + ", seqno=" + dpImpCfg.getSeqNo() + "]");
			messages.add("e.dp.imp.0011", dpImpCfg.getBranchId(), dpImpCfg.getProjId(), 
					dpImpCfg.getBatchNo(), dpImpCfg.getSeqNo());
			throw new BusinessException(messages);
		}
		// ContextConst.getMenu("123")
		TlrLogPrint.tlrSysLogPrint("数据导入映射", CommonConst.DATA_LOG_OPERTYPE_MODIFY, 
				"", generateConfigString(cond));
		log.info("Add the configuration information success, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBatchNo() + ", seqno=" + dpImpCfg.getSeqNo() + "]");
		
		log.info("[transAdd] - end");
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transQueryDetail(java.lang.String)
	 */
	@Override
	public List<Object> transQueryDetail(String id) {
		log.info("[transQueryDetail] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the configuration information
		DpImpCfg dpImpCfg = dpImpCfgRepository.query(id.trim());
		if (null == dpImpCfg) {
			log.error("[e.dp.imp.0008] Failed to search the detail information, param[id=" + id + "]");
			messages.add("e.dp.imp.0008", id);
			throw new BusinessException(messages);
		}
		
		// getting fileId and tableName
		String fileId = dpImpCfg.getFileId();
		String tableName = dpImpCfg.getTableName();
		
		// search the detail information
		List<DpImpCfgDtlVO> cList = dpImpCfgDtlRepository.queryList(id.trim());
		if (null == cList || cList.isEmpty()) {
			cList = null;
		}
		List<DpFileDtl> fList = dpFileDtlRepository.queryList(fileId);
		if (null == fList || fList.isEmpty()) {
			fList = null;
		}
		List<DpTableDtl> tList = dpTableDtlRepository.queryListForName(tableName);
		if (null == tList || tList.isEmpty()) {
			tList = null;
		}
		DpMapDict dpMapDict = new DpMapDict();
		dpMapDict.setGroupCode(CommonConst.DP_MAP_DICT_GROUP_CODE_0001);
		List<DpMapDict> mList = dpMapDictRepository.queryList(dpMapDict);
		if (null == tList || tList.isEmpty()) {
			tList = null;
		}
		
		List<Object> list = new ArrayList<Object>();
		list.add(dpImpCfg);
		list.add(cList);
		list.add(fList);
		list.add(tList);
		list.add(mList);
		
		log.info("[transQueryDetail] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transUpt(com.synesoft.fisp.domain.model.DpImpCfg)
	 */
	@Override
	@Transactional
	public void transUpt(DpImpCfg dpImpCfg) {
		log.info("[transUpt] - start");

		ResultMessages messages = ResultMessages.error();
		
		// remove space
		DpImpCfg cond = new DpImpCfg();
		if (null != dpImpCfg) {
			cond.setProjId(StringUtil.trim(dpImpCfg.getProjId()));
			cond.setBranchId(StringUtil.trim(dpImpCfg.getBranchId()));
			cond.setBatchNo(dpImpCfg.getBatchNo());
			cond.setSeqNo(dpImpCfg.getSeqNo());
		}
		
		// whether or not the configuration is existed
		DpImpCfg result = dpImpCfgRepository.query(dpImpCfg.getImpId());
		if (null == result) {
			log.error("[e.dp.imp.0008] Failed to search any configuration information, param[impid=" 
					+ dpImpCfg.getImpId() + "]");
			messages.add("e.dp.imp.0008", dpImpCfg.getImpId());
			throw new BusinessException(messages);
		}
		
		// search projId + branchId + batchNo + seqNo is existed when any one field was updated.
		if (!result.getProjId().equals(dpImpCfg.getProjId().trim()) 
				|| !result.getBranchId().equals(dpImpCfg.getBranchId().trim()) 
						|| !result.getBatchNo().equals(dpImpCfg.getBatchNo()) 
								|| !result.getSeqNo().equals(dpImpCfg.getSeqNo())) {
			transQueryDpImpCfgLogicKey(cond);
		}
		
		// update a record
		DpImpCfgVO vo = new DpImpCfgVO();
		vo.setImpId(dpImpCfg.getImpId());
		vo.setProjId(cond.getProjId());
		vo.setBranchId(cond.getBranchId());
		vo.setBatchNo(dpImpCfg.getBatchNo());
		vo.setSeqNo(dpImpCfg.getSeqNo());
		vo.setFilePath(StringUtil.trim(dpImpCfg.getFilePath()));
		vo.setMandFlag(StringUtil.trim(dpImpCfg.getMandFlag()));
		vo.setComments(StringUtil.trim(dpImpCfg.getComments()));
		vo.setUpdateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		vo.setUpdateUser(ContextConst.getCurrentUser().getUserid());
		vo.setOldUpdateTime(dpImpCfg.getUpdateTime());
		vo.setOldUpdateUser(dpImpCfg.getUpdateUser());
		
		int re = dpImpCfgRepository.update(vo);
		if (re < 1) {
			log.error("[e.dp.imp.0021] Failed to update the configuration information, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBatchNo() + ", seqno=" + dpImpCfg.getSeqNo() + ", filepath=" 
					+ dpImpCfg.getFilePath() + ", mandflag=" + dpImpCfg.getMandFlag() + ", comments=" 
					+ dpImpCfg.getComments() + "]");
			messages.add("e.dp.imp.0021", dpImpCfg.getBranchId(), dpImpCfg.getProjId(), 
					dpImpCfg.getBatchNo(), dpImpCfg.getSeqNo());
			throw new BusinessException(messages);
		}
		//ContextConst.getMenu("123")
		TlrLogPrint.tlrSysLogPrint("数据导入映射更新", CommonConst.DATA_LOG_OPERTYPE_MODIFY, 
				generateConfigString(result), generateConfigString(vo));
		log.info("Update the configuration information success, param[branchid=" 
					+ dpImpCfg.getBranchId() + ", projid=" + dpImpCfg.getProjId() + ", batchno=" 
					+ dpImpCfg.getBatchNo() + ", seqno=" + dpImpCfg.getSeqNo() + ", filepath=" 
					+ dpImpCfg.getFilePath() + ", mandflag=" + dpImpCfg.getMandFlag() + ", comments=" 
					+ dpImpCfg.getComments() + "]");
		
		log.info("[transUpt] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpImpCfgService#transAddDtl(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional
	public void transAddDtl(String id, List<DpImpCfgDtlVO> list) {
		log.info("[transAdd] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the configuration information
		DpImpCfg dpImpCfg = dpImpCfgRepository.query(id.trim());
		if (null == dpImpCfg) {
			log.error("[e.dp.imp.0008] Failed to search the detail information, param[id=" + id + "]");
			messages.add("e.dp.imp.0008", id);
			throw new BusinessException(messages);
		}
		
		// compare tableSeletedList with fileSelectedList
		int index = validate(dpImpCfg, list);
		if (index != 99) {
			log.error("[e.dp.imp.0024] The length required of element is not meeting request");
			messages.add("e.dp.imp.0024", index);
			throw new BusinessException(messages);
		}
		
		// deleting all records
		int re = dpImpCfgDtlRepository.delete(id);
		// ContextConst.getMenu("123")
		TlrLogPrint.tlrSysLogPrint("数据导入映射字段配置", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				"Total Number:" + re, "");
		log.info("Deleting all detail information, param[numb=" + re + "]");
		
		// parse List, add record
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				DpImpCfgDtl tmp = list.get(i);
	
				DpImpCfgDtl dtl = new DpImpCfgDtl();
				dtl.setImpDtlId(numberService.getSysIDSequence(32));
				dtl.setImpId(id);
				dtl.setFieldName(tmp.getFieldName().trim());
				dtl.setColName(tmp.getColName().trim());
				
				log.info("adding one record, param[fielname=" + tmp.getFieldName() + ", colname=" + tmp.getColName() + "]");
				re = dpImpCfgDtlRepository.insert(dtl);
				if (re < 1) {
					log.error("[e.dp.imp.0025] Failure to adding one record");
					messages.add("e.dp.imp.0025");
					throw new BusinessException(messages);
				}
				//ContextConst.getMenu("123")
				TlrLogPrint.tlrSysLogPrint("数据导入映射新增", CommonConst.DATA_LOG_OPERTYPE_ADD, 
						generateDtlString(dtl), "");
			}   
		}
		log.info("Add the detail information success, result[num=" 
				+ (list == null? 0:list.size()) + "]");
		log.info("[transAdd] - end");
	}

	private String generateConfigString(DpImpCfg dpImpCfg) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.impId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getImpId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.branchId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getBranchId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.batchNo")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getBatchNo()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getFileId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getFileName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.filePath")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getFilePath()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.mandFlag")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getMandFlag()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.projId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getProjId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.comments")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getComments()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getCreateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getCreateUser()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getUpdateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfg.getUpdateUser()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

	private String generateDtlString(DpImpCfgDtl dpImpCfgDtl) {
		MessagesUtil message = MessagesUtil.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.impDtlId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfgDtl.getImpDtlId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.colName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfgDtl.getColName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fieldName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfgDtl.getFieldName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.impId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpImpCfgDtl.getImpId()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

	private Integer validate(DpImpCfg dpImpCfg, List<DpImpCfgDtlVO> list) {
		int index = 99;
		try {
			if (null != list && !list.isEmpty()) {
				List<DpFileDtl> fileList = dpFileDtlRepository.queryList(dpImpCfg.getFileId());
				List<DpTableDtl> tableList = dpTableDtlRepository.queryListForName(dpImpCfg.getTableName());
				Map<String, Short> fields = new HashMap<String, Short>();
				for (int i = 0; i < fileList.size(); i++) {
					DpFileDtl dtl = fileList.get(i);
					fields.put(dtl.getFieldName(), dtl.getFieldLen());
				}
				Map<String, Short> cols = new HashMap<String, Short>();
				for (int i = 0; i < tableList.size(); i++) {
					DpTableDtl dtl = tableList.get(i);
					cols.put(dtl.getColName(), Short.valueOf(dtl.getColLen()));
				}
				
				DpMapDict dpMapDict = new DpMapDict();
				dpMapDict.setGroupCode(CommonConst.DP_MAP_DICT_GROUP_CODE_0001);
				
				log.info("Whether to validate the length required of element meeting request or not");
				for (int i = 0; i < list.size(); i++) {
					DpImpCfgDtl dtl = list.get(i);

					String fieldName = dtl.getFieldName();
					String colName = dtl.getColName();
					if ((null == fieldName || ("").equals(fieldName.trim())) || 
							(null == colName || ("").equals(colName.trim()))) {
						log.error("the number of element in two list is not equal");
						index = i;
						break;
					}
					
					if (!fields.containsKey(fieldName)) {
						dpMapDict.setInVal(fieldName);
						List<DpMapDict> dictlist = dpMapDictRepository.queryList(dpMapDict);
						if (null == dictlist || dictlist.isEmpty()) {
							log.error("the number of element in two list is not equal");
							index = i;
							break;
						}
					} else {
						Short fieldLen = fields.get(fieldName);
						Short colLen = cols.get(colName);
						if (fieldLen > 0 && colLen > 0) {
							if (fieldLen > colLen) {
								log.error("the length required of element in first list is larger than the other, index = " + i);
								index = i;
								break;
							}
						}
					}
				}
			}
			
			return index;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}


}
