package com.synesoft.pisa.domain.service.draft;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.pisa.domain.model.BizDraftAcptList;

/**
 * 纸质商业汇票操作接口
 * @author michelle.wang
 *
 */
public interface BizDraftAcptListService {

	/**
	 * 纸质商业汇票列表查询
	 * @param pageable
	 * @param bizDraftAcptList
	 * @return
	 */
	public Page<BizDraftAcptList> transQueryBizDraftAcptListList(Pageable pageable, BizDraftAcptList bizDraftAcptList);
	
	/**
	 *  纸质商业汇票详细查询
	 * @param bizDraftAcptList
	 * @return
	 */
	public BizDraftAcptList transQueryBizDraftAcptList(BizDraftAcptList bizDraftAcptList);
}
