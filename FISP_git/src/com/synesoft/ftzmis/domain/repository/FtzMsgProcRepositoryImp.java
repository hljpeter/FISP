package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzMsgProc;

@Repository
public class FtzMsgProcRepositoryImp implements FtzMsgProcRepository {

	// 查询待处理消息队列
	public List<FtzMsgProc> queryPendingMsg(int resendCount) {
		return queryDAO.executeForObjectList(Table.FTZ_MSG_PROC + "." + "selectPendingMsg", resendCount);
	}

	// 根据主键查询信息
	public FtzMsgProc queryFtzMsgProc(FtzMsgProc ftzMsgProc) {
		return queryDAO.executeForObject(Table.FTZ_MSG_PROC + "." + SQLMap.SELECT_PRIMARY_KEY, ftzMsgProc, FtzMsgProc.class);
	}
	
	// 插入信息
	public int insertFtzMsgProc(FtzMsgProc ftzMsgProc) {
		return updateDAO.execute(Table.FTZ_MSG_PROC + "." + SQLMap.INSERT, ftzMsgProc);
	}

	public int updateFtzMsgProc(FtzMsgProc ftzMsgProc) {
		return updateDAO.execute(Table.FTZ_MSG_PROC + "." + SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzMsgProc);
	}

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
}

