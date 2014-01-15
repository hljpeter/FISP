package com.synesoft.pisa.domain.repository.sheet;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetFormula;

/**
 * @author 李峰
 * @date 2013-12-19 下午1:18:20
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class FormulaRepositoryImpl implements FormulaRepository {

	@Resource
	private QueryDAO queryDAO;
	
	public List<ExpSheetFormula> queryFormulaCheckList(){
		return queryDAO.executeForObjectList(Table.EXP_SHEET_FORMULA+".selectFormulaCheck", new HashMap<String,String>());
	}
	
	public ExpSheetFormula getByFormula(ExpSheetFormula formula_){
		return queryDAO.executeForObject(Table.EXP_SHEET_FORMULA+"."+SQLMap.SELECT_BYKEY, formula_, ExpSheetFormula.class);
	}
	
	public int checkWhere(String where){
		return queryDAO.executeForObject(Table.EXP_SHEET_FORMULA+".checkWhere", where, Integer.class);
	}
}
