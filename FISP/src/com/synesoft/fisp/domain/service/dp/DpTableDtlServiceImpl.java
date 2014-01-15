package com.synesoft.fisp.domain.service.dp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.repository.dp.DpTableDtlRepository;

/**
 * 表定义ServiceImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Service("DpTableDtlService")
public class DpTableDtlServiceImpl implements DpTableDtlService {

	private static final LogUtil log = new LogUtil(DpTableDtlServiceImpl.class);
	
	@Autowired
	protected DpTableDtlRepository dpTableDtlRepository;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.dp.DpTableDtlService#transQueryDpTableDtl(java.lang.String)
	 */
	@Override
	public DpTableDtl transQueryDpTableDtl(String id) {
		log.info("[transQueryDpTableDtl] - start");

		ResultMessages messages = ResultMessages.error();

		log.info("Searching detail information, param[id=" + id + "]");
		DpTableDtl dpTableDtl = dpTableDtlRepository.query(id);
		if (null == dpTableDtl) {
			log.error("[e.dp.table.0030] Failed to search records");
			messages.add("e.dp.table.0030", id);
			throw new BusinessException(messages);
		}
		
		log.info("[transQueryDpTableDtl] - end");
		return dpTableDtl;
	}

}
