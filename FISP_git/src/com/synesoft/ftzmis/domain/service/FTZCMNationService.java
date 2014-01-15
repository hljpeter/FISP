package com.synesoft.ftzmis.domain.service; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.SysNationInfo;

/** 
 *国别代码查询
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public interface FTZCMNationService {

	Page<SysNationInfo> queryMetaPage(Pageable pageable,SysNationInfo sysNationInfo);

}
 