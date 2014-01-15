package com.synesoft.fisp.domain.service.dp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DpTableVO;
import com.synesoft.fisp.domain.repository.DBTableInfoRepository;
import com.synesoft.fisp.domain.repository.dp.DpImpCfgRepository;
import com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository;
import com.synesoft.fisp.domain.repository.dp.DpTableRepository;
import com.synesoft.fisp.domain.service.NumberService;

/**
 * 表定义ServiceImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Service("dpTableService")
public class DpTableServiceImpl implements DpTableService {

	private static final LogUtil log = new LogUtil(DpTableServiceImpl.class);
	
	@Autowired
	protected DpTableRepository dpTableRepository;
	@Autowired
	protected DpTableDtlRepository dpTableDtlRepository;
	@Autowired
	protected DpImpCfgRepository dpImpCfgRepository;
	@Autowired
	protected DBTableInfoRepository dbTableInfoRepository;
	
	@Autowired
	protected NumberService numberService;

	private MessagesUtil message = MessagesUtil.getInstance();
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transQueryDpTable(String)
	 */
	@Override
	public DpTable transQueryDpTable(String id) {
		log.info("[transQueryDpTable] - start");
		
		DpTable DpTable = dpTableRepository.query(id);
		
		log.info("[transQueryDpTable] - end");
		return DpTable;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transQueryDpTablePage(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public Page<DpTable> transQueryDpTablePage(Pageable pageable, DpTable DpTable) {
		log.info("[transQueryDpTablePage] - start");

		ResultMessages messages = ResultMessages.error();
		
		log.info("searching record, param[tablename=" + (DpTable == null? "":DpTable.getTableName()) 
				+ ", tabledesc=" + (DpTable == null? "":DpTable.getTableDesc()) 
				+ ", pagenum=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");
		
		// remove space
		DpTable cond = new DpTable();
		if (null != DpTable) {
			cond.setTableName(DpTable.getTableName() == null? "":DpTable.getTableName().trim());
			cond.setTableDesc(DpTable.getTableDesc() == null? "":DpTable.getTableDesc().trim());
		}
		
		Page<DpTable> page = dpTableRepository.queryList(pageable, DpTable);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] Failed to search records");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpTablePage] - end");
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transQueryDpTableList(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	public List<DpTable> transQueryDpTableList(DpTable dpTable) {
		log.info("[transQueryDpTableList] - start");
		
		List<DpTable> list = dpTableRepository.queryList(dpTable);
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		log.info("[transQueryDpTableList] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transQueryDetail(org.springframework.data.domain.Pageable, java.lang.String)
	 */
	@Override
	public List<Object> transQueryDetail(Pageable pageable, String id) {
		log.info("[transQueryDetail] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the table definition information
		DpTable dpTable = dpTableRepository.query(id);
		if (null == dpTable) {
			log.error("[e.dp.table.0029] Cannot find any record, param[id=" + id + "]");
			messages.add("e.dp.table.0029", id);
			throw new BusinessException(messages);
		}

		// search the detail information
		Page<DpTableDtl> page = dpTableDtlRepository.queryPage(pageable, id);
		
		List<Object> result = new ArrayList<Object>();
		result.add(dpTable);
		result.add(page);
				
		log.info("[transQueryDetail] - end");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transQueryDetailForUpt(java.lang.String)
	 */
	@Override
	public List<Object> transQueryDetailForUpt(String id) {

		log.info("[transQueryDetail] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search the table definition information
		DpTable dpTable = dpTableRepository.query(id);
		if (null == dpTable) {
			log.error("[e.dp.table.0029] Cannot find any record, param[id=" + id + "]");
			messages.add("e.dp.table.0029", id);
			throw new BusinessException(messages);
		}

		// whether or not be used
		DpImpCfg cfg = new DpImpCfg();
		cfg.setTableName(dpTable.getTableName());
		List<DpImpCfg> cfgList = dpImpCfgRepository.queryList(cfg);
		Boolean flag = true;
		if (null != cfgList && cfgList.size() > 0) {
//			log.error("[e.dp.table.0037] The table definition was used, param[tablename=" + dpTable.getTableName() + "]");
//			messages.add("e.dp.table.0037", dpTable.getTableName());
//			throw new BusinessException(messages);
			flag = false;
		}
		
		// search the detail information
		List<DpTableDtl> list = dpTableDtlRepository.queryList(id);
		
		List<Object> result = new ArrayList<Object>();
		result.add(dpTable);
		result.add(list);
		result.add(flag);
				
		log.info("[transQueryDetail] - end");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transDel(com.synesoft.fisp.domain.model.DpTable)
	 */
	@Override
	@Transactional
	public void transDel(DpTable dpTable) {
		log.info("[transDel] - start");

		ResultMessages messages = ResultMessages.error();
		
		// search table
		DpTable result = dpTableRepository.query(dpTable.getTableId());
		if (null == result) {
			log.error("[e.dp.table.0029] Cannot find the table definition information, param[tableid=" 
					+ dpTable.getTableId() + "]");
			messages.add("e.dp.table.0029", dpTable.getTableId());
			throw new BusinessException(messages);
		}
		
		// whether or not the table definition is used
		DpImpCfg dpImpCfg = new DpImpCfg();
		dpImpCfg.setTableName(result.getTableName());
		List<DpImpCfg> list = dpImpCfgRepository.queryList(dpImpCfg);
		if (null != list && !list.isEmpty()) {
			log.error("[e.dp.table.0053] The table definition information is used, cannot be deleted, param[tablename=" + result.getTableName() + "]");
			messages.add("e.dp.table.0053", result.getTableName());
			throw new BusinessException(messages);
		}
		
		// deleting the table detail information
		int re = dpTableDtlRepository.delete(result.getTableId());
		// ContextConst.getMenu("123")
		TlrLogPrint.tlrSysLogPrint("表定义维护", CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				"Total Number" + re, "");
		log.info("Deleting the table detail information success, result[num=" + re + "]");
		
		// deleting the table definition information
		re = dpTableRepository.delete(dpTable);
		if (1 != re) {
			log.error("[e.dp.table.0004] Failed to delete record, param[id=" + dpTable.getTableId() + "]");
			messages.add("e.dp.table.0004", result.getTableName());
			throw new BusinessException(messages);
		}
		TlrLogPrint.tlrSysLogPrint(ContextConst.getMenu("123"), CommonConst.DATA_LOG_OPERTYPE_DELETE, 
				generateTableString(result), "");
		log.info("Deleting the table definition information success, param[id=" + dpTable.getTableId() + "]");
		
		log.info("[transDel] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transAdd(com.synesoft.fisp.domain.model.DpTable, java.util.List)
	 */
	@Override
	@Transactional
	public void transAdd(DpTable dpTable, List<DpTableDtl> list) {
		log.info("[transAdd] - start");

		ResultMessages messages = ResultMessages.error();
		
		DpTable cond = new DpTable();
		cond.setTableName(StringUtils.trim(dpTable.getTableName()));
		
		// search table
		DpTable result = dpTableRepository.queryForLogicKey(cond.getTableName());
		if (null != result) {
			log.error("[e.dp.table.0033] The table definition has been existed, param[tablename=" + dpTable.getTableName() + "]");
			messages.add("e.dp.table.0033", dpTable.getTableName());
			throw new BusinessException(messages);
		}
		
		cond.setTableDesc(StringUtils.trim(dpTable.getTableDesc()));
		cond.setComments(StringUtils.trim(dpTable.getComments()));
		cond.setTableId(numberService.getSysIDSequence(32));
		cond.setCreateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		cond.setCreateUser(ContextConst.getCurrentUser().getUserid());
		
		// adding table
		int re = dpTableRepository.insert(cond);
		if (1 != re) {
			log.error("[e.dp.table.0034] Failed to add record, param[tablename=" + dpTable.getTableName() 
					+ ", tabledesc=" + dpTable.getTableDesc() + ", comments=" + dpTable.getComments() + "]");
			messages.add("e.dp.table.0034", dpTable.getTableName());
			throw new BusinessException(messages);
		}
		//ContextConst.getMenu("123")
		TlrLogPrint.tlrSysLogPrint("表定义维护", CommonConst.DATA_LOG_OPERTYPE_ADD, 
				"", generateTableString(cond));
		log.info("Add the table definition information success, param[tablename=" + dpTable.getTableName() + "]");

		// adding table detail
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				DpTableDtl dpTableDtl = list.get(i);
				
				DpTableDtl dtl = new DpTableDtl();
				dtl.setColId(numberService.getSysIDSequence(32));
				dtl.setTableId(cond.getTableId());
				dtl.setColName(StringUtils.trim(dpTableDtl.getColName()));
				dtl.setColType(StringUtils.trim(dpTableDtl.getColType()));
				dtl.setColDesc(StringUtils.trim(dpTableDtl.getColDesc()));
				dtl.setColLen(StringUtils.trim(dpTableDtl.getColLen()));
				dtl.setColSeqNo(dpTableDtl.getColSeqNo());

				re = dpTableDtlRepository.insert(dtl);
				if (1 != re) {
					log.error("[e.dp.table.0035] Failed to add record, param[colname=" + dpTableDtl.getColName() 
							+ ", coltype=" + dpTableDtl.getColType() + ", coldesc=" + dpTableDtl.getColDesc() 
							+ ", collen=" + dpTableDtl.getColLen() + ", colseqno=" + dpTableDtl.getColSeqNo() 
							+ "]");
					messages.add("e.dp.table.0035", dtl.getColName());
					throw new BusinessException(messages);
				}
				//ContextConst.getMenu("123")
				TlrLogPrint.tlrSysLogPrint("表定义维护", CommonConst.DATA_LOG_OPERTYPE_ADD, 
						"", generateDtlString(dtl));
			}
		}
		log.info("Add the table detail information success, param[num=" + (list == null? 0:list.size()) + "]");
		
		log.info("[transAdd] - end");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableService#transUpt(com.synesoft.fisp.domain.model.DpTable, java.util.List)
	 */
	@Override
	@Transactional
	public void transUpt(DpTable dpTable, List<DpTableDtl> list) {
		log.info("[transUpt] - start");
		
		ResultMessages messages = ResultMessages.error();
		
		DpTableVO cond = new DpTableVO();
		cond.setTableId(StringUtils.trim(dpTable.getTableId()));
		
		// whether or not the table definition is existed
		DpTable result = dpTableRepository.query(cond.getTableId());
		if (null == result) {
			log.error("[e.dp.table.0029] Failed to search the table definition, param[tablename=" + dpTable.getTableId() + "]");
			messages.add("e.dp.table.0029", dpTable.getTableId());
			throw new BusinessException(messages);
		}
		
		// whether or not the table structure in database is existed
		DBTableInfo dbTableInfo = dbTableInfoRepository.query(result.getTableName());
		if (null == dbTableInfo) {
			log.error("[e.dp.table.0032] Cannot find any record, param[tablename=" + result.getTableName() + "]");
			messages.add("e.dp.table.0032", result.getTableName());
			throw new BusinessException(messages);
		}
		
		// whether or not the table definition is used
		DpImpCfg cfg = new DpImpCfg();
		cfg.setTableName(dpTable.getTableName());
		List<DpImpCfg> cfgList = dpImpCfgRepository.queryList(cfg);
		Boolean flag = (null != cfgList && cfgList.size() > 0);
		
		cond.setTableDesc(StringUtils.trim(dpTable.getTableDesc()));
		cond.setComments(StringUtils.trim(dpTable.getComments()));
		cond.setUpdateTime(DateUtil.getNow("yyyyMMddhhmmss"));
		cond.setUpdateUser(ContextConst.getCurrentUser().getUserid());
		cond.setOldUpdateTime(StringUtils.trim(dpTable.getUpdateTime()));
		cond.setOldUpdateUser(StringUtils.trim(dpTable.getUpdateUser()));
		
		// updating table
		int re = dpTableRepository.update(cond);
		if (1 != re) {
			log.error("[e.dp.table.0038] Failed to update record, param[tablename=" + dpTable.getTableName() 
					+ ", tabledesc=" + dpTable.getTableDesc() + ", comments=" + dpTable.getComments() + "]");
			messages.add("e.dp.table.0038", dpTable.getTableName());
			throw new BusinessException(messages);
		}
		TlrLogPrint.tlrSysLogPrint(ContextConst.getMenu("123"), CommonConst.DATA_LOG_OPERTYPE_MODIFY, 
				generateTableString(result), generateTableString(cond));
		log.info("Updating the table definition information success, param[tablename=" + dpTable.getTableName() 
					+ ", tabledesc=" + dpTable.getTableDesc() + ", comments=" + dpTable.getComments() + "]");

		if (!flag) {
			// update table detail
			// first, deleting the table detail information
			re = dpTableDtlRepository.delete(cond.getTableId());
			TlrLogPrint.tlrSysLogPrint(ContextConst.getMenu("123"), CommonConst.DATA_LOG_OPERTYPE_DELETE, 
					"Total Number:" + re, "");
			log.info("Deleting the table detail information, result[num=" + re + "]");
	
			// second, adding the table detail information
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					DpTableDtl dpTableDtl = list.get(i);
					
					DpTableDtl dtl = new DpTableDtl();
					dtl.setColId(numberService.getSysIDSequence(32));
					dtl.setTableId(cond.getTableId());
					dtl.setColName(StringUtils.trim(dpTableDtl.getColName()));
					dtl.setColType(StringUtils.trim(dpTableDtl.getColType()));
					dtl.setColDesc(StringUtils.trim(dpTableDtl.getColDesc()));
					dtl.setColLen(StringUtils.trim(dpTableDtl.getColLen()));
					dtl.setColSeqNo(dpTableDtl.getColSeqNo());
	
					re = dpTableDtlRepository.insert(dtl);
					if (1 != re) {
						log.error("[e.dp.table.0035] Failed to add record, param[colname=" + dpTableDtl.getColName() 
								+ ", coltype=" + dpTableDtl.getColType() + ", coldesc=" + dpTableDtl.getColDesc() 
								+ ", collen=" + dpTableDtl.getColLen() + ", colseqno=" + dpTableDtl.getColSeqNo() 
								+ "]");
						messages.add("e.dp.table.0035", dtl.getColName());
						throw new BusinessException(messages);
					}
					TlrLogPrint.tlrSysLogPrint(ContextConst.getMenu("123"), CommonConst.DATA_LOG_OPERTYPE_ADD, 
							generateDtlString(dtl), "");
				}
			}
			log.info("Add the table detail information success, result[num=" + (list == null? 0:list.size()) + "]");
		} else {
			log.info("The table definition is used, cannot update the table detail information.");
		}
		
		log.info("[transUpt] - end");
	}

	private String generateTableString(DpTable dpTable) {
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.tableId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getTableId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.tableName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getTableName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.tableDesc")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getTableDesc()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.comments")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getComments()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getCreateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.createUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getCreateUser()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateTime")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getUpdateTime()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.updateUser")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTable.getUpdateUser()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

	private String generateDtlString(DpTableDtl dpTableDtl) {
		MessagesUtil message = MessagesUtil.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(message.getMessage("fisp.label.common.colId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.tableId")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getTableId()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.colDesc")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColName()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.colName")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColDesc()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.cutFlag")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColLen()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.comments")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColType()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.colLen")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getComments()).append(CommonConst.SEPARATE_TWO_FIELD)
			.append(message.getMessage("fisp.label.common.colSeqNo")).append(CommonConst.SEPARATE_KEY_VALUE)
			.append(dpTableDtl.getColSeqNo()).append(CommonConst.SEPARATE_TWO_FIELD);
		
		return sb.toString();
	}

}
