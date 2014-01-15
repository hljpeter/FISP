package com.synesoft.pisa.domain.service.sheet;

import java.util.List;

import com.synesoft.pisa.domain.model.ExpSheetFormula;

/**
 * @author 李峰
 * @date 2013-12-18 上午10:29:55
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface FormulaService {

	public List<ExpSheetFormula> queryFormulaCheckList();
	
	public ExpSheetFormula getByFormula(ExpSheetFormula formula_);

	public int checkWhere(String where);

}
