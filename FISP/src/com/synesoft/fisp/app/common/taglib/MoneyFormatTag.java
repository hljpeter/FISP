/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.taglib;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.synesoft.fisp.app.common.utils.StringUtil;

/**
 * @file 	MoneyFormatTag.java
 * @author 	Jon
 * @date 	2013-4-22
 * @description display money in format style
 * @tag 	1.0.0
 * 
 */
public class MoneyFormatTag  extends HtmlEscapingAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String DEFAULT_FORMAT = "###,###,###,##0.00";
	
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
		
		if(StringUtil.isBlank(this.format)) {
            format = this.DEFAULT_FORMAT;
        }
		
		if(StringUtil.isBlank(this.type)){
			type = this.DEFAULT_TYPE;
		}
		
		if(StringUtil.isBlank(this.id)){
			id = this.name;
		}
		
		String writeText = "";
		if(!StringUtil.isBlank(this.value)){
			this.value=this.value.replace(",", "");
			BigDecimal bd = new BigDecimal(this.value);
			if("true".equals(percent)){
				Double temp = Double.parseDouble(this.value);
				bd = new BigDecimal(temp*100);
				writeText = parseMoney(format,bd)+"%";
        	}else{
        		writeText = parseMoney(format, bd);
        	}
		}
		
        TagWriter tagWriter = createTagWriter();
        if(type.equals(this.DEFAULT_TYPE)){
        	tagWriter.startTag("label");
        	tagWriter.appendValue(writeText);
        } else if(type.equals(this.DEFAULT_TYPE_TEXT)) {
        	tagWriter.startTag("input");
        	tagWriter.writeAttribute("type", this.DEFAULT_TYPE_TEXT);
        	tagWriter.writeAttribute("id", id);
        	tagWriter.writeAttribute("name", name);
        	tagWriter.writeAttribute("value", writeText);
        	if(!maxlength.equals("")){
        		tagWriter.writeAttribute("maxlength", maxlength);
        	}
        	if(!dot.equals("")){
        		tagWriter.writeAttribute("dot", dot);
        	}
        	if(!oncontextmenu.equals("")){
        		tagWriter.writeAttribute("oncontextmenu", oncontextmenu);
        	}
        	if(!onbeforepaste.equals("")){
        		tagWriter.writeAttribute("onbeforepaste", onbeforepaste);
        	}
        	if(!cssClass.equals("")){
        		tagWriter.writeAttribute("class", cssClass);
        	}
        	String textAlign = "right";
        	if(!cssStyle.equals("")){
        		tagWriter.writeAttribute("style", "text-align:"+textAlign+";"+cssStyle);
        	}else{
        		tagWriter.writeAttribute("style", "text-align:"+textAlign+";");
        	}
        	if("true".equals(this.readonly)){
        		tagWriter.writeAttribute("readonly", "true");
    		}else{
    			if("true".equals(percent)){
//    				tagWriter.writeAttribute("readonly", "true");
    			}else{
    				if(dot.equals("true")){
        				tagWriter.writeAttribute("onkeyup", "formatMoneyWithDot(this,'');");
        			}else{
        				tagWriter.writeAttribute("onkeyup", "formatMoney(this);");
        			}
    			}
    		}
        }
        tagWriter.endTag();
		
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * 
	 * @param pattern
	 * @param bd
	 * @return
	 */
	protected String parseMoney(String pattern,BigDecimal bd){
		DecimalFormat df=new DecimalFormat(pattern);
		return df.format(bd);
	}

	private String format = "";
	
	private String value = "";
	
	private String type = "";
	
	private String id = "";
	
	private String name = "";
	
	private String readonly = "";
	
	private String cssClass = "";
	
	private String cssStyle = "";
	
	private String maxlength = "";
	
	private String dot = "";
	
	private String oncontextmenu = "";
	
	private String onbeforepaste = "";
	
	// 100 percent flg
	private String percent = "";

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getOncontextmenu() {
		return oncontextmenu;
	}

	public void setOncontextmenu(String oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	public String getOnbeforepaste() {
		return onbeforepaste;
	}

	public void setOnbeforepaste(String onbeforepaste) {
		this.onbeforepaste = onbeforepaste;
	}

	public String getDot() {
		return dot;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
