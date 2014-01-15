package com.synesoft.pisa.domain.service.sheet; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;
import com.synesoft.pisa.domain.repository.sheet.SheetRepository;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-17
 */
@Service
public class SheetServiceImpl implements SheetService{

	@Autowired
	private SheetRepository sheetRepository;
	
	@Override
	public Page<ExpSheetInfo> querySheetList(Pageable pageable,
			ExpSheetInfo sheetNo) {
		return sheetRepository.queryLiest(pageable,sheetNo);
	}

	@Override
	public Page<ExpSheetDtl> querySheetDetilList(Pageable pageable,
			ExpSheetDtl sheetNo) {		
		return sheetRepository.queryDetilLiest(pageable, sheetNo);
	}

}
 