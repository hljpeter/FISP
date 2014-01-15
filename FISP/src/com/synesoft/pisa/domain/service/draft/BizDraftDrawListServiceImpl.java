package com.synesoft.pisa.domain.service.draft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.BizDraftDrawList;
import com.synesoft.pisa.domain.repository.draft.BizDraftDrawListRepository;

@Service("bizDraftDrawListService")
public class BizDraftDrawListServiceImpl implements BizDraftDrawListService {

	@Autowired
	private BizDraftDrawListRepository bizDraftDrawListRepository;
	
	@Override
	public Page<BizDraftDrawList> transQueryBizDraftDrawListList(Pageable pageable,
			BizDraftDrawList bizDraftDrawList) {
		return bizDraftDrawListRepository.queryList(pageable, bizDraftDrawList);
	}

	@Override
	public BizDraftDrawList transQueryBizDraftDrawList(BizDraftDrawList bizDraftDrawList) {
		return bizDraftDrawListRepository.query(bizDraftDrawList);
	}


}
