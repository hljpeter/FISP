package com.synesoft.pisa.domain.repository.sheet; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;

/** 
 *
 * description:实现类
 * @author wjl 
 * @version 2013-12-17
 */
@Repository
public class SheetRepositoryImpl implements SheetRepository{
	@Resource
	private PageHandler<ExpSheetInfo> pageH;
	@Override
	public Page<ExpSheetInfo> queryLiest(Pageable pageable,
			ExpSheetInfo sheetNo) {
		return pageH.getPage(Table.EXP_SHEET_INFO, SQLMap.SELECT_SHEEETCOUNTS,SQLMap.SELECT_SHEEET,sheetNo, pageable);
	}

	@Resource
	private PageHandler<ExpSheetDtl> pageHD;
	
	@Override
	public Page<ExpSheetDtl> queryDetilLiest(Pageable pageable,
			ExpSheetDtl sheetNo) {
		return pageHD.getPage(Table.EXP_SHEET_DTL, SQLMap.SELECT_SHEEETDETILCOUNTS,SQLMap.SELECT_SHEEETDETIL,sheetNo, pageable);
	}

}
 