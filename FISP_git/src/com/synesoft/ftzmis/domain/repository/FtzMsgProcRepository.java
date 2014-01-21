package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import com.synesoft.ftzmis.domain.model.FtzMsgProc;

public interface FtzMsgProcRepository {	

	public List<FtzMsgProc> queryPendingMsg(int resendCount);

	public int insertFtzMsgProc(FtzMsgProc ftzMsgProc);
	
	public int updateFtzMsgProc(FtzMsgProc ftzMsgProc);
	
	public FtzMsgProc queryFtzMsgProc(FtzMsgProc ftzMsgProc);
}