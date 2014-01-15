package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.vo.DpImpCfgVO;

public interface DpImpCfgRepository {
	
	/**
	 * 查询 一页记录
	 * @param pageable	分页对象
	 * @param dpImpCfg	封装了条件值的对象
	 * @return
	 * 		Page<DpImpCfg>	分页对象
	 */
	public Page<DpImpCfg> queryList(Pageable pageable, DpImpCfg dpImpCfg);

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（IMP_ID）
	 * @return
	 * 		DpImpCfg	一条记录的对象
	 */
	public DpImpCfg query(String id);

	/**
	 * 查询一条记录，根据逻辑主键
	 * @param id	主键（PROJ_ID + BRANCH_ID + BATCH_NO）
	 * @return
	 * 		DpImpCfg	一条记录的对象
	 */
	public DpImpCfg query(DpImpCfg dpImpCfg);
	
	/**
	 * 查询符合条件的所有记录
	 * @param dpImpCfg	封装了条件值的对象
	 * @return
	 * 		List<DpImpCfg>	结果List
	 */
	public List<DpImpCfg> queryList(DpImpCfg dpImpCfg);
	
	/**
	 * 插入一条记录
	 * @param dpImpCfg	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpImpCfg dpImpCfg);
	
	/**
	 * 更新一条记录，根据主键，修改用户和修改时间
	 * @param dpImpCfgVO	更新对象
	 * @return
	 * 		int	成功更新的记录数
	 */
	public int update(DpImpCfgVO dpImpCfgVO);
	
	/**
	 * 删除一条记录，根据主键，修改用户和修改时间
	 * @param dpImpCfg	封装了三个字段值的对象
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(DpImpCfg dpImpCfg);
}
