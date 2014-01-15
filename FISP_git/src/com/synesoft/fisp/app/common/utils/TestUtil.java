package com.synesoft.fisp.app.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.synesoft.dataproc.common.util.SpringUtil;
import com.synesoft.fisp.app.common.trigger.TriggerDpExprParaval;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;

/**
 * @author zhongHubo
 * @date 2013年12月5日 15:12:09
 * @version 1.0
 * @Description 测试Util
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class TestUtil {
	
	/**
	 * 测试4-测试TriggerDpExprParaval
	 * @param args
	 */
	public static void main(String[] args) {
		// 加载Spring配置文件
		SpringUtil.getBean("triggerDpExprParaval");
//		TriggerDpExprParaval triggerDpExprParaval = new TriggerDpExprParaval();
		TriggerDpExprParaval triggerDpExprParaval = (TriggerDpExprParaval) ContextUtil.getCtx().getBean("triggerDpExprParaval");
		triggerDpExprParaval.execute();
	}
	
	/**
	 * 测试3-测试class
	 * @param args
	 */
	public static void main3(String[] args) {
		System.out.println(String.class);
		System.out.println(Double.class);
		System.out.println(double.class);
	}
	
	/**
	 * 测试2-获取根据seqNo获取所有的表达式参数值对象，包括递归获取的
	 * @param args
	 */
	public static void main2(String[] args) {
		// 加载Spring配置文件
		SpringUtil.getBean("dpExprParavalRepository");
		List<DpExprParaval> list = CommonUtil.getAllDepBySeqNo("00000000000000000000000000010976");
		
		List<List<DpExprParaval>> list_list = new ArrayList<List<DpExprParaval>>();
		list_list.add(list);
		PrintTransUtil.printReqMsg("param", "Local", list_list);
	}

	/**
	 * 测试1-页面所需要格式的解析完成的参数值
	 * @param args
	 * @throws Exception
	 */
	public static void main1(String[] args) throws Exception {
		// 加载Spring配置文件
		SpringUtil.getBean("dpExprParavalRepository");
		// 测试解析表达式
		Map<String, Object> map = CommonUtil.analyticalExpression("00000000000000000000000000010976");
		if (map == null || map.size() < 1) {
			System.out.println("没有查到对应的表达式！");
			return;
		}
		
		DpExprParavalVO vo_method = (DpExprParavalVO) map.get(CommonUtil.expr_method);
		@SuppressWarnings("unchecked")
		List<DpExprParavalVO> list = (List<DpExprParavalVO>) map.get(CommonUtil.expr_param);
		
		PrintTransUtil.printReqMsg("vo_method", "Local", vo_method);
		PrintTransUtil.printReqMsg("param", "Local", list);
	}
	
	
}
