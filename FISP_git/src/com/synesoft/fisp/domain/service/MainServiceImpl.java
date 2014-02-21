package com.synesoft.fisp.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.MainSysMapping;
import com.synesoft.fisp.domain.model.MainParam;
import com.synesoft.fisp.domain.repository.MainRepository;

public abstract class MainServiceImpl implements MainService {
	@Autowired
	protected MainRepository mainRepository;

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.service.MainService#getGeneralTODO()
	 */
	@Override
	public List<MainParam> getGeneralTODO() {
		List<MainParam> list = getSysGeneralTODO();
		List<MainParam> bizList = getBizGeneralTODO();
		return mergeList(list, bizList);
	}

	/**
	 * 查询系统一般代办事项
	 * @return
	 */
	protected List<MainParam> getSysGeneralTODO() {
		List<MainParam> list = new ArrayList<MainParam>();
		String[] menus = ContextConst.getUserMenuList();
		for (int i = 0; i < menus.length; i++) {
			if (MainSysMapping.checkFlag(MainSysMapping.isSelectTODO(menus[i]))) {
				list.add(mainRepository.getSysGeneralTODO(MainSysMapping.getSqlId(menus[i])));
			}
		}
		return list;
	}

	protected List<MainParam> mergeList(List<MainParam> list1, List<MainParam> list2) {
		List<MainParam> resultList = new ArrayList<MainParam>();
		if (null != list1 && !list1.isEmpty()) {
			resultList = list1;
		}
		if (null != list2 && !list2.isEmpty()) {
			for (int i = 0; i < list2.size(); i++) {
				resultList.add(list2.get(i));
			}
		}
		return resultList;
	}
	
	/**
	 * 查询业务一般代办事项
	 * @return
	 */
	protected abstract List<MainParam> getBizGeneralTODO();

}
