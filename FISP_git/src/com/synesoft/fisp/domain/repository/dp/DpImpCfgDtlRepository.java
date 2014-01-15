package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import com.synesoft.fisp.domain.model.DpImpCfgDtl;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;

/**
 * 数据导入映射配置明细Repository
 * @date 2013-11-13
 * @author yyw
 *
 */
public interface DpImpCfgDtlRepository {
	
	/**
	 * 查询映射配置下的所有明细记录
	 * @param impId	映射配置ID
	 * @return
	 * 		List<DpImpCfgDtlVO>	结果List
	 */
	public List<DpImpCfgDtlVO> queryList(String impId);

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（IMP_DTL_ID）
	 * @return
	 * 		DpImpCfgDtl	一条记录的对象
	 */
	public DpImpCfgDtl query(String id);
	
	/**
	 * 插入一条记录
	 * @param dpImpCfgDtl	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpImpCfgDtl dpImpCfgDtl);
	
	/**
	 * 删除所有记录，根据映射配置ID，修改用户和修改时间
	 * @param impId	映射配置ID
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(String impId);
}
