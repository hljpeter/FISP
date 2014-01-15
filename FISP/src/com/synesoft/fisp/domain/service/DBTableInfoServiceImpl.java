package com.synesoft.fisp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.DBTableInfo;
import com.synesoft.fisp.domain.model.vo.DBTableInfoVO;
import com.synesoft.fisp.domain.repository.DBTableInfoRepository;

@Service("dbTableInfoService")
public class DBTableInfoServiceImpl implements DBTableInfoService {

	private static final LogUtil log = new LogUtil(DBTableInfoServiceImpl.class);
	
	@Value("#{app['dp.search.default.tablespace']}")
	private String defaultTablespace;
	@Value("#{app['dp.search.owner']}")
	private String owner;
	@Value("#{db['database.username']}")
	private String defaultOwner;
	@Value("#{app['dp.search.tablespace']}")
	private String tablespace;
	@Value("#{app['dp.search.tablename.prefix']}")
	private String tablenamePrefix;
	@Value("#{app['dp.search.tablename.exclude']}")
	private String tablenameExclude;
	
	@Autowired
	protected DBTableInfoRepository dbTableInfoRepository;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.DBTableInfoService#transQueryTableList()
	 */
	@Override
	public List<DBTableInfo> transQueryTableList() {
		log.info("[transQueryTableList] - start");

		DBTableInfo info = generateCond();
		
		List<DBTableInfo> list = dbTableInfoRepository.queryTableList(info);
				
		log.info("[transQueryTableList] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.DBTableInfoService#transQueryTableColList(java.lang.String)
	 */
	@Override
	public List<DBTableInfoVO> transQueryTableColList(String tableName) {
		log.info("[transQueryTableColList] - start");

		ResultMessages messages = ResultMessages.error();
				
		// search the columns information of table
		List<DBTableInfoVO> list = dbTableInfoRepository.queryColumnList(tableName);
		if (null == list || list.isEmpty()) {
			log.error("[e.dp.table.0032] Cannot find any record, param[tablename=" + tableName + "]");
			messages.add("e.dp.table.0032", tableName);
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryTableColList] - end");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.DBTableInfoService#transQueryTable(java.lang.String)
	 */
	@Override
	public DBTableInfo transQueryTable(String tableName) {
		log.info("[transQueryTable] - start");

		ResultMessages messages = ResultMessages.error();
				
		// search the columns information of table
		DBTableInfo dbTableInfo = dbTableInfoRepository.query(tableName);
		if (null == dbTableInfo) {
			log.error("[e.dp.table.0032] Cannot find any record, param[tablename=" + tableName + "]");
			messages.add("e.dp.table.0032", tableName);
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryTable] - end");
		return dbTableInfo;
	}

	private DBTableInfo generateCond() {
		DBTableInfo info = new DBTableInfo();
		if (StringUtil.isNotTrimEmpty(owner)) {
			info.setOwners(owner.split(","));
		} else {
			info.setOwner(defaultOwner.toUpperCase());
		}
		
		if (StringUtil.isNotTrimEmpty(tablenameExclude)) {
			info.setTableNameExcludes(tablenameExclude.split(","));
		}
		
		if (StringUtil.isNotTrimEmpty(tablenamePrefix)) {
			// search by table name and tablespace
			if (StringUtil.isNotTrimEmpty(tablespace)) {
				info.setType(CommonConst.DB_TYPE_TABLESPACE_AND_TABLENAME);
				String[] names = tablenamePrefix.split(",");
				String[] spaces = tablespace.split(",");
				String sql = "";
				String tableSql = "";
				for (int i = 0; i < names.length; i++) {
					tableSql = tableSql + ("ALL_TABLES.TABLE_NAME LIKE '" + names[i] + "%'");
					if (i != names.length - 1) {
						tableSql = tableSql + " OR "; 
					}
				}
				tableSql = "(" + tableSql + ")";
				for (int i = 0; i < spaces.length; i++) {
					sql = sql + ("(ALL_TABLES.TABLESPACE_NAME = '" + spaces[i] + "' AND " + tableSql + ")");
					if (i != spaces.length - 1) {
						sql = sql + " OR "; 
					}
				}
				info.setTableSpaceName(sql);
				
			// only search by table name
			} else {
				info.setType(CommonConst.DB_TYPE_TABLENAME);
				info.setTableNameStarts(tablenamePrefix.split(","));
			}
			
		// only search by tablespace
		} else if (StringUtil.isNotTrimEmpty(tablespace)) {
			info.setType(CommonConst.DB_TYPE_TABLESPACE);
			info.setTablespaceNames(tablespace.split(","));
			
		// only search by default tablespace
		} else if (StringUtil.isNotTrimEmpty(defaultTablespace)) {
			info.setType(CommonConst.DB_TYPE_DEFAULT_TABLESPACE);
			info.setTableSpaceName(defaultTablespace);
		}
		
		//if (StringUtil.isNotTrimEmpty(info.getType())) {
			return info;
		//} else {
		//	return null;
		//}
	}
	
}
