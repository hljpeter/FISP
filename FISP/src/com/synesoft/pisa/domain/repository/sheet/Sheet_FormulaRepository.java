package com.synesoft.pisa.domain.repository.sheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.ExpSheetFormula;

public interface Sheet_FormulaRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param expSheetFormula
	 * @return
	 */
	public Page<ExpSheetFormula> queryList(Pageable pageable, ExpSheetFormula expSheetFormula);
	
	/**
	 * 详细查询
	 * @param expSheetFormula
	 * @return
	 */
	public ExpSheetFormula query(ExpSheetFormula expSheetFormula);
	
	/**
	 * 新增
	 * 
	 * @param expSheetFormula
	 * @return
	 */

	public int insertExpSheetFormula(ExpSheetFormula expSheetFormula);

	/**
	 * 更新
	 * 
	 * @param expSheetFormula
	 * @return
	 */
	public int updateExpSheetFormula(ExpSheetFormula expSheetFormula);

	/**
	 * 删除
	 * 
	 * @param expSheetFormula
	 * @return
	 */
	public int deleteExpSheetFormula(ExpSheetFormula expSheetFormula);
}
