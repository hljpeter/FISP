package com.synesoft.pisa.domain.service.sheet; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;

/** 
 *
 * description: 报表查询
 * @author wjl 
 * @version 2013-12-17
 */
public interface SheetService {
	

	/**
	 * 报表查询
	 * @param pageable
	 * @param sheetNo
	 * @return
	 */
	Page<ExpSheetInfo> querySheetList(Pageable pageable,ExpSheetInfo sheetNo);

	/**
	 * 详情信息显示
	 * @param pageable
	 * @param sheetNo
	 * @return
	 */
	Page<ExpSheetDtl> querySheetDetilList(Pageable pageable,ExpSheetDtl sheetNo);

}
 