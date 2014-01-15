package com.synesoft.pisa.domain.repository.sheet; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;

/** 
 *报表查询
 * description:
 * @author wjl 
 * @version 2013-12-17
 */
public interface SheetRepository {

	/**
	 * 列表查询
	 * @param pageable
	 * @param sheetNo
	 * @return
	 */
	Page<ExpSheetInfo> queryLiest(Pageable pageable, ExpSheetInfo sheetNo);

	/**
	 * 详情信息显示
	 * @param pageable
	 * @param sheetNo
	 * @return
	 */
	Page<ExpSheetDtl> queryDetilLiest(Pageable pageable, ExpSheetDtl sheetNo);

}
 