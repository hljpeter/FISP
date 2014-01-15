package com.synesoft.pisa.app.common.formulacheck;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.pisa.app.common.constants.CommonConst;
import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.model.ExpSheetFormulaResult;
import com.synesoft.pisa.domain.service.sheet.FormulaResultService;
import com.synesoft.pisa.domain.service.sheet.FormulaService;
import com.synesoft.pisa.domain.service.sheet.SheetDtlService;

/**
 * @author 李峰
 * @date 2013-12-18 上午10:21:41
 * @version 1.0
 * @description 
 * @system FISP
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service
public class FormulaCheck {

	@Autowired
	private FormulaService formulaService;
	
	@Autowired
	private SheetDtlService sheetDtlService;
	
	@Autowired
	private NumberService numberService;
	
	@Autowired
	private FormulaResultService formulaResultService;
	
	/**
	 * 传入公式 检查公式是否成立  成立返回true 不成立返回false
	 * @param formula
	 * @return
	 */
	@Transactional
	public void check(ExpSheetFormula formula){
		//获取公式比较符号
		String mark = formula.getFormula().substring(formula.getFormula().indexOf("]")+1, formula.getFormula().indexOf("[",formula.getFormula().indexOf("]")));
		//比较结果默认成立
		String resultType1="1",resultType2 = "1";
		String[] expression = formula.getFormula().split(mark);
		/**获取表单代码**/
		String sheetNo = formula.getSheetNo();
		/** 首先获取左边的表达式  去掉[]*/
		String leftExpression = expression[0].replace("[", "").replace("]", "");
		String[] left = leftExpression.split("#");
		/**获取指标代码   维度代码*/
		String leftItemNo = left[0];
		String leftDimNo = left[1];
		/**根据指标代码 指标代码   维度代码 查询表单详细**/
		ExpSheetDtl dtl = new ExpSheetDtl();
		dtl.setSheetNo(sheetNo);
		dtl.setItemNo(leftItemNo);
		dtl.setDimNo(leftDimNo);
		ExpSheetDtl leftEsd = sheetDtlService.getExpSheetDtl(dtl);
		String leftValue1="",leftValue2 = "";
		Pattern p = Pattern.compile(CommonConst.PATTERN_NUMERICAL);
		if(leftEsd!=null){
			leftValue1 = StringUtils.isBlank(leftEsd.getDataValue1())?"0":leftEsd.getDataValue1();
			leftValue2 = StringUtils.isBlank(leftEsd.getDataValue2())?"0":leftEsd.getDataValue2();
			if(!p.matcher(leftValue1).find()){
				resultType1 = "2";
				leftValue1=leftExpression+"表达式的第一个值"+leftEsd.getDataValue1()+"不是有效的数值值";
			}
			if(!p.matcher(leftValue2).find()){
				resultType2 = "2";
				leftValue2=leftExpression+"表达式的第二个值"+leftEsd.getDataValue2()+"不是有效的数值值";
			}
		}else{
			resultType1 = "2";
			leftValue1="找不到"+leftExpression+"对应的数据";
			resultType2 = "2";
			leftValue2="找不到"+leftExpression+"对应的数据";
		}
		/*** 获取右边表达式  **/
		String rightExpression = expression[1];
		String rightType = rightExpression.indexOf("+......+")>0?"2":"1";
		String rightValue1 = "",rightValue2 = "";
		//多个值之和
		if("1".equals(rightType)){
			String[] array_right = rightExpression.split("\\+");
			for(String expression_r : array_right){
				expression_r = expression_r.replace("[", "").replace("]", "");
				String[] right = expression_r.split("#");
				dtl.setItemNo(right[0]);
				dtl.setDimNo(right[1]);
				ExpSheetDtl rightEsd = sheetDtlService.getExpSheetDtl(dtl);
				if(rightEsd!=null){
					rightValue1 += StringUtils.isBlank(rightEsd.getDataValue1())?"0":rightEsd.getDataValue1()+"+";
					rightValue2 += StringUtils.isBlank(rightEsd.getDataValue2())?"0":rightEsd.getDataValue2()+"+";
					if(!p.matcher(rightEsd.getDataValue1()).find()){
						resultType1 = "2";
						rightValue1=expression_r+"表达式的第一个值"+rightEsd.getDataValue1()+"不是有效的数值值";
						break;
					}
					if(!p.matcher(rightEsd.getDataValue2()).find()){
						resultType2 = "2";
						rightValue2=expression_r+"表达式的第二个值"+rightEsd.getDataValue2()+"不是有效的数值值";
						break;
					}
				}else{
					resultType1 = "2";
					rightValue1="找不到"+expression_r+"对应的数据";
					resultType2 = "2";
					rightValue2="找不到"+expression_r+"对应的数据";
					break;
				}
			}
			if("1".equals(resultType1)){
				rightValue1 = rightValue1.substring(0, rightValue1.length()-1);
			}
			if("1".equals(resultType2)){
				rightValue2 = rightValue2.substring(0, rightValue2.length()-1);
			}
		}else{
			//两个值区间之和
			String[] array_right = rightExpression.split("\\+......\\+");
			String beginExpression = array_right[0];
			String[] beginArray = beginExpression.split("#");
			String endExpression = array_right[1];
			String[] endArray = endExpression.split("#");
			Map<String,String> param = new HashMap<String,String>();
			param.put("sheetNo", sheetNo);
			param.put("itemNo", beginArray[0].replace("[", "").replace("]", ""));
			param.put("beginDimNo", beginArray[1].replace("[", "").replace("]", ""));
			param.put("endDimNo", endArray[1].replace("[", "").replace("]", ""));
			Map m = sheetDtlService.getRegionCount(param);
			//统计该区间的值
			rightValue1 = m.get("DATA_VALUE1").toString();
			
			rightValue2 = m.get("DATA_VALUE2").toString();
		}
		/**
		 * 开始比较两个值
		 */
		if("1".equals(resultType1)){
			String where = leftValue1+mark+rightValue1;
			int i = formulaService.checkWhere(where);
			if(i==0){
				resultType1 = "2";
			}
		}
		if("1".equals(resultType2)){
			String where = leftValue2+mark+rightValue2;
			int i = formulaService.checkWhere(where);
			if(i==0){
				resultType2 = "2";
			}
		}
		ExpSheetFormulaResult result = new ExpSheetFormulaResult();
		result.setResultNo(numberService.getSysIDSequence(20));
		result.setFormulaId(formula.getFormulaId());
		result.setSheetNo(formula.getSheetNo());
		result.setResultType1(resultType1);
		result.setLeftValue1(leftValue1);
		result.setRightValue1(rightValue1);
		result.setResultType2(resultType2);
		result.setLeftValue2(leftValue2);
		result.setRightValue2(rightValue2);
		result.setCreateTime(DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
		formulaResultService.insert(result);
	}
}
