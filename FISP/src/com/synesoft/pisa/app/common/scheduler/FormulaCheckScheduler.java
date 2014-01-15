package com.synesoft.pisa.app.common.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.app.common.formulacheck.FormulaCheck;
import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.service.sheet.FormulaService;

/**
 * 公式检查定时任务
 * @author 李峰
 * @date 2013-12-24 上午10:28:29
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service
public class FormulaCheckScheduler {

	private static final Logger logger = LoggerFactory.getLogger(FormulaCheckScheduler.class);
	
	@Autowired
	private FormulaCheck formulaCheck;
	
	@Autowired
	private FormulaService formulaService;
	
	/**
	 * 定时任务调度入口
	 */
	public void execute(){
		logger.info("开始公式校验...");
		formulaCheck();
		logger.info("结束公式校验...");
	}
	
	/**
	 * 查询需要进行校验的公式
	 */
	public void formulaCheck(){
		List<ExpSheetFormula> list = formulaService.queryFormulaCheckList();
		for(ExpSheetFormula formula : list){
			try{
				formulaCheck.check(formula);
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
	}
}
