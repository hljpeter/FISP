package com.synesoft.fisp.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.repository.NumberRepository;

@Service("numberService")
public class NumberServiceImpl implements NumberService {
	@Autowired
	protected NumberRepository numberRepository;
	
	@Override
	public String getSysIDSequence(String MsgNo,int len) {
		return DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDD)+MsgNo+StringUtil.addZeroForNum(numberRepository.getSysIDSequence().toString(),len);
	}
	
	@Override
	public String getSysIDSequence(int len) {
		return StringUtil.addZeroForNum(numberRepository.getSysIDSequence().toString(),len);
	}
}
