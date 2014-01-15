package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrTmp;

/**
 * @author hb_huang
 *
 * 2013-12-27 下午01:01:20
 *
 */
@Repository
public class FTZ210501RepositoryImpl implements FTZ210501Repository {
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<FtzActMstr> pageFtzActMstr;
	
	@Resource
	private PageHandler<FtzActMstrTmp> pageFtzActMstrTmp;
	
	@Override
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable,
			FtzActMstr ftzActMstr) {
		return pageFtzActMstr.getPage(Table.FTZ_ACT_MSTR, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzActMstr, pageable);
	}

	@Override
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr) {
		return queryDAO.executeForObject(Table.FTZ_ACT_MSTR + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzActMstr, FtzActMstr.class);
	}
	
	@Override
	public List<FtzActMstr> queryFtzActMstrList(FtzActMstr ftzActMstr) {
		return queryDAO.executeForObjectList(Table.FTZ_ACT_MSTR + "." + 
				SQLMap.SELECT_LIST, ftzActMstr);
	}
	
	@Override
	public int insertFtzActMstr(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + 
				SQLMap.INSERT, ftzActMstr);
	}
	
	@Override
	public int updateFtzActMstr(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + 
				SQLMap.UPDATE_PRIMARY_KEY_SELECTIVE, ftzActMstr);
	}
	
	@Override
	public int deleteFtzActMstr(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + 
				SQLMap.DELETE_PRIMARY_KEY, ftzActMstr);
	}
	
	@Override
	public FtzActMstrTmp queryFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp) {
		return queryDAO.executeForObject(Table.FTZ_ACT_MSTR_TMP + "."
				+ SQLMap.SELECT_PRIMARY_KEY, ftzActMstrTmp, FtzActMstrTmp.class);
	}

	@Override
	public int insertFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR_TMP + "." + 
				SQLMap.INSERT, ftzActMstrTmp);
	}

	@Override
	public int deleteFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR_TMP + "." + 
				SQLMap.DELETE_PRIMARY_KEY, ftzActMstrTmp);
	}

	@Override
	public int updateFtzActMstrStatus(FtzActMstr ftzActMstr) {
		return updateDAO.execute(Table.FTZ_ACT_MSTR + "." + 
				SQLMap.UPDATE_CONTENT_BY_PMKEY, ftzActMstr);
	}

	@Override
	public Page<FtzActMstrTmp> queryFtzActMstrTmpPage(Pageable pageable,
			FtzActMstrTmp ftzActMstrTmp) {	
		return pageFtzActMstrTmp.getPage(Table.FTZ_ACT_MSTR_TMP, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, ftzActMstrTmp, pageable);
	}

}
