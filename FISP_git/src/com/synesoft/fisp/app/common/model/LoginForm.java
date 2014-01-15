/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @file 	LoginForm.java
 * @author 	Jon
 * @date 	2013-3-31
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class LoginForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(min = 1, max = 20,message = "{e.cm.1001}")
	private String userId ;
	
	@NotNull
	@Size(min = 3,message = "{e.cm.0001}")
	private String password ;

	private String languageType;
	
	/** 用户切换的机构代码 **/
	private String userlogorg;
	
	
	public String getUserlogorg() {
		return userlogorg;
	}

	public void setUserlogorg(String userlogorg) {
		this.userlogorg = userlogorg;
	}

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
