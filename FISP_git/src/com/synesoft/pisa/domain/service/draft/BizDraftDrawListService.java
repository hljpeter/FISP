package com.synesoft.pisa.domain.service.draft;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.BizDraftDrawList;

/**
 * 纸质商业汇票操作接口
 * @author michelle.wang
 *
 */
public interface BizDraftDrawListService {

	/**
	 * 纸质商业汇票列表查询
	 * @param pageable
	 * @param bizDraftDrawList
	 * @return
	 */
	public Page<BizDraftDrawList> transQueryBizDraftDrawListList(Pageable pageable, BizDraftDrawList bizDraftDrawList);
	
	/**
	 *  纸质商业汇票详细查询
	 * @param bizDraftDrawList
	 * @return
	 */
	public BizDraftDrawList transQueryBizDraftDrawList(BizDraftDrawList bizDraftDrawList);
}
