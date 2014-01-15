package com.synesoft.fisp.domain.component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;

/**
 * 
 * @author Jon
 *         <p>
 *         Description:
 *         </p>
 * @2012-7-17
 * @version 1.0
 */
public interface PageHandler<T> {

	/**
	 * get page list 
	 * @param table query table constant.name
	 * @param sqlMapCount  query counts constant.name
	 * @param sqlMapList query list constant.name
	 * @param input query condition
	 * @return
	 */
	public Page<T> getPage(String table, String sqlMapCount,
			String sqlMapList, Object input, Pageable pageable);
	
	/**
	 * get page list by default page size {@link getPage}
	 * @param table 
	 * @param sqlMapCount 
	 * @param sqlMapList 
	 * @param input 
	 * @param pageNum page number
	 * @param steps page size
	 * @return
	 */
	public Page<T> getPage(String table, String sqlMapCount,
			String sqlMapList, Object input, int pageNum , int steps);
	
	/**
	 * get page list from memory resource
	 * @param MemoryResourceType
	 * @param code
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Page<T> getPageFromMemory(MemoryResourceType mrt, String code, String name, Pageable pageable);	
	
	/**
	 * get page list from memory resource by default page size {@link getPage}
	 * @param MemoryResourceType
	 * @param pageNum page number
	 * @param steps page size
	 * @return
	 */
	public Page<T> getPageFromMemory(MemoryResourceType mrt, String code, String name, int pageNum , int steps);

}