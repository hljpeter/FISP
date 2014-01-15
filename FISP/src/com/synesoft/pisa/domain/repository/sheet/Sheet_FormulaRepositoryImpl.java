package com.synesoft.pisa.domain.repository.sheet;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.pisa.app.common.constants.SQLMap;
import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetFormula;

@Repository
public class Sheet_FormulaRepositoryImpl implements Sheet_FormulaRepository {

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<ExpSheetFormula> pageH;

	@Override
	public Page<ExpSheetFormula> queryList(Pageable pageable,
			ExpSheetFormula expSheetFormula) {
		return pageH.getPage(Table.EXP_SHEET_FORMULA, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, expSheetFormula, pageable);
	}

	@Override
	public ExpSheetFormula query(ExpSheetFormula expSheetFormula) {
		return queryDAO.executeForObject(Table.EXP_SHEET_FORMULA + "."
				+ SQLMap.SELECT_KEY, expSheetFormula, ExpSheetFormula.class);
	}

	@Override
	public int insertExpSheetFormula(ExpSheetFormula expSheetFormula) {
		return updateDAO.execute(Table.EXP_SHEET_FORMULA + "." + SQLMap.INSERT,
				expSheetFormula);
	}

	@Override
	public int updateExpSheetFormula(ExpSheetFormula expSheetFormula) {
		return updateDAO.execute(Table.EXP_SHEET_FORMULA + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, expSheetFormula);
	}

	@Override
	public int deleteExpSheetFormula(ExpSheetFormula expSheetFormula) {
		return updateDAO.execute(Table.EXP_SHEET_FORMULA + "."
				+ SQLMap.DELETE_BYKEY, expSheetFormula);
	}

}
