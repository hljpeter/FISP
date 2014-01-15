package com.synesoft.fisp.app.common.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.synesoft.fisp.app.common.utils.AuthorityUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;

/**
 * @author zhongHubo
 * @date 2013年12月10日 19:34:25
 * @version 1.0
 * @Description 权限控制：判断用户是否没有当前指定的权限
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class NotAuthorityTag extends HtmlEscapingAwareTag {

	private static final long serialVersionUID = -8145256539724819904L;

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

		// 判断权限
		boolean result = false;
		if (StringUtil.isNotTrimEmpty(path)) {
			result = AuthorityUtil.judgeUrlAuthority(this.path);
		}
		
		if (!result) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	// 权限
	private String path = "";
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
