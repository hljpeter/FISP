package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import com.synesoft.fisp.domain.model.DpMapDict;

public interface DpMapDictRepository {
	
	/**
	 * 查询所有符合条件的数据
	 * @param dpMapDict	条件对象
	 * @return
	 * 		List<DpMapDict>	结果List
	 */
	public List<DpMapDict> queryList(DpMapDict dpMapDict);
}
