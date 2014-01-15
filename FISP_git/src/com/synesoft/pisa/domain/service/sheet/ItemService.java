package com.synesoft.pisa.domain.service.sheet; 

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.pisa.domain.model.ExpSheetDtl;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-20
 */
public interface ItemService {

	/**
	 * 指标查询
	 * @param pageable
	 * @param sheetList
	 * @return
	 */
	Page<ExpSheetDtl> queryItemList(Pageable pageable, ExpSheetDtl sheetList);

	/**
	 * 指标查询详情
	 * @param pageable
	 * @param sheetList
	 * @return
	 */
	Page<ExpSheetDtl> queryItemDetilList(Pageable pageable,ExpSheetDtl sheetList);

	ExpSheetDtl queryItemDetil(ExpSheetDtl sheetDtl);

	int updateSheetDtl(ExpSheetDtl expSheetDtl);

	int auditSheetDtl(ExpSheetDtl dtl);

	public Page queryCounts(Pageable pageable,ReportData reportData);

}
 