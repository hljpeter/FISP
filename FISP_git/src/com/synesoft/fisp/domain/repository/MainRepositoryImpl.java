package com.synesoft.fisp.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.model.MainParam;

@Repository
public class MainRepositoryImpl implements MainRepository{

	@Resource
	private QueryDAO queryDAO;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.MainRepository#getSysGeneralTODO(java.lang.String)
	 */
	@Override
	public MainParam getSysGeneralTODO(String sqlId) {
		return queryDAO.executeForObject("MAIN." + sqlId, null, MainParam.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.MainRepository#getBizGeneralTODO(com.synesoft.fisp.domain.model.MainParam)
	 */
	@Override
	public List<MainParam> getBizGeneralTODO(MainParam param) {
		return queryDAO.executeForObjectList("MAIN.selectFTZ", param);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.MainRepository#getMsgSendFail()
	 */
	@Override
	public List<MainParam> getMsg() {
		return queryDAO.executeForObjectList("MAIN.selectMsg", null);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.MainRepository#getActMstr(java.lang.String)
	 */
	@Override
	public int getActMstr(String date) {
		return queryDAO.executeForObject("MAIN.selectActMstr", date, Integer.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.MainRepository#getBatchToMsg()
	 */
	@Override
	public int getBatchToMsg() {
		return queryDAO.executeForObject("MAIN.selectBatToMsg", null, Integer.class);
	}
}
