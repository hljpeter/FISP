package com.synesoft.pisa.domain.repository.sheet; 

import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetDtl;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-20
 */
@Repository
public class ItemRepositoryImpl implements ItemRepository{

	@Resource
	private PageHandler<ExpSheetDtl> pageH;
	
	@Autowired
	private QueryDAO queryDAO;
	
	@Autowired
	private UpdateDAO updateDAO;
	
	@Override
	public Page<ExpSheetDtl> queryItemList(Pageable pageable,
			ExpSheetDtl sheetList) {
		return pageH.getPage(Table.EXP_SHEET_DTL, SQLMap.SELECT_ITEMCOUNTS,SQLMap.SELECT_ITEMLIST,sheetList, pageable);
	}
	@Override
	public Page<ExpSheetDtl> queryItemDetilList(Pageable pageable,
			ExpSheetDtl sheetList) {
		return pageH.getPage(Table.EXP_SHEET_DTL, SQLMap.SELECT_ITEMDETCOUNTS,SQLMap.SELECT_ITEMDETLIST,sheetList, pageable);
	}

	public ExpSheetDtl queryItemDetil(ExpSheetDtl sheetDtl){
		return queryDAO.executeForObject(Table.EXP_SHEET_DTL+"."+SQLMap.SELECT_BYKEY,sheetDtl,ExpSheetDtl.class);
	}
	
	public int updateSheetDtl(ExpSheetDtl expSheetDtl){
		return updateDAO.execute(Table.EXP_SHEET_DTL+"."+SQLMap.UPDATE_BYKEY_SELECTIVE,expSheetDtl);
	}
	
	public int auditSheetDtl(ExpSheetDtl dtl){
		return updateDAO.execute(Table.EXP_SHEET_DTL+"."+SQLMap.UPDATE_BYKEY_SELECTIVE1,dtl);
	}
	
	public Page queryCounts(Pageable pageable,ReportData reportData){
		return pageH.getPage(Table.EXP_SHEET_DTL, "selectCountCount","selectCounts",reportData, pageable);
	}
}
 