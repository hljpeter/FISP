package com.synesoft.pisa.domain.service.sheet;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.repository.sheet.SheetDtlRepository;

/**
 * @author 李峰
 * @date 2013-12-19 下午1:58:41
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("sheetDtlService")
public class SheetDtlServiceImpl implements SheetDtlService {

	@Autowired
	private SheetDtlRepository sheetDtlRepository;
	
	public ExpSheetDtl getExpSheetDtl(ExpSheetDtl dtl){
		return sheetDtlRepository.getExpSheetDtl(dtl);
	}
	
	/**
	 * 返回两个维度代码之间的统计数据
	 * @param param
	 * @return
	 */
	public Map getRegionCount(Map<String, String> param){
		return sheetDtlRepository.getRegionCount(param);
	}
}
