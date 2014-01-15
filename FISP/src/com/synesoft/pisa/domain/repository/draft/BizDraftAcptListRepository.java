package com.synesoft.pisa.domain.repository.draft;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.BizDraftAcptList;

/**
 * 纸质商业汇票操作
 * @author michelle.wang
 * 
 */

public interface BizDraftAcptListRepository {

	/**
	 * 列表查询
	 * 
	 * @param pageable
	 * @param bizDraftAcptList
	 * @return
	 */
	public Page<BizDraftAcptList> queryList(Pageable pageable, BizDraftAcptList bizDraftAcptList);
	
	/**
	 * 详细查询
	 * @param bizDraftAcptList
	 * @return
	 */
	public BizDraftAcptList query(BizDraftAcptList bizDraftAcptList);

}
