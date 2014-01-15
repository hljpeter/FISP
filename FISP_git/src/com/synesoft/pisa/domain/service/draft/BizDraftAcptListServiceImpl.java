package com.synesoft.pisa.domain.service.draft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.BizDraftAcptList;
import com.synesoft.pisa.domain.repository.draft.BizDraftAcptListRepository;

@Service("bizDraftAcptListService")
public class BizDraftAcptListServiceImpl implements BizDraftAcptListService {

	@Autowired
	private BizDraftAcptListRepository bizDraftAcptListRepository;
	
	@Override
	public Page<BizDraftAcptList> transQueryBizDraftAcptListList(Pageable pageable,
			BizDraftAcptList bizDraftAcptList) {
		return bizDraftAcptListRepository.queryList(pageable, bizDraftAcptList);
	}

	@Override
	public BizDraftAcptList transQueryBizDraftAcptList(BizDraftAcptList bizDraftAcptList) {
		return bizDraftAcptListRepository.query(bizDraftAcptList);
	}


}
