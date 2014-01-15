package com.synesoft.pisa.domain.service.sheet;

import java.util.Map;

import com.synesoft.pisa.domain.model.ExpSheetDtl;

/**
 * @author 李峰
 * @date 2013-12-19 下午1:58:05
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface SheetDtlService {

	public ExpSheetDtl getExpSheetDtl(ExpSheetDtl dtl);

	/**
	 * 返回两个维度代码之间的统计数据
	 * @param param
	 * @return
	 */
	public Map getRegionCount(Map<String, String> param);

}
