package com.synesoft.pisa.domain.service.sheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form;
import com.synesoft.pisa.domain.model.ExpSheetFormula;

public interface Sheet_FormulaService {
	
	/**
	 * 列表查询
	 * @param pageable
	 * @param expSheetFormula
	 * @return
	 */
	public Page<ExpSheetFormula> transQueryExpSheetFormulaList(Pageable pageable, ExpSheetFormula expSheetFormula);
	
	/**
	 *  查询
	 * @param expSheetFormula
	 * @return
	 */
	public ExpSheetFormula transExpSheetFormula(ExpSheetFormula expSheetFormula); 

	/**
	 * 新增
	 * @param form
	 */
	public void transAdd(Sheet_Formula_Form form);
	
	/**
	 * 更新
	 * @param form
	 */
	public void transUpdate(Sheet_Formula_Form form);
	
	/**
	 * 删除
	 * @param form
	 */
	public void transDel(Sheet_Formula_Form form);
}
