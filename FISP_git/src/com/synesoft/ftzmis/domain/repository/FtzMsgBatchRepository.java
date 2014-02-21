package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import com.synesoft.ftzmis.domain.model.FtzMsgBatch;

public interface FtzMsgBatchRepository {	

	public List<FtzMsgBatch> queryFtzMsgBatch(String msg_proc_msgId);
}