package com.synesoft.pisa.domain.repository.draft;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.BizDraftDrawList;

/**
 * 纸质商业汇票操作
 * @author michelle.wang
 * 
 */

public interface BizDraftDrawListRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param bizDraftDrawList
	 * @return
	 */
	public Page<BizDraftDrawList> queryList(Pageable pageable, BizDraftDrawList bizDraftDrawList);
	
	/**
	 * 详细查询
	 * @param bizDraftDrawList
	 * @return
	 */
	public BizDraftDrawList query(BizDraftDrawList bizDraftDrawList);

}
