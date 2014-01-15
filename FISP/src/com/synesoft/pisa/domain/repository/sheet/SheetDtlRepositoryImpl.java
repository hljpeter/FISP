package com.synesoft.pisa.domain.repository.sheet;

import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.ExpSheetDtl;

/**
 * @author 李峰
 * @date 2013-12-19 下午2:00:28
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class SheetDtlRepositoryImpl implements SheetDtlRepository {

	@Resource
	private QueryDAO queryDAO;
	
	public ExpSheetDtl getExpSheetDtl(ExpSheetDtl dtl){
		return queryDAO.executeForObject(Table.EXP_SHEET_DTL+".selectByItemDim", dtl, ExpSheetDtl.class);
	}
	
	public Map getRegionCount(Map<String, String> param){
		return queryDAO.executeForMap(Table.EXP_SHEET_DTL+".selectRegionCount", param);
	}
}
