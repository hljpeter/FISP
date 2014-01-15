/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.taglib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.synesoft.fisp.app.common.constants.WebServletConst;
import com.synesoft.fisp.app.common.model.SessionForm;
import com.synesoft.fisp.app.common.utils.urlcfg.UrlCfg;

/**
 * @file 	BackLastUrlTag.java
 * @author 	Jon
 * @date 	2013-5-13
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class BackLastUrlTag extends HtmlEscapingAwareTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates TagWriter
     * @return Created TagWriter
     */
    TagWriter createTagWriter() {
        TagWriter tagWriter = new TagWriter(this.pageContext);
        return tagWriter;
    }
    
    protected int doStartTagInternal() throws JspException {
    	TagWriter tagWriter = createTagWriter();
    	tagWriter.startTag("input");
    	tagWriter.writeAttribute("type", "hidden");
    	tagWriter.writeAttribute("name", "referer");
    	tagWriter.writeAttribute("id", "refererUrl");
    	
    	HttpSession session = this.pageContext.getSession();
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	
//    	UrlBackConfiguration ubcfg = WebServletConst.ubcfg;
    	
    	String currentUrl = request.getRequestURI();
    	String contextPath = getRequestContext().getContextPath();
    	currentUrl = currentUrl.replaceAll(contextPath, "");
    	logger.debug("currentUrl-------:"+ currentUrl);
    	
//    	List<String> urlList = new ArrayList<String>();
    	Map<String, String> urlList = new HashMap<String, String>();
		Object urlListObj = session.getAttribute(SessionForm.URL_LIST);
		String refererUrl = request.getHeader("referer");
		String currSuffix = refererUrl.substring(0, refererUrl.indexOf(contextPath))+contextPath;
		String _refererUrl = refererUrl;
//		refererUrl = refererUrl.replaceAll("\\/cm\\/backLastUrl\\?refererUrl\\="+contextPath, "");
		logger.debug("refererUrl-------:"+ refererUrl);
		
		// match this referer is configured in urlback.xml
		String tmpRefererUrl = _refererUrl.substring(refererUrl.indexOf(request.getContextPath()));
//		tmpRefererUrl = tmpRefererUrl.replaceAll("\\/cm\\/backLastUrl\\?refererUrl\\=", "");
		tmpRefererUrl = tmpRefererUrl.replaceAll(contextPath, "");
		logger.debug("tmpRefererUrl----:"+ tmpRefererUrl);
		
		Map<String,String> urlMap = WebServletConst.urlMap;
		Map<String,List<UrlCfg>> urlcfgMap = WebServletConst.urlCfgMap;
		
		String refererUrlId = "";
		String curRefererUrlId = ""; // current url id is config ?
		Set<String> key = urlMap.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
        	refererUrlId = (String) it.next();
            String refererMapUrl = urlMap.get(refererUrlId);
            if(currentUrl.startsWith(refererMapUrl)){
            	curRefererUrlId = refererUrlId;
            }
        }
        
        String retRefererUrl = "";
		List<UrlCfg> urlCfgList = urlcfgMap.get(curRefererUrlId);
		if(urlCfgList != null){
			for(int i = 0 ; urlCfgList != null && i < urlCfgList.size() ; i++){
				UrlCfg ucfg = urlCfgList.get(i);
				if(tmpRefererUrl.startsWith(urlMap.get(ucfg.getId()))){
					// referer url is in this back url cfg
					retRefererUrl = refererUrl;
					break;
				}
			}
			if(retRefererUrl.equals("")){
				// search from url list in session
				logger.debug("search from url list in session.");
				if(urlListObj != null){
					urlList = (Map<String, String>)urlListObj;
					if(urlList.containsKey(curRefererUrlId)){
						retRefererUrl = currSuffix + urlList.get(curRefererUrlId);
					}else{
						logger.error("This url back is not config in backurl.xml!");
						throw new JspException("This url back is not config in backurl.xml!");
					}
				}
			} else {
				// referer url is in this back url cfg
				if(urlListObj != null){
					urlList = (Map<String, String>)urlListObj;
				}
				urlList.put(curRefererUrlId, tmpRefererUrl);
			}
		} else {
			Object refererObj = request.getAttribute("referer");
			if(refererObj != null){
				retRefererUrl = (String)refererObj;
			}
		}
		logger.debug("retRefererUrl:======================" + retRefererUrl);
		tagWriter.writeAttribute("value", retRefererUrl);
		session.setAttribute(SessionForm.URL_LIST, urlList);
    	tagWriter.endTag();
		
		return EVAL_BODY_INCLUDE;
    }
    
}
