package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.vo.DpFileVO;

/**
 * 文件定义Repository
 * @date 2013-11-12
 * @author yyw
 *
 */
public interface DpFileRepository {
	
	/**
	 * 查询 一页记录
	 * @param pageable	分页对象
	 * @param dpFile	封装了条件值的对象
	 * @return
	 * 		Page<DpFile>	分页对象
	 */
	public Page<DpFile> queryList(Pageable pageable, DpFile dpFile);

	/**
	 * 查询一条记录，根据主键
	 * @param id	主键（FILE_ID）
	 * @return
	 * 		DpFile	一条记录的对象
	 */
	public DpFile query(String id);

	/**
	 * 查询一条记录，根据逻辑主键
	 * @param name	逻辑主键（FILE_NAME）
	 * @return
	 * 		DpFile	一条记录的对象
	 */
	public DpFile queryForLogicKey(String name);

	/**
	 * 查询符合条件的所有记录
	 * @param dpFile	封装了条件值的对象
	 * @return
	 * 		List<DpFile>	结果List
	 */
	public List<DpFile> queryList(DpFile dpFile);

	/**
	 * 查询匹配的文件名
	 * @param name	名称开头字符
	 * @return
	 * 		List<String>	结果List
	 */
	public List<String> queryNameList(String name);
	
	/**
	 * 插入一条记录
	 * @param dpFile	封装了值的对象
	 * @return
	 * 		int	成功插入的记录数
	 */
	public int insert(DpFile dpFile);
	
	/**
	 * 更新一条记录，根据主键，修改用户和修改时间
	 * @param dpFileVo	封装了三个字段值的对象
	 * @return
	 * 		int	成功更新的记录数
	 */
	public int update(DpFileVO dpFileVo);
	
	/**
	 * 删除一条记录，根据主键，修改用户和修改时间
	 * @param dpFile	封装了三个字段值的对象
	 * @return
	 * 		int	成功删除的记录数
	 */
	public int delete(DpFile dpFile);
}
