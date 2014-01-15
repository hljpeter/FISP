/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.utils.urlcfg;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.synesoft.fisp.app.common.utils.XmlFileUtil;

/**
 * @file UrlBackXmlCfg.java
 * @author Jon
 * @date 2013-5-13
 * @description TODO
 * @tag 1.0.0
 * 
 */
public class UrlBackXmlCfg extends XmlFileUtil {

	private static String urlcfgPath = UrlBackXmlCfg.class.getClassLoader()
			.getResource("/").getPath();

	public static UrlBackConfiguration analyzeXmlFile()
			throws DocumentException {

		SAXReader saxReader = new SAXReader();
		Document document = saxReader
				.read(new File(urlcfgPath + "urlback.xml"));
		// get root element
		Element messageElement = document.getRootElement();

		// get root's sub element
		Iterator<Element> oneLevelElementItor = messageElement
				.elementIterator();

		List<UrlCfg> urlList = new ArrayList<UrlCfg>();
		List<UrlCfgs> urlCfgsList = new ArrayList<UrlCfgs>();

		while (oneLevelElementItor.hasNext()) {
			Element e1 = oneLevelElementItor.next();
			Iterator<Element> twoLevelElementItor = e1.elementIterator();
			// get url list
			if (e1.getName().equals("url-list")) {
				while (twoLevelElementItor.hasNext()) {
					Element e2 = twoLevelElementItor.next();
					UrlCfg uc = new UrlCfg();
					uc.setId(e2.attributeValue("id"));
					uc.setValue(e2.attributeValue("value"));
					urlList.add(uc);
				}
			} else if (e1.getName().equals("url-cfgs")) {
				// get url back cfgs
				while (twoLevelElementItor.hasNext()) {
					UrlCfgs urlcfgs = new UrlCfgs();
					Element e2 = twoLevelElementItor.next();
					List<UrlCfg> urlBackCfgList = new ArrayList<UrlCfg>();
					Iterator<Element> threeLevelElementItor = e2
							.elementIterator();
					while (threeLevelElementItor.hasNext()) {
						Element e3 = threeLevelElementItor.next();
						UrlCfg uc = new UrlCfg();
						uc.setId(e3.attributeValue("id"));
						urlBackCfgList.add(uc);
					}
					urlcfgs.setUrlCfgList(urlBackCfgList);
					urlcfgs.setId(e2.attributeValue("id"));
					urlCfgsList.add(urlcfgs);
				}
			}
		}

		UrlBackConfiguration ubcfg = new UrlBackConfiguration();
		ubcfg.setUrlList(urlList);
		ubcfg.setUrlCfgs(urlCfgsList);
		return ubcfg;
	}

}
