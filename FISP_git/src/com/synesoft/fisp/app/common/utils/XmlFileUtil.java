/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @file XmlFileUtil.java
 * @author Jon
 * @date 2013-5-13
 * @description TODO
 * @tag 1.0.0
 * 
 */
public class XmlFileUtil {

	/**
	 * create document
	 * 
	 * @return
	 */
	public static Document createDom4J() {
		Document doc = DocumentHelper.createDocument();
		return doc;
	}

	/**
	 * add root element
	 * 
	 * @param elName
	 *            child node name
	 * @param doc
	 *            document
	 * @return
	 */
	public static Element appendChile(String elName, Document doc) {
		Element root = doc.addElement(elName);
		return root;
	}

	/**
	 * add element for parent
	 * 
	 * @param elName
	 *            child node name
	 * @param el
	 *            parent node
	 * @return
	 */
	public static Element appendChile(String elName, Element el) {
		Element sub = el.addElement(elName);
		return sub;
	}

	/**
	 * add sub element and value for parent
	 * 
	 * @param elName
	 *            ele name
	 * @param value
	 *            ele value
	 * @param el
	 *            parent ele
	 */
	public static void appendChile(String elName, String value, Element el) {
		Element sub = el.addElement(elName);
		sub.setText(value);
	}

	/**
	 * add comment for root ele
	 * 
	 * @param explain
	 *            text
	 * @param doc
	 */
	public static void addCommend(String explain, Document doc) {
		doc.addComment(explain);
	}

	/**
	 * add comment for sub ele
	 * 
	 * @param explain
	 * @param element
	 */
	public static void addCommend(String explain, Element element) {
		element.addComment(explain);
	}

	/**
	 * add attribute for element
	 * 
	 * @param attName
	 * @param attValue
	 * @param element
	 */
	public static void addAttribute(String attName, String attValue,
			Element element) {
		element.addAttribute(attName, attValue);
	}

	/**
	 * read all elements
	 * @param filePathName
	 * @return
	 */
	public static List<Element> readAllElements(String filePathName) {
		// read xml document
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(new FileInputStream(new File(filePathName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc.getRootElement().elements();
	}

}
