/**
 * 
 */
package com.synesoft.fisp.domain.component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.synesoft.fisp.app.common.context.MemoryContextHolder;
import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;

/**
 * @author Jon
 *         <p>
 *         Description:
 *         </p>
 * @2012-7-17
 * @version 1.0
 */
@Component
public class PageHandlerImpl<T> implements PageHandler<T> {
	
	@Value("#{app['application.pagesize']}")
	private String pageSize;
	
	public Page<T> getPage(String table, String sqlMapCount,
			String sqlMapList, Object input, Pageable pageable) {
		int startIndex = 0;
		int pageRows = Integer.parseInt(pageSize);
		
		Pageable pager = new PageRequest(pageable.getPageNumber(), pageRows);
		startIndex = pageable.getPageNumber() * pageRows;
		// total records
		int totalCount = queryDAO.executeForObject(table + "." + sqlMapCount,
				input, Integer.class);
		List<T> resultList = new ArrayList<T>();
		if(totalCount > 0){
			// query list
			resultList = queryDAO.executeForObjectList(table + "."
					+ sqlMapList, input, startIndex, pageRows);
		}
		return new PageImpl<T>(resultList, pager, totalCount);
	}
	
	public Page<T> getPage(String table, String sqlMapCount,
			String sqlMapList, Object input,  int pageNum , int steps){
		PageRequest pageReq = new PageRequest(pageNum,steps);
		return getPage(table,sqlMapCount,sqlMapList,input, pageReq);
	}

	@Override
	public Page<T> getPageFromMemory(MemoryResourceType mrt,String code, String name, Pageable pageable) {
		int startIndex = 0;
		int pageRows = Integer.parseInt(pageSize);
		
		Pageable pager = new PageRequest(pageable.getPageNumber(), pageRows);
		startIndex = pageable.getPageNumber() * pageRows;
		
		List<T> resultList = (List<T>) MemoryContextHolder.getMemoryResourceByFilter(mrt, code ,name);
		
		int totalCount = 0;
		if (resultList!=null)
			totalCount = resultList.size();
		
		List<T> rtnList = new ArrayList<T>();
		int j;
		if (startIndex+pageRows <= totalCount)
			j = startIndex+pageRows;
		else
			j = resultList.size();

		for (int i = startIndex; i <= j-1; i++) 
			rtnList.add(resultList.get(i));
		
		return new PageImpl<T>(rtnList, pager, totalCount);
	}
	
	@Override
	public Page<T> getPageFromMemory(MemoryResourceType mrt, String code, String name, int pageNum, int steps) {
		PageRequest pageReq = new PageRequest(pageNum,steps);
		return getPageFromMemory(mrt,code, name, pageReq);
	}
	
	@Resource
	private QueryDAO queryDAO;

}
