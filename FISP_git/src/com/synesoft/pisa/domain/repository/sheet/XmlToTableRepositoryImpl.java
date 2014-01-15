package com.synesoft.pisa.domain.repository.sheet;

import java.util.List;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synesoft.pisa.app.common.constants.Table;
import com.synesoft.pisa.domain.model.XmlToTable;

/**
 * @author 李峰
 * @date 2013-12-27 下午2:55:58
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class XmlToTableRepositoryImpl implements XmlToTableRepository {

	@Autowired
	private QueryDAO queryDAO;

	public List<XmlToTable> queryXmlToTable(String xmlType){
		return queryDAO.executeForObjectList(Table.XML_TO_TABLE+".selectByXmlType", xmlType);
	}
	
}
