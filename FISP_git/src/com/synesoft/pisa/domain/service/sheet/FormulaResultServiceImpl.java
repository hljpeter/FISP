package com.synesoft.pisa.domain.service.sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.ExpSheetFormulaResult;
import com.synesoft.pisa.domain.repository.sheet.FormulaResultRepository;

/**
 * @author 李峰
 * @date 2013-12-25 上午11:09:14
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service
public class FormulaResultServiceImpl implements FormulaResultService {

	@Autowired
	private FormulaResultRepository formulaResultRepository;
	
	public void insert(ExpSheetFormulaResult result){
		formulaResultRepository.insert(result);
	}
}
