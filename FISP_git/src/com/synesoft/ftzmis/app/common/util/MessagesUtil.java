/**
 * 
 */
package com.synesoft.ftzmis.app.common.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.model.SessionForm;

/**
 * @author yyw
 *
 */
public class MessagesUtil {

	private Locale local;
	private ResourceBundle message;
	private static MessagesUtil util;
	
	private MessagesUtil() {
		Locale local = new Locale("zh", "CN");
		//local = (Locale) ContextConst.getAttribute(SessionForm.LOCAL);
		message = ResourceBundle.getBundle(CommonConst.I18N_PROPERTIES_NAME, local);
	}
	
	public static MessagesUtil getInstance() {
		if (null == util) {
			util = new MessagesUtil();
		}
		return util;
	}
	
	public String getMessage(String key) {
		return message.getString(key);
	}
}
