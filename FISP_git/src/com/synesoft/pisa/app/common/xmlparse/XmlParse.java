package com.synesoft.pisa.app.common.xmlparse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.synesoft.dataproc.common.util.AppCtxUtil;
import com.synesoft.dataproc.common.util.SpringUtil;
import com.synesoft.pisa.domain.model.XmlToTable;
import com.synesoft.pisa.domain.service.sheet.XmlToTableService;

/**
 * @author 李峰
 * @date 2013-12-25 下午3:01:07
 * @version 1.0
 * @description 
 * @system PISA
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
public class XmlParse {

	@Autowired
	private XmlToTableService xmlToTableService;
	
	public XmlParse(){
		xmlToTableService = (XmlToTableService) AppCtxUtil.getCtx().getBean("xmlToTableService");
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args){
		SpringUtil.getBean("xmlToTableService");
		XmlParse p = new XmlParse();
		try{
			InputStream in = new FileInputStream(new File("E:\\项目\\公式校验\\pisa.101.001.01.xml"));
			p.parseXML(in, "101");
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 解析xml
	 * @param in xml流
	 * @param XmlType 报文类型
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public void parseXML(InputStream in,String XmlType) throws SAXException, IOException, ParserConfigurationException {
		List<XmlToTable> list = xmlToTableService.queryXmlToTable(XmlType);
		/**将每个XmlToTable映射到对面的 id为key值得map中 和 nodeName为key值得map中*/
		Map<String,XmlToTable> mapId = new HashMap<String,XmlToTable>();
		Map<String,XmlToTable> mapNode = new HashMap<String,XmlToTable>();
		for(XmlToTable  xml: list){
			mapId.put("ID"+xml.getXmlNo(), xml);
			mapNode.put(xml.getNoteName().toUpperCase(), xml);
		}
		/**解析xml*/
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(in);
		//获得根元素结点
		NodeList nodeList = doc.getChildNodes();
		Element root = doc.getDocumentElement();
		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();
		parseNode(nodeList);
		//parseElement(root,mapId,mapNode,sb,params);
	}
	
	
	public void parseNode(NodeList nodeList){
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
//			if("#text".equals(node.getNodeName())){
//				continue;
//			}
			//获得结点的类型
			short nodeType = node.getNodeType();
			if(nodeType==Node.ELEMENT_NODE){
				System.out.println("有子节点="+node.getNodeName());
				System.out.println("有子节点="+node.getNodeValue());
				parseNode(node.getChildNodes());
			}else if(nodeType == Node.TEXT_NODE){
				System.out.println("当前节点");
				System.out.println("-b1-"+node.getTextContent()+"-b1-");
				System.out.println("-b2-"+node.getNodeName()+"-b2-");
			}
		}
	}
	
	public void parseElement(Element element,Map<String,XmlToTable> mapId,Map<String,XmlToTable> mapNode,StringBuffer sb,List<String> params)
	{
		String tagName = element.getNodeName();
		XmlToTable xmlToTable = mapNode.get(tagName.toUpperCase());
		NodeList children = element.getChildNodes();
		
		if("1".equals(xmlToTable.getType())){
			System.out.println(sb.toString());
			System.out.println(params.toString());
			sb = new StringBuffer();
			params = new ArrayList<String>();
			//该节点父节点 对应表
			sb.append("insert into ").append(xmlToTable.getTableOrColumn()).append("(");
		}else{
			//该节点为子节点  对应字段
			sb.append(xmlToTable.getTableOrColumn()).append(",");
			//System.out.println("--"+element.getNodeName());
		}
		
		for(int i = 0; i < children.getLength(); i++)
		{
			Node node = children.item(i);
			//获得结点的类型
			short nodeType = node.getNodeType();
			if(nodeType == Node.ELEMENT_NODE)
			{
				//是元素，继续递归
				parseElement((Element)node,mapId,mapNode,sb,params);
			}
			else if(nodeType == Node.TEXT_NODE)
			{
//				params.add(node.getNodeValue());
//				//递归出口
//				
				System.out.println("-n1-"+node.getNodeName()+"-n2-");
				System.out.println("-b1-"+node.getTextContent()+"-e1-");
				System.out.println("-b2-"+node.getNodeValue()+"-e2-");
			}

		}
		
		//System.out.println("</" + tagName + ">");
	}

}
