package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.vo.DpTableVO;

/**
 * 表定义Repository
 * @date 2013-11-12
 * @author yyw
 *
 */
public interface DpTableRepository {
	
	/**
	 * 查询 一页记录
	 * @param pageable	分页对象
	 * @param dpTable	封装了条件值的对象
	 * @return
	 * 		Page<DpTable>	分页对象
	 */
	public Page<DpTable> queryList(Pageable pageable, DpTable dpTable);

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（TABLE_ID）
	 * @return
	 * 		DpTable	一条记录的对象
	 */
	public DpTable query(String id);

	/**
	 * 查询一条记录，根据逻辑主键
	 * @param name	主键（TABLE_NAME）
	 * @return
	 * 		DpTable	一条记录的对象
	 */
	public DpTable queryForLogicKey(String name);
	
	/**
	 * 查询符合条件的所有记录
	 * @param dpTable	封装了条件值的对象
	 * @return
	 * 		List<DpTable>	结果List
	 */
	public List<DpTable> queryList(DpTable dpTable);
	
	/**
	 * 插入一条记录
	 * @param DpTable	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpTable dpTable);
	
	/**
	 * 更新一条记录，根据主键，修改用户和修改时间
	 * @param dpTableVo	封装了三个字段值的对象
	 * @return
	 * 		int	成功更新的记录数
	 */
	public int update(DpTableVO dpTableVo);
	
	/**
	 * 删除一条记录，根据主键，修改用户和修改时间
	 * @param dpTable	封装了三个字段值的对象
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(DpTable dpTable);
}
