package com.synesoft.pisa.domain.repository.sheet;

import java.util.Map;

import com.synesoft.pisa.domain.model.ExpSheetDtl;

/**
 * @author 李峰
 * @date 2013-12-19 下午1:59:24
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public interface SheetDtlRepository {

	public ExpSheetDtl getExpSheetDtl(ExpSheetDtl dtl);

	public Map<String,Long> getRegionCount(Map<String, String> param);

}
