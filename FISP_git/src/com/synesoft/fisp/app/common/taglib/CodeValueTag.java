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
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.synesoft.fisp.app.common.utils.StringUtil;

/**
 * @file 	CodeValueTag.java
 * @author 	Jon
 * @date 	2013-4-23
 * @description Display code display value by key
 * @tag 	1.0.0
 * 
 */
public class CodeValueTag extends HtmlEscapingAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String DEFAULT_TYPE = "label";
	
	private final String DEFAULT_TYPE_TEXT = "text";
	
	/**
     * Creates TagWriter
     * @return Created TagWriter
     */
    TagWriter createTagWriter() {
        TagWriter tagWriter = new TagWriter(this.pageContext);
        return tagWriter;
    }
    
	@Override
	protected int doStartTagInternal() throws JspException {
		
		TagWriter tagWriter = createTagWriter();
		
		if(StringUtil.isBlank(this.type)){
			type = this.DEFAULT_TYPE;
		}
		
		if(StringUtil.isBlank(this.id)){
			id = this.name;
		}
		
		String writeText = "";
		if(items != null && items.size() > 0){
			if(items.containsKey(key)){
				writeText = items.get(key)==null?"":(String)items.get(key);
			}
		}
		
		if(type.equals(DEFAULT_TYPE)){
			tagWriter.startTag("label");
			tagWriter.appendValue(writeText);
		}else if(type.equals(DEFAULT_TYPE_TEXT)){
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", this.DEFAULT_TYPE_TEXT);
			tagWriter.writeAttribute("id", id);
        	tagWriter.writeAttribute("name", name);
        	tagWriter.writeAttribute("value", writeText);
        	if(!readonly.equals("")){
        		tagWriter.writeAttribute("readonly", readonly);
        	}
        	if(!cssClass.equals("")){
        		tagWriter.writeAttribute("class", cssClass);
        	}
        	if(!cssStyle.equals("")){
        		tagWriter.writeAttribute("style", cssStyle);
        	}
		}
		tagWriter.endTag();
		
		return EVAL_BODY_INCLUDE;
	}
	
	private Map<String,Object> items = new HashMap<String,Object>();
	
	private String key = "";
	
	private String type = "";
	
	private String cssClass = "";
	
	private String cssStyle = "";
	
	private String id = "";
	
	private String name = "";
	
	private String readonly = "";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getItems() {
		return items;
	}

	public void setItems(Map<String, Object> items) {
		this.items = items;
	}


}
