package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.vo.DpFileVO;

/**
 * 文件定义RepositoryImpl
 * @date 2013-11-12
 * @author yyw
 *
 */
@Repository
public class DpFileRepositoryImpl implements DpFileRepository {

	private static final LogUtil log = new LogUtil(DpFileRepositoryImpl.class);
	
	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<DpFile> pageH;
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#queryList(org.springframework.data.domain.Pageable, com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public Page<DpFile> queryList(Pageable pageable, DpFile dpFile) {
		log.info("[queryList] - query one page");
		return pageH.getPage(Table.DP_FILE, SQLMap.SELECT_QUERY_COUNTS, SQLMap.SELECT_QUERY_LIST, dpFile, pageable);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#query(java.lang.String)
	 */
	@Override
	public DpFile query(String id) {
		log.info("[query] - query one record, param[id=" + id + "]");
		return queryDAO.executeForObject(Table.DP_FILE + "." + SQLMap.SELECT_BYKEY, id, DpFile.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#queryForLogicKey(java.lang.String)
	 */
	@Override
	public DpFile queryForLogicKey(String name) {
		log.info("[queryForLogicKey] - query one record, param[filename=" + name + "]");
		return queryDAO.executeForObject(Table.DP_FILE + "." + SQLMap.SELECT_KEY, name, DpFile.class);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#queryList(com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public List<DpFile> queryList(DpFile dpFile) {
		log.info("[queryList] - query records for requirement");
		return queryDAO.executeForObjectList(Table.DP_FILE + "." + SQLMap.SELECT_QUERY_LIST, dpFile);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#queryNameList(java.lang.String)
	 */
	@Override
	public List<String> queryNameList(String name) {
		log.info("[queryNameList] - query name begining with '" + name + "'");
		return queryDAO.executeForObjectList(Table.DP_FILE + "." + SQLMap.SELECT_LIST, name);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#insert(com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public int insert(DpFile dpFile) {
		log.info("[insert] - add a new record");
		return updateDAO.execute(Table.DP_FILE + "." + SQLMap.INSERT, dpFile);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#update(com.synesoft.fisp.domain.model.vo.DpFileVO)
	 */
	@Override
	public int update(DpFileVO dpFileVo) {
		log.info("[update] - update one record");
		return updateDAO.execute(Table.DP_FILE + "." + SQLMap.UPDATE_BYKEY, dpFileVo);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.dp.DpFileRepository#delete(com.synesoft.fisp.domain.model.DpFile)
	 */
	@Override
	public int delete(DpFile dpFile) {
		log.info("[delete] - delete records for requirement");
		return updateDAO.execute(Table.DP_FILE + "." + SQLMap.DELETE_BYKEY, dpFile);
	}

}
