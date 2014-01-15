package com.synesoft.pisa.domain.service.sheet; 

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.domain.model.ReportData;
import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.repository.sheet.ItemRepository;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-20
 */
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired ItemRepository itemRepository;
	@Override
	public Page<ExpSheetDtl> queryItemList(Pageable pageable,
			ExpSheetDtl sheetList) {
		return itemRepository.queryItemList(pageable, sheetList);
	}
	@Override
	public Page<ExpSheetDtl> queryItemDetilList(Pageable pageable,
			ExpSheetDtl sheetList) {
		return itemRepository.queryItemDetilList(pageable, sheetList);
	}
	
	public ExpSheetDtl queryItemDetil(ExpSheetDtl sheetDtl){
		return itemRepository.queryItemDetil(sheetDtl);
	}

	@Transactional
	public int updateSheetDtl(ExpSheetDtl expSheetDtl){
		return itemRepository.updateSheetDtl(expSheetDtl);
	}
	
	@Transactional
	public int auditSheetDtl(ExpSheetDtl dtl){
		return itemRepository.auditSheetDtl(dtl);
	}
	
	public Page queryCounts(Pageable pageable,ReportData reportData){
		return itemRepository.queryCounts(pageable,reportData);
	}
}
 