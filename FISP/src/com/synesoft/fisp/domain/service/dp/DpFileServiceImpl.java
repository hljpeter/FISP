package com.synesoft.fisp.domain.service.dp;

import java.util.ArrayList;
import java.util.List;

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
import com.synesoft.fisp.app.common.validation.FileNameRuleValidator;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.vo.DpFileVO;
import com.synesoft.fisp.domain.repository.dp.DpFileDtlRepository;
import com.synesoft.fisp.domain.repository.dp.DpFileRepository;
import com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * 文件定义ServiceImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Service("dpFileService")
public class DpFileServiceImpl implements DpFileService {

	private static final LogUtil log = new LogUtil(DpFileServiceImpl.class);
	
	@Autowired
	protected DpFileRepository dpFileRepository;
	@Autowired
	protected DpImpCfgRepository dpImpCfgRepository;
	@Autowired
	protected DpFileDtlRepository dpFileDtlRepository;
	@Autowired
	protected NumberService numberService;
	
	private MessagesUtil message = MessagesUtil.getInstance();
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDpFile(String)
	 */
	@Override
	public DpFile transQueryDpFile(String id) {
		log.info("[transQueryDpFile] - start");
		
		DpFile DpFile = dpFileRepository.query(id);
		
		log.info("[transQueryDpFile] - end");
		return DpFile;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDpFilePage(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public Page<DpFile> transQueryDpFilePage(Pageable pageable, DpFile dpFile) {
		log.info("[transQueryDpFilePage] - start");

		ResultMessages messages = ResultMessages.error();
		
		log.info("searching record, param[filename=" + (dpFile == null? "":dpFile.getFileName()) 
				+ ", filetype=" + (dpFile == null? "":dpFile.getFileType()) 
				+ ", ioflag=" + (dpFile == null? "":dpFile.getIoFlag()) 
				+ ", pagenum=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");
		
		// remove space
		DpFile cond = new DpFile();
		if (null != dpFile) {
			cond.setFileType(dpFile.getFileType() == null? "":dpFile.getFileType().trim());
			cond.setFileName(dpFile.getFileName() == null? "":dpFile.getFileName().trim());
			cond.setIoFlag(dpFile.getIoFlag() == null? "":dpFile.getIoFlag().trim());
		}
		
		Page<DpFile> page = dpFileRepository.queryList(pageable, dpFile);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] Failed to search records");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpFilePage] - end");
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDpFileList(com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public List<DpFile> transQueryDpFileList(DpFile dpFile) {
		log.info("[transQueryDpFileList] - start");
		
		List<DpFile> list = dpFileRepository.queryList(dpFile);
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		log.info("[transQueryDpFileList] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDpFileNameList(java.lang.String)
	 */
	@Override
	public List<String> transQueryDpFileNameList(String name) {
		log.info("[transQueryDpFileNameList] - start");
		
		List<String> list = dpFileRepository.queryNameList(name);
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		log.info("[transQueryDpFileNameList] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDetail(java.lang.String)
	 */
	@Override
	public List<Object> transQueryDetail(String id) {
		log.info("[transQueryDetail] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the file definition information
		DpFile dpFile = dpFileRepository.query(id);
		if (null == dpFile) {
			log.error("[e.dp.file.0042] Cannot search the file definition information, param[fileid=" + id + "]");
			messages.add("e.dp.file.0042", id);
			throw new BusinessException(messages);
		}
		
		// search the file detail information
		List<DpFileDtl> list = dpFileDtlRepository.queryList(id);

		List<Object> result = new ArrayList<Object>();
		result.add(dpFile);
		result.add(list);
		
		log.info("[transQueryDetail] - end");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transQueryDetailForUpt(java.lang.String)
	 */
	@Override
	public List<Object> transQueryDetailForUpt(String id) {
		log.info("[transQueryDetailForUpt] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the file definition information
		DpFile dpFile = dpFileRepository.query(id);
		if (null == dpFile) {
			log.error("[e.dp.file.0042] Cannot search the file definition information, param[fileid=" + id + "]");
			messages.add("e.dp.file.0042", id);
			throw new BusinessException(messages);
		}

		DpImpCfg dpImpCfg = new DpImpCfg();
		dpImpCfg.setFileId(dpFile.getFileId());
		List<DpImpCfg> cfgList = dpImpCfgRepository.queryList(dpImpCfg);
		
		// search the file detail information
		List<DpFileDtl> list = dpFileDtlRepository.queryList(id);

		List<Object> result = new ArrayList<Object>();
		result.add(dpFile);
		result.add(list);
		result.add((cfgList == null || cfgList.isEmpty()));
		
		log.info("[transQueryDetailForUpt] - end");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transDel(com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	@Transactional
	public void transDel(DpFile dpFile) {
		log.info("[transDel] - start");

		ResultMessages messages = ResultMessages.error();

		// search the file is existed
		DpFile result = dpFileRepository.query(dpFile.getFileId());
		if (null == result) {
			log.error("[e.dp.file.0042] Cannot find the file definition information, param[fileid=" + dpFile.getFileId() + "]");
			messages.add("e.dp.file.0042", dpFile.getFileId());
			throw new BusinessException(messages);
		}
		
		// whether or not file be used
		DpImpCfg dpImpCfg = new DpImpCfg();
		dpImpCfg.setFileId(dpFile.getFileId());
		List<DpImpCfg> list = dpImpCfgRepository.queryList(dpImpCfg);
		if (null != list && !list.isEmpty()) {
			log.error("[e.dp.file.0041] The file definition was been used, param[filename=" + result.getFileName() + "]");
			messages.add("e.dp.file.0041", dpFile.getFileName());
			throw new BusinessException(messages);
		}
		
		// deleting the file detail information
		int re = dpFileDtlRepository.delete(dpFile.getFileId());
		// "DP_File_Qry"
		TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				"Total Number:" + re, "");
		log.info("Deleting the file detail informaiton success. result[num=" + re + "]");
		
		// deleting the file definition information
		re = dpFileRepository.delete(dpFile);
		if (1 != re) {
			log.error("[e.dp.file.0005] Failed to delete record, param[id=" + dpFile.getFileId() + "]");
			messages.add("e.dp.file.0005", dpFile.getFileId());
			throw new BusinessException(messages);
		}
		//"DP_File_Qry"
		TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				generateFileString(result), "");
		log.info("Deleting the file definition information success. param[filename=" 
				+ result.getFileName() + "]");
		
		log.info("[transDel] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transAdd(com.synesoft.fisp.domain.model.DpFile, java.util.List)
	 */
	@Override
	@Transactional
	public void transAdd(DpFile dpFile, List<DpFileDtl> list) {
		log.info("[transAdd] - start");

		ResultMessages messages = ResultMessages.error();

		// check
		validateFileType(messages, dpFile);

		// validate file name rule
		if (!FileNameRuleValidator.checkName(dpFile.getFileName())) {
			log.error("[e.dp.file.0084] The file name is not requirement, param[filename=" + dpFile.getFileName() + "]");
			messages.add("e.dp.file.0084");
			throw new BusinessException(messages);
		}
		
		// search file name is existed
		DpFile result = dpFileRepository.queryForLogicKey(dpFile.getFileName());
		if (null != result) {
			log.error("[e.dp.file.0044] The file name is existed, param[filename=" + dpFile.getFileName() + "]");
			messages.add("e.dp.file.0044", dpFile.getFileName());
			throw new BusinessException(messages);
		}
		
		// add the file definition
		DpFile cond = new DpFile();
		cond.setFileId(numberService.getSysIDSequence(32));
		cond.setFileName(StringUtil.trim(dpFile.getFileName()));
		cond.setFileEncoding(StringUtil.trim(dpFile.getFileEncoding()));
		cond.setFileType(StringUtil.trim(dpFile.getFileType()));
		cond.setCommitCount(dpFile.getCommitCount());
		cond.setComments(StringUtil.trim(dpFile.getComments()));
		cond.setCutFlag(StringUtil.trim(dpFile.getCutFlag()));
		cond.setDelimiter(StringUtil.trim(dpFile.getDelimiter()));
		cond.setEndColumn(dpFile.getEndColumn());
		cond.setErowIgnrNum(dpFile.getErowIgnrNum());
		cond.setSheetNo(dpFile.getSheetNo());
		cond.setSrowIgnrNum(dpFile.getSrowIgnrNum());
		cond.setStartColumn(dpFile.getStartColumn());
		cond.setFixedLenCfg(StringUtil.trim(dpFile.getFixedLenCfg()));
		cond.setIoFlag(StringUtil.trim(dpFile.getIoFlag()));
		cond.setCreateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		cond.setCreateUser(ContextConst.getCurrentUser().getUserid());
		
		int re = dpFileRepository.insert(cond);
		if (1 != re) {
			log.error("[e.dp.file.0045] Failed to add record, param[filename=" + dpFile.getFileName() + "]");
			messages.add("e.dp.file.0045", dpFile.getFileName());
			throw new BusinessException(messages);
		}
		TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_ADD, 
				"", generateFileString(cond));
		log.info("Add the file definition information success, result[filename=" + dpFile.getFileName() + "]");
		
		// add the file detail information
		transUptCfg(cond.getFileId(), list);
		log.info("Add the file detail information success, result[num=" + (list == null? 0:list.size()) + "]");
		
		
		log.info("[transAdd] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpFileService#transUpt(com.synesoft.fisp.domain.model.DpFile, java.util.List)
	 */
	@Override
	@Transactional
	public void transUpt(DpFile dpFile, List<DpFileDtl> list) {
		log.info("[transUpt] - start");

		ResultMessages messages = ResultMessages.error();
		
		// check
		validateFileType(messages, dpFile);
		
		// search file is existed
		DpFile result = dpFileRepository.query(dpFile.getFileId());
		if (null == result) {
			log.error("[e.dp.file.0042] The file is not existed, param[filename=" + dpFile.getFileId() + "]");
			messages.add("e.dp.file.0042", dpFile.getFileId());
			throw new BusinessException(messages);
		}
		
		// whether or not file be used
		DpImpCfg dpImpCfg = new DpImpCfg();
		dpImpCfg.setFileId(dpFile.getFileId());
		List<DpImpCfg> dpImpCfgList = dpImpCfgRepository.queryList(dpImpCfg);
		if (null != dpImpCfgList && !dpImpCfgList.isEmpty()) {
			if (!result.getFileName().equals(dpFile.getFileName())) {
				log.error("[e.dp.file.0041] The file definition was been used, param[filename=" + result.getFileName() + "]");
				messages.add("e.dp.file.0041", dpFile.getFileName());
				throw new BusinessException(messages);
			}
		}
		
		// add the file definition
		DpFileVO cond = new DpFileVO();
		cond.setFileId(StringUtil.trim(dpFile.getFileId()));
		cond.setFileName(StringUtil.trim(dpFile.getFileName()));
		cond.setFileEncoding(StringUtil.trim(dpFile.getFileEncoding()));
		cond.setFileType(StringUtil.trim(dpFile.getFileType()));
		cond.setCommitCount(dpFile.getCommitCount());
		cond.setComments(StringUtil.trim(dpFile.getComments()));
		cond.setCutFlag(StringUtil.trim(dpFile.getCutFlag()));
		cond.setDelimiter(StringUtil.trim(dpFile.getDelimiter()));
		cond.setEndColumn(dpFile.getEndColumn());
		cond.setErowIgnrNum(dpFile.getErowIgnrNum());
		cond.setSheetNo(dpFile.getSheetNo());
		cond.setSrowIgnrNum(dpFile.getSrowIgnrNum());
		cond.setStartColumn(dpFile.getStartColumn());
		cond.setFixedLenCfg(StringUtil.trim(dpFile.getFixedLenCfg()));
		cond.setIoFlag(StringUtil.trim(dpFile.getIoFlag()));
		cond.setUpdateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		cond.setUpdateUser(ContextConst.getCurrentUser().getUserid());
		cond.setOldUpdateTime(StringUtil.trim(dpFile.getUpdateTime()));
		cond.setOldUpdateUser(StringUtil.trim(dpFile.getUpdateUser()));
		
		int re = dpFileRepository.update(cond);
		if (1 != re) {
			log.error("[e.dp.file.0048] Failed to update record, param[filename=" + dpFile.getFileName() + "]");
			messages.add("e.dp.file.0048");
			throw new BusinessException(messages);
		}
		TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_MODIFY, 
				generateFileString(result), generateFileString(cond));
		log.info("Update the file definition information success, result[filename=" + dpFile.getFileName() + "]");
		
		// update the file detail information
		transUptCfg(dpFile.getFileId(), list);
		
		log.info("[transUpt] - end");
	}

	private void transUptCfg(String fileId, List<DpFileDtl> list) {
		log.info("[transUptCfg] - start");

		ResultMessages messages = ResultMessages.error();

		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				DpFileDtl dpFileDtl = list.get(i);
				for (int j = i + 1; j < list.size(); j++) {
					DpFileDtl dpFileDtl2 = list.get(j);
					if (StringUtil.trim(dpFileDtl.getFieldName())
							.equals(StringUtil.trim(dpFileDtl2.getFieldName()))) {
						log.error("[e.dp.file.0092] FieldName cannot be equals");
						messages.add("e.dp.file.0092");
						throw new BusinessException(messages);
					}
				}
			}
		}
		
		// delete all records
		int re = dpFileDtlRepository.delete(fileId);
		TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				"Total Number:" + re, "");
		log.info("Deleting the file detail information success, result[num=" + re + "]");
		
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				DpFileDtl dpFileDtl = list.get(i);
				
				if (!StringUtil.isNotTrimEmpty(dpFileDtl.getFieldName())) {
					log.error("[e.dp.file.0060] FieldName cannot be empty");
					messages.add("e.dp.file.0060");
					throw new BusinessException(messages);
				}
				if (null == dpFileDtl.getFieldLen()) {
					log.error("[e.dp.file.0061] FieldLen cannot be empty");
					messages.add("e.dp.file.0061");
					throw new BusinessException(messages);
				}
				if (!StringUtil.isNotTrimEmpty(dpFileDtl.getCutFlag())) {
					log.error("[e.dp.file.0062] CutFlag cannot be empty");
					messages.add("e.dp.file.0062");
					throw new BusinessException(messages);
				}
				
				DpFileDtl dtl = new DpFileDtl();
				dtl.setFieldId(numberService.getSysIDSequence(32));
				dtl.setFieldName(StringUtil.trim(dpFileDtl.getFieldName()));
				dtl.setFieldDesc(StringUtil.trim(dpFileDtl.getFieldDesc()));
				dtl.setComments(StringUtil.trim(dpFileDtl.getComments()));
				dtl.setCutFlag(StringUtil.trim(dpFileDtl.getCutFlag()));
				dtl.setFieldLen(dpFileDtl.getFieldLen());
				// add by wy
				Short s;
				s=Short.valueOf(Integer.toString(i));
				s=(short) (s+1);
				dtl.setFieldSeqNo(s);
				dtl.setFileId(fileId);
				
				re = dpFileDtlRepository.insert(dtl);
				if (1 != re) {
					log.error("[e.dp.file.0046] Failed to add record, param[fieldname=" + dtl.getFieldName() + "]");
					messages.add("e.dp.file.0046", dtl.getFieldName());
					throw new BusinessException(messages);
				}

				TlrLogPrint.tlrSysLogPrint("DP_File_Qry", CommonConst.DATA_LOG_OPERTYPE_ADD, 
						"", generateDtlString(dtl));
			}
		}
		
		log.info("Add the detail information success, result[num=" + (list==null? 0:list.size()) + "]");
		log.info("[transUptCfg] - end");
	}

	/**
	 * 根据文件类型，校验必填项是否为空，并对不需要输入的项清空
	 * @param dpFile
	 * @return
	 * 		DpFile	去除空后的对象
	 */
	private void validateFileType(ResultMessages messages, DpFile dpFile) {
		if (null != dpFile) {
			int i = Integer.parseInt(dpFile.getFileType());
			switch(i) {
			case 1:
				if (StringUtil.isNotTrimEmpty(dpFile.getFixedLenCfg())) {
					dpFile.setSheetNo(null);
					dpFile.setStartColumn(null);
					dpFile.setEndColumn(null);
					dpFile.setDelimiter(null);
				} else {
					messages.add("e.dp.file.0090", dpFile.getFileId());
					throw new BusinessException(messages);
				}
				break;
			case 2:
				if (StringUtil.isNotTrimEmpty(dpFile.getDelimiter())) {
					dpFile.setSheetNo(null);
					dpFile.setStartColumn(null);
					dpFile.setEndColumn(null);
					dpFile.setFixedLenCfg(null);
				} else {
					messages.add("e.dp.file.0090", dpFile.getFileId());
					throw new BusinessException(messages);
				}
				break;
			case 3:
				dpFile.setSheetNo(null);
				dpFile.setStartColumn(null);
				dpFile.setEndColumn(null);
				dpFile.setFixedLenCfg(null);
				dpFile.setDelimiter(null);
				break;
			case 4:
				dpFile.setFixedLenCfg(null);
				dpFile.setDelimiter(null);
				break;
			}
		}
	}
	
	private String generateFileString(DpFile dpFile) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.fileId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getFileId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getFileName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileType")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getFileType()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileEncoding")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getFileEncoding()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fixLenCfg")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getFixedLenCfg()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.delimiter")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getDelimiter()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.ioFlag")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getIoFlag()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.cutFlag")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getCutFlag()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.commitCount")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getCommitCount()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.endColumn")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getEndColumn()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.erowIgnrNum")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getErowIgnrNum()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.sheetno")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getSheetNo()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.srowIgnrNum")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getSrowIgnrNum()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.startColumn")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getStartColumn()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.comments")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getComments()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getCreateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getCreateUser()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getUpdateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFile.getUpdateUser()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

	private String generateDtlString(DpFileDtl dpFileDtl) {
		MessagesUtil message = MessagesUtil.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.fileId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFieldId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fileId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFileId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fieldDesc")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFieldDesc()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fieldName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFieldName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.cutFlag")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getCutFlag()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.comments")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getComments()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fieldLen")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFieldLen()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.fieldSeqNo")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpFileDtl.getFieldSeqNo()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

}
