package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpMapDict;

@Repository
public class DpMapDictRepositoryImpl implements DpMapDictRepository {

	private static final LogUtil log = new LogUtil(DpMapDictRepositoryImpl.class);
	
	@Resource
	private QueryDAO queryDAO;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DP_Map_DictRepository#queryList(com.synesoft.fisp.domain.model.DpMapDict)
	 */
	@Override
	public List<DpMapDict> queryList(DpMapDict dpMapDict) {
		log.info("[queryList] - query all records");
		return queryDAO.executeForObjectList(Table.DP_MAP_DICT + "." + SQLMap.SELECT_QUERY_LIST, dpMapDict);
	}
	
	
	
}
