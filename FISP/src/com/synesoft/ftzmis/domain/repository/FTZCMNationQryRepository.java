package com.synesoft.ftzmis.domain.repository; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.SysNationInfo;

/** 
 *国别代码查询
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public interface FTZCMNationQryRepository {

	Page<SysNationInfo> queryFtzNattion(Pageable pageable,SysNationInfo sysNationInfo);

}
 