package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * @author 李峰
 * @date 2013-12-29 上午11:55:27
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface FTZ210302Service {

	public Page<FtzOffMsgCtl> queryFtzOffMsgCtlPage(Pageable pageable,
			FtzOffMsgCtl query_FtzOffMsgCtl);

	public int insertFtzOffMsgCtl(FtzOffMsgCtl insert_FtzOffMsgCtl);

	public List<FtzOffTxnDtl> queryFtzOffTxnDtlList(
			FtzOffTxnDtl query_FtzOffTxnDtl);

	public int insertFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	public FtzOffMsgCtl queryFtzOffMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

	public Page<FtzOffTxnDtl> queryFtzOffTxnDtlPage(Pageable pageable,
			FtzOffTxnDtl ftzOffTxnDtl);

	public FtzOffTxnDtl queryFtzOffTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl);

	public int updateFtzOffTxnDtlSelective(FtzOffTxnDtl ftzOffTxnDtl);

	public int deleteFtzOffTxnDtl(FtzOffTxnDtl del_FtzOffTxnDtl);

	public int deleteFtzOffMsgCtl(FtzOffMsgCtl del_FtzOffMsgCtl);

	public int updateFtzOffMsgCtlForSubmit(FtzOffMsgCtl ftzOffMsgCtl);

	public int updateFtzOffMsgCtl(FtzOffMsgCtl update_FtzOffMsgCtl,
			List<FtzOffTxnDtl> ftzOffTxnDtls);

}
