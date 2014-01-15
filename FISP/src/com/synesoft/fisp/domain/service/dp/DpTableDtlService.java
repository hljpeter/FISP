package com.synesoft.fisp.domain.service.dp;

import com.synesoft.fisp.domain.model.DpTableDtl;

/**
 * 表定义明细Service
 * @date 2013-11-15
 * @author yyw
 *
 */
public interface DpTableDtlService {
	
	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（colId)
	 * @return
	 * 		DpTableDtl	结果对象
	 */
	public DpTableDtl transQueryDpTableDtl(String id);

}
