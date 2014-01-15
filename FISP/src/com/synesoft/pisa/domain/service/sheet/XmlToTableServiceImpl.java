package com.synesoft.pisa.domain.service.sheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synesoft.pisa.domain.model.XmlToTable;
import com.synesoft.pisa.domain.repository.sheet.XmlToTableRepository;

/**
 * @author 李峰
 * @date 2013-12-27 下午2:57:03
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Service("xmlToTableService")
public class XmlToTableServiceImpl implements XmlToTableService{

	@Autowired
	private XmlToTableRepository xmlTotableRepository;

	public List<XmlToTable> queryXmlToTable(String xmlType){
		return xmlTotableRepository.queryXmlToTable(xmlType);
	}
	
	
}
