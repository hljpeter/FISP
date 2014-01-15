package com.synesoft.pisa.domain.service.sheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.repository.sheet.FormulaRepository;

/**
 * @author 李峰
 * @date 2013-12-18 上午10:31:51
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("formulaService")
public class FormulaServiceImpl implements FormulaService {

	@Autowired
	private FormulaRepository formulaRepository;
	
	public List<ExpSheetFormula> queryFormulaCheckList(){
		return formulaRepository.queryFormulaCheckList();
	}
	
	public ExpSheetFormula getByFormula(ExpSheetFormula formula_) {
		return formulaRepository.getByFormula(formula_);
	}
	
	public int checkWhere(String where){
		return formulaRepository.checkWhere(where);
	}

}
