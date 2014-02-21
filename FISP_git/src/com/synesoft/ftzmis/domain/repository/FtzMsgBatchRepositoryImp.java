package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzMsgBatch;

@Repository("ftzMsgBatchRepository")
public class FtzMsgBatchRepositoryImp implements FtzMsgBatchRepository {

	// 根据主键查询信息
	public List<FtzMsgBatch> queryFtzMsgBatch(String msg_proc_msgId) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_BATCH + "." + SQLMap.SELECT_PRIMARY_KEY, msg_proc_msgId);
	}
	
	@Resource
	private QueryDAO queryDAO;
}

