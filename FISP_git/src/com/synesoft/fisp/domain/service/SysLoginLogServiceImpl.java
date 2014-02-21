package com.synesoft.fisp.domain.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.SysLoginLog;
import com.synesoft.fisp.domain.repository.SysLoginLogRepository;

@Service("sysLoginLogService")
public class SysLoginLogServiceImpl implements SysLoginLogService {
	private final static LogUtil log = new LogUtil(SysLoginLogServiceImpl.class);
	
	@Autowired
	protected SysLoginLogRepository sysLoginLogRepository;
	@Resource
	protected NumberService numberService;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.SysLoginLogService#insert(com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	@Transactional
	public void insert(SysLoginLog sysLoginLog) {
		log.debug("insert() start ...");
		if (null != sysLoginLog) {
			ResultMessages messages = ResultMessages.error();

			sysLoginLog.setId(numberService.getSysIDSequence(20));
			int ret = sysLoginLogRepository.insert(sysLoginLog);
			if (ret != 1) {
				log.error("Insert SysLoginLog failure!");
				messages.add("w.dp.0001");
				throw new BusinessException(messages);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.SysLoginLogService#getPage(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	public Page<SysLoginLog> getPage(Pageable pageable, SysLoginLog sysLoginLog) {
		log.debug("getPage() start ...");
		ResultMessages messages = ResultMessages.error();
		
		Page<SysLoginLog> page = sysLoginLogRepository.queryPage(pageable, sysLoginLog);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.SysLoginLogService#getLastSuccLogin(com.synesoft.fisp.domain.model.SysLoginLog)
	 */
	@Override
	public SysLoginLog getLastSuccLogin(SysLoginLog sysLoginLog) {
		SysLoginLog log = sysLoginLogRepository.query(sysLoginLog);
		return log;
	}

}
