package com.synesoft.pisa.domain.repository.sheet;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetFormulaResult;

/**
 * @author 李峰
 * @date 2013-12-25 上午11:10:49
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class FormulaResultRepositoryImpl implements FormulaResultRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	public void insert(ExpSheetFormulaResult result){
		updateDAO.execute(Table.EXP_SHEET_FORMULA_RESULT+"."+SQLMap.INSERT, result);
	}
}
