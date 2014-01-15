package com.synesoft.pisa.domain.repository.sheet;

import java.util.List;

import com.synesoft.pisa.domain.model.ExpSheetFormula;

/**
 * @author 李峰
 * @date 2013-12-19 下午1:16:08
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface FormulaRepository {

	public ExpSheetFormula getByFormula(ExpSheetFormula formula_);

	public List<ExpSheetFormula> queryFormulaCheckList();

	public int checkWhere(String where);
}
