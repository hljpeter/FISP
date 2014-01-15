package com.synesoft.fisp.domain.repository.dp;

import java.util.List;
import java.util.Map;

import com.synesoft.fisp.domain.model.DpFileDtl;

/**
 * 文件定义明细Repository
 * @date 2013-11-13
 * @author yyw
 *
 */
public interface DpFileDtlRepository {
	
	/**
	 * 查询 一个文件定义下的所有明细记录
	 * @param fileId	文件定义ID
	 * @return
	 * 		List<DpFileDtl>	结果List
	 */
	public List<DpFileDtl> queryList(String fileId);

	/**
	 * 查询 一个文件定义下的所有明细记录
	 * @param fileId	文件定义ID
	 * @return
	 * 		List<Map<String, Object>>	结果Map，名称和长度对
	 */
	public List<Map<String, Object>> queryMap(String fileId);

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（FIELD_ID）
	 * @return
	 * 		DpFileDtl	一条记录的对象
	 */
	public DpFileDtl query(String id);
	
	/**
	 * 插入一条记录
	 * @param dpFileDtl	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpFileDtl dpFileDtl);
	
	/**
	 * 删除一个文件定义下的所有明细记录，根据文件定义ID
	 * @param fileId	文件定义ID
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(String fileId);

	/**
	 * 查询文件定义下的未被使用的明细信息
	 * @param fileId	文件定义ID
	 * @return
	 * 		List<DpFileDtl>	结果List
	 */
	public List<DpFileDtl> queryNotUsedList(String fileId);

}
